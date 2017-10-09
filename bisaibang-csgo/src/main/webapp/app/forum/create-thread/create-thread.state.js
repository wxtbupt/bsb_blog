(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('create-thread', {
            parent: 'app',
            url: '/station/forum/:id/create',
            data: {
                authorities: [],
                hasCustomTitle: true
            },
            views: {
                'content@': {
                    templateUrl: 'app/forum/create-thread/create-thread.html',
                    controller: 'CreateThreadController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
