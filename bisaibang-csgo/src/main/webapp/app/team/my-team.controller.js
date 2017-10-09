(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('MyTeamController', MyTeamController);

    MyTeamController.$inject = ['$state', '$stateParams', 'AccountCurrent', 'ViewerGetMyTeam', 'Principal', 'RegisterService', 'ViewerGetAll', 'toaster', 'getRegistrationsInfo'];

    function MyTeamController($state, $stateParams, AccountCurrent, ViewerGetMyTeam, Principal, RegisterService, ViewerGetAll, toaster, getRegistrationsInfo) {
        var vm = this;

        var userId = "";
        var userTeamId = "";
        vm.isTeamBindingId = false;//战网ID是否绑定
        vm.isOrganizeTeam = false;//是否已经组队
        vm.isCreateTeam = false;//是否创建小队
        vm.isTeamMember = false;//队员页
        vm.isTeamCaptain = false;//队长页
        vm.applyDisabled = true;//能否申请
        vm.isRegistrations = true;//是否报名
        vm.team = {};
        vm.teamMemberState = {
            isCaptain:false,
            isApply: false,
            cancelApply: false,
            isMember: false,
            teamName:""
        };

        /*判断是不是报名*/
        getRegistrationsInfo.save(function (response) {
            if (response.message === "已报名") {
                vm.isRegistrations = false;
            }
        }, function (res) {

        });

        /*检测是不是登录*/
        Principal.identity().then(function (account) {
            if (account === null) {
                RegisterService.open('signin', function success() {
                    $state.reload();
                }, function fail() {

                });
            } else {
                checkBindingId();
            }
        });


        /*检测是不是绑定站网站id*/
        function checkBindingId() {
            AccountCurrent.get(function (response) {
                /*有nickName表示*/
                if (response.nickName) {
                    userId = response.id;
                    getMyTeamInfo();
                } else {
                    vm.isTeamBindingId = true;
                }
            }, function (res) {

            });
        }


        /*得到小队信息 并根据url判断情况*/
        function getMyTeamInfo() {
            ViewerGetMyTeam.get({},function (response) {
                if(response.message === "未加入任何小站"){
                    if ($stateParams.id) {
                        viewerGetAll($stateParams.id);
                    } else {
                        vm.isOrganizeTeam = true;
                    }
                }else {
                    userTeamId = response.id;
                    if ($stateParams.id) {
                        viewerGetAll($stateParams.id);
                    } else {
                        $state.go('my-team', {id: response.id});
                        viewerGetAll(response.id);
                    }
                }
            }, function (res) {

            });
        }


        /*得到所有队员的信息 并判断有无此小队*/
        function viewerGetAll(teamid) {
            ViewerGetAll.query({teamid: teamid}, function (response) {
                if (response.length === 0) {
                    vm.isTeamMemberApply = false;
                    toaster.pop('error', " ", "无此队伍");
                } else {
                    vm.teamMemberState.teamName=response[0].team.name;
                    /*判断有无队伍 有队伍的话是不是自己的队伍*/
                    if (!userTeamId) {
                        vm.isTeamMember = true;
                        vm.teamMemberState.isApply = true;
                    }else if(userTeamId !== response[0].team.id){
                        vm.isTeamMember = true;
                    }


                    vm.team.member = [];
                    vm.team.applyMember = [];
                    response.forEach(function (item) {
                        /*队员队长页的数据*/
                        if (item.state === "captain") {
                            vm.team.captain = item;
                            if (item.user.id === userId) {
                                vm.isTeamCaptain = true;
                                vm.teamMemberState.isCaptain=true;
                            }
                        } else if (item.state === "member") {
                            vm.team.member.push(item);
                            if (item.user.id === userId) {
                                vm.isTeamMember = true;
                                vm.teamMemberState.isMember = true;
                            }
                        } else if (item.state === "apply") {
                            vm.team.applyMember.push(item);
                            if (item.user.id === userId) {
                                vm.isTeamMember = true;
                                vm.teamMemberState.cancelApply = true;
                            }
                        }
                    });


                }
            }, function (res) {

            });
        }
    }
})();
