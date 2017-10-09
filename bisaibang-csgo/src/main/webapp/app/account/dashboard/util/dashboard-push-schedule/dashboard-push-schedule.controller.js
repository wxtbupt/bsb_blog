/**
 * Created by BaoChaoying on 08/04/2017.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
            .controller('DashboardPushScheduleController', DashboardPushScheduleController);

    DashboardPushScheduleController.$inject = ['AvatarUploadService','CreateVideo','toaster'];

    function DashboardPushScheduleController (AvatarUploadService,CreateVideo,toaster) {
        var vm = this;
        /*vm.Upload = Upload;
        vm.submit = submit;
        vm.thumbnailUploadStatus = '';
        if(!vm.video) {
            //若修改vm.video初始化数据，请在dashboard-sidebar.controller.js中同时进行修改
            vm.video = {
                thumbnailUrl: '',
            };
        }

        function Upload() {
            AvatarUploadService.open("ArticleImage", {aspectRatio:16/9,compress:true, width:350},
                function (result) {
                    if(result){
                        vm.video.thumbnailUrl = 'https://msone.bisaibang.com/' + result;
                        vm.thumbnailUploadStatus = '上传成功';
                    }
                }, function () {
                    vm.thumbnailUploadStatus = '发生虾米事情了?上传未成功';
                });
        }

        function submit() {
            console.log(vm.video.thumbnailUrl);
            if(vm.video.thumbnailUrl){
                CreateVideo.save({}, vm.video, function success(result) {
                    toaster.pop('success', " ", '已成功');
                    vm.video = null;
                }, function error(result) {
                    toaster.pop('error', " ", '失败');
                });
            }
        }*/
    }
})();
