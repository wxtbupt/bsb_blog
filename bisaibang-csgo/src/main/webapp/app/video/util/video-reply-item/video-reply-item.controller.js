(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('VideoReplyItemController', VideoReplyItemController);

    VideoReplyItemController.$inject = ['LeaderCommentGetAllComment', '$rootScope', '$rootScope', 'RegisterService'];

    function VideoReplyItemController (LeaderCommentGetAllComment, $rootScope, RegisterService) {
        var vm = this;
        vm.showBox = false;

        vm.replyComment = replyComment;

        $rootScope.$watch('globalVariable.id', function () {
            vm.personName = $rootScope.globalVariable.nickName || $rootScope.globalVariable.phone;
        });

        $rootScope.$watch('globalVariable.avatarUrl', function() {
            vm.avatar = $rootScope.globalVariable.avatarUrl? $rootScope.globalVariable.avatarUrl: 'https://www.battlenet.com.cn/forums/static/images/avatars/avatar-default.png';
        });

        LeaderCommentGetAllComment.get({id: vm.comment.id}, {page: 0, size: 5, sort: 'desc'}, function onSuccess(response) {
            // console.log(response.data);
            vm.isFirst = response.data.first;
            vm.isLast = response.data.last;
            vm.innerCommentAll = response.data.content;
        });

        function replyComment(commentTarget) {
            // console.log(commentTarget);
            if($rootScope.globalVariable.id !== -1) {
                vm.commentTarget = null;
                vm.showBox = !vm.showBox;
                if(commentTarget) {
                    vm.commentTarget = commentTarget;
                }
            }else {
                RegisterService.open('signin', function success() {
                    $state.reload();
                }, function fail() {

                });
            }


        }
    }
})();
