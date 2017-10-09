/**
 * Created by arslan on 3/3/17.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ReplyTextBoxForNewsController', ReplyTextBoxForNewsController);

    ReplyTextBoxForNewsController.$inject = ['createComment', 'toaster', '$state', '$rootScope', 'Principal', 'RegisterService'];

    function ReplyTextBoxForNewsController (createComment, toaster, $state, $rootScope, Principal, RegisterService) {
        var vm = this;
        vm.submit = submit;

        vm.avatar = 'https://www.battlenet.com.cn/forums/static/images/avatars/avatar-default.png';
        $rootScope.$watch('globalVariable.avatarUrl', function() {
            vm.avatar = $rootScope.globalVariable.avatarUrl? $rootScope.globalVariable.avatarUrl: 'https://www.battlenet.com.cn/forums/static/images/avatars/avatar-default.png';
        });

        vm.reply = {
            title: '',
            content: vm.commentTarget?'@' + vm.commentTarget + ' ': '',
            state:  '',
            level: vm.level,
            article: {id: vm.articleId},
            video: {id: 0},
            leaderComment: {id: vm.leaderPostId} || null
        };

        $rootScope.$watch('globalVariable.id', function () {
            vm.personName = $rootScope.globalVariable.nickName || $rootScope.globalVariable.phone;
        });

        function submit() {
            Principal.identity().then(function(account) {
                if (account == null) {
                    RegisterService.open('signin', function success() {
                        $state.reload();
                    }, function fail() {

                    });
                } else {
                    createComment.post(vm.reply, function success(result) {
                        toaster.pop('success', " ", '已成功');
                        $state.reload();
                    }, function error(result) {
                        toaster.pop('error', " ", '失败');
                    });
                }
            });


        }
    }
})();
