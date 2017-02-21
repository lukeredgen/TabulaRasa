(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ProjectReleaseTabRasaSuffixController', ProjectReleaseTabRasaSuffixController);

    ProjectReleaseTabRasaSuffixController.$inject = ['$scope', '$state', 'ProjectRelease'];

    function ProjectReleaseTabRasaSuffixController ($scope, $state, ProjectRelease) {
        var vm = this;

        vm.projectReleases = [];

        loadAll();

        function loadAll() {
            ProjectRelease.query(function(result) {
                vm.projectReleases = result;
                vm.searchQuery = null;
            });
        }
    }
})();
