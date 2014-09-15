(function(){
    angular.module('noteApp.TodoController', ['ngRoute'])

        .config(function($routeProvider){
            $routeProvider.when('/todo', {
                templateUrl: 'views/todo/todo.html'
            });
        });
})();