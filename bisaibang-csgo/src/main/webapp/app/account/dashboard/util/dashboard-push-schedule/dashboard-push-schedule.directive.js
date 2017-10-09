/**
 * Created by BaoChaoying on 08/04/2017.
 */
angular
    .module('bsbmsoneApp')
    .directive('dashboardPushSchedule', dashboardPushSchedule);

function dashboardPushSchedule() {
    var directive = {
        templateUrl: 'app/account/dashboard/util/dashboard-push-schedule/dashboard-push-schedule.html',
        restrict: 'EA',
        scope: {
        },
        controller: 'DashboardPushScheduleController',
        controllerAs: 'vm'
    };
    return directive;
}
