(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('project-releasetabRasaSuffix', {
            parent: 'entity',
            url: '/project-releasetabRasaSuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.projectRelease.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project-release/project-releasestabRasaSuffix.html',
                    controller: 'ProjectReleaseTabRasaSuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('projectRelease');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('project-releasetabRasaSuffix-detail', {
            parent: 'project-releasetabRasaSuffix',
            url: '/project-releasetabRasaSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.projectRelease.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project-release/project-releasetabRasaSuffix-detail.html',
                    controller: 'ProjectReleaseTabRasaSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('projectRelease');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProjectRelease', function($stateParams, ProjectRelease) {
                    return ProjectRelease.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'project-releasetabRasaSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('project-releasetabRasaSuffix-detail.edit', {
            parent: 'project-releasetabRasaSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-release/project-releasetabRasaSuffix-dialog.html',
                    controller: 'ProjectReleaseTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProjectRelease', function(ProjectRelease) {
                            return ProjectRelease.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('project-releasetabRasaSuffix.new', {
            parent: 'project-releasetabRasaSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-release/project-releasetabRasaSuffix-dialog.html',
                    controller: 'ProjectReleaseTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codename: null,
                                description: null,
                                version: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('project-releasetabRasaSuffix', null, { reload: 'project-releasetabRasaSuffix' });
                }, function() {
                    $state.go('project-releasetabRasaSuffix');
                });
            }]
        })
        .state('project-releasetabRasaSuffix.edit', {
            parent: 'project-releasetabRasaSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-release/project-releasetabRasaSuffix-dialog.html',
                    controller: 'ProjectReleaseTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProjectRelease', function(ProjectRelease) {
                            return ProjectRelease.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('project-releasetabRasaSuffix', null, { reload: 'project-releasetabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('project-releasetabRasaSuffix.delete', {
            parent: 'project-releasetabRasaSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project-release/project-releasetabRasaSuffix-delete-dialog.html',
                    controller: 'ProjectReleaseTabRasaSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProjectRelease', function(ProjectRelease) {
                            return ProjectRelease.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('project-releasetabRasaSuffix', null, { reload: 'project-releasetabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
