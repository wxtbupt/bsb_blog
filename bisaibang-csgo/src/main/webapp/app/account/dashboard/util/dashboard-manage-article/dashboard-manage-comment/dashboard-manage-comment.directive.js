angular
    .module('bsbmsoneApp')
    .directive('dashboardManageComment', dashboardManageComment);

function dashboardManageComment() {
    var directive = {
        templateUrl: 'app/account/dashboard/util/dashboard-manage-article/dashboard-manage-comment/dashboard-manage-comment.html',
        restrict: 'EA',
        scope: {
            article:'=',
            refresh:'='
        },
        controller: 'DashboardManageCommentController',
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;
}
