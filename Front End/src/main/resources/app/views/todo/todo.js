(function(){
    angular.module('noteApp.TodoController', ['ngRoute', 'mgcrea.ngStrap'])

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
        .controller('TodoListController', function($location){
            this.openThing = function(id){
            	$location.path('/todo/'+id);
            }
        })
        .controller('TodoShowController', function($scope, $log, $location, dataService, $routeParams){
            var init = function(){
                var thingId = $routeParams.thingId;
                dataService.registerObserverCallback(updateCurrentThing);
	    		dataService.setCurrentThing(thingId);

        	};

            var updateCurrentThing = function(){
                $scope.currentTing = dataService.getCurrentThing();
            };

        	init();

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
                    }
                },
                controllerAs: "listCtrl"
            }
        })
        .directive('showTodo', function(){
        	return{
	        	restrict: 'E',
	        	require: '^thing',
	        	scope: {
        			thing :'='
        		},
        		templateUrl: 'views/todo/tmpl/show-todo.tmpl.html',
        		controller: function($scope, $log){
        			
        			$scope.$watch('thing', angular.bind(this, function(newVal, oldVal){
        				this.thing = $scope.thing
        				if(typeof this.thing != "undefined"){
            				this.progress = this.thing.status + '%';
            			}
        			}));
        			
        			this.thing = $scope.thing
        			
        			if(typeof this.thing != "undefined"){
        				this.progress = this.thing.status + '%';
        			}
        			
        			this.logName = function(){
                		$log.info(this.thing);
                	};
        			
        		},
        		controllerAs: 'todoCtrl'
        	}
        	
        })
        .directive('textThing', function(){
        	return{
	        	restrict: 'E',
	        	scope: {
	        		thing: '='
	        	},
	        	controller: function($scope, $aside){
	        		$scope.edit = function(){
                        var myAside = $modal({title: 'test', content: 'test test', animation: 'am-slide-left', template: 'views/todo/aside/edit-text-thing.tpl.html'});

                        myAside.$promise.then(function(){
                            myAside.show();
                        });

                    }
	        	},
	        	templateUrl: 'views/todo/tmpl/show-text-todo.tmpl.html',
        	};
        })
        .directive('fileThing', function(){
        	return{
	        	restrict: 'E',
	        	scope: {
	        		thing: '='
	        	},
	        	controller: function($scope){
	        		
	        	},
	        	templateUrl: 'views/todo/tmpl/show-file-todo.tmpl.html',
        	};
        })
        .directive('commentThing', function(){
        	return{
	        	restrict: 'E',
	        	scope: {
	        		thing: '='
	        	},
	        	controller: function($scope){
	        		
	        	},
	        	templateUrl: 'views/todo/tmpl/show-comment-todo.tmpl.html',
        	};
        })
        .directive('linkThing', function(){
        	return{
	        	restrict: 'E',
	        	scope: {
	        		thing: '='
	        	},
	        	controller: function($scope){
	        		
	        	},
	        	templateUrl: 'views/todo/tmpl/show-link-todo.tmpl.html',
        	};
        })
    ;
})();