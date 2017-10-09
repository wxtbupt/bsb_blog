/**
 * Created by arslan on 3/3/17.
 */
angular
    .module('bsbmsoneApp')
    .directive('replyTextBox', replyTextBox);

function replyTextBox() {
    var directive = {
        templateUrl: 'app/forums/util/reply-text-box/reply-text-box.html',
        restrict: 'EA',
        scope: {
            level:'=',
            threadId: '=',
            leaderPostId: '=',
            postTarget: '='
        },
        controller: 'ReplyTextBoxController',
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;
}
