/**
 * Created by OlyLis on 2017/3/14.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('threadReplyItem', threadReplyItem);

    function threadReplyItem() {
        var directive = {
            templateUrl: 'app/forums/util/thread-reply-item/thread-reply-item.html',
            restrict: 'EA',
            scope: {
                threadId: '=',
                post: '='
            },
            controller: 'threadReplyItemController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;
    }
})();
