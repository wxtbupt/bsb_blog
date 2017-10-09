(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('RuleController', RuleController);

    RuleController.$inject = ['GetMyCard', '$scope', 'Principal', 'LoginService', '$state', 'GetMyPlayersInfo'];

    function RuleController (GetMyCard, $scope, Principal, LoginService, $state, GetMyPlayersInfo) {
        var vm = this;
        vm.showCompetition = true;
        vm.toShow = true;
        vm.toShowGameSystem=toShowGameSystem;
        vm.toShowRuleContent=toShowRuleContent;

        function toShowGameSystem() {
            vm.showCompetition=!vm.showCompetition;
            vm.toShow=!vm.toShow;
        }
        function toShowRuleContent() {
            vm.showCompetition=!vm.showCompetition;
            vm.toShow=!vm.toShow;
        }
    }
})();
