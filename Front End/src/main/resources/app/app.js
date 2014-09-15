

// Declare app level module which depends on views, and components
(function(){
    angular.module('noteApp', ['ngRoute', 'noteApp.ThingController', 'noteApp.directive.thing'])
        .config(function($routeProvider){
            $routeProvider.otherwise({redirectTo: '/thing/create'})
        })
})();
