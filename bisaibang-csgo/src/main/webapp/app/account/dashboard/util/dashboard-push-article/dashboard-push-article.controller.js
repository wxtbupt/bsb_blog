/**
 * Created by BaoChaoying on 08/04/2017.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardPushArticleController', DashboardPushArticleController);

    DashboardPushArticleController.$inject = ['toaster', 'GetHomePage', 'UpdateHomePageArticle'];

    function DashboardPushArticleController(toaster, GetHomePage, UpdateHomePageArticle) {
        var vm = this;

        var articleIdStr = "";
        vm.thumbnailUrl = "";
        vm.submit = submit;

        getHomePage();

        /*得到所有首页新闻id*/
        function getHomePage() {
            GetHomePage.get(function (response) {
                articleIdStr = response.article_config;
            }, function (err) {

            })
        }

        /*发送新闻id 使articleIdStr存储的id永远是6个*/
        function submit() {
            if (vm.articleId) {
                if (!articleIdStr) {
                    articleIdStr = vm.articleId;
                } else {
                    articleIdStr = vm.articleId + "&" + articleIdStr;
                }
                if (articleIdStr.split("&").length > 6) {
                    articleIdStr = articleIdStr.split("&").slice(0, 6).join("&");
                }
                UpdateHomePageArticle.save({article_config: articleIdStr}, function (response) {
                    vm.articleId = "";
                    toaster.pop('success', " ", "首页新闻添加成功");
                }, function (err) {

                })
            }
        }
    }
})();
