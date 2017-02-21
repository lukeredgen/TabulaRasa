(function() {
    'use strict';
    angular
        .module('tabulaRasaApp')
        .factory('Organisation', Organisation);

    Organisation.$inject = ['$resource'];

    function Organisation ($resource) {
        var resourceUrl =  'api/organisations/:id';

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
