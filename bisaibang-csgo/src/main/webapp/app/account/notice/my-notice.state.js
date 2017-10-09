(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('myNotice', {
            parent: 'account',
            url: '/myNotice',
            data: {
                // authorities: ['ROLE_USER'],
                pageTitle: 'global.menu.account.myNotice'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/notice/my-notice.html',
                    controller: 'MyNoticeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    // $translatePartialLoader.addPart('person');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
