/**
 * Created by jhas on 2014-09-23.
 */
(function(){
    var app = angular.module('noteApp.directive.thing', ['mgcrea.ngStrap', 'noteApp.filters'])
        /*
        * As a User I want to see the choosen text object
        * As a User I want to be able to edit the object
        * As a User I want to be able to delete the object
        * */
        .directive('showTextThing', function(){
            return{
                restrict: 'E',
                scope: {
                    thing: '=',
                    removeFn: '&',
                    saveFn: '&',
                    showMenu: '@'
                },
                controller: function($scope, $log){

                    var remove = function(){
                        var id = $scope.thing.id;
                        $scope.removeFn({id: id});
                    }

                    $scope.deletePopover = {
                        url: '/app_components/thing-text/popover/delete-popover.html',
                        data: {
                            title: 'Delete',
                            name: $scope.thing.name,
                            call: function () {
                                remove();
                            }
                        }
                    };
                    $scope.editAside = {
                        url: '/app_components/thing-text/aside/edit-text-aside.tpl.html',
                        data: {
                            thing: angular.copy($scope.thing),
                            call: function(updatedThing){
                                save(updatedThing);
                            }
                        }
                    }

                    var save = function(updatedThing){
                        $scope.saveFn({thing: updatedThing});
                    };
                },
                templateUrl: '/app_components/thing-text/show-text.tpl.html'

            };
        })
        .directive('editableTextThing', function(){
            return{
                restrict: 'E',
                scope: {
                    thing: '='
                },
                controller: function($scope){

                },
                templateUrl: '/app_components/thing-text/edit-text.tpl.html'
            }
        })
        .directive('createTextThing', function(){
            return{
                restrict: 'E',
                scope: {
                    saveFn: '&',
                    cancelFn: '&?'
                },
                controller: function($scope){
                    var init = function(){
                        $scope.thing = {
                            type: 'TEXT'
                        };
                    };

                    $scope.save = function(){
                        $scope.saveFn({thing: $scope.thing});
                    };

                    $scope.cancel =function(){
                        init();
                        $scope.cancelFn();
                    }

                    init();
                },
                templateUrl: '/app_components/thing-text/create-text-tpl.html'
            }
        })
})();