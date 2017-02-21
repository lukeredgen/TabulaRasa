(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('FeatureFunctionTabRasaSuffixDialogController', FeatureFunctionTabRasaSuffixDialogController);

    FeatureFunctionTabRasaSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FeatureFunction', 'ReleaseFeature'];

    function FeatureFunctionTabRasaSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FeatureFunction, ReleaseFeature) {
        var vm = this;

        vm.featureFunction = entity;
        vm.clear = clear;
        vm.save = save;
        vm.releasefeatures = ReleaseFeature.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.featureFunction.id !== null) {
                FeatureFunction.update(vm.featureFunction, onSaveSuccess, onSaveError);
            } else {
                FeatureFunction.save(vm.featureFunction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tabulaRasaApp:featureFunctionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
