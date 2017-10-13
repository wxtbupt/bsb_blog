/**
 * Created by BaoChaoying on 08/04/2017.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardPushArticleController', DashboardPushArticleController);

    DashboardPushArticleController.$inject = ['toaster', 'GetHomePage', 'CreateArticle'];

    function DashboardPushArticleController(toaster, GetHomePage, CreateArticle) {
        var vm = this;

        var articleIdStr = "";
        vm.thumbnailUrl = "";
        vm.content = "";
        vm.article = {};
        vm.getHtml = getHtml;
        vm.getMD = getMD;

        getHomePage();


        var editor = editormd("editormd", {
            path : "bower_components/editor.md/lib/", // Autoload modules mode, codemirror, marked... dependents libs path
            saveHTMLToTextarea : true,
            onload : function() {
                // alert("onload");
                this.setMarkdown("# 欢迎使用比赛帮Blog编辑器");
                // console.log("onload =>", this, this.id, this.settings);
            }
        });

        $()

        /*得到所有首页新闻id*/
        function getHomePage() {
            GetHomePage.get(function (response) {
                articleIdStr = response.article_config;
            }, function (err) {

            })
        }

        // /*发送新闻id 使articleIdStr存储的id永远是6个*/
        function getHtml() {
            console.log(editor.getHTML());
            vm.content = editor.getHTML();
            vm.article.content = editor.getHTML();
            vm.article.authorName = "比赛帮RD";
            CreateArticle.save(vm.article,function (res) {
                console.log(res)
            })

        }
        function getMD() {
            console.log(editor.getMarkdown());

        }

        function delHtmlTag() {
            //去掉所有的html标记
             console.log(vm.content.replace(/<[^>]+>/g,""));
        }


    }
})();
