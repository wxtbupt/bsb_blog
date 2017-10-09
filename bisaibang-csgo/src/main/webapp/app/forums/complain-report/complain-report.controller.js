(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ForumsComplainReportController', ForumsComplainReportController);

    ForumsComplainReportController.$inject = ['$state', 'pagingParams', 'forumsGetAllThread', 'forumsGetAllThreadByState', 'toaster', '$rootScope', 'RegisterService'];

    function ForumsComplainReportController ($state, pagingParams, forumsGetAllThread, forumsGetAllThreadByState, toaster, $rootScope, RegisterService) {
        var vm = this;

        vm.isShowNew = false;
        vm.createNewTheme = createNewTheme;
        vm.page = pagingParams.page;
        vm.transition = transition;

        if(!pagingParams.page) {
            $state.transitionTo($state.$current, {page: 1});
        }

        // forumsGetAllThread.post({page: vm.page - 1, size: 20, sort: 'asc'}, '3', function onSuccess(response) {
        //     var data = response.data.normalThreads;
        //     vm.threadList = data.content;
        //     vm.totalItems = data.totalPages;
        //     vm.isFirst = data.first;
        //     vm.isLast = data.last;
        // });

        forumsGetAllThreadByState.post({state: 'normal', page: vm.page - 1, size: 20, sort: 'asc'}, '3', function onSuccess(response) {
            // console.log(response);
            if(response.data.topThreads || response.data.normalThreads) {
                //置顶帖子
                vm.topThreadAll = response.data.topThreads;

                //普通帖子
                var data = response.data.normalThreads;
                vm.threadList = data.content;
                vm.totalItems = data.totalPages;
                vm.isFirst = data.first;
                vm.isLast = data.last;
            }else {
                toaster.pop('error', " ", response.data.message);
                $state.transitionTo('forums');
            }
        });

        function transition(isNext) {
            if(vm.page + isNext > 0) {
                $state.transitionTo($state.$current, {
                    page: vm.page + isNext
                });
            }
        }

        function createNewTheme() {
            if($rootScope.globalVariable.id === -1) {
                RegisterService.open('signin', function success() {

                }, function fail() {

                });
            }else {
                vm.isShowNew = true;
            }
        }
    }
})();
