/**
 * Created by gsy on 2017/3/25.
 */

angular
    .module('bsbmsoneApp')
    .directive('dashboardManageForum', dashboardManageForum);

function dashboardManageForum() {
    var directive = {
        templateUrl: 'app/account/dashboard/util/dashboard-manage-forum/dashboard-manage-forum.html',
        restrict: 'EA',
        scope: {
            refresh:'='
        },
        controller: 'DashboardManageForumController',
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;
}
