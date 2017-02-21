'use strict';

describe('Controller Tests', function() {

    describe('FeatureFunction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFeatureFunction, MockReleaseFeature;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFeatureFunction = jasmine.createSpy('MockFeatureFunction');
            MockReleaseFeature = jasmine.createSpy('MockReleaseFeature');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'FeatureFunction': MockFeatureFunction,
                'ReleaseFeature': MockReleaseFeature
            };
            createController = function() {
                $injector.get('$controller')("FeatureFunctionTabRasaSuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tabulaRasaApp:featureFunctionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
