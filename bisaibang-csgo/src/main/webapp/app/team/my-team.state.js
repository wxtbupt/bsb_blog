(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('my-team', {
            parent: 'app',
            url: '/team/:id',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/team/my-team.html',
                    controller: 'MyTeamController',
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
