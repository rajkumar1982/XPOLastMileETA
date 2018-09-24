package com.lastmile.mvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.algorithm.state.StateManager;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.constraint.ConstraintManager;
import com.graphhopper.jsprit.core.problem.job.Delivery;
import com.graphhopper.jsprit.core.problem.job.Pickup;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl.Builder;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import com.lastmile.elastic.util.ElasticUtil;
import com.lastmile.elastic.util.SendEmailUsingGMailSMTP;
import com.lastmile.vo.Activity;
import com.lastmile.vo.Address;
import com.lastmile.vo.DriverDetailVO;
import com.lastmile.vo.RouteDetails;

import net.gpedro.integrations.slack.SlackSendMesage;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	ElasticUtil elasticUtil;
	
	@Autowired
	SendEmailUsingGMailSMTP sendMail;
	
	final int THREADHOLDLIMIT=2;
	
	public RouteDetails findById(String driverId) {
		SearchResponse response = elasticUtil.searchResponse(driverId);
		RouteDetails routeDetails = new RouteDetails();
		List<Activity> activities = new ArrayList<>();
		for (SearchHit hit : response.getHits()) {
			Activity activity = new Activity();
			Address address = new Address();
			activity.setId(hit.getId());
			routeDetails.setDriverID(hit.getSource().get("driverId").toString());
			activity.setType(hit.getSource().get("type").toString());
			activity.setContact(hit.getSource().get("contact").toString());
			routeDetails.setVehicleType(hit.getSource().get("vehicleType").toString());
			address.setLat(Double.parseDouble(hit.getSource().get("latitude").toString()));
			address.setLng(Double.parseDouble(hit.getSource().get("longtitude").toString()));
			address.setAddressName(hit.getSource().get("addressName").toString());
			activity.setIsCompleted("true".equals(hit.getSource().get("isCompleted").toString()));
			activity.setEstimatedArrivalTime(Integer.parseInt(hit.getSource().get("estimatedArrivalTime").toString()));
			activity.setRevisedEstimatedTime(Integer.parseInt(hit.getSource().get("revisedEstimatedTime").toString()));
			activity.setCompletionTime(Integer.parseInt(hit.getSource().get("completionTime").toString()));
			routeDetails.setRouteID(hit.getSource().get("routeID").toString());
			//driverDetailVO.setCarrierid(Integer.parseInt(hit.getSource().get("carrierid").toString()));
			//activity.setLoad(Integer.parseInt(hit.getSource().get("load").toString()));
			activity.setAddress(address);
			activities.add(activity);
			
		}
		routeDetails.setActivities(activities);
		return routeDetails;
	}
	

	public void updateTime(String driverId, int delay, String activityID) {
		SearchResponse response = elasticUtil.searchResponse(driverId);
		ObjectMapper mapper = new ObjectMapper();
		for (SearchHit hit : response.getHits()) {
			DriverDetailVO driverDetailVO = null;
			try {
				driverDetailVO = mapper.readValue(mapper.writeValueAsString(hit.getSource()), DriverDetailVO.class);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!driverDetailVO.getIsCompleted()) {
				if(hit.getId().equals(activityID)) {
					driverDetailVO.setIsCompleted(true);
					if(driverDetailVO.getRevisedEstimatedTime() != 0) {
						driverDetailVO.setCompletionTime(driverDetailVO.getRevisedEstimatedTime());
					} else {
						driverDetailVO.setCompletionTime(driverDetailVO.getEstimatedArrivalTime());
					}
				} else {
					int newDelay=0;
					newDelay=delay/30;
					driverDetailVO.setRevisedEstimatedTime(driverDetailVO.getEstimatedArrivalTime() + newDelay);
					if(newDelay>THREADHOLDLIMIT){
						
						SlackSendMesage slack = new SlackSendMesage(); 
						slack.SendSlackNotification("XPO Logistics ETA Alert-DriverID: " + driverDetailVO.getDriverId() + " - Shipment "+ driverDetailVO.getName() +" is delayed.");
						 /*String szTo = "ranji.abr@gmail.com";//change accordingly
					      String szSubject = "XPO Logistics ETA Alert";
					      String szBody = "The new ETA time for your shipment"+driverDetailVO.getName()+"is"+ driverDetailVO.getRevisedEstimatedTime();
					      sendMail.SendGmail(szTo, szSubject, szBody);*/
					}
				}
				elasticUtil.updateDocument(hit.getId(), driverDetailVO);
			}
		}
	}

	@Override
	public void reRoute(RouteDetails orgRouteDetails, RouteDetails routeDetails) {
		Activity lastActivity = null;
		
		VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
		Builder vehicleBuilder = VehicleImpl.Builder.newInstance("vehicle");
		VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("vehicleType").addCapacityDimension(0, 4);
		VehicleType vehicleType = vehicleTypeBuilder.build();
		vehicleBuilder.setType(vehicleType);
		
		lastActivity = orgRouteDetails.getActivities().get(0);
		for(Activity activity : orgRouteDetails.getActivities()) {
			if(!activity.getIsCompleted()) {
				if("D".equals(activity.getType())){
					Delivery delivery = Delivery.Builder.newInstance(activity.getId()).addSizeDimension(0, 1).
							setLocation(Location.newInstance(activity.getAddress().getLat(),activity.getAddress().getLng())).build();
					vrpBuilder.addJob(delivery);
				} else if("P".equals(activity.getType())) {
					Pickup pickup = Pickup.Builder.newInstance(activity.getId()).addSizeDimension(0, 1).
							setLocation(Location.newInstance(activity.getAddress().getLat(),activity.getAddress().getLng())).build();
					vrpBuilder.addJob(pickup);
				}
			} else {
				lastActivity = activity;
			}
		}
		
		for(Activity activity : routeDetails.getActivities()) {
			if("D".equals(activity.getType())){
				Delivery delivery = Delivery.Builder.newInstance(activity.getId()).addSizeDimension(0, 1).
						setLocation(Location.newInstance(activity.getAddress().getLat(),activity.getAddress().getLng())).build();
				vrpBuilder.addJob(delivery);
			} else if("P".equals(activity.getType())) {
				Pickup pickup = Pickup.Builder.newInstance(activity.getId()).addSizeDimension(0, 1).
						setLocation(Location.newInstance(activity.getAddress().getLat(),activity.getAddress().getLng())).build();
				vrpBuilder.addJob(pickup);
			}
		} 
		
		vehicleBuilder.setStartLocation(Location.newInstance(lastActivity.getAddress().getLat(),lastActivity.getAddress().getLng()));
		vehicleBuilder.setEarliestStart(lastActivity.getEstimatedArrivalTime());
		VehicleImpl vehicle = vehicleBuilder.build();
		vrpBuilder.addVehicle(vehicle);
		
		VehicleRoutingProblem problem = vrpBuilder.build();
		StateManager stateManager = new StateManager(problem);
		ConstraintManager constraintManager = new ConstraintManager(problem, stateManager);
		VehicleRoutingAlgorithm algorithm = Jsprit.Builder.newInstance(problem).setStateAndConstraintManager(stateManager,constraintManager).buildAlgorithm();
		
		Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
		
		VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
		List<VehicleRoute> list = new ArrayList<VehicleRoute>(bestSolution.getRoutes());
		for (VehicleRoute route : list) {
	    	   
	    	   for (TourActivity act : route.getActivities()) {
	    		   String jobId;
	    		   if (act instanceof TourActivity.JobActivity) {
	                   jobId = ((TourActivity.JobActivity) act).getJob().getId();
	               } else {
	                   jobId = "-";
	               }
	    		 System.out.println("jobId="+jobId+"timeArrival="+act.getArrTime()+"Location="+act.getLocation().getName()+"Name="+act.getName());
	    		 for(Activity activity : orgRouteDetails.getActivities()) {
    				if(!activity.getIsCompleted() && activity.getId().equals(jobId)) {
    					elasticUtil.updatePartialDocument(activity.getId(), (int)act.getArrTime());
    				} 
	    	   }
	    		 for(Activity activity : routeDetails.getActivities()) {
	    			 if(activity.getId().equals(jobId)) {
	    				 DriverDetailVO driverVO = new DriverDetailVO();
	    				 driverVO.setDriverId(routeDetails.getDriverID());
	    				 driverVO.setAddressName(activity.getAddress().getAddressName());
	    				 driverVO.setCarrierid(1);
	    				 driverVO.setEstimatedArrivalTime((int)act.getArrTime());
	    				 driverVO.setRevisedEstimatedTime(0);
	    				 driverVO.setIsCompleted(false);
	    				 driverVO.setLatitude(activity.getAddress().getLat());
	    				 driverVO.setLongtitude(activity.getAddress().getLng());
	    				 driverVO.setLoad(1);
	    				 driverVO.setType("P");
	    				 driverVO.setCompletionTime(0);
	    				 driverVO.setName("dummy");
	    				 driverVO.setRouteID(1);
	    				 driverVO.setVehicleType("Truck");
	    				 driverVO.setContact("rajkumar.tito@gmail.com");
	    				 elasticUtil.insert(driverVO, jobId);
	    			 }
	    		 }
	    		 
	       }
		SolutionPrinter.print(problem,bestSolution,SolutionPrinter.Print.VERBOSE);
		
			
		}
	}

}
