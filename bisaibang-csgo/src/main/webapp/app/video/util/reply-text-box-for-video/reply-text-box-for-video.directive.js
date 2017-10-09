/**
 * Created by arslan on 3/3/17.
 */
angular
    .module('bsbmsoneApp')
    .directive('replyTextBoxForVideo', replyTextBoxForVideo);

function replyTextBoxForVideo() {
    var directive = {
        templateUrl: 'app/video/util/reply-text-box-for-video/reply-text-box-for-video.html',
        restrict: 'EA',
        scope: {
            level:'=',
            videoId: '=',
            leaderPostId: '=',
            commentTarget: '='
        },
        controller: 'ReplyTextBoxForVideoController',
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;
}
