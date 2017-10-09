(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ForumsGeneralController', ForumsGeneralController);

    ForumsGeneralController.$inject = ['$state', 'pagingParams', 'forumsGetAllThread', 'forumsGetAllThreadByState', 'toaster', '$rootScope', 'RegisterService'];

    function ForumsGeneralController ($state, pagingParams, forumsGetAllThread, forumsGetAllThreadByState, toaster, $rootScope, RegisterService) {
        var vm = this;

        vm.isShowNew = false;
        vm.createNewTheme = createNewTheme;
        vm.page = pagingParams.page;
        // console.log(vm.page);
        vm.transition = transition;
        vm.search = search;

        if(!pagingParams.page) {
            $state.transitionTo($state.$current, {page: 1});
        }

        // forumsGetAllThread.post({page: vm.page - 1, size: 20, sort: 'asc'}, '1', function onSuccess(response) {
        //     // console.log(response);
        //     //置顶帖子
        //     vm.topThreadAll = response.data.topThreads;
        //
        //     //普通帖子
        //     var data = response.data.normalThreads;
        //     vm.threadList = data.content;
        //     vm.totalItems = data.totalPages;
        //     vm.isFirst = data.first;
        //     vm.isLast = data.last;
        //
        // });

        forumsGetAllThreadByState.post({state: 'normal', page: vm.page - 1, size: 20, sort: 'asc'}, '1', function onSuccess(response) {
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

        function search(content) {
            console.log(content);
            $state.go('search-thread', {content:content});
            // SearchForumsThread.query(vm.thread,  function (response) {
            //     console.log(response);
            // }, function () {
            //
            // });

        }

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
