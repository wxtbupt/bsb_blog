(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$state','RegisterService','CheckCityMember','CheckCreateTeam'];

    function HomeController($state,RegisterService,CheckCityMember,CheckCreateTeam) {
        var vm = this;
        vm.personRegister = personRegister;
        vm.teamRegister = teamRegister;

        function personRegister() {
            getMyPersonInfo();
        }

        function teamRegister() {
            getMyTeamInfo();
        }


        /*得到小队信息 并根据url判断情况*/
        function getMyPersonInfo() {
            CheckCityMember.save({}, function (response) {
                if(response.message === "请登录"){
                    signin();
                }else {
                    $state.go("competingInfo", {resigterState: 'person'});
                }
            }, function (error) {
                $state.go("person-register");
            });
        }


        function getMyTeamInfo() {
            CheckCreateTeam.save({}, function (response) {
                if(response.message === "请登录"){
                    signin();
                }else {
                    $state.go("competingInfo",{resigterState:'team'});
                }
            }, function (error) {
                $state.go("team-register");
            });
        }

        function signin() {
            RegisterService.open('signin', function success() {
                $state.reload();
            }, function fail() {

            });
        }

    }
})();
