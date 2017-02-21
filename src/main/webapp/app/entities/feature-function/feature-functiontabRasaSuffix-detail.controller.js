(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('FeatureFunctionTabRasaSuffixDetailController', FeatureFunctionTabRasaSuffixDetailController);

    FeatureFunctionTabRasaSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FeatureFunction', 'ReleaseFeature'];

    function FeatureFunctionTabRasaSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, FeatureFunction, ReleaseFeature) {
        var vm = this;

        vm.featureFunction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tabulaRasaApp:featureFunctionUpdate', function(event, result) {
            vm.featureFunction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
