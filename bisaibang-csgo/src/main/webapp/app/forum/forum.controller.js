(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('NewForumController', NewForumController);

    NewForumController.$inject = ['$rootScope', '$stateParams', '$state', 'ForumGetAllThread', 'ForumGetMarkThread'];

    function NewForumController($rootScope, $stateParams, $state, ForumGetAllThread, ForumGetMarkThread) {
        var vm = this;
        vm.page = 1;
        vm.toPageNumber = 1;
        vm.createNewTheme = false;
        vm.topThreadAll = [];
        vm.threadList = [];
        vm.markThreadList = [];
        vm.forumId = 1;

        vm.createThread = createThread;
        vm.pageChange = pageChange;
        vm.toPage = toPage;
        // console.log(vm.forumId);

        refreshThread();

        function refreshThread() {
            ForumGetAllThread.get({
                page: vm.page - 1,
                size: 10,
                sort: ["id,asc"],
                forumid: vm.forumId
            }, function success(res) {
                //console.log(res);
                vm.threadList = res.content;
                vm.totalPages = res.totalPages;
            });
            ForumGetMarkThread.get({
                page: vm.page - 1,
                size: 10,
                sort: ["id,asc"],
                forumid: vm.forumId
            }, function (res) {
                vm.markThreadList = res.content;
                // console.log(JSON.stringify(res));
            });
        }

        function createThread() {
            $state.go('create-thread', {id: vm.forumId})
        }

        function pageChange(num) {
            if (num == -1 && vm.page == 1) {
                toaster.pop("error", "", "已经是第一页！");
            } else {
                if (num == 1) {
                    vm.page++;
                } else {
                    vm.page--;
                }
                refreshThread();
            }
        }

        function toPage(page) {
            vm.page = page;
            refreshThread();
        }


    }
})();
