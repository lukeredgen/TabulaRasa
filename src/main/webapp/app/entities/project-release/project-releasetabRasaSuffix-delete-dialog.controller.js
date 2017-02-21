(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ProjectReleaseTabRasaSuffixDeleteController',ProjectReleaseTabRasaSuffixDeleteController);

    ProjectReleaseTabRasaSuffixDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProjectRelease'];

    function ProjectReleaseTabRasaSuffixDeleteController($uibModalInstance, entity, ProjectRelease) {
        var vm = this;

        vm.projectRelease = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ProjectRelease.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
