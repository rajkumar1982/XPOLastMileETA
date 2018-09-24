package com.lastmile.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"driverID",
"routeID",
"vehicleType",
"Activities"
})
public class RouteDetails {

@JsonProperty("driverID")
private String driverID;
@JsonProperty("routeID")
private String routeID;
@JsonProperty("vehicleType")
private String vehicleType;
@JsonProperty("Activities")
private List<Activity> activities = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("driverID")
public String getDriverID() {
return driverID;
}

@JsonProperty("driverID")
public void setDriverID(String driverID) {
this.driverID = driverID;
}

@JsonProperty("routeID")
public String getRouteID() {
return routeID;
}

@JsonProperty("routeID")
public void setRouteID(String routeID) {
this.routeID = routeID;
}

@JsonProperty("vehicleType")
public String getVehicleType() {
return vehicleType;
}

@JsonProperty("vehicleType")
public void setVehicleType(String vehicleType) {
this.vehicleType = vehicleType;
}

@JsonProperty("Activities")
public List<Activity> getActivities() {
return activities;
}

@JsonProperty("Activities")
public void setActivities(List<Activity> activities) {
this.activities = activities;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}


