/**
 * Created by OlyLis on 2017/3/3.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ForumsNewThemeController', ForumsNewThemeController);

    ForumsNewThemeController.$inject = ['createThread', '$state', 'AccountCurrent', '$rootScope'];

    function ForumsNewThemeController (createThread, $state, AccountCurrent, $rootScope) {
        var vm = this;
        vm.cancelNewTheme = cancelNewTheme;
        vm.publishThread = publishThread;
        vm.dataIsReady = false;
        vm.author = null;
        // vm.autoTest = autoTest;

        // console.log(vm.forumsId);

        vm.thread = {
            "authorName": null,
            "content": "",
            "forum": {
                "id": 1
            },
            "creator": {
                "nickName": "string"
            },
            "state": "正常",
            "title": "",
            "section": vm.forumsId,
            "color": ""
        };

        $rootScope.$watch('globalVariable.id', function () {
            // 判断是否为登录状态
            if($rootScope.globalVariable.id !== -1) {
                vm.authorName = $rootScope.globalVariable.nickName || $rootScope.globalVariable.phone;
                vm.avatarUrl = $rootScope.globalVariable.avatarUrl;
                vm.thread.authorName = vm.authorName;
            }
        });

        // AccountCurrent.get(function onSuccess(response) {
        //     // console.log(response);
        //     vm.author = response;
        //     // console.log(vm.author);
        //     vm.thread.authorName = vm.author.nickName;
        //     }
        // );


        function cancelNewTheme() {
            vm.isShow = false;
        }

        function publishThread() {
            // console.log(vm.thread);
            createThread.post(vm.thread, function onSuccess(result) {
                // console.log(result);
                if(result.data.message == 'thread创建成功') {
                    vm.thread.title = "";
                    vm.thread.content = "";
                    cancelNewTheme();
                    $state.go($state.$current.name, {page: 1}, {reload: true});
                    // console.log('thread创建成功');
                }
            });
        }

        //创建50个帖子（测试用）
        // function autoTest() {
        //     vm.thread.content = "随便写点什么";
        //     for(var i=0;i<50;i++) {
        //         vm.thread.title = "测试帖子";
        //         createThread.post(vm.thread, function onSuccess(result) {
        //             if(result.data.message == 'thread创建成功') {
        //                 // console.log('thread创建成功');
        //             }
        //         });
        //     }
        //     $state.go($state.$current.name, {page: 1}, {reload: true});
        // }
    }
})();
