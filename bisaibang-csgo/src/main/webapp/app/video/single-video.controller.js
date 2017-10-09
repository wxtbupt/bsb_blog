/**
 * Created by DELL on 2017/3/13.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('SingleVideoController', SingleVideoController);

    SingleVideoController.$inject = ['YoukuUtils', '$stateParams', 'GetVideoById', 'VideoGetAllCommentByLevel'];

    function SingleVideoController(YoukuUtils, $stateParams, GetVideoById, VideoGetAllCommentByLevel) {
        var vm = this;
        vm.videoId = $stateParams.id;
        vm.transition = transition;
        vm.toPage = toPage;
        vm.page = 1;
        // console.log(vm.videoId);
        function loadAllComment() {
            VideoGetAllCommentByLevel.get({
                id: vm.videoId,
                level:11,
                page: vm.page - 1,
                size: 20,
                sort: 'asc'
            }, {}, function onSuccess(response) {
                vm.commentAll = response.content;
                vm.isFirst = response.first;
                vm.isLast = response.last;
                vm.totalElements = response.totalElements;
                vm.totalPages = response.totalPages;
            });
        }

        GetVideoById.get({id: vm.videoId}, function (result) {
            // console.log(result)
            vm.video = result;
            var videoFrame = YoukuUtils.getFrame(vm.video.url);
            videoFrame.css('height', 563);
            videoFrame.css('width', 1000);
            document.getElementById('sv-video').append(videoFrame[0]);
            loadAllComment();
        });


        function transition(isNext) {
            if (vm.page + isNext > 0) {
                vm.page = vm.page + isNext;
            }
            loadAllComment();
        }

        function toPage(page) {
            vm.page = page;
            loadAllComment();
        }
    }
})();
