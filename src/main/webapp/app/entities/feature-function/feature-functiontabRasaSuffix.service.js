(function() {
    'use strict';
    angular
        .module('tabulaRasaApp')
        .factory('FeatureFunction', FeatureFunction);

    FeatureFunction.$inject = ['$resource'];

    function FeatureFunction ($resource) {
        var resourceUrl =  'api/feature-functions/:id';

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
