package com.lastmile.mvc.service;

import com.lastmile.vo.RouteDetails;



public interface UserService {
	
	RouteDetails findById(String driverId);
	
	void updateTime(String driverId, int delay, String activityID);

	void reRoute(RouteDetails orgRouteDetails, RouteDetails routeDetails);
	
}
