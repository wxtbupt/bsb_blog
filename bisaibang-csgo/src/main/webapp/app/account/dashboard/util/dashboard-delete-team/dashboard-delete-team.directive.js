/**
 * Created by BaoChaoying on 08/04/2017.
 */
angular
    .module('bsbmsoneApp')
    .directive('dashboardDeleteTeam', dashboardDeleteTeam);

function dashboardDeleteTeam() {
    var directive = {
        templateUrl: 'app/account/dashboard/util/dashboard-delete-team/dashboard-delete-team.html',
        restrict: 'EA',
        scope: {
        },
        controller: 'DashboardDeleteTeamController',
        controllerAs: 'vm'
    };
    return directive;
}
