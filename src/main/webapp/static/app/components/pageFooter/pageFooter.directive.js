'use strict';

angular.module('myApp.pageFooter',[]).
	directive('pageFooter', pageFooter);

/** @ngInject */
function pageFooter() {
  return {
    restrict: 'E',
    templateUrl: 'components/pageFooter/pageFooter.html'
  };
}
