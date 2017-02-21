(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ProjectReleaseTabRasaSuffixDialogController', ProjectReleaseTabRasaSuffixDialogController);

    ProjectReleaseTabRasaSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProjectRelease', 'Project', 'ReleaseFeature'];

    function ProjectReleaseTabRasaSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProjectRelease, Project, ReleaseFeature) {
        var vm = this;

        vm.projectRelease = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projects = Project.query();
        vm.releasefeatures = ReleaseFeature.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.projectRelease.id !== null) {
                ProjectRelease.update(vm.projectRelease, onSaveSuccess, onSaveError);
            } else {
                ProjectRelease.save(vm.projectRelease, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tabulaRasaApp:projectReleaseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
