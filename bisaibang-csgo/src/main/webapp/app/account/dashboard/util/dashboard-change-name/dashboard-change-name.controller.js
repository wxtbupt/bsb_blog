/**
 * Created by BaoChaoying on 08/04/2017.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardChangeNameController', DashboardChangeNameController);

    DashboardChangeNameController.$inject = ['ChangeteamName', 'toaster'];

    function DashboardChangeNameController(ChangeteamName, toaster) {
        var vm = this;

        vm.submit = submit;

        function submit() {
            ChangeteamName.save({teamid: vm.teamId}, vm.teamName, function (response) {
                vm.teamId = "";
                vm.teamName = "";
                toaster.pop('success', " ", "小队名修改成功");
            }, function (err) {

            })
        }

    }
})();
