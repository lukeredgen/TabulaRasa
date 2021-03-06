(function() {
    'use strict';
    angular
        .module('tabulaRasaApp')
        .factory('ReleaseFeature', ReleaseFeature);

    ReleaseFeature.$inject = ['$resource'];

    function ReleaseFeature ($resource) {
        var resourceUrl =  'api/release-features/:id';

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
