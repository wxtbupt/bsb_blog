(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig ($stateProvider) {
        $stateProvider.state('person-register', {
            parent: 'app',
            url: '/register/person',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'app/registion/person-register/person-register.html',
                    controller: 'PersonRegisterController',
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
