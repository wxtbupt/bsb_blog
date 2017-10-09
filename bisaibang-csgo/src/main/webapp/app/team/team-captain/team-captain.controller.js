(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('TeamCaptainController', TeamCaptainController);

    TeamCaptainController.$inject = ['$state', '$stateParams', '$location', 'DeleteTeamById', 'AddTeamMember', 'DeleteTeamMember', 'TeamRegistrations', 'toaster', 'ExitRegistrations', 'SweetAlert'];

    function TeamCaptainController($state, $stateParams, $location, DeleteTeamById, AddTeamMember, DeleteTeamMember, TeamRegistrations, toaster, ExitRegistrations, SweetAlert) {
        var vm = this;

        vm.warn = false;
        vm.deleteTeam = deleteTeam;
        vm.addTeamMember = addTeamMember;
        vm.deleteTeamMember = deleteTeamMember;
        vm.deleteTeamApply = deleteTeamApply;
        vm.register = register;
        vm.exitRegister = exitRegister;

        /*分享链接得到当前地址框*/
        vm.shareLink = $location.absUrl();

        /*解散队伍*/
        function deleteTeam() {
            SweetAlert.swal({
                    title: "确定要解散队伍吗？",
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    backgroundColor: "#292e3a",
                    confirmButtonColor: "#cb6228",
                    confirmButtonText: "确认解散队伍",
                    //cancelButtonColor: "#2a2e39",
                    cancelButtonText: "取消"
                },
                function (isConfirm) {
                    if (isConfirm) {
                        DeleteTeamById.save({teamid: $stateParams.id}, {}, function (response) {
                            $state.go('my-team', {id: ""})
                        }, function (res) {

                        })
                    }
                });
        }

        /*通过申请*/
        function addTeamMember(userid) {
            var member = {
                state: "main",
                teamid: $stateParams.id,
                userid: userid
            };
            AddTeamMember.save(member, function (response) {
                $state.reload();
            }, function (res) {

            })
        }

        /*踢出该队员*/
        function deleteTeamMember(userid) {
            SweetAlert.swal({
                    title: "确定要踢出该队员吗？",
                    text: "踢出后还可以再次申请",
                    type: "warning",
                    showCancelButton: true,
                    backgroundColor: "#292e3a",
                    confirmButtonColor: "#cb6228",
                    confirmButtonText: "确认踢出该队员",
                    cancelButtonText: "取消"
                },
                function (isConfirm) {
                    if (isConfirm) {
                        DeleteTeamMember.save({teamid: $stateParams.id, userid: userid}, {}, function (response) {
                            $state.reload();
                        }, function (res) {
                            $state.reload();
                        })
                    }
                });
        }

        /*拒绝申请*/
        function deleteTeamApply(userid) {
            SweetAlert.swal({
                    title: "确定要拒绝该申请吗？",
                    text: "拒绝后还可以再次申请",
                    type: "warning",
                    showCancelButton: true,
                    backgroundColor: "#292e3a",
                    confirmButtonColor: "#cb6228",
                    confirmButtonText: "确认拒绝该队员",
                    cancelButtonText: "取消"
                },
                function (isConfirm) {
                    if (isConfirm) {
                        DeleteTeamMember.save({teamid: $stateParams.id, userid: userid}, {}, function (response) {
                            $state.reload();
                        }, function (res) {
                            $state.reload();
                        })
                    }
                });
        }


        /*比赛报名*/
        function register() {
            if (vm.team.member.length >= 5) {
                SweetAlert.swal({
                        title: "",
                        text: "确认提交报名信息后将不可修改战队信息， \r\n9月14日24:00前队长可解散并重新建立战队",
                        type: "warning",
                        showCancelButton: true,
                        backgroundColor: "#292e3a",
                        confirmButtonColor: "#cb6228",
                        confirmButtonText: "确认报名",
                        //cancelButtonColor: "#2a2e39",
                        cancelButtonText: "取消"
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            TeamRegistrations.save({teamid: $stateParams.id}, {}, function (response) {
                                vm.isRegistrations = false;
                            }, function (res) {

                            })
                        }
                    });
            } else {
                vm.warn = true;
            }

        }

        /*退出报名*/
        function exitRegister() {
            SweetAlert.swal({
                    title: "确定要退出报名吗？",
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    backgroundColor: "#292e3a",
                    confirmButtonColor: "#cb6228",
                    confirmButtonText: "确认退出报名",
                    //cancelButtonColor: "#2a2e39",
                    cancelButtonText: "取消"
                },
                function (isConfirm) {
                    if (isConfirm) {
                        ExitRegistrations.save({teamid: $stateParams.id}, {}, function (response) {
                            vm.isRegistrations = true;
                            toaster.pop('success', " ", "退出报名成功");
                        }, function (res) {

                        })
                    }
                });
        }
    }
})();
