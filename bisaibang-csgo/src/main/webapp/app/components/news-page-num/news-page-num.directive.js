/**
 * Created by rhd on 2017/7/18.
 */
angular
    .module('bsbmsoneApp')
    .directive('newsPageNum', newsPageNum);

function newsPageNum() {
    var directive = {
        templateUrl: "app/components/news-page-num/news-page-num.html",
        restrict: 'EA',
        scope: {
            totalPages: "=",
            toPage: "&"
        },
        controller: 'NewsPageNumController',
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;
}
