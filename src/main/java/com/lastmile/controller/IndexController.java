package com.lastmile.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lastmile.mvc.service.UserService;
import com.lastmile.vo.Activity;
import com.lastmile.vo.Address;
import com.lastmile.vo.RouteDetails;



@Controller
@RequestMapping("/")
public class IndexController {
	 
	@Autowired
	private UserService service;
	
	@RequestMapping(method = RequestMethod.GET)
    public String getIndexPage() {
	
/*		RouteDetails routeDetails = new RouteDetails();
		routeDetails.setDriverID("ABC");
		Address address = new Address();
		address.setLat(5);
		address.setLng(6);
		Activity activity = new Activity();
		activity.setId("xpo123123");
		activity.setType("P");
		activity.setAddress(address);
		List<Activity> list = new ArrayList<>();
		list.add(activity);
		routeDetails.setActivities(list);
		service.reRoute(service.findById("ABC"), routeDetails);*/
        return "index";
    }
		
}