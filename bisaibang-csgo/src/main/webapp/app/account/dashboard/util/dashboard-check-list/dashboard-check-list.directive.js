/**
 * Created by BaoChaoying on 08/04/2017.
 */
angular
    .module('bsbmsoneApp')
    .directive('dashboardCheckList', dashboardCheckList);

function dashboardCheckList() {
    var directive = {
        templateUrl: 'app/account/dashboard/util/dashboard-check-list/dashboard-check-list.html',
        restrict: 'EA',
        scope: {
        },
        controller: 'DashboardCheckListController',
        controllerAs: 'vm'
    };
    return directive;
}
