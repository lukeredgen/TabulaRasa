(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ReleaseFeatureTabRasaSuffixDeleteController',ReleaseFeatureTabRasaSuffixDeleteController);

    ReleaseFeatureTabRasaSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReleaseFeature'];

    function ReleaseFeatureTabRasaSuffixDeleteController($uibModalInstance, entity, ReleaseFeature) {
        var vm = this;

        vm.releaseFeature = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ReleaseFeature.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
