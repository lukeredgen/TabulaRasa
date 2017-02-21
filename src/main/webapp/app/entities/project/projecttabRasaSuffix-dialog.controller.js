(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ProjectTabRasaSuffixDialogController', ProjectTabRasaSuffixDialogController);

    ProjectTabRasaSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Project', 'Organisation', 'ProjectRelease'];

    function ProjectTabRasaSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Project, Organisation, ProjectRelease) {
        var vm = this;

        vm.project = entity;
        vm.clear = clear;
        vm.save = save;
        vm.organisations = Organisation.query();
        vm.projectreleases = ProjectRelease.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.project.id !== null) {
                Project.update(vm.project, onSaveSuccess, onSaveError);
            } else {
                Project.save(vm.project, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tabulaRasaApp:projectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
