(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chat-log', {
            parent: 'entity',
            url: '/chat-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bsbmsoneApp.chatLog.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chat-log/chat-logs.html',
                    controller: 'ChatLogController',
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
                    $translatePartialLoader.addPart('chatLog');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('chat-log-detail', {
            parent: 'chat-log',
            url: '/chat-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bsbmsoneApp.chatLog.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chat-log/chat-log-detail.html',
                    controller: 'ChatLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('chatLog');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ChatLog', function($stateParams, ChatLog) {
                    return ChatLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chat-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chat-log-detail.edit', {
            parent: 'chat-log-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chat-log/chat-log-dialog.html',
                    controller: 'ChatLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChatLog', function(ChatLog) {
                            return ChatLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chat-log.new', {
            parent: 'chat-log',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chat-log/chat-log-dialog.html',
                    controller: 'ChatLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                message: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('chat-log', null, { reload: 'chat-log' });
                }, function() {
                    $state.go('chat-log');
                });
            }]
        })
        .state('chat-log.edit', {
            parent: 'chat-log',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chat-log/chat-log-dialog.html',
                    controller: 'ChatLogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChatLog', function(ChatLog) {
                            return ChatLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chat-log', null, { reload: 'chat-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chat-log.delete', {
            parent: 'chat-log',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chat-log/chat-log-delete-dialog.html',
                    controller: 'ChatLogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChatLog', function(ChatLog) {
                            return ChatLog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chat-log', null, { reload: 'chat-log' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
