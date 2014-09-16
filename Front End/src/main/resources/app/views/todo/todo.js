(function(){
    angular.module('noteApp.TodoController', ['ngRoute'])

        .config(function($routeProvider){
            $routeProvider.when('/todo', {
                templateUrl: 'views/todo/todo.html'
            });
        })
        .controller('TodoViewController', function(){

            var showList = true;

            this.showList = function(){
                return showList;
            }

            this.openThing = function(id){
                this.thingId = id;
                showList = false;
            }
        })
        .directive('listTodo', function(dataService){
            return {
                restrict: 'E',
                scope: {
                  jhClick: '&'
                },
                templateUrl: 'views/todo/list-todo.tmpl.html',
                controller: function($scope, dataService, $log){
                    this.list = {TOM:"TOM"};

                    var tmpList = this.list;

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
    ;
})();