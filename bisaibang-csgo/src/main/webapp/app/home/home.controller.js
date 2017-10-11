(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$state','RegisterService', 'GetArticle'];

    function HomeController($state,RegisterService, GetArticle) {
        var vm = this;

        GetArticle.get({
            page: 0,
            size: 10
        }, onSuccess, onError);

        function onSuccess(data, headers) {
            //console.log(data)
            vm.articles = data.content;
            articleDescriptions();
        }

        function onError(error) {
            console.log(error)
        }


        function articleDescriptions() {
            var articleNum = vm.articles.length;
            var articleDescription = new Array();
            for (var i = 0; i < articleNum; i++) {
                articleDescription[i] = vm.articles[i].content.slice(0, 20);
            }
            console.log(articleDescription)
        }



        function signin() {
            RegisterService.open('signin', function success() {
                $state.reload();
            }, function fail() {

            });
        }

    }
})();
