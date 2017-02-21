'use strict';

describe('Controller Tests', function() {

    describe('ProjectRelease Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProjectRelease, MockProject, MockReleaseFeature;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProjectRelease = jasmine.createSpy('MockProjectRelease');
            MockProject = jasmine.createSpy('MockProject');
            MockReleaseFeature = jasmine.createSpy('MockReleaseFeature');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ProjectRelease': MockProjectRelease,
                'Project': MockProject,
                'ReleaseFeature': MockReleaseFeature
            };
            createController = function() {
                $injector.get('$controller')("ProjectReleaseTabRasaSuffixDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'tabulaRasaApp:projectReleaseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
