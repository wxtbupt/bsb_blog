/**
 * Created by DELL on 2017/3/13.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('video', {
            parent: 'app',
            url: '/video/page:page',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/video/video.html',
                    controller: 'VideoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page) || 1
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
