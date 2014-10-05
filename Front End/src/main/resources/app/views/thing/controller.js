/**
 * Created by jhas on 2014-09-13.
 */
(function(){
    angular.module('noteApp.ThingController', ['ngRoute', 'textAngular'])

        .config(function($routeProvider){
            $routeProvider.when('/thing/create', {
                templateUrl: 'views/thing/create.html'
            });
        })
        .controller('ThingController', function(){
            this.things = thingMock;
        })
        .controller('ThingCreateController', function(){
            this.thing = {};
        });

    var thingMock =
    	[
	        {
	            name: 'Test Thing',
	            description: 'En mock för en thing',
	            type: 'text',
	            things:
	                [
	                    {
	                        name: 'Child 1',
	                        description: 'En mock för första barnet',
	                        type: 'text'
	                    },
	                    {
	                        name: 'Child 2',
	                        description: 'En mock för andra barnet',
	                        type: 'todo'
	                    }
	                ]
	        },
	        {
	        	name:'Test',
	        	description: 'En mock för en thing',
	            type: 'text'
	        }
        ];

})();