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
        $stateProvider.state('single-video', {
            parent: 'app',
            url: '/video/:id',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/video/single-video.html',
                    controller: 'SingleVideoController',
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
