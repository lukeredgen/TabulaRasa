(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ReleaseFeatureTabRasaSuffixDetailController', ReleaseFeatureTabRasaSuffixDetailController);

    ReleaseFeatureTabRasaSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ReleaseFeature', 'FeatureFunction', 'ProjectRelease'];

    function ReleaseFeatureTabRasaSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, ReleaseFeature, FeatureFunction, ProjectRelease) {
        var vm = this;

        vm.releaseFeature = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tabulaRasaApp:releaseFeatureUpdate', function(event, result) {
            vm.releaseFeature = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
