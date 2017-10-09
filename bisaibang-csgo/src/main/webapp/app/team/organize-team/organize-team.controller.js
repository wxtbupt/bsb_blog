(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('OrganizeTeamController', OrganizeTeamController);

    OrganizeTeamController.$inject = [];

    function OrganizeTeamController() {
        var vm = this;


        vm.createTeam = createTeam;

        function createTeam() {
            vm.isCreateTeam = true;
            vm.isOrganizeTeam = false;
        }
    }
})();
