'use strict';

describe('Controller Tests', function() {

    describe('ReleaseFeature Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockReleaseFeature, MockFeatureFunction, MockProjectRelease;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockReleaseFeature = jasmine.createSpy('MockReleaseFeature');
            MockFeatureFunction = jasmine.createSpy('MockFeatureFunction');
            MockProjectRelease = jasmine.createSpy('MockProjectRelease');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ReleaseFeature': MockReleaseFeature,
                'FeatureFunction': MockFeatureFunction,
                'ProjectRelease': MockProjectRelease
            };
            createController = function() {
                $injector.get('$controller')("ReleaseFeatureTabRasaSuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tabulaRasaApp:releaseFeatureUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
