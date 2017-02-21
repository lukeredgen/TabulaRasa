(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('organisationtabRasaSuffix', {
            parent: 'entity',
            url: '/organisationtabRasaSuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.organisation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/organisation/organisationstabRasaSuffix.html',
                    controller: 'OrganisationTabRasaSuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('organisation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('organisationtabRasaSuffix-detail', {
            parent: 'organisationtabRasaSuffix',
            url: '/organisationtabRasaSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.organisation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/organisation/organisationtabRasaSuffix-detail.html',
                    controller: 'OrganisationTabRasaSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('organisation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Organisation', function($stateParams, Organisation) {
                    return Organisation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'organisationtabRasaSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('organisationtabRasaSuffix-detail.edit', {
            parent: 'organisationtabRasaSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organisation/organisationtabRasaSuffix-dialog.html',
                    controller: 'OrganisationTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Organisation', function(Organisation) {
                            return Organisation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('organisationtabRasaSuffix.new', {
            parent: 'organisationtabRasaSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organisation/organisationtabRasaSuffix-dialog.html',
                    controller: 'OrganisationTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('organisationtabRasaSuffix', null, { reload: 'organisationtabRasaSuffix' });
                }, function() {
                    $state.go('organisationtabRasaSuffix');
                });
            }]
        })
        .state('organisationtabRasaSuffix.edit', {
            parent: 'organisationtabRasaSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organisation/organisationtabRasaSuffix-dialog.html',
                    controller: 'OrganisationTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Organisation', function(Organisation) {
                            return Organisation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('organisationtabRasaSuffix', null, { reload: 'organisationtabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('organisationtabRasaSuffix.delete', {
            parent: 'organisationtabRasaSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/organisation/organisationtabRasaSuffix-delete-dialog.html',
                    controller: 'OrganisationTabRasaSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Organisation', function(Organisation) {
                            return Organisation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('organisationtabRasaSuffix', null, { reload: 'organisationtabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
