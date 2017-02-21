(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('FeatureFunctionTabRasaSuffixDeleteController',FeatureFunctionTabRasaSuffixDeleteController);

    FeatureFunctionTabRasaSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'FeatureFunction'];

    function FeatureFunctionTabRasaSuffixDeleteController($uibModalInstance, entity, FeatureFunction) {
        var vm = this;

        vm.featureFunction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FeatureFunction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
