(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .controller('ProjectTabRasaSuffixController', ProjectTabRasaSuffixController);

    ProjectTabRasaSuffixController.$inject = ['$scope', '$state', 'Project'];

    function ProjectTabRasaSuffixController ($scope, $state, Project) {
        var vm = this;

        vm.projects = [];

        loadAll();

        function loadAll() {
            Project.query(function(result) {
                vm.projects = result;
                vm.searchQuery = null;
            });
        }
    }
})();
