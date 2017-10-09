/**
 * Created by OlyLis on 2017/2/28.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('forums-thread', {
            parent: 'app',
            url: '/forums/thread/:threadId/page/:page',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/forums/thread/thread.html',
                    controller: 'ForumsThreadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page)
                    };
                }],
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
