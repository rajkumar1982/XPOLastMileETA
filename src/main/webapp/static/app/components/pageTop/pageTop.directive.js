'use strict';

angular.module('myApp.pageTop',[]).
	directive('pageTop', pageTop);

/** @ngInject */
function pageTop() {
  return {
    restrict: 'E',
    templateUrl: 'components/pageTop/pageTop.html'
  };
}
