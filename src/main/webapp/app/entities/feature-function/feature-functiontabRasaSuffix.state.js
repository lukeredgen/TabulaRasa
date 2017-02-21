(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('feature-functiontabRasaSuffix', {
            parent: 'entity',
            url: '/feature-functiontabRasaSuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.featureFunction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/feature-function/feature-functionstabRasaSuffix.html',
                    controller: 'FeatureFunctionTabRasaSuffixController',
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
                    $translatePartialLoader.addPart('featureFunction');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('feature-functiontabRasaSuffix-detail', {
            parent: 'feature-functiontabRasaSuffix',
            url: '/feature-functiontabRasaSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.featureFunction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/feature-function/feature-functiontabRasaSuffix-detail.html',
                    controller: 'FeatureFunctionTabRasaSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('featureFunction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FeatureFunction', function($stateParams, FeatureFunction) {
                    return FeatureFunction.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'feature-functiontabRasaSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('feature-functiontabRasaSuffix-detail.edit', {
            parent: 'feature-functiontabRasaSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/feature-function/feature-functiontabRasaSuffix-dialog.html',
                    controller: 'FeatureFunctionTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FeatureFunction', function(FeatureFunction) {
                            return FeatureFunction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('feature-functiontabRasaSuffix.new', {
            parent: 'feature-functiontabRasaSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/feature-function/feature-functiontabRasaSuffix-dialog.html',
                    controller: 'FeatureFunctionTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('feature-functiontabRasaSuffix', null, { reload: 'feature-functiontabRasaSuffix' });
                }, function() {
                    $state.go('feature-functiontabRasaSuffix');
                });
            }]
        })
        .state('feature-functiontabRasaSuffix.edit', {
            parent: 'feature-functiontabRasaSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/feature-function/feature-functiontabRasaSuffix-dialog.html',
                    controller: 'FeatureFunctionTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FeatureFunction', function(FeatureFunction) {
                            return FeatureFunction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('feature-functiontabRasaSuffix', null, { reload: 'feature-functiontabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('feature-functiontabRasaSuffix.delete', {
            parent: 'feature-functiontabRasaSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/feature-function/feature-functiontabRasaSuffix-delete-dialog.html',
                    controller: 'FeatureFunctionTabRasaSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FeatureFunction', function(FeatureFunction) {
                            return FeatureFunction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('feature-functiontabRasaSuffix', null, { reload: 'feature-functiontabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
