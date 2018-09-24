'use strict';

angular.module('myApp.view2', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view2', {
    templateUrl: 'view2/view2.html',
    controller: 'View2Ctrl'
  });
}])

.controller('View2Ctrl', ['$scope','$http', function($scope,$http) {
$scope.types = ["Normal Delivery", "Express Delivery"];
$scope.lat = null;
$scope.lng = null;
$scope.newConsignment = function(){
  
//  $scope.pickupaddr = null;
//  $scope.deliveryaddr = null;
//  $scope.name = document.getElementById("customerName").value;
//  $scope.addressName = document.getElementById("addressName").value;
  $scope.city = document.getElementById("city").value;
  $scope.state = document.getElementById("state").value;
//  $scope.consignmentId = document.getElementById("consignment").value;
  $scope.address = $scope.city+","+$scope.state;
  $http.get('https://maps.googleapis.com/maps/api/geocode/json?address='+$scope.address).success(function(data){
    $scope.lat = data.results[0].geometry.location.lat;
    $scope.lng = data.results[0].geometry.location.lng;
    $scope.lat = Math.round($scope.lat * 1000000) / 1000000;
    $scope.lng = Math.round($scope.lng * 1000000) / 1000000;
    
    //Fire Post
    $scope.activities = [{
      "id": document.getElementById("consignment").value,
      "type": "P",
      "contact": document.getElementById("customerName").value,
      "estimatedArrivalTime": 0,
      "revisedEstimatedTime": 0,
      "isCompleted": false,
      "address": {
        "lat": $scope.lat,
        "lng": $scope.lng,
        "addressName": document.getElementById("addressName").value
      },
      "completionTime": 0
    }];
    $scope.url = '/LastMileHack/user/reroute';
    $scope.requestBody = {driverID:'DRIVER7',routeID:"1",vehicleType: "Truck",Activities:$scope.activities};
    var addConsignment = {
            method: "POST",
            url: $scope.url,
            data: $scope.requestBody,
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            }
        };
        return $http(addConsignment);
  });
  
//  
//  
//  $scope.requestBody = {driverID:$scope.url,deliveryAddress:$scope.deliveryaddr,typeOfDelivery:$scope.typeofdelivery}
//  $http.post($scope.url,$scope.requestBody).success(function(data){
//       console.log(data);
//  });
//  // $scope.typeofdelivery = document.getElementById("typeofdelivery");
//  console.log($scope.name+$scope.pickupaddr+$scope.deliveryaddr+$scope.typeofdelivery);

}
}]);