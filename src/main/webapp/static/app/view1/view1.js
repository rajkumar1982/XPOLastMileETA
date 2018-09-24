'use strict';

angular.module('myApp.view1', ['ngRoute','smart-table'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  });
}])

.controller('View1Ctrl', ['$scope','$timeout','$rootScope','$http',function($scope,$timeout,$rootScope,$http) {
$scope.lastmile = {
               date: "01-04-2017",
               totalDistance: 98,
               unit:"miles",
               
               routes:[
                  {consignment:'CNSG11123',pickupordelivery:'Pickup',poc:'✔ ',intransit:'✔ ',pod:'✔',installation:'NA',warranty:'NA'},
                  {consignment:'CNSG11124',pickupordelivery:'Pickup',poc:'✔ ',intransit:'✗ ',pod:'✗',},
                  {consignment:'CNSG11125',pickupordelivery:'Delivery'},
                  {consignment:'CNSG11126',pickupordelivery:'Delivery'},
                  {consignment:'CNSG11127',pickupordelivery:'Delivery'},
                  {consignment:'CNSG11128',pickupordelivery:'Pickup'}
               ]
            };
  $scope.lastmileroutes = $scope.lastmile.routes;
 // $scope.start = {lat: 34.063344, lng:-117.650888};
 // $scope.end = {lat: 34.063344, lng:-117.650888};
  //  $scope.lastmilesafe.push($scope.lastmile);
  $scope.initMap = function(routeCoordinates){
	  var directionsService = new google.maps.DirectionsService;
	  var directionsDisplay = new google.maps.DirectionsRenderer; 
        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 10,
          //center:       {lat:40.813750, lng: -74.603833},
          mapTypeId: 'roadmap'
        });
        console.log(map)
        directionsDisplay.setMap(map);
       /* var routeCoordinates = [
          {lat: 40.893475,  lng: -74.716875 , markerText: 'Mr. Olive Township,New Jersey'},
         // {lat: 40.817650, lng: -74.712755},
          {lat: 40.798445, lng:-74.707058 },
          {lat: 40.705856, lng: -74.661739, markerText: '<b>Mrs. Jesy Doe <br>Ph</b>:551123456<br>Scheduled:<b>11:15'},
          {lat: 40.654825,  lng: -74.645947},
          //{lat:	40.678783,  lng: -74.604748},
          {lat:	40.692841,  lng: -74.569042},
          //{lat:	40.755807,  lng: -74.525097},
          {lat:	40.770369,  lng: -74.486645},
          {lat:	40.856615,  lng: -74.428301},
          {lat:	40.912934,  lng: -74.535855, markerText: '<b>Mr. John <br>Ph</b>:551123457<br>Scheduled:<b>16:05'},
          {lat: 40.893475,  lng: -74.716875, markerText: 'Mt Olive Township,New Jersey'}
        ];*/
        var routePath = new google.maps.Polyline({
          path: routeCoordinates,
          geodesic: true,
          strokeColor: '#FF0000',
          strokeOpacity: 1.0,
          strokeWeight: 2
        });
        calculateAndDisplayRoute(directionsService, directionsDisplay, routeCoordinates, $scope.start, $scope.end); 
        //routePath.setMap(map);
        
        //---------
//        routeCoordinates.forEach(function(entry) {
//            var contentString = entry.markerText//+'<br>'+entry.lat+','+entry.lng;
//            var infowindow = new google.maps.InfoWindow({
//                content: contentString
//              });
//            var marker = new google.maps.Marker({
//                position: entry,
//                map: map,
//                title: 'Marker one'
//              });
//              marker.addListener('click', function() {
//                infowindow.open(map, marker);
//              });
//              
//              
//        })
    };
   
    $scope.start = {lat: 34.063344, lng:-117.650888};
    $scope.end = {lat: 34.063344, lng:-117.650888};
    var routeCoordinates = [
    	{lat: 34.102464, lng:-118.100771, markerText: 'Mt Olive Township,New Jersey'},
        {lat: 33.826022, lng:-117.566678, markerText: 'Mt Olive Township,New Jersey'},
        {lat: 34.979172, lng:  -118.434419, markerText: 'Mt Olive Township,New Jersey'},
        {lat: 35.835293, lng:   -117.914504, markerText: 'Mt Olive Township,New Jersey'}
    	];
  // $timeout( $scope.initMap(routeCoordinates), 1000);
   function calculateAndDisplayRoute(directionsService, directionsDisplay, routeCoordinates, start, end) {
	   var waypts = [];
	   var checkboxArray = routeCoordinates;
	   for (var i = 0; i < checkboxArray.length; i++) {
	   waypts.push({
	   location: new google.maps.LatLng(checkboxArray[i].lat,checkboxArray[i].lng),
	   stopover: true
	   });
	   }

	   directionsService.route({
	   origin: new google.maps.LatLng(start.lat,start.lng),
	   destination: new google.maps.LatLng(end.lat,end.lng),
	   waypoints: waypts,
	   optimizeWaypoints: true,
	   travelMode: 'DRIVING'
	   }, function(response, status) {
	   if (status === 'OK') {
	   directionsDisplay.setDirections(response);
	   var route = response.routes[0];
	   } else {
	   window.alert('Directions request failed due to ' + status);
	   }
	   });
	   } 

   //------------------
////Listner Functions//
 /*  var destroyListnerPromise = 
	   $rootScope.$on('updated', function(event, newVal) {
 	//  $scope.loading = true;
   	console.log($scope.id+"->Emit received in VIEW1 :"+newVal); 
       //$scope.reload();

	   });
   $scope.$on('$destroy', destroyListnerPromise);*/
 //Listner End

   //Dummy Functions
   $scope.reload = function(){

	      $http.get('/LastMileHack/user/DRIVER7').
	 		  then(function(response) {
	 			 var routeCoordinates = [];
	 		     var tabledata = [];
	 		    var chartdata = []
	 			 var firstPoint;
	 			 // $scope.resp=response.data;
	 		    
	 			 angular.forEach(response.data.Activities, function(value, key) {
		 				/*angular.forEach(value, function(value, key) {
			 				  console.log(key + ': ' + value); 	
			 				});*/
		 				 if(!firstPoint){
		 					firstPoint={};
		 					firstPoint.lat= value.address.lat;
		 					firstPoint.lng= value.address.lng;
		 				 }else
		 				 {
		 				var lat_lon={};
		 				lat_lon.lat= value.address.lat;
		 				lat_lon.lng= value.address.lng;
		 				routeCoordinates.push(lat_lon);
		 				 }
		 				 
		 				var table_row={};
		 				table_row.id=value.id;
		 				table_row.type=value.type;
		 				//table_row.ETA=value.estimatedArrivalTime;
		 				//table_row.revisedETA=value.revisedEstimatedTime;
		 				table_row.isCompleted=value.isCompleted;		 				
		 				tabledata.push(table_row);
		 				
		 				
		 				var chart_row={};
		 				chart_row.consignmentNumber=value.id;
		 				chart_row.type=value.type;
		 				chart_row.etaCommunicated=480+value.estimatedArrivalTime*30;
		 				chart_row.revisedETA=480+value.revisedEstimatedTime*30;
		 				chart_row.isCompleted=value.isCompleted;		 
		 				if(value.isCompleted) chart_row.actualTime=480+value.completionTime*30;		
		 				if((value.completionTime-value.estimatedArrivalTime)>0)chart_row.delay=(value.completionTime-value.estimatedArrivalTime)*30;;
		 				if(chartdata.length>0 && chartdata[chartdata.length-1].isCompleted==true && value.isCompleted==false) chartdata[chartdata.length-1].bulletClass="lastBullet";
		 				//if(chartdata.length>0 && chartdata[chartdata.length-1].isCompleted==true && value.isCompleted==false && chart_row.revisedETA>0) chart_row.etaCommunicated=chart_row.revisedETA;
		 				chartdata.push(chart_row);
		 				  /*	 {
		 				  "delay": actualTime - initialETA,
		 				  "cityName": "New York",
		 				  "actualTime": 40.71,
		 				  "etaCommunicated": 408,
		 				  "consignmentNumber" : "C1"
		 				}*/
	 				});
	 			  //After prosessing response and creating route co ords
	 			  $scope.start = firstPoint;
	 		   	 	$scope.end = firstPoint;
	 		    	//console.log(routeCoordinates)
	 		    	$scope.initMap(routeCoordinates)
	 			  
	 			  //Process and update table var 
				 	  if(tabledata.length>=0) {
			             if(tabledata[0].type=='P'){
			               tabledata[0].addnl='24 Hour Delivery $30'
			             }
			             if(tabledata[0].type=='D'){
			               tabledata[0].addnl='Installation $40,2 Year Warranty $50'
			             }
			             }
			           if(chartdata.length>=1) {
			             if(tabledata[1].type=='P'){
			               tabledata[1].addnl='12 Hour Delivery $30'
			             }
			             if(tabledata[1].type=='D'){
			               tabledata[1].addnl='1 Year Warranty $20'
			             }
			             }
			           if(chartdata.length>=2) {
			             if(tabledata[2].type=='P'){
			               tabledata[2].addnl='12 Hour Delivery $30'
			             }
			             if(tabledata[2].type=='D'){
			               tabledata[2].addnl='Installation $30'
			             }
			             }
	 			  $scope.lastmileroutes=tabledata;
	 			  
	 			  //emit change to areachartcontroler
	 			 $rootScope.$emit('chart-updated', chartdata);
	 		   });
	      
 	  $timeout(function(){
 	     // $scope.reload();
 	    },5000)
   }
   $timeout( $scope.reload(), 1000);
   
 //End of Controller constr/
    
}]);