(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('OrganisationTabRasaSuffixDialogController', OrganisationTabRasaSuffixDialogController);

    OrganisationTabRasaSuffixDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Organisation', 'Project', 'User'];

    function OrganisationTabRasaSuffixDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Organisation, Project, User) {
        var vm = this;

        vm.organisation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.projects = Project.query();
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.organisation.id !== null) {
                Organisation.update(vm.organisation, onSaveSuccess, onSaveError);
            } else {
                Organisation.save(vm.organisation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tabulaRasaApp:organisationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
