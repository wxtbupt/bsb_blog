(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('news-list', {
            parent: 'app',
            url: '/news',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/news/news.html',
                    controller: 'NewsListController',
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

