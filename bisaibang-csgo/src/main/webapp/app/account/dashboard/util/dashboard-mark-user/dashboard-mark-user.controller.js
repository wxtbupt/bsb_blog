/**
 * Created by BaoChaoying on 08/04/2017.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardMarkUserController', DashboardMarkUserController);

    DashboardMarkUserController.$inject = ['CreateCard', 'GetTaskData', 'TaskRemove', 'toaster', 'RemoveTeam','$state'];

    function DashboardMarkUserController (CreateCard, GetTaskData, TaskRemove, toaster, RemoveTeam, $state) {
        var vm = this;


    }
})();
