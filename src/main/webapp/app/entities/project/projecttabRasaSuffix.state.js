(function() {
    'use strict';

    angular
        .module('tabulaRasaApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('projecttabRasaSuffix', {
            parent: 'entity',
            url: '/projecttabRasaSuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.project.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project/projectstabRasaSuffix.html',
                    controller: 'ProjectTabRasaSuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('project');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('projecttabRasaSuffix-detail', {
            parent: 'projecttabRasaSuffix',
            url: '/projecttabRasaSuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'tabulaRasaApp.project.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/project/projecttabRasaSuffix-detail.html',
                    controller: 'ProjectTabRasaSuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('project');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Project', function($stateParams, Project) {
                    return Project.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'projecttabRasaSuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('projecttabRasaSuffix-detail.edit', {
            parent: 'projecttabRasaSuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project/projecttabRasaSuffix-dialog.html',
                    controller: 'ProjectTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Project', function(Project) {
                            return Project.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projecttabRasaSuffix.new', {
            parent: 'projecttabRasaSuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project/projecttabRasaSuffix-dialog.html',
                    controller: 'ProjectTabRasaSuffixDialogController',
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
                    $state.go('projecttabRasaSuffix', null, { reload: 'projecttabRasaSuffix' });
                }, function() {
                    $state.go('projecttabRasaSuffix');
                });
            }]
        })
        .state('projecttabRasaSuffix.edit', {
            parent: 'projecttabRasaSuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project/projecttabRasaSuffix-dialog.html',
                    controller: 'ProjectTabRasaSuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Project', function(Project) {
                            return Project.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projecttabRasaSuffix', null, { reload: 'projecttabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('projecttabRasaSuffix.delete', {
            parent: 'projecttabRasaSuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/project/projecttabRasaSuffix-delete-dialog.html',
                    controller: 'ProjectTabRasaSuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Project', function(Project) {
                            return Project.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('projecttabRasaSuffix', null, { reload: 'projecttabRasaSuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
