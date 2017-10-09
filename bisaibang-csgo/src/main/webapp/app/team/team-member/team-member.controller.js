(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('TeamMemberController', TeamMemberController);

    TeamMemberController.$inject = ['$stateParams', '$state','$location', 'ViewerLeaveTeam','ViewerApply','DeleteTeamApply'];

    function TeamMemberController($stateParams, $state,$location, ViewerLeaveTeam,ViewerApply,DeleteTeamApply) {
        var vm = this;

        vm.leaveTeam = leaveTeam;//退出小队
        vm.teamApply = teamApply;//申请加入小队
        vm.teamCancelApply = teamCancelApply;//取消申请

        vm.shareLink=$location.absUrl();

        function leaveTeam() {
            ViewerLeaveTeam.get({teamid: $stateParams.id}, {}, function success(response) {
                $state.reload();
            }, function error(res) {

            })
        }

        function teamApply() {
            ViewerApply.get({teamid: $stateParams.id}, function (response) {
                vm.teamMemberState.isApply=false;
                vm.teamMemberState.cancelApply=true;
            }, function (res) {
                console.log(res);
            })
        }

        function teamCancelApply() {
            DeleteTeamApply.save({teamid: $stateParams.id}, {}, function (response) {
                vm.teamMemberState.isApply=true;
                vm.teamMemberState.cancelApply=false;
            }, function (res) {

            })
        }
    }
})();
