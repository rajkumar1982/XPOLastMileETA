/**
 * @author v.lugovsky
 * created on 16.12.2015
 */
(function () {
  'use strict';

  angular.module('myApp.view1')
      .controller('AreaChartCtrl',AreaChartCtrl);

  /** @ngInject */
  function AreaChartCtrl($scope, $element,$rootScope,$timeout) {
	  var layoutPaths ={}
    var layoutColors = 
    		{
    		defaultText:'black',
    		info:'green',
    		warning:'orange',
    		danger:'red',
    		border:'blue'
    		}
    var id = $element[0].getAttribute('id');
	  
	  var chartData = [ {
		  "time": "2012-01-01",
		  "delay": 227,
		  "cityName": "New York",
		  "cityName2": "New York",
		  "bulletSize": 25,
		  "actualTime": 40.71,
		  "etaCommunicated": 408,
		  "consignmentNumber" : "C1"
		}, {
		  "time": "2012-01-02",
		  "delay": 371,
		  "cityName": "Washington",
		  "bulletSize": 14,
		  "actualTime": 38.89,
		  "etaCommunicated": 482,
		  "consignmentNumber" : "C2"
		}, {
		  "time": "2012-01-03",
		  "delay": 433,
		  "cityName": "Wilmington",
		  "bulletSize": 6,
		  "actualTime": 34.22,
		  "etaCommunicated": 562,
		  "consignmentNumber" : "C3"
		}, {
		  "time": "2012-01-04",
		  "delay": 345,
		  "cityName": "Jacksonville",
		  "bulletSize": 7,
		  "actualTime": 30.35,
		  "etaCommunicated": 379,
		  "consignmentNumber" : "C3"
		}, {
		  "time": "2012-01-05",
		  "delay": 480,
		  "cityName": "Miami",
		  "cityName2": "Miami",
		  "bulletSize": 10,
		  "actualTime": 25.83,
		  "etaCommunicated": 501,
		  "consignmentNumber" : "C4"
		}, {
		  "time": "2012-01-06",
		  "delay": 386,
		  "cityName": "Tallahassee",
		  "bulletSize": 7,
		  "actualTime": 30.46,
		  "etaCommunicated": 443,
		  "consignmentNumber" : "C5"
		}, {
		  "time": "2012-01-07",
		  "delay": 348,
		  "cityName": "New Orleans",
		  "bulletSize": 10,
		  "actualTime": 29.94,
		  "etaCommunicated": 405,
		  "consignmentNumber" : "C6"
		}, {
		  "time": "2012-01-08",
		  "delay": 238,
		  "cityName": "Houston",
		  "cityName2": "Houston",
		  "bulletSize": 16,
		  "actualTime": 29.76,
		  "etaCommunicated": 309,
		  "consignmentNumber" : "C7"
		}, {
		  "time": "2012-01-09",
		  "delay": 218,
		  "cityName": "Dalas",
		  "bulletSize": 17,
		  "actualTime": 32.8,
		  "etaCommunicated": 287,
		  "consignmentNumber" : "C8"
		}, {
		  "time": "2012-01-10",
		  "delay": 349,
		  "cityName": "Oklahoma City",
		  "bulletSize": 11,
		  "actualTime": 35.49,
		  "etaCommunicated": 485,
		  "consignmentNumber" : "C9"
		}, {
		  "time": "2012-01-11",
		  "delay": 603,
		  "cityName": "Kansas City",
		  "cityName2": "Kansas City",
		  "bulletSize": 10,
		  "actualTime": 39.1,
		  "etaCommunicated": 890,
		  "bulletClass": "lastBullet",
		  "consignmentNumber" : "C10"
		}, {
		  "time": "2012-01-12",
		  "delay": 534,
		  "cityName": "Denver",
		  "cityName2": "Denver",
		  "bulletSize": 18,
		 // "actualTime": 39.74,
		  "etaCommunicated": 810,
		  "consignmentNumber" : "C11"
		}, {
		  "time": "2012-01-13",
		  "cityName": "Salt Lake City",
		  "bulletSize": 12,
		  "delay": 425,
		  "etaCommunicated": 670,
		 // "actualTime": 40.75,
		  "alpha": 0.4,
		  "consignmentNumber" : "C12"
		}, {
		  "time": "2012-01-14",
		 // "actualTime": 36.1,
		  "etaCommunicated": 470,
		  "cityName": "Las Vegas",
		  "cityName2": "Las Vegas",
		  "consignmentNumber" : "C13"
		 
		}, {
		  "time": "2012-01-15",
		  "consignmentNumber" : "C14"
			  
		}, {
		  "time": "2012-01-16",
		  "consignmentNumber" : "C15"
		}, {
		  "time": "2012-01-17",
		  "consignmentNumber" : "C16"
		}, {
		  "time": "2012-01-18",
		  "consignmentNumber" : "C17"
		}, {
		  "time": "2012-01-19",
		  "consignmentNumber" : "C18"
		} ];
	  $scope.areaChart = AmCharts.makeChart(id, {
    	  "type": "serial",
    	"theme": "light",

    	  "dataDateFormat": "YYYY-MM-DD",
    	  "dataProvider": chartData,

    	  "addClassNames": true,
    	  "startDuration": 1,
    	  "color": "#FFFFFF",
    	  "marginLeft": 0,

    	  "categoryField": "consignmentNumber",
    	  /*"categoryAxis": {
    	    "parseDates": true,
    	    "minPeriod": "DD",
    	    "autoGridCount": false,
    	    "gridCount": 50,
    	    "gridAlpha": 0.1,
    	    "gridColor": "#FFFFFF",
    	    "axisColor": "#555555",
    	    "dateFormats": [ {
    	      "period": 'DD',
    	      "format": 'DD'
    	    }, {
    	      "period": 'WW',
    	      "format": 'MMM DD'
    	    }, {
    	      "period": 'MM',
    	      "format": 'MMM'
    	    }, {
    	      "period": 'YYYY',
    	      "format": 'YYYY'
    	    } ]
    	  },*/

    	  "valueAxes": [ {
    	    "id": "a1",
    	    "title": "Delay",
    	    "gridAlpha": 0,
    	    "axisAlpha": 0
    	  }, {
    	    "id": "a2",
    	    //"position": "right",
       	    "labelsEnabled": false,
       	    "gridAlpha": 0,
    	    "axisAlpha": 0,
    	    "inside": true,
    	    "duration": "mm",
    	    "durationUnits": {
    	      "DD": "d. ",
    	      "hh": "h ",
    	      "mm": "min",
    	      "ss": ""
    	    }
    	  }, {
    	    "id": "a3",
    	    "title": "Delivery Time",
    	    "position": "right",
    	    "gridAlpha": 0,
    	    "axisAlpha": 0,
    	    "inside": true,
    	    "duration": "mm",
    	    "durationUnits": {
    	      "DD": "d. ",
    	      "hh": "h ",
    	      "mm": "min",
    	      "ss": ""
    	    }
    	  } ],
    	  "graphs": [ {
    		    "id": "g1",
    		    "valueField": "delay",
    		    "title": "Delay",
    		    "type": "column",
    		    "fillAlphas": 0.9,
    		    "valueAxis": "a1",
    		    "balloonText": "Delayed by [[value]] minutes",
    		    "legendValueText": "[[value]] mi",
    		    "legendPeriodValueText": "total: [[value.sum]] mi",
    		    "lineColor": "#fc544e",
    		    "alphaField": "alpha"
    		  }, {
    	    "id": "g2",
    	    "valueField": "actualTime",
    	    "classNameField": "bulletClass",
    	    "title": "Actual Delivery/Pickup Time",
    	    "type": "line",
    	    "valueAxis": "a2",
    	    "lineColor": "#786c56",
    	    "lineThickness": 1,
    	    "legendValueText": "[[value]]/[[description]]",
    	    "descriptionField": "cityName",
    	    "bullet": "round",
    	    "bulletSizeField": "bulletSize",
    	    "bulletBorderColor": "#786c56",
    	    "bulletBorderAlpha": 1,
    	    "bulletBorderThickness": 2,
    	    "bulletColor": "#b6f442",
    	    "labelText": "[[cityName]]",
    	    "labelPosition": "right",
    	    "balloonText": "Completed at:[[value]]",
    	    "showBalloon": true,
    	    "animationPlayed": true
    	  }, {
    	    "id": "g3",
    	    "title": "ETA/Scheduled Time",
    	    "valueField": "etaCommunicated",
    	    "type": "line",
    	    "valueAxis": "a3",
    	    "lineColor": "#ff5755",
    	    "balloonText": "[[value]]",
    	    "lineThickness": 1,
    	    "legendValueText": "[[value]]",
    	    "balloonText": "Scheduled at:[[value]]",
    	    "bullet": "square",
    	    "bulletBorderColor": "#ff5755",
    	    "bulletBorderThickness": 1,
    	    "bulletBorderAlpha": 1,
    	    "dashLengthField": "dashLength",
    	    "animationPlayed": true
    	  } ],

    	  "chartCursor": {
    	    //"zoomable": false,
    	    "categoryBalloonDateFormat": "DD",
    	    "cursorAlpha": 0,
    	    "valueBalloonsEnabled": false
    	  },
    	  "legend": {
    	    "bulletType": "round",
    	    "equalWidths": false,
    	    "valueWidth": 120,
    	    "useGraphSettings": true,
    	    "color": "#FCFCFC"
    	  }
    	} );

  /*  areaChart.addListener('dataUpdated', zoomAreaChart);

    function zoomAreaChart() {
      areaChart.zoomToDates(new Date(2012, 0, 3), new Date(2012, 0, 11));
    }*/
  
  var destroyListnerPromise =
	  $rootScope.$on('chart-updated', function(event, newData) {
		//  $scope.loading = true;
	  	console.log($scope.id+"->Emit received in chart:"+newData);
	  	//iterate Convert newVAl to chartData Format
	  	//var chartData=[].....
	  /*	 {
			  "delay": actualTime - initialETA,
			  "cityName": "New York",
			  "actualTime": 40.71,
			  "etaCommunicated": 408,
			  "consignmentNumber" : "C1"
			}*/
	     // $scope.reload();
	      $scope.areaChart.dataProvider = newData;
		  $scope.areaChart.validateData();	
	  }); 
  $scope.$on('$destroy', destroyListnerPromise);
  //Listner End

  //Dummy Functions
  $scope.reload = function(){
	  var chartData = [ {
		  "time": "2012-01-01",
		  "delay": 227,
		  "cityName": "New York",
		  "cityName2": "New York",
		  "bulletSize": 25,
		  "actualTime": 40.71,
		  "etaCommunicated": 408,
		  "consignmentNumber" : "C1"
		}, {
		  "time": "2012-01-02",
		  "delay": 371,
		  "cityName": "Washington",
		  "bulletSize": 14,
		  "actualTime": 38.89,
		  "etaCommunicated": 482,
		  "consignmentNumber" : "C2"
		}, {
		  "time": "2012-01-03",
		  "delay": 433,
		  "cityName": "Wilmington",
		  "bulletSize": 6,
		  "actualTime": 34.22,
		  "etaCommunicated": 562,
		  "consignmentNumber" : "C3"
		}, {
		  "time": "2012-01-04",
		  "delay": 345,
		  "cityName": "Jacksonville",
		  "bulletSize": 7,
		  "actualTime": 30.35,
		  "etaCommunicated": 379,
		  "consignmentNumber" : "C3"
		}, {
		  "time": "2012-01-05",
		  "delay": 480,
		  "cityName": "Miami",
		  "cityName2": "Miami",
		  "bulletSize": 10,
		  "actualTime": 25.83,
		  "etaCommunicated": 501,
		  "consignmentNumber" : "C4"
		}, {
		  "time": "2012-01-06",
		  "delay": 386,
		  "cityName": "Tallahassee",
		  "bulletSize": 7,
		  "actualTime": 30.46,
		  "etaCommunicated": 443,
		  "consignmentNumber" : "C5"
		}, {
		  "time": "2012-01-07",
		  "delay": 348,
		  "cityName": "New Orleans",
		  "bulletSize": 10,
		  "actualTime": 29.94,
		  "etaCommunicated": 405,
		  "consignmentNumber" : "C6"
		}, {
		  "time": "2012-01-08",
		  "delay": 238,
		  "cityName": "Houston",
		  "cityName2": "Houston",
		  "bulletSize": 16,
		  "actualTime": 29.76,
		  "etaCommunicated": 309,
		  "consignmentNumber" : "C7"
		}, {
		  "time": "2012-01-09",
		  "delay": 218,
		  "cityName": "Dalas",
		  "bulletSize": 17,
		  "actualTime": 32.8,
		  "etaCommunicated": 287,
		  "consignmentNumber" : "C8"
		}, {
		  "time": "2012-01-10",
		  "delay": 349,
		  "cityName": "Oklahoma City",
		  "bulletSize": 11,
		  "actualTime": 35.49,
		  "etaCommunicated": 485,
		  "consignmentNumber" : "C9"
		}, {
		  "time": "2012-01-11",
		  "delay": 603,
		  "cityName": "Kansas City",
		  "cityName2": "Kansas City",
		  "bulletSize": 10,
		  "actualTime": 39.1,
		  "etaCommunicated": 890,
		  "consignmentNumber" : "C10"
		}, {
		  "time": "2012-01-12",
		  "delay": 534,
		  "cityName": "Denver",
		  "cityName2": "Denver",
		  "bulletSize": 18,
		  "actualTime": 39.74,
		  "bulletClass": "lastBullet",
		  "etaCommunicated": 810,
		  "consignmentNumber" : "C11"
		}, {
		  "time": "2012-01-13",
		  "cityName": "Salt Lake City",
		  "bulletSize": 12,
		  "delay": 425,
		  "etaCommunicated": 670,
		 // "actualTime": 40.75,
		  "alpha": 0.4,
		  "consignmentNumber" : "C12"
		}, {
		  "time": "2012-01-14",
		 // "actualTime": 36.1,
		  "etaCommunicated": 470,
		  "cityName": "Las Vegas",
		  "cityName2": "Las Vegas",
		  "consignmentNumber" : "C13"
		 
		}, {
		  "time": "2012-01-15",
		  "consignmentNumber" : "C14"
			  
		}, {
		  "time": "2012-01-16",
		  "consignmentNumber" : "C15"
		}, {
		  "time": "2012-01-17",
		  "consignmentNumber" : "C16"
		}, {
		  "time": "2012-01-18",
		  "consignmentNumber" : "C17"
		}, {
		  "time": "2012-01-19",
		  "consignmentNumber" : "C18"
		} ];
	  $scope.areaChart.dataProvider = chartData;
	  $scope.areaChart.validateData();	 
  }
//End of Controller constr/
 /* $timeout(function(){
	  console.log('reloading..')
      $scope.reload();
    },5000)*/
    

  }
})();
