package com.lastmile.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lastmile.mvc.service.UserService;
import com.lastmile.vo.RouteDetails;
 
@RestController
public class WebRestController {
 
    @Autowired
    UserService userService;  
 
    
    @RequestMapping(value = "/user/updateTime", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateTime(@RequestParam("driverId") String driverId, 
    		@RequestParam("delay") int delay, @RequestParam("activityId") String activityId) {
        
        userService.updateTime(driverId, delay, activityId);
 
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
 
    
    //-------------------Retrieve Single User--------------------------------------------------------
     
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RouteDetails> getUser(@PathVariable("id") String driverId) {
        System.out.println("Fetching User with id " + driverId);
        RouteDetails routeDetails = userService.findById(driverId);
        if (routeDetails == null) {
            System.out.println("User with id " + driverId + " not found");
            return new ResponseEntity<RouteDetails>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<RouteDetails>(routeDetails, HttpStatus.OK);
    }
 
     
     
    //-------------------Create a User--------------------------------------------------------
     
    @RequestMapping(value = "/user/reroute", method = RequestMethod.POST)
    public ResponseEntity<Void> reRouteUser(@RequestBody RouteDetails routeDetails) {
        System.out.println("Creating User " + routeDetails.getDriverID());
        RouteDetails orgRouteDetails = userService.findById(routeDetails.getDriverID()); 
        userService.reRoute(orgRouteDetails, routeDetails);
 
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
 
 
}