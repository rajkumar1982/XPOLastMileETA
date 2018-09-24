package com.lastmile.vo;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"driverId",
"type",
"name",
"contact",
"vehicleType",
"latitude",
"longtitude",
"addressName",
"isCompleted",
"estimatedArrivalTime",
"revisedEstimatedTime",
"routeID",
"carrierid",
"load",
"completionTime"
})
public class DriverDetailVO {

@JsonProperty("driverId")
private String driverId;
@JsonProperty("type")
private String type;
@JsonProperty("name")
private String name;
@JsonProperty("contact")
private String contact;
@JsonProperty("vehicleType")
private String vehicleType;
@JsonProperty("latitude")
private double latitude;
@JsonProperty("longtitude")
private double longtitude;
@JsonProperty("addressName")
private String addressName;
@JsonProperty("isCompleted")
private Boolean isCompleted;
@JsonProperty("estimatedArrivalTime")
private Integer estimatedArrivalTime;
@JsonProperty("revisedEstimatedTime")
private Integer revisedEstimatedTime;
@JsonProperty("routeID")
private Integer routeID;
@JsonProperty("carrierid")
private Integer carrierid;
@JsonProperty("load")
private Integer load;
@JsonProperty("completionTime")
private Integer completionTime;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();
@JsonProperty("completionTime")
public Integer getCompletionTime() {
	return completionTime;
}
@JsonProperty("completionTime")
public void setCompletionTime(Integer completionTime) {
	this.completionTime = completionTime;
}

@JsonProperty("driverId")
public String getDriverId() {
return driverId;
}

@JsonProperty("driverId")
public void setDriverId(String driverId) {
this.driverId = driverId;
}

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("contact")
public String getContact() {
return contact;
}

@JsonProperty("contact")
public void setContact(String contact) {
this.contact = contact;
}

@JsonProperty("vehicleType")
public String getVehicleType() {
return vehicleType;
}

@JsonProperty("vehicleType")
public void setVehicleType(String vehicleType) {
this.vehicleType = vehicleType;
}

@JsonProperty("latitude")
public double getLatitude() {
return latitude;
}

@JsonProperty("latitude")
public void setLatitude(double latitude) {
this.latitude = latitude;
}

@JsonProperty("longtitude")
public double getLongtitude() {
return longtitude;
}

@JsonProperty("longtitude")
public void setLongtitude(double longtitude) {
this.longtitude = longtitude;
}

@JsonProperty("addressName")
public String getAddressName() {
return addressName;
}

@JsonProperty("addressName")
public void setAddressName(String addressName) {
this.addressName = addressName;
}

@JsonProperty("isCompleted")
public Boolean getIsCompleted() {
return isCompleted;
}

@JsonProperty("isCompleted")
public void setIsCompleted(Boolean isCompleted) {
this.isCompleted = isCompleted;
}

@JsonProperty("estimatedArrivalTime")
public Integer getEstimatedArrivalTime() {
return estimatedArrivalTime;
}

@JsonProperty("estimatedArrivalTime")
public void setEstimatedArrivalTime(Integer estimatedArrivalTime) {
this.estimatedArrivalTime = estimatedArrivalTime;
}

@JsonProperty("revisedEstimatedTime")
public Integer getRevisedEstimatedTime() {
return revisedEstimatedTime;
}

@JsonProperty("revisedEstimatedTime")
public void setRevisedEstimatedTime(Integer revisedEstimatedTime) {
this.revisedEstimatedTime = revisedEstimatedTime;
}

@JsonProperty("routeID")
public Integer getRouteID() {
return routeID;
}

@JsonProperty("routeID")
public void setRouteID(Integer routeID) {
this.routeID = routeID;
}

@JsonProperty("carrierid")
public Integer getCarrierid() {
return carrierid;
}

@JsonProperty("carrierid")
public void setCarrierid(Integer carrierid) {
this.carrierid = carrierid;
}

@JsonProperty("load")
public Integer getLoad() {
return load;
}

@JsonProperty("load")
public void setLoad(Integer load) {
this.load = load;
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