(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$state', 'RegisterService', 'GetArticle'];

    function HomeController($state, RegisterService, GetArticle) {
        var vm = this;
        vm.counter = 0;
        vm.articles = [];
        vm.size = 0;

        vm.loadArticle= loadArticle;

        GetArticle.get({
            page: vm.counter,
            size: 6
        }, onSuccess, onError);

        function onSuccess(data, headers) {
            console.log(data);
            vm.size = data.totalElements
            vm.articles = data.content;
            vm.articles.forEach(function (item) {
                item.description = item.content.replace(/<\/?.+?>/g,"").slice(0,20);
            });
            console.log(vm.articles)
        }

        function onError(error) {
            console.log(error)
        }

        // function articleDescription() {
        //     return vm.article.replace(/<\/?.+?>/g,"").slice(0,20);
        // }

        function signin() {
            RegisterService.open('signin', function success() {
                $state.reload();
            }, function fail() {

            });
        }

        function loadArticle() {
            console.log(vm.articles.length);
            GetArticle.get({
                page: ++vm.counter,
                size: 6
            }, function(data){
                data.content.forEach(function (item) {
                    item.description = item.content.replace(/<\/?.+?>/g,"").slice(0,20);
                });
                vm.articles =  vm.articles.concat(data.content);
            }, onError);
        }

    }
})();
