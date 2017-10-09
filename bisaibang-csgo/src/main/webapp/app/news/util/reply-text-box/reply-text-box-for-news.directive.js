/**
 * Created by arslan on 3/3/17.
 */
angular
    .module('bsbmsoneApp')
    .directive('replyTextBoxForNews', replyTextBoxForNews);

function replyTextBoxForNews() {
    var directive = {
        templateUrl: 'app/news/util/reply-text-box/reply-text-box-for-news.html',
        restrict: 'EA',
        scope: {
            level:'=',
            articleId: '=',
            leaderPostId: '=',
            commentTarget: '='
        },
        controller: 'ReplyTextBoxForNewsController',
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;
}
