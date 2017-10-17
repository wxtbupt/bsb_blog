/**
 * Created by arslan on 1/31/17.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardPostArticleController', DashboardPostArticleController);

    DashboardPostArticleController.$inject = ['GetHomePage', 'CreateArticle', 'toaster', 'AvatarUploadService', 'EditArticle'];

    function DashboardPostArticleController(GetHomePage, CreateArticle, toaster, AvatarUploadService, EditArticle) {
        var vm = this;

        var articleIdStr = "";
        vm.thumbnailUrl = "";
        vm.content = "";

        vm.getHtml = getHtml;
        vm.getMD = getMD;

        getHomePage();
        console.log(vm.article);
        if (vm.article == undefined || vm.article == null || vm.article.id == null){
            //若修改vm.article初始化数据，请在dashboard-sidebar.controller.js中同时进行修改
            vm.article = {
                authorName: null,
                introduction: '',
                thumbnailUrl: '',
                content: '',
                contentContentType: null,
                createDate: new Date(),
                editDate: null,
                id: null,
                isAbandon: null,
                name: null,
                state: null,
                title: '',
                type: null,
                term:{id:1}
            };
        }

        vm.note = {
            isHtmlShow: false,
            isEditShow: true,
            isConfirmEditButtonShow: true
        };

        var editor = editormd("editormd", {
            path : "bower_components/editor.md/lib/", // Autoload modules mode, codemirror, marked... dependents libs path
            saveHTMLToTextarea : true,
            onload : function() {
                // alert("onload");
                this.setMarkdown(vm.article.introduction || "# 欢迎使用比赛帮Blog编辑器");
                // console.log("onload =>", this, this.id, this.settings);
            }
        });



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
            vm.article.description = editor.getMarkdown()
            vm.article.introduction = editor.getMarkdown()
            vm.article.authorName = "比赛帮RD";

            if (!vm.article.id && typeof (vm.article.id)!="undefined" && vm.article.id!=0){
                CreateArticle.save(vm.article,function (res) {
                    console.log(res)
                })
            } else {

                EditArticle.save({articleid:vm.article.id},vm.article, function onSuccess(data){
                    toaster.pop('success', " ", '已成功');
                    console.log(data)
                    vm.article = data;
                    vm.title = data.title;
                    vm.content = data.content;
                    setTimeout(function () {
                        window.location.href="/dashboard/manageArticle";
                    },300);
                }, function onError(error) {
                    toaster.pop('error', " ", '失败');
                });

            }
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
