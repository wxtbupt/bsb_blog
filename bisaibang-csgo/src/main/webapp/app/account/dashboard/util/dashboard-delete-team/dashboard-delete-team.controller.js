/**
 * Created by BaoChaoying on 08/04/2017.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardDeleteTeamController', DashboardDeleteTeamController);

    DashboardDeleteTeamController.$inject = ['CreateCard', 'GetTaskData', 'TaskRemove', 'toaster', 'RemoveTeam','$state'];

    function DashboardDeleteTeamController (CreateCard, GetTaskData, TaskRemove, toaster, RemoveTeam, $state) {
        var vm = this;


    }
})();
