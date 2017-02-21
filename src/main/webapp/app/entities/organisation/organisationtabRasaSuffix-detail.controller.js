(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('OrganisationTabRasaSuffixDetailController', OrganisationTabRasaSuffixDetailController);

    OrganisationTabRasaSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Organisation', 'Project', 'User'];

    function OrganisationTabRasaSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Organisation, Project, User) {
        var vm = this;

        vm.organisation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tabulaRasaApp:organisationUpdate', function(event, result) {
            vm.organisation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
