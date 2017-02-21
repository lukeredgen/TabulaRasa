(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('release-featuretabRasaSuffix', {
            parent: 'entity',
            url: '/release-featuretabRasaSuffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.releaseFeature.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/release-feature/release-featurestabRasaSuffix.html',
                    controller: 'ReleaseFeatureTabRasaSuffixController',
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
                    $translatePartialLoader.addPart('releaseFeature');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('release-featuretabRasaSuffix-detail', {
            parent: 'release-featuretabRasaSuffix',
            url: '/release-featuretabRasaSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.releaseFeature.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/release-feature/release-featuretabRasaSuffix-detail.html',
                    controller: 'ReleaseFeatureTabRasaSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('releaseFeature');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ReleaseFeature', function($stateParams, ReleaseFeature) {
                    return ReleaseFeature.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'release-featuretabRasaSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('release-featuretabRasaSuffix-detail.edit', {
            parent: 'release-featuretabRasaSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/release-feature/release-featuretabRasaSuffix-dialog.html',
                    controller: 'ReleaseFeatureTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReleaseFeature', function(ReleaseFeature) {
                            return ReleaseFeature.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('release-featuretabRasaSuffix.new', {
            parent: 'release-featuretabRasaSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/release-feature/release-featuretabRasaSuffix-dialog.html',
                    controller: 'ReleaseFeatureTabRasaSuffixDialogController',
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
                    $state.go('release-featuretabRasaSuffix', null, { reload: 'release-featuretabRasaSuffix' });
                }, function() {
                    $state.go('release-featuretabRasaSuffix');
                });
            }]
        })
        .state('release-featuretabRasaSuffix.edit', {
            parent: 'release-featuretabRasaSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/release-feature/release-featuretabRasaSuffix-dialog.html',
                    controller: 'ReleaseFeatureTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReleaseFeature', function(ReleaseFeature) {
                            return ReleaseFeature.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('release-featuretabRasaSuffix', null, { reload: 'release-featuretabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('release-featuretabRasaSuffix.delete', {
            parent: 'release-featuretabRasaSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/release-feature/release-featuretabRasaSuffix-delete-dialog.html',
                    controller: 'ReleaseFeatureTabRasaSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ReleaseFeature', function(ReleaseFeature) {
                            return ReleaseFeature.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('release-featuretabRasaSuffix', null, { reload: 'release-featuretabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
