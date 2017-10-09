(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('team-register', {
            parent: 'app',
            url: '/register/team',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/registion/team-register/team-register.html',
                    controller: 'TeamRegisterController',
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
