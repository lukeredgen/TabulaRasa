(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ProjectTabRasaSuffixDetailController', ProjectTabRasaSuffixDetailController);

    ProjectTabRasaSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Project', 'Organisation', 'ProjectRelease'];

    function ProjectTabRasaSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, Project, Organisation, ProjectRelease) {
        var vm = this;

        vm.project = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tabulaRasaApp:projectUpdate', function(event, result) {
            vm.project = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
