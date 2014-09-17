

// Declare app level module which depends on views, and components
(function(){
    angular.module('noteApp', ['ngRoute', 'ngSanitize', 'mgcrea.ngStrap', 'noteApp.ThingController', 'noteApp.directive.thing', 'noteApp.TodoController', 'noteApp.data'])
        .config(function($routeProvider){
            $routeProvider.otherwise({redirectTo: '/todo'});
        })
})();
