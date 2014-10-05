/**
 * Created by jhas on 2014-09-23.
 */
(function(){
    var app = angular.module('noteApp.directive.delete', ['mgcrea.ngStrap', 'noteApp.filters']);
        /*
        * As a User I want to see the choosen text object
        * As a User I want to be able to edit the object
        * As a User I want to be able to delete the object
        * */
    app.directive('showToDoThing', function(){
        return{
            restrict: 'E',
            scope: {
                thing: '=',
                save: '&',
                removeFn: '&'
            },
            controller: function(scope, $log){

                _this = this;

//                scope.$watch('thing', angular.bind(this, function(newVal, oldVal){
//
//                    if(typeof this.thing != "undefined"){
//                        this.progress = this.thing.status + '%';
//                    }
//
//                }));

                scope.progress = scope.thing.status +'%';

                _this.edit = function(thing){
                    scope.save({thing: thing});
                };

                scope.deleteThing = function(id){
                    scope.removeFn({id: id});
                };


                var copyFunction = function(){
                    var copy = angular.copy(scope.thing);
                    return copy;
                };

                this.aside =  {
                    thing: copyFunction(),
                    save: function(thing){
                        _this.edit(thing);
                    }
                };
            },
            templateUrl: '/app_components/thing-todo/show-todo.tpl.html'

        };
    });
//    app.directive('editableToDoThing', function(){
//        return{
//            restrict: 'E',
//            scope: {
//                thing: '='
//            },
//            controller: function($scope){
//
//            },
//            templateUrl: '/app_components/thing-text/edit-text.tpl.html'
//        };
//    });
//    app.directive('createToDoThing', function(){
//        return{
//            restrict: 'E',
//            templateUrl: '/app_components/thing-text/create-text-tpl.html'
//        };
//    });
})();


