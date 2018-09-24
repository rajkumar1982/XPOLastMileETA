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
"id",
"name",
"type",
"contact",
"estimatedArrivalTime",
"revisedEstimatedTime",
"isCompleted",
"address",
"completionTime"
})
public class Activity {

@JsonProperty("id")
private String id;
@JsonProperty("name")
private String name;
@JsonProperty("type")
private String type;
@JsonProperty("contact")
private String contact;
@JsonProperty("estimatedArrivalTime")
private Integer estimatedArrivalTime;
@JsonProperty("revisedEstimatedTime")
private Integer revisedEstimatedTime;
@JsonProperty("isCompleted")
private Boolean isCompleted;
@JsonProperty("address")
private Address address;
@JsonProperty("completionTime")
private Integer completionTime;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("id")
public String getId() {
return id;
}

@JsonProperty("id")
public void setId(String id) {
this.id = id;
}

@JsonProperty("name")
public String getName() {
return name;
}

@JsonProperty("name")
public void setName(String name) {
this.name = name;
}

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

@JsonProperty("contact")
public String getContact() {
return contact;
}

@JsonProperty("contact")
public void setContact(String contact) {
this.contact = contact;
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

@JsonProperty("isCompleted")
public Boolean getIsCompleted() {
return isCompleted;
}

@JsonProperty("isCompleted")
public void setIsCompleted(Boolean isCompleted) {
this.isCompleted = isCompleted;
}

@JsonProperty("address")
public Address getAddress() {
return address;
}

@JsonProperty("address")
public void setAddress(Address address) {
this.address = address;
}

@JsonProperty("completionTime")
public Integer getCompletionTime() {
return completionTime;
}

@JsonProperty("completionTime")
public void setCompletionTime(Integer completionTime) {
this.completionTime = completionTime;
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