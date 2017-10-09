(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('StationForumThreadController', StationForumThreadController);

    StationForumThreadController.$inject = ['$rootScope', '$stateParams', '$state', 'ForumGetAllPost','ForumCreatePost','toaster', 'Principal'];

    function StationForumThreadController($rootScope, $stateParams, $state, ForumGetAllPost, ForumCreatePost,toaster, Principal) {
        var vm = this;
        vm.isShowBox = false;
        vm.forumId = $stateParams.id;
        vm.threadId = $stateParams.threadid;
        vm.thread = {};
        vm.replyContent = "";
        vm.page = 1;
        vm.toPageNumber =1;
        vm.replySubmit = replySubmit;
        vm.pageChange = pageChange;
        vm.toPage = toPage;

        refreshPost();


        function replySubmit() {
            var reply = {
                content:vm.replyContent
            };
            Principal.identity().then(function (account) {
                if (account == null) {
                    SigninupService.open('signin', function success() {
                        $state.reload();
                    }, function fail() {

                    });
                } else {
                    ForumCreatePost.save({threadid: vm.threadId},reply ,function (res) {
                        console.log(res);
                        refreshPost();
                    })
                }
            })
        }

        function pageChange(num) {
            if(num == -1 && vm.page == 1){
                toaster.pop("error", "", "已经是第一页！");
            }else {
                if(num == 1){
                    vm.page++;
                }else{
                    vm.page--;
                }
                refreshPost();
            }
        }

        function refreshPost() {
            ForumGetAllPost.get({
                threadid: vm.threadId,
                page: vm.page - 1,
                size: 100,
                sort:["id,asc"]
            }, function (res) {

                vm.thread = res.singleThread;
                vm.postAll = res.posts.content;
                vm.totalPages = res.posts.totalPages;
                vm.replyContent = '';
                // console.log(res);
            });
        }

        function toPage(page) {
            vm.page = page;
            refreshPost();
        }
    }
})();
