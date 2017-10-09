(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('organizeTeam', organizeTeam);

    organizeTeam.$inject = [];
    function organizeTeam() {
        var directive;
        directive = {
            restrict: 'EA',
            templateUrl: 'app/team/organize-team/organize-team.html',
            scope: {
                isCreateTeam:"=",
                isOrganizeTeam:"="
            },
            controller: 'OrganizeTeamController',
            controllerAs: 'vm',
            bindToController: true
        };

        return directive;
    }
})();
