(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('teamMember', teamMember);

    teamMember.$inject = [];

    function teamMember() {
        var directive;
        directive = {
            restrict: 'EA',
            templateUrl: 'app/team/team-member/team-member.html',
            scope: {
                team: "=",
                teamMemberState: "=",
                isRegistrations: "="
            },
            controller: 'TeamMemberController',
            controllerAs: 'vm',
            bindToController: true
        };

        return directive;
    }
})();
