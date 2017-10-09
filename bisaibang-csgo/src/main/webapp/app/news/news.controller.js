(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('NewsListController', NewsListController);

    NewsListController.$inject = ['GetArticle'];

    function NewsListController(GetArticle) {
        var vm = this;

        vm.page = 1;
        vm.pageSize=10;
        vm.articles=[];

        vm.loadPage = loadPage;

        loadAll();

        function loadAll() {
            GetArticle.get({
                page: vm.page-1,
                size: vm.pageSize,
                sort: ['id,asc']
            }, onSuccess, onError);

            function onSuccess(response) {
                response.content.forEach(function (item) {
                    vm.articles.push(item);
                })
                vm.totalPages = response.totalPages;
            }

            function onError(error) {
               //console.log('失败了');
            }
        }

        function loadPage() {
            vm.page++;
            loadAll();
        }




    }
})();
