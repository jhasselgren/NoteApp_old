/**
 * Created by jhas on 2014-09-16.
 */
(function(){
    angular.module('noteApp.data', [])
        .service('dataService', function($http, $q, $log){
            var service =
            {
                currentThing: {},
                listOfThings: {},

                setCurrentThing: function(id){
                    var thing = this.currentThing;
                    $http.get('/api/thing/'+id).then(function(response){
                        if(typeof response.data === 'object'){
                            thing = response.data;
                        }
                        else{
                            return $q.reject(response.data);
                        }
                    }, function(response){
                        return $q.reject(response.data);
                    });
                },

                loadListOfThings: function(){
                    return $http.get('/api/thing/all');
                }
            };

            return service;

        });
})();