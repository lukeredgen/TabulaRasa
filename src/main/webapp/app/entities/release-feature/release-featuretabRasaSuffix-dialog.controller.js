(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ReleaseFeatureTabRasaSuffixDialogController', ReleaseFeatureTabRasaSuffixDialogController);

    ReleaseFeatureTabRasaSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ReleaseFeature', 'FeatureFunction', 'ProjectRelease'];

    function ReleaseFeatureTabRasaSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ReleaseFeature, FeatureFunction, ProjectRelease) {
        var vm = this;

        vm.releaseFeature = entity;
        vm.clear = clear;
        vm.save = save;
        vm.featurefunctions = FeatureFunction.query();
        vm.projectreleases = ProjectRelease.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.releaseFeature.id !== null) {
                ReleaseFeature.update(vm.releaseFeature, onSaveSuccess, onSaveError);
            } else {
                ReleaseFeature.save(vm.releaseFeature, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tabulaRasaApp:releaseFeatureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
