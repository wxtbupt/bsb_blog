/**
 * Created by gsy on 2017/3/11.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('search-thread', {
            parent: 'app',
            url: '/forums/thread/search/:content',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/forums/search-thread/search.html',
                    controller: 'ForumsSearchController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
