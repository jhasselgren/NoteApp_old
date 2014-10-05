/**
 * Created by jhas on 2014-09-25.
 */
/*
* As a User I want to see the choosen text object
* As a User I want to be able to edit the object
* As a User I want to be able to delete the object
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

        elementHtml = angular.element('<create-text-thing save-fn="save(thing)" cancel-fn="cancelFn()"></create-text-thing>');

    }));
    
    it('should have marked the ting as a text thing', function(){
    	var scope = $rootScope.$new();
    	
    	scope.cancelFn = function() {};
    	
    	var element = $compile(elementHtml)(scope);
    	
    	scope.$digest();
    	
    	var isloatedScope = element.isolateScope();
    	
    	expect(isloatedScope.thing).not.toBeUndefined();
    	expect(isloatedScope.thing.type).not.toBeUndefined();
    	expect(isloatedScope.thing.type).toBe('TEXT');
    });

    it('should call the linked save function when save is called', function(){
        var scope = $rootScope.$new();

        scope.save = function (thing) {};

        var element = $compile(elementHtml)(scope);

        scope.$digest();

        var isolateScope = element.isolateScope();

        expect(isolateScope.save).toEqual(jasmine.any(Function));

        spyOn(scope, 'save');

        isolateScope.save();

        expect(scope.save).toHaveBeenCalledWith({type: 'TEXT'});

    });

    it('should call the linked cancel function when cancel is called', function(){
        var scope = $rootScope.$new();

        scope.cancelFn = function () {};

        var element = $compile(elementHtml)(scope);

        scope.$digest();

        var isolateScope = element.isolateScope();

        expect(isolateScope.cancel).toEqual(jasmine.any(Function));

        spyOn(scope, 'cancelFn');

        isolateScope.cancel();

        expect(scope.cancelFn).toHaveBeenCalled();

        expect(isolateScope.thing.name).toBeUndefined();
    });
    
    it('should reset the Thing data when cancel is pressed', function(){
    	var scope = $rootScope.$new();
    	
    	scope.cancelFn = function() {};
    	
    	var element = $compile(elementHtml)(scope);
    	
    	scope.$digest();
    	
    	var isloatedScope = element.isolateScope();
    	
    	isloatedScope.thing.name = 'Test';
    	
    	isloatedScope.cancel();
    	
    	expect(isloatedScope.thing).not.toBeUndefined();
    	expect(isloatedScope.thing.name).toBeUndefined();
    });
});