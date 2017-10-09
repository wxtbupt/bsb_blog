/**
 * Created by BaoChaoying on 08/04/2017.
 */
angular
    .module('bsbmsoneApp')
    .directive('dashboardMarkUser', dashboardMarkUser);

function dashboardMarkUser() {
    var directive = {
        templateUrl: 'app/account/dashboard/util/dashboard-mark-user/dashboard-mark-user.html',
        restrict: 'EA',
        scope: {
        },
        controller: 'DashboardMarkUserController',
        controllerAs: 'vm'
    };
    return directive;
}
