/**
 * 
 */

describe('Testing create-text-thing direcive', function(){


    var $element, $rootScope, elementHtml;

    beforeEach(angular.mock.module('noteApp.directive.thing.text'));
    beforeEach(angular.mock.module('/app_components/thing-text/create-text-tpl.html',
        '/app_components/thing-text/edit-text.tpl.html'
    ));

    //beforeEach(module('noteApp.templates'));

    beforeEach(angular.mock.inject(function(_$rootScope_, _$compile_){

        // The injector unwraps the underscores (_) from around the parameter names when matching
        $rootScope = _$rootScope_;
        $compile = _$compile_;

        elementHtml = angular.element('<editable-text-thing thing="thing"></editable-text-thing>');

    }));
    
    it('should view name of thing in a input', function(){
    	 var scope = $rootScope.$new();

         scope.thing = {
             id: 1,
             name: 'Test Thing',
             description: 'Thing Description'
         };

         var element = $compile(elementHtml)(scope);

         scope.$digest();

         expect(element.find('input').first().attr("ng-model")).toContain('thing.name');
    });

    it('should view description of thing in a element', function(){
        var scope = $rootScope.$new();

        scope.thing = {
            id: 1,
            name: 'Test Thing',
            description: 'Thing Description'
        };

        var element = $compile(elementHtml)(scope);

        scope.$digest();

        expect(element.html()).toContain('ng-model="thing.description"');
    });

});