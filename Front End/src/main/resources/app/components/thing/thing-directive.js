/**
 * Created by jhas on 2014-09-14.
 */
(function(){

    angular.module('noteApp.directive.thing',[])
        .directive('jhParentThing', function(){
            return{
                restrict: 'E',
                templateUrl: 'thing-parent-directive.html',
                scope:{
                    model: '=thing'
                }
            }
        })
        .directive('jhThingText', function(){
            return {
                restrict: 'A',
                templateUrl: 'thing-text-directive.html',
                scope: {
                    model: '=thing'
                }
            }

        })
})();