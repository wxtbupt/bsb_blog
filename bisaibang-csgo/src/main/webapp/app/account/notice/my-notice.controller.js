(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('MyNoticeController', MyNoticeController);

    MyNoticeController.$inject = ['$state', 'toaster','AccountCurrent', 'ReceiverGetAllNotice', 'Principal', 'RegisterService', 'SenderGetAllNotice', 'ReceiverAgreeNotice', 'ReceiverDenialNotice'];

    function MyNoticeController($state, toaster,AccountCurrent,  ReceiverGetAllNotice, Principal, RegisterService, SenderGetAllNotice, ReceiverAgreeNotice, ReceiverDenialNotice) {
        var vm = this;

        vm.nickName = '';

        vm.receiverNotice = [];
        vm.senderNotice = [];
        vm.agreeNotice = agreeNotice;
        vm.denialNotice = denialNotice;

        vm.notices = [];

        vm.order = order;
        // filter排序函数
        function order(obj){
            return obj.notice.createDate;
        }

        Principal.identity().then(function(account) {
            if (account == null) {
                RegisterService.open('signin', function success() {
                    $state.reload();
                }, function fail() {

                });
            } else {

                AccountCurrent.get(function (response) {
                    var result = response;
                    // 代表着已经绑定
                    if(result.nickName){
                        console.log(result);
                        vm.nickName = result.nickName;
                    }
                }, function(res) {

                });


                // 我接收的
                ReceiverGetAllNotice.get({},function (res) {
                    vm.notices = vm.notices.concat(res);
                    vm.receiverNotice = res;
                },function (res) {

                });
                // 我发送的
                SenderGetAllNotice.get({},function (res) {

                    vm.senderNotice = res;
                    vm.notices = vm.notices.concat(res);
                },function (res) {

                })

            }
        });

        function agreeNotice(noticeId) {
            ReceiverAgreeNotice.post({noticeid: noticeId}, {}, function onSuccess(res) {
                // console.log(res);
                if(res.message == "已经同意过其他小队"){
                    toaster.pop('error','','已经同意过其他小队');
                }else{
                    toaster.pop('success','','同意成功');
                }
                $state.reload();
            }, function () {
                toaster.pop('error','','同意失败');
            });
        }

        function denialNotice(noticeId) {
            ReceiverDenialNotice.post({noticeid: noticeId}, {}, function onSuccess() {
                toaster.pop('success','','拒绝成功');
                $state.reload();
            }, function () {
                toaster.pop('error','','拒绝失败');
            });
        }
    }
})();
