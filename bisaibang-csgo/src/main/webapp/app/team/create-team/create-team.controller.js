(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('CreateTeamController', CreateTeamController);

    CreateTeamController.$inject = ['$state', 'CreateTeam', 'ViewerGetMyTeam'];

    function CreateTeamController($state, CreateTeam, ViewerGetMyTeam) {
        var vm = this;
        vm.warn = false;
        vm.lengthWarn = false;
        vm.createTeam = createTeam;


        /*限制队名，8-16个字符*/
        String.prototype.gblen = function () {
            var len = 0;
            for (var i = 0; i < this.length; i++) {
                if (this.charCodeAt(i) > 127 || this.charCodeAt(i) == 94) {
                    len += 2;
                } else {
                    len++;
                }
            }
            return len;
        };


        function createTeam() {
            if (vm.teamName) {
                if (vm.teamName.gblen() <= 16) {
                    var team = {
                        description: "",
                        name: vm.teamName,
                        remark: "",
                        teamAvatar: ""
                    };
                    CreateTeam.save(team, function success(response) {
                        getMyTeamInfo();
                    }, function error(error) {

                    });
                } else {
                    vm.lengthWarn = true;
                    vm.warn = false;
                }
            } else {
                vm.warn = true;
                vm.lengthWarn = false;
            }
        }

        /*得到小队信息*/
        function getMyTeamInfo() {
            ViewerGetMyTeam.get(function (response) {
                /*有小队的话就改变地址栏*/
                $state.go('my-team', {id: response.id});
            }, function (res) {

            });
        }

    }
})();
