(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('competingInfo', {
            parent: 'app',
            url: '/competingInfo',
            params: {'resigterState': null},
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/competing-info/competing-info.html',
                    controller: 'CompetingInfoController',
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
