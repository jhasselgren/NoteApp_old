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

                //$scope.uploadInProgress = false;

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

                $scope.historyPopover = {
                    url: 'app_components/thing-file/popover/file-history-popover.html',
                    data: {
                        title: 'File History',
                        fileArchive: $scope.thing.fileArchive
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

                    $scope.progress = 0;
                    $scope.progressstyle = {'width': '0%'};
                    var file = $files[0];


                    $scope.upload = uploadFile($upload, $scope, file)
                        .progress(function(evt){
                            $scope.uploadinprogress = true;

                            $log.info('percent: ' + parseInt(100.0 * evt.loaded / evt.total));

                            $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
                            $scope.progressstyle = {'width': $scope.progress+'%'};


                        }).success(function(data){
                            $scope.thing = data;
                            $scope.historyPopover.data.fileArchive = $scope.thing.fileArchive;
                            $scope.uploadinprogress = false;
                        }).error(function(){
                            $scope.uploadinprogress = false;
                        });
                };


                var save = function(updatedThing){
                    $scope.saveFn({thing: updatedThing});
                };
            },
            templateUrl: '/app_components/thing-file/show-file.tpl.html'

        };
    });

    var uploadFile = function(upload, scope, file){
        var promise = upload.upload({
            url: 'api/thing/'+scope.thing.id+'/upload',
            data: {fileType: file.type},
            file: file
        });

        return promise;
    };


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
                refreshFn: '&',
                cancelFn: '&?'
            },
            controller: function createFileThingCtrl($scope, $upload, $log){
                var init = function(){
                    $scope.thing = {
                        type: 'FILE'
                    };
                };

                $scope.save = function(){
                    var promise = $scope.saveFn({thing: $scope.thing});

                    promise.then(function(response){
                        $scope.thing = response.data.thing;
                        $scope.upload = uploadFile($upload, $scope, file)
                            .progress(function(evt){
                                $scope.uploadinprogress = true;

                                $log.info('percent: ' + parseInt(100.0 * evt.loaded / evt.total));

                                $scope.progress = parseInt(100.0 * evt.loaded / evt.total);
                                $scope.progressstyle = {'width': $scope.progress+'%'};

                            }).success(function(data){
                                $scope.uploadinprogress = false;
                                $scope.refreshFn();
                            }).error(function(){
                                $scope.uploadinprogress = false;
                            });
                    }, null);

                };

                $scope.cancel =function(){
                    init();
                    $scope.cancelFn();
                };

                var file;

                $scope.onFileSelect = function($files){

                    $scope.progress = 0;
                    $scope.progressstyle = {'width': '0%'};
                    file = $files[0];

                    $log.info(file);

                    $scope.thing.name = file.name.replace(/\.[^/.]+$/, "");

                    //$scope.upload = uploadFile($upload, $scope, $log, file);
                };

                init();
            },
            templateUrl: '/app_components/thing-file/create-file-tpl.html'
        };
    });
})();