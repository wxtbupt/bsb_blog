/**
 * Created by arslan on 3/3/17.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ReplyTextBoxController', ReplyTextBoxController);

    ReplyTextBoxController.$inject = ['createPost', 'toaster', '$state', '$rootScope'];

    function ReplyTextBoxController (createPost, toaster, $state, $rootScope) {
        var vm = this;
        vm.submit = submit;

        $rootScope.$watch('globalVariable.id', function () {
            vm.personName = $rootScope.globalVariable.nickName || $rootScope.globalVariable.phone;
        });

        $rootScope.$watch('globalVariable.avatarUrl', function() {
            vm.avatar = $rootScope.globalVariable.avatarUrl? $rootScope.globalVariable.avatarUrl: 'https://www.battlenet.com.cn/forums/static/images/avatars/avatar-default.png';
        });

        vm.reply = {
            title: '',
            content: vm.postTarget?'@' + vm.postTarget + ' ': '',
            state:  '',
            level: vm.level,
            thread: {id: vm.threadId},
            leaderPost: {id: vm.leaderPostId} || null
        };

        vm.personName = $rootScope.globalVariable.nickName;

        $rootScope.$watch('globalVariable.nickName', function () {
            vm.personName = $rootScope.globalVariable.nickName;
        });

        function submit() {
            console.log(vm.reply)
            createPost.post(vm.reply, function success(result) {
                toaster.pop('success', " ", '已成功');
                $state.reload();
            }, function error(result) {
                toaster.pop('error', " ", '失败');
            });
        }
    }
})();
