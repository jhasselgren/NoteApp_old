/**
 * Created by jhas on 2014-10-08.
 */
(function(){
    var app = angular.module('noteApp.directive.thing.link', ['mgcrea.ngStrap', 'noteApp.filters']);

    app.directive('showLinkThing', function showLinkThing(){
        return{
            restrict: 'E',
            scope: {
                thing: '=',
                removeFn: '&',
                saveFn: '&'
            },
            controller: function showLinkThigCtrl($scope){
                var remove = function(){
                    var id = $scope.thing.id;
                    $scope.removeFn({id: id});
                };

                $scope.deletePopover = {
                    url: '/app_components/thing-link/popover/delete-popover.html',
                    data: {
                        title: 'Delete',
                        name: $scope.thing.name,
                        call: function () {
                            remove();
                        }
                    }
                };
                $scope.editAside = {
                    url: '/app_components/thing-link/aside/edit-link-aside.tpl.html',
                    data: {
                        thing: angular.copy($scope.thing),
                        call: function(updatedThing){
                            save(updatedThing);
                        }
                    }
                };

                var save = function(updatedThing){
                    $scope.saveFn({thing: updatedThing});
                };
            },
            templateUrl: '/app_components/thing-link/show-link.tpl.html'
        }
    });

    app.directive('editableLinkThing', function editableLinkThing(){
        return{
            restrict: 'E',
            scope: {
                thing: '='
            },
            controller: function editableLinkThingCtrl($scope){

            },
            templateUrl: '/app_components/thing-link/edit-link.tpl.html'
        };
    });

    app.directive('createLinkThing', function createLinkThing(){
        return{
            restrict: 'E',
            scope: {
                saveFn: '&',
                cancelFn: '&?'
            },
            controller: function createLinkThingCtrl($scope){
                var init = function(){
                    $scope.thing = {
                        type: 'LINK'
                    };
                };

                $scope.save = function(){
                    $scope.saveFn({thing: $scope.thing});
                };

                $scope.cancel =function(){
                    init();
                    $scope.cancelFn();
                };

                init();
            },
            templateUrl: '/app_components/thing-link/create-link-tpl.html'
        };
    });

})();