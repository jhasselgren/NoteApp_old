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
                },
                saveThing: function(thing){
                	var promise = $http.put('/api/thing/save',thing)
                	.success(function(data){
                		currentThing = data;
                		notifyObservers();
                	});

                    return promise;
                },
                deleteThing: function(id){
                	$http.delete('api/thing/delete/'+id)
                		.success(function(data){
                			currentThing = data;
                			notifyObservers();
                		});
                },
                addChild: function(thing){
                    var promise = $http.put('api/thing/' + currentThing.id + '/add', thing);

                    promise.success(function(data){
                       currentThing = data.parent;
                        notifyObservers();
                    });

                    return promise;
                }
            };

            return service;

        });
})();