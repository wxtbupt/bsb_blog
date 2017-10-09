/**
 * Created by gsy on 2017/3/25.
 */

(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardManageForumController', DashboardManageForumController);

    DashboardManageForumController.$inject = ['$scope', '$state', 'ThreadsDelete',  'RevertEssentialThread', 'EssentialThread', 'ForumGetAllThread', 'ForumGetMarkThread', 'SweetAlert'];

    function DashboardManageForumController($scope, $state, ThreadsDelete,  RevertEssentialThread, EssentialThread, ForumGetAllThread, ForumGetMarkThread, SweetAlert) {
        var vm = this;
        vm.forumId = 1;//默认讨论区的id为1
        vm.page = 1;//默认加载第一页


        vm.loadAll = loadAll;//加载所有置顶和非置顶的帖子
        vm.confirmDelete = confirmDelete;//删除选中的帖子
        vm.confirmTop = confirmTop;//置顶选中的帖子
        vm.cancelTop = cancelTop;//把选中的帖子取消置顶
        vm.loadPage = loadPage;//翻页功能


        loadAll();//进入页面加载所有帖子信息

        /*加载帖子*/
        function loadAll() {
            ForumGetAllThread.get({
                page: vm.page - 1,
                size: 20,
                sort: ["id,asc"],
                forumid: vm.forumId
            }, function success(res) {
                vm.normalThreadList = res.content;
            });
            ForumGetMarkThread.get({
                page: vm.page - 1,
                size: 20,
                sort: ["id,asc"],
                forumid: vm.forumId
            }, function (res) {
                vm.topThreadList = res.content;
                // console.log(JSON.stringify(res));
            });
        }

        /*删除帖子*/
        function confirmDelete() {
            SweetAlert.swal({
                    title: "您要删除帖子吗？",
                    text: "删除帖子后不可恢复",
                    type: "warning",
                    showCancelButton: true,
                    backgroundColor: "#292e3a",
                    confirmButtonColor: "#cb6228",
                    confirmButtonText: "确认删除",
                    //cancelButtonColor: "#2a2e39",
                    cancelButtonText: "放弃"
                },
                function (isConfirm) {
                    if (isConfirm) {
                        var listid = [];
                        vm.normalThreadList.forEach(function (item) {
                            item.isSelected && listid.push(item.id);
                        });
                        vm.topThreadList.forEach(function (item) {
                            item.isSelected && listid.push(item.id);
                        });

                        ThreadsDelete.save({}, {list: listid}, function success(res) {
                            loadAll();
                        }, function error(result) {
                            $state.reload();
                        });
                    }
                }
            );
        }


        /*置顶帖子*/
        function confirmTop() {
            vm.normalThreadList.forEach(function (item) {
                item.isSelected && essentialThread(item.id);
            });
        }

        function essentialThread(threadid) {
            EssentialThread.save({threadid: threadid}, {}, function success(res) {
                loadAll();
            }, function error(result) {
                $state.reload();
            });
        }

        /*取消置頂*/
        function cancelTop() {
            vm.topThreadList.forEach(function (item) {
                item.isSelected && revertEssentialThread(item.id);
            });
        }

        function revertEssentialThread(threadid) {
            RevertEssentialThread.save({threadid: threadid}, {}, function success(res) {
                loadAll();
            }, function error(result) {
                $state.reload();
            });
        }

        /*翻页*/
        function loadPage(page) {
            if (page && page % 1 === 0) {
                vm.page = page;
                loadAll();
            }
        }
    }
})();

