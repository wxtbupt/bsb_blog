(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('CreateThreadController', CreateThreadController);

    CreateThreadController.$inject = ['$rootScope', '$state', 'ThreadCreate', 'Principal'];

    function CreateThreadController($rootScope, $state, ThreadCreate, Principal) {
        var vm = this;

        vm.threadHead = '';
        vm.threadBody = '';
        vm.forumid = 1;
        vm.postThread = postThread;
        vm.person = null;

        $rootScope.$watch('globalVariable.avatarUrl', function() {
            vm.avatar = $rootScope.globalVariable.avatarUrl? $rootScope.globalVariable.avatarUrl: 'https://www.battlenet.com.cn/forums/static/images/avatars/avatar-default.png';
        });

        vm.stripFormat = function (text) {
            return text ? String(text).replace(/<[^>]+>/gm, '') : '';
        };

        function postThread(){
            var thread = {
                'name':vm.threadHead,
                'title':vm.threadHead,
                'content':vm.threadBody
            };
            Principal.identity().then(function (account) {
                if (account == null) {
                    SigninupService.open('signin', function success() {
                        $state.reload();
                    }, function fail() {

                    });
                } else {
                    ThreadCreate.save({forumid:vm.forumid},thread,function success(res) {
                        $state.go("station-forum-thread", {"threadid":res.id, "id":vm.forumid})
                    }, function error(result) {
                        $state.reload();
                    });
                }
            });



        }
    }
})();
