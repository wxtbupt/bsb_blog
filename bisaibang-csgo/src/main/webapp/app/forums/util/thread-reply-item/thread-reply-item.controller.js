(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('threadReplyItemController', threadReplyItemController);

    threadReplyItemController.$inject = ['LeaderPostGetAllPost', '$stateParams', '$rootScope', 'RegisterService'];

    function threadReplyItemController (LeaderPostGetAllPost, $stateParams, $rootScope, RegisterService) {
        var vm = this;
        vm.showBox = false;

        vm.replyPost = replyPost;

        $rootScope.$watch('globalVariable.id', function () {
            vm.personName = $rootScope.globalVariable.nickName || $rootScope.globalVariable.phone;
        });

        $rootScope.$watch('globalVariable.avatarUrl', function() {
            vm.avatar = $rootScope.globalVariable.avatarUrl? $rootScope.globalVariable.avatarUrl: 'https://www.battlenet.com.cn/forums/static/images/avatars/avatar-default.png';
        });

        LeaderPostGetAllPost.get({id: vm.post.id}, {page: 0, size: 5, sort: 'desc'}, function onSuccess(response) {
            // console.log(response.data);
            vm.isFirst = response.data.first;
            vm.isLast = response.data.last;
            vm.innerPostAll = response.data.content;
        });

        function replyPost(postTarget) {
            if($rootScope.globalVariable.id !== -1) {
                vm.postTarget = null;
                vm.showBox = !vm.showBox;
                if(postTarget) {
                    vm.postTarget = postTarget;
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
