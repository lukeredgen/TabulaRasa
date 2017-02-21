(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ProjectReleaseTabRasaSuffixDetailController', ProjectReleaseTabRasaSuffixDetailController);

    ProjectReleaseTabRasaSuffixDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProjectRelease', 'Project', 'ReleaseFeature'];

    function ProjectReleaseTabRasaSuffixDetailController($scope, $rootScope, $stateParams, previousState, entity, ProjectRelease, Project, ReleaseFeature) {
        var vm = this;

        vm.projectRelease = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('tabulaRasaApp:projectReleaseUpdate', function(event, result) {
            vm.projectRelease = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
