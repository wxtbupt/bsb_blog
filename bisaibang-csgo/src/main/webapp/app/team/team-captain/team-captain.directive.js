(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('teamCaptain', teamCaptain);

    teamCaptain.$inject = [];

    function teamCaptain() {
        var directive;
        directive = {
            restrict: 'EA',
            templateUrl: 'app/team/team-captain/team-captain.html',
            scope: {
                team: "=",
                isRegistrations: "=",
                teamMemberState: "="
            },
            controller: 'TeamCaptainController',
            controllerAs: 'vm',
            bindToController: true
        };

        return directive;
    }
})();
