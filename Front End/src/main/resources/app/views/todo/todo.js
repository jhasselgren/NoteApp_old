(function(){
    var app = angular.module('noteApp.TodoController', ['ngRoute', 'mgcrea.ngStrap'])

        .config(function($routeProvider){
            $routeProvider
            .when('/todo', {
                templateUrl: 'views/todo/todo-list.html',
                controller: 'TodoListController',
                controllerAs: 'viewCtrl'
            })
            .when('/todo/:thingId', {
            	templateUrl: 'views/todo/todo-show.html',
            	controller: 'TodoShowController',
            });
        })
        .config(function($asideProvider) {
		  angular.extend($asideProvider.defaults, {
		    container: 'body',
		    html: true,
		    backdrop: 'static'
		  });
		})
        .controller('TodoListController', function($location){
            this.openThing = function(id){
            	$location.path('/todo/'+id);
            };
        })
        .controller('TodoShowController', function($scope, $log, $location, dataService, $routeParams, $aside){

                var isCreateThingMode = false;
                var thingId;

                var init = function(){
                    thingId = $routeParams.thingId;
                    dataService.registerObserverCallback(updateCurrentThing);
                    $scope.reload();
                };


                $scope.reload = function(){
                    dataService.setCurrentThing(thingId);
                };

                $scope.showCreateThing = function(){
                    isCreateThingMode = true;
                };

                $scope.isCreateThingMode = function(){
                    return isCreateThingMode;
                };

                $scope.cancelCreateThing = function(){
                    isCreateThingMode = false;
                };
        	
                $scope.save = function(thing){
                    return dataService.saveThing(thing);
                };



                var updateCurrentThing = function(){
                    $scope.currentTing = dataService.getCurrentThing();
                };

                $scope.deleteThing = function(id){
                    dataService.deleteThing(id);
                };

                $scope.create =function(thing){
                    var promise = dataService.addChild(thing);

                    promise.then(function(data){
                        isCreateThingMode = false;
                    }, function(error){
                        $log.error(error);
                    });

                    return promise;
                };

                $scope.aside =  function(){
                    return{
                        thing: angular.copy($scope.currentTing),
                        save: function(thing){
                            $scope.save(thing);
                        }
                    };
                };

                if(typeof $scope.currentTing != "undefined"){
                    $scope.progress = $scope.currentTing.status + '%';
                }

                $scope.$watch('currentTing', function(newVal, oldVal){
                    if(typeof $scope.currentTing != "undefined"){
                        $scope.progress = $scope.currentTing.status + '%';
                    }

                });

                init();

        })
        .controller('ThingDirectiveController', function($scope){
            $scope.edit = function(thing){
                $scope.save({thing: thing});
            };

            $scope.aside =  {
                thing: angular.copy($scope.thing),
                save: function(thing){
                    $scope.edit(thing);
                }
            };

            $scope.deletePopover = {
                title: 'Delete',
                name: $scope.thing.name,
                call: function(){
                    $scope.remove();
                }
            };

            $scope.remove = function(){
                var id = $scope.thing.id;
                $scope.removeFn({id: id});
            };
        })
        .directive('listTodo', function(dataService){
            return {
                restrict: 'E',
                scope: {
                  jhClick: '&'
                },
                templateUrl: 'views/todo/tmpl/list-todo.tmpl.html',
                controller: function($scope, dataService, $log){
                    this.list = {};

                    dataService.loadListOfThings().success(angular.bind(this, function(data){
                        this.list = data;
                        $log.info($scope.list);
                    }))
                    .error(function(msg, statuscode){
                        $log.error('failure loading movie', msg, statuscode);
                    });

                    this.rowClick = function(id){
                        $scope.jhClick({id: id});
                    };
                },
                controllerAs: "listCtrl"
            };
        })

            /*
        .directive('textThing', function(){
        	return{
	        	restrict: 'E',
	        	scope: {
	        		thing: '=',
	        		removeFn: '&',
	        		save: '&'
	        	},
	        	controller: 'ThingDirectiveController',
	        	templateUrl: 'views/todo/tmpl/show-text-todo.tmpl.html',
        	};
        })
        .directive('fileThing', function(){
        	return{
	        	restrict: 'E',
	        	scope: {
                    thing: '=',
                    removeFn: '&',
                    save: '&'
	        	},
                controller: 'ThingDirectiveController',
	        	templateUrl: 'views/todo/tmpl/show-file-todo.tmpl.html',
        	};
        })
        .directive('commentThing', function(){
        	return{
	        	restrict: 'E',
	        	scope: {
                    thing: '=',
                    removeFn: '&',
                    save: '&'
	        	},
                controller: 'ThingDirectiveController',
	        	templateUrl: 'views/todo/tmpl/show-comment-todo.tmpl.html',
        	};
        })
        .directive('linkThing', function(){
        	return{
	        	restrict: 'E',
	        	scope: {
                    thing: '=',
                    removeFn: '&',
                    save: '&'
	        	},
                controller: 'ThingDirectiveController',
	        	templateUrl: 'views/todo/tmpl/show-link-todo.tmpl.html',
        	};
        })
        */
    ;
})();