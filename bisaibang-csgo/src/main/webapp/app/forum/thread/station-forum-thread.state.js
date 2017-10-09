(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {

        $stateProvider.state('station-forum-thread', {
            parent: 'app',
            url: '/station/forum/:id/thread/:threadid',
            data: {
                authorities: [],
                hasCustomTitle: true
            },
            views: {
                'content@': {
                    templateUrl: 'app/forum/thread/station-forum-thread.html',
                    controller: 'StationForumThreadController',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
