/**
 * Created by OlyLis on 2017/3/14.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('videoReplyItem', videoReplyItem);

    function videoReplyItem() {
        var directive = {
            templateUrl: 'app/video/util/video-reply-item/video-reply-item.html',
            restrict: 'EA',
            scope: {
                videoId: '=',
                comment: '='
            },
            controller: 'VideoReplyItemController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;
    }
})();
