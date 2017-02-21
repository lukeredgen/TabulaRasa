(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ProjectTabRasaSuffixDeleteController',ProjectTabRasaSuffixDeleteController);

    ProjectTabRasaSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'Project'];

    function ProjectTabRasaSuffixDeleteController($uibModalInstance, entity, Project) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Project.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
