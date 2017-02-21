(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('OrganisationTabRasaSuffixDeleteController',OrganisationTabRasaSuffixDeleteController);

    OrganisationTabRasaSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Organisation'];

    function OrganisationTabRasaSuffixDeleteController($uibModalInstance, entity, Organisation) {
        var vm = this;

        vm.organisation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Organisation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
