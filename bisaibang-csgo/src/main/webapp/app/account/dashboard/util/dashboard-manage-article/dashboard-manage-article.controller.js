/**
 * Created by arslan on 1/31/17.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardManageArticleController', DashboardManageArticleController);

    DashboardManageArticleController.$inject = ['$timeout', '$rootScope', 'DeleteArticle', '$scope', 'GetArticle', '$state', 'AlertService', 'PaginationUtil', 'paginationConstants'];

    function DashboardManageArticleController($timeout, $rootScope, DeleteArticle, $scope, GetArticle, $state, AlertService, PaginationUtil, paginationConstants) {
        var vm = this;
        vm.page = 1;
        var params = {
            page: vm.page,
            sort: 'id,asc',
            search: null
        };
        var pagingParams = {
            page: PaginationUtil.parsePage(params.page),
            sort: params.sort,
            predicate: PaginationUtil.parsePredicate(params.sort),
            ascending: PaginationUtil.parseAscending(params.sort),
            search: params.search
        };
        vm.checkboxValue = [];
        vm.articles = [];
        vm.confirmDelete = confirmDelete;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.setHomeNews = setHomeNews;
        vm.selectAll = selectAll;
        vm.gotoEdit = gotoEdit;
        vm.deleteComment = deleteComment;

       function deleteComment(article) {
           vm.article = article;
           vm.refresh = 'manageComment';
       }


        $scope.$watch('vm.refresh', function () {
            if (vm.refresh == 'manageArticle')
                loadAll();
        });

        function confirmDelete() {
            vm.checkboxValue.forEach(function (item, index) {
                if (item == true)
                    DeleteArticle.save({id: index},null,function () {

                    });
            });
            $timeout(function () {
                loadAll();
            },1000)
        }

        function selectAll(state) {
            vm.articles.forEach(function (item) {
                vm.checkboxValue[item.id] = state;
            })
        }

        function gotoEdit(article) {
            vm.article = article;
            vm.refresh = 'postArticle';
        }

        function loadAll() {
            vm.checkboxValue = [];
            GetArticle.get({
                page: vm.page - 1,
                size: 10,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                //vm.links = ParseLinks.parse(headers('link'));
                vm.articles = data.content;
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }

        function setHomeNews(news) {
            $rootScope.homeNews = news;
        }
    }
})();
