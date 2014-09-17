/**
 * Created by jhas on 2014-09-16.
 */
(function(){
    angular.module('noteApp.data', [])
        .service('dataService', function($http, $q, $log){

            var observerCallbacks = [];

            var notifyObservers = function(){
                angular.forEach(observerCallbacks, function(callback){
                    callback();
                });
            };

            var currentThing;

            var service =
            {
                listOfThings: {},

                registerObserverCallback: function(callback){
                    observerCallbacks.push(callback);
                },

                setCurrentThing: function(id){

                    
                    $http.get('/api/thing/'+id)
                        .success(function(data){
                            currentThing = data;
                            notifyObservers();
                        });
                },
                getCurrentThing: function(){
                    return currentThing;
                },
                loadListOfThings: function(){
                    return $http.get('/api/thing/all');
                }
            };

            return service;

        });
})();