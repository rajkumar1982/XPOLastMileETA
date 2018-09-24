'use strict';

angular.module('myApp.view3', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view3', {
    templateUrl: 'view3/view3.html',
    controller: 'View3Ctrl'
  });
}])

.controller('View3Ctrl', ['$scope','$http', function($scope,$http) {
$scope.types = ["Normal Delivery", "Express Delivery"];
$scope.selectedName = null;
$scope.addDelay = function(){
  $scope.consignmentNumber = null;
  $scope.delay = null;
  $scope.did = null;
  $scope.consignmentNumber = document.getElementById("cnsgnnmbr").value;
  $scope.delay = document.getElementById("delay").value;
  $scope.did = document.getElementById("dId").value;
  $scope.url = '/LastMileHack/user/updateTime?driverId='+$scope.did+"&activityId="+$scope.consignmentNumber+"&delay="+$scope.delay;
  //String driverId, int delay, String activityID
  //$scope.requestBody = {activityID : $scope.consignmentNumber, delay:$scope.delay, driverId:$scope.did};
  $http.put($scope.url).success(function(data){
       console.log(data);
  });
  // $scope.typeofdelivery = document.getElementById("typeofdelivery");
  console.log($scope.consignmentNumber+$scope.delay);

}
}]);