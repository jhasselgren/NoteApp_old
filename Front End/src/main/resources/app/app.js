

// Declare app level module which depends on views, and components
(function(){
    angular.module('noteApp',
        [
            'ngRoute',
            'ngSanitize',
            'ngAnimate',
            'mgcrea.ngStrap',
            'noteApp.ThingController',
            'noteApp.directive.thing.text',
            'noteApp.directive.thing.file',
            'noteApp.directive.thing.link',
            'noteApp.directive.thing.todo',
            'noteApp.TodoController',
            'noteApp.data',
            'noteApp.filters'
        ])
        .config(function($routeProvider){
            $routeProvider.otherwise({redirectTo: '/todo'});
        })
})();
