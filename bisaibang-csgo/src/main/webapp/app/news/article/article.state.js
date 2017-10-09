/**
 * Created by OlyLis on 2017/2/4.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('news-article', {
            parent: 'app',
            url: '/news/article',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/news/article/article.html',
                    controller: 'NewsArticleController',
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
