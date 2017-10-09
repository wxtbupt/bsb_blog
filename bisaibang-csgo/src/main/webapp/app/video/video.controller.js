/**
 * Created by DELL on 2017/3/13.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('VideoController', VideoController);

    VideoController.$inject = ['VideoGetAll','GetAllVideoTag', 'VideoGetAllByTag','toaster', 'pagingParams', '$state'];

    function VideoController (VideoGetAll,GetAllVideoTag,VideoGetAllByTag, toaster, pagingParams, $state) {
        var vm = this;

        vm.searchVideo = searchVideo;
        vm.lastPage = lastPage;
        vm.nextPage = nextPage;

        vm.currentVideoTag = 0;
        vm.selectVideoTag = selectVideoTag;

        vm.page = pagingParams.page;

        vm.getVideoAll = getVideoAll;

        VideoGetAll.query({page: vm.page - 1, size: 9, sort: 'asc'}, {}, function onSuccess(response) {
            // console.log(response);
            vm.videoAll = response.content;
            vm.isFirst = response.first;
            vm.isLast = response.last;
        });

        function getVideoAll() {
            VideoGetAll.query({page: vm.page - 1, size: 9, sort: 'asc'}, {}, function onSuccess(response) {
                // console.log(response);
                vm.videoAll = response.content;
                vm.isFirst = response.first;
                vm.isLast = response.last;
                // console.log(vm.currentVideoTag);
            });
        }

        // GetAllVideoTag.get(function (result) {
        //     vm.tags = result;
        //     // console.log(vm.tags[0].id);
        // });



        function searchVideo() {
            toaster.pop('error', '', '点什么点，还没写呢！');
        }
        function lastPage() {
            if(!vm.isFirst) {
                transition(-1);
            }
        }

        function nextPage() {
            if(!vm.isLast) {
                transition(1);
            }
        }

        function selectVideoTag(num) {
            vm.currentVideoTag = num;
            VideoGetAllByTag.query({page: vm.page - 1, size: 9, sort: 'asc',id: num},{},function onSuccess(response) {
                // console.log(response);
                vm.videoAll = response.content;
                vm.isFirst = response.first;
                vm.isLast = response.last;
                // console.log(vm.currentVideoTag);
            });
        }

        function transition(pageCount) {
            $state.transitionTo($state.$current, {
                page: vm.page + pageCount
            });
        }
    }
})();
