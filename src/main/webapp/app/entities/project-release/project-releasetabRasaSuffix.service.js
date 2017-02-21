(function() {
    'use strict';
    angular
        .module('tabulaRasaApp')
        .factory('ProjectRelease', ProjectRelease);

    ProjectRelease.$inject = ['$resource'];

    function ProjectRelease ($resource) {
        var resourceUrl =  'api/project-releases/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
