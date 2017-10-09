/**
 * Created by BaoChaoying on 08/04/2017.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardCheckListController', DashboardCheckListController);

    DashboardCheckListController.$inject = ['$state', 'toaster', 'GetTaskData', 'AdminGetAllRegistrations', 'SweetAlert', 'ExitRegistrations'];

    function DashboardCheckListController($state, toaster, GetTaskData, AdminGetAllRegistrations, SweetAlert, ExitRegistrations) {
        var vm = this;
        vm.page = 1;
        vm.loadAll = loadAll;
        vm.exitRegister = exitRegister;
        vm.loadPage = loadPage;

        vm.loadAll();

        /*注册用户   绑定战网ID的人数  注册队伍数量*/
        GetTaskData.get({}, function (res) {
            vm.content = res;
        }, function () {

        });

        /*加载所有队伍 */
        function loadAll() {
            AdminGetAllRegistrations.get({
                page: vm.page - 1,
                size: 10,
                sort: ""
            }, function (result) {
                vm.team = result;
            }, function (error) {

            });
        }


        /*退出报名*/
        function exitRegister(teamId) {
            SweetAlert.swal({
                    title: "确定把该队伍踢出报名队伍？",
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    backgroundColor: "#292e3a",
                    confirmButtonColor: "#cb6228",
                    confirmButtonText: "确认踢出队伍",
                    //cancelButtonColor: "#2a2e39",
                    cancelButtonText: "取消"
                },
                function (isConfirm) {
                    if (isConfirm) {
                        ExitRegistrations.save({teamid: teamId}, {}, function (response) {
                            toaster.pop('success', " ", "退出报名成功");
                            $state.reload();
                        }, function (res) {

                        })
                    }
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
