/**
 * Created by jhas on 2014-09-23.
 */
(function(){
    var app = angular.module('noteApp.directive.thing.file', ['mgcrea.ngStrap', 'angularFileUpload', 'noteApp.filters']);
        /*
        * As a User I want to see the choosen file object
        * As a User I want to be able to edit the object
        * As a User I want to be able to delete the object
        * */
    app.directive('showFileThing', function showFileThing(){
        return{
            restrict: 'E',
            scope: {
                thing: '=',
                removeFn: '&',
                saveFn: '&',
                showMenu: '@'
            },
            controller: function showFileThingCtrl($scope, $log, $upload){

                var remove = function(){
                    var id = $scope.thing.id;
                    $scope.removeFn({id: id});
                };

                $scope.deletePopover = {
                    url: '/app_components/thing-file/popover/delete-popover.html',
                    data: {
                        title: 'Delete',
                        name: $scope.thing.name,
                        call: function () {
                            remove();
                        }
                    }
                };
                $scope.editAside = {
                    url: '/app_components/thing-file/aside/edit-file-aside.tpl.html',
                    data: {
                        thing: angular.copy($scope.thing),
                        call: function(updatedThing){
                            save(updatedThing);
                        }
                    }
                };

                $scope.onFileSelect = function($files){
                    var file = $files[0];
                    $scope.upload = $upload.upload({
                        url: 'api/thing/'+$scope.thing.id+'/upload',
                        file: file
                    }).progress(function(evt){
                        $log.info('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
                    }).success(function(data){
                        $scope.thing = data;
                    });
                }


                var save = function(updatedThing){
                    $scope.saveFn({thing: updatedThing});
                };
            },
            templateUrl: '/app_components/thing-file/show-file.tpl.html'

        };
    });
    app.directive('editableFileThing', function editableFileThing(){
        return{
            restrict: 'E',
            scope: {
                thing: '='
            },
            controller: function editableFileThingCtrl($scope){

            },
            templateUrl: '/app_components/thing-file/edit-file.tpl.html'
        };
    });
    app.directive('createFileThing', function createFileThing(){
        return{
            restrict: 'E',
            scope: {
                saveFn: '&',
                cancelFn: '&?'
            },
            controller: function createFileThingCtrl($scope){
                var init = function(){
                    $scope.thing = {
                        type: 'FILE'
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
            templateUrl: '/app_components/thing-file/create-file-tpl.html'
        };
    });
})();