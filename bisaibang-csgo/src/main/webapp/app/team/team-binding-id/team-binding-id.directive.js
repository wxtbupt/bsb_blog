(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('teamBindingId', teamBindingId);

    teamBindingId.$inject = [];
    function teamBindingId() {
        var directive;
        directive = {
            restrict: 'EA',
            templateUrl: 'app/team/team-binding-id/team-binding-id.html',
            scope: {},
            controller: 'TeamBindingIdController',
            controllerAs: 'vm',
            bindToController: true
        };

        return directive;
    }
})();
