(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('createTeam', createTeam);

    createTeam.$inject = [];

    function createTeam() {
        var directive;
        directive = {
            restrict: 'EA',
            templateUrl: 'app/team/create-team/create-team.html',
            scope: {},
            controller: 'CreateTeamController',
            controllerAs: 'vm',
            bindToController: true
        };

        return directive;
    }
})();

