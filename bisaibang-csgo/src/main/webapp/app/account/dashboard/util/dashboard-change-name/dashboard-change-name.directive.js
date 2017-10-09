/**
 * Created by BaoChaoying on 08/04/2017.
 */
angular
    .module('bsbmsoneApp')
    .directive('dashboardChangeName', dashboardChangeName);

function dashboardChangeName() {
    var directive = {
        templateUrl: 'app/account/dashboard/util/dashboard-change-name/dashboard-change-name.html',
        restrict: 'EA',
        scope: {
        },
        controller: 'DashboardChangeNameController',
        controllerAs: 'vm'
    };
    return directive;
}
