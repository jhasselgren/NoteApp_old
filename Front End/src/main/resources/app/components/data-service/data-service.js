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
                    
                    var promise = $http.get('/api/thing/'+id).success(angular.bind(this, function(data){
                    	this.currentThing = data;
                    	return this.currentThing;
                    }));
                    
                    return promise;
                },

                loadListOfThings: function(){
                    return $http.get('/api/thing/all');
                }
            };

            return service;

        });
})();