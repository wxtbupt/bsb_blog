(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('forums-team', {
            parent: 'app',
            url: '/forums/2/page/:page',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/forums/team-match/team-match.html',
                    controller: 'ForumsTeamMatchController',
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
