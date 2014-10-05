/**
 * Created by jhas on 2014-09-25.
 */
/*
* As a User I want to see the choosen text object
* As a User I want to be able to edit the object
* As a User I want to be able to delete the object
*
*/

describe('Check that test working', function () {
    it('true should br true', function(){
        expect(true).toBe(true);
    });
});

describe('Testing show-text-thing direcive', function(){


    var $element, $rootScope, elementHtml;

    beforeEach(angular.mock.module('noteApp.directive.thing.text'));
    beforeEach(angular.mock.module('/app_components/thing-text/show-text.tpl.html',
        '/app_components/thing-text/aside/edit-text-aside.tpl.html',
        '/app_components/thing-text/popover/delete-popover.html',
        '/app_components/thing-text/edit-text.tpl.html'
    ));
    
    beforeEach(angular.mock.inject(function(_$rootScope_, _$compile_){
        // The injector unwraps the underscores (_) from around the parameter names when matching
        $rootScope = _$rootScope_;
        $compile = _$compile_;

        elementHtml = angular.element('<show-text-thing thing="thing" save-fn="save(thing)" remove-fn="removeFn()"></show-text-thing>');

    }));

    it('ensure that todo thing name is shown', function() {
        var scope = $rootScope.$new();

        scope.thing = {
            id: 1,
            name: 'Test Thing',
            description: 'Thing Description'
        };

        scope.removeFn = function (id) {
        };

        scope.save = function (thing) {
        };

        var element = $compile(elementHtml)(scope);

        scope.$digest();

        expect(element.html()).toContain('Test Thing');
    });

    it('ensure that todo description is shown', function(){
        var scope = $rootScope.$new();

        scope.thing = {
            id: 1,
            name: 'Test Thing',
            description: 'Thing Description'
        };

        scope.removeFn = function (id) {
        };

        scope.save = function (thing) {
        };

        var element = $compile(elementHtml)(scope);

        scope.$digest();

        expect(element.html()).toContain('Thing Description');
    });

    it('ensure that there is a save function and that linked function is called', function(){
        var scope = $rootScope.$new();

        scope.thing = {
            id: 1,
            name: 'Test Thing',
            description: 'Thing Description'
        };

        scope.removeFn = function (id) {
        };

        scope.save = function (thing) {
        };

        var element = $compile(elementHtml)(scope);

        scope.$digest();

        var isolateScope = element.isolateScope();

        expect(isolateScope.editAside.data.call).toEqual(jasmine.any(Function));

        spyOn(scope, 'save');

        isolateScope.editAside.data.call(123);

        expect(scope.save).toHaveBeenCalledWith(123);

    });

    it('ensure that there is a remove function and that linked function is called', function(){
        var scope = $rootScope.$new();

        scope.thing = {
            id: 1,
            name: 'Test Thing',
            description: 'Thing Description'
        };

        scope.removeFn = function (id) {};
        scope.save = function (thing) {};

        var element = $compile(elementHtml)(scope);

        scope.$digest();

        var isolateScope = element.isolateScope();

        expect(isolateScope.deletePopover.data.call).toEqual(jasmine.any(Function));

        spyOn(scope, 'removeFn');

        isolateScope.deletePopover.data.call();

        expect(scope.removeFn).toHaveBeenCalled();
    });
});