/**
 * Created by OlyLis on 2017/2/28.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ForumsThreadController', ForumsThreadController);

    ForumsThreadController.$inject = ['forumsGetThreadById', 'ThreadGetAllPostByLevel', '$state', '$stateParams', 'RegisterService', '$rootScope', 'pagingParams', 'createPost', 'toaster'];

    function ForumsThreadController (forumsGetThreadById, ThreadGetAllPostByLevel, $state, $stateParams, RegisterService, $rootScope, pagingParams, createPost, toaster) {
        var vm = this;
        vm.replyAuthor = replyAuthor;
        vm.lastPage = lastPage;
        vm.nextPage = nextPage;
        vm.isShowBox = false;
        vm.page = pagingParams.page;
        vm.replyContent = "";
        vm.submit = submit;


        function submit() {
            vm.reply = {
                title: '',
                content: vm.replyContent? vm.replyContent : '',
                state:  '',
                level: 1,
                thread: {id: vm.thread.id},
                leaderPost: {id: vm.leaderPostId} || null
            };
            console.log(vm.reply)
            createPost.post(vm.reply, function success(result) {
                toaster.pop('success', " ", '已成功');
                $state.reload();
            }, function error(result) {
                toaster.pop('error', " ", '失败');
            });
        }
        forumsGetThreadById.get({id: $stateParams.threadId}, function onSuccess(response) {
            vm.thread = response.data;
        });

        ThreadGetAllPostByLevel.get({id: $stateParams.threadId, level: 1, page: vm.page - 1, size: 10, sort: 'desc'}, {}, function onSuccess(response) {
            // console.log(response.data);
            vm.isFirst = response.data.first;
            vm.isLast = response.data.last;
            vm.postAll = response.data.content;
            // console.log(vm.postAll);
        });

        function lastPage() {
            if(!vm.isFirst) {
                transition(-1);
            }
        }

        function nextPage() {
            if(!vm.isLast) {
                transition(1);
            }
        }

        function transition(pageCount) {
            $state.transitionTo($state.$current, {
                threadId: $stateParams.threadId,
                page: vm.page + pageCount
            });
        }

        function replyAuthor() {
            // $location.hash('reply_author');
            // $anchorScroll();
            if($rootScope.globalVariable.id !== -1) {
                vm.isShowBox = !vm.isShowBox;
            }else {
                RegisterService.open('signin', function success() {
                    $state.reload();
                }, function fail() {

                });
            }
        }
    }
})();
