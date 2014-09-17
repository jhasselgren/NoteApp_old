(function(){
    angular.module('noteApp.TodoController', ['ngRoute'])

        .config(function($routeProvider){
            $routeProvider.when('/todo', {
                templateUrl: 'views/todo/todo.html',
                controller: 'TodoViewController',
                controllerAs: 'viewCtrl'
            });
        })
        .controller('TodoViewController', function($log, dataService){
        	this.thingId = -1;
        	this.data = {};
        	this.data.currentTing = {};
        	this.showList = true;

            this.openThing = function(id){
            	this.thingId = id;
            	
            	dataService.setCurrentThing(id)
            	.success(angular.bind(this, function(data){
            		this.showList = false;
            		this.data.currentTing = data;
            		$log.debug(this.data.currentTing);
            	}))
            	.error(function(msg, status){
            		$log.error(msg, status);
            	});
            	
            	
            }
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
	        	scope: {
        			thing :'='
        		},
        		templateUrl: 'views/todo/tmpl/show-todo.tmpl.html',
        		controller: function($scope, $log){
        			
        			$scope.$watch('thing', angular.bind(this, function(newVal, oldVal){
        				this.thing = $scope.thing
        			}));
        			
        			this.thing = $scope.thing
        			
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
	        	controller: function($scope){
	        		
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