/**
 * Created by jhas on 2014-09-14.
 */
(function(){

    angular.module('noteApp.directive.thing',[])
        .directive('jhParentThing', function(){
            return{
                restrict: 'E',
                templateUrl: 'components/thing/thing-parent-directive.html',
                require: '^ngModel'
            }
        })
        .directive('jhThingText', function(){
            return {
                restrict: 'E',
                templateUrl: 'components/thing/thing-text-directive.html',
                require: '^ngModel',
                controller: function($scope){
                	this.data = $scope;
                }
            }
        })
})();