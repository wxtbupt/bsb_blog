/**
 * Created by BaoChaoying on 08/04/2017.
 */
angular
    .module('bsbmsoneApp')
    .directive('dashboardPushArticle', dashboardPushArticle);

function dashboardPushArticle() {
    var directive = {
        templateUrl: 'app/account/dashboard/util/dashboard-push-article/dashboard-push-article.html',
        restrict: 'EA',
        scope: {
        },
        controller: 'DashboardPushArticleController',
        controllerAs: 'vm'
    };
    return directive;
}
