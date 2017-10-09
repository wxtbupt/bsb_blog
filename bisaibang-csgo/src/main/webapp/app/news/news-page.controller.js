(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('NewsPageController', NewsPageController);

    NewsPageController.$inject = ['$state', 'GetArticleById', 'NewsGetAllComment', '$stateParams','Principal','RegisterService'];

    function NewsPageController($state, GetArticleById, NewsGetAllComment, $stateParams,Principal,RegisterService) {
        var vm = this;
        var articleId = $stateParams.id;
        vm.page = 1;
        vm.isLogIn = false;
        vm.transition = transition;
        vm.toPage = toPage;
        vm.login = login;

        GetArticleById.get({id: articleId},{}, function onSuccess(data) {
            //console.log(data);
            vm.article = data;
            vm.title = data.title;
            vm.createDate = data.createDate.toLocaleString();
            vm.content = data.content;
            loadAllComment();
        }, function onError(error) {
            //console.log(error);
        });

        /*检测是不是登录*/
        Principal.identity().then(function (account) {
            if (account === null) {
                vm.isLogIn = false;
            } else {
                vm.isLogIn = true;
            }
        });

        function login() {
            RegisterService.open('signin', function success() {
                $state.reload();
            }, function fail() {

            });
        }

        function loadAllComment() {
            NewsGetAllComment.get({articleId: $stateParams.id, level: 1, page: vm.page-1, size: 20, sort: 'asc'}, {}, function onSuccess(response) {
                //console.log(response);
                vm.commentAll = response.content;
                vm.isFirst = response.first;
                vm.isLast = response.last;
                vm.totalPages=response.totalPages;
            });
        }

        function transition(isNext) {
            if(vm.page + isNext > 0) {
                vm.page = vm.page + isNext;
            }
            loadAllComment();
        }

        function toPage(page) {
            vm.page=page;
            loadAllComment();
        }

    }
})();
