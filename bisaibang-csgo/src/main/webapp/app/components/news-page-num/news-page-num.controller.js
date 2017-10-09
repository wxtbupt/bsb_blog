/**
 * Created by rhd on 2017/7/18.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('NewsPageNumController', NewsPageNumController);

    NewsPageNumController.$inject = ["$timeout"];

    function NewsPageNumController($timeout) {
        var vm = this;
        vm.page = 1;
        vm.createPageNum = createPageNum;
        vm.loadMore = loadMore;

        vm.loadMore(vm.page);

        function loadMore(page) {

            /*记录当前页数*/
            vm.page = page;
            /*加载相应页数的评论*/
            vm.toPage({page: page});
            $timeout(function () {
                createPageNum(page);
            }, 500);

        }

        function createPageNum(page) {
            /*根据页数创建数组 并且ng-repeat*/
            vm.pages = [];
            var startPage;//非省略的初始值

            var pageLen = vm.totalPages > 5 ? 5 : vm.totalPages;//小于5时等于最大页数，大于5时等于5
            if (page <= 4) {
                startPage = 1
            } else if (page >= (vm.totalPages - 3)) {
                startPage = vm.totalPages - 4
            } else {
                startPage = page - 2
            }

            for (var i = 1; i <= pageLen; i++) {
                vm.pages.push(startPage);
                startPage++;
            }
            /*判断第一页和最后一页的显示与否*/
            vm.isFirstShow = (vm.pages[2] > 4);
            vm.isLastShow = (vm.pages[2] < (vm.totalPages - 3));
            //console.log(vm.isFirstShow);
            //console.log(vm.isLastShow);
            /*判断上一页和下一页是不是显示*/
            vm.isPrevShow = (page !== 1);
            vm.isNextShow = (vm.totalPages && page !== vm.totalPages);
        }
    }
})();
