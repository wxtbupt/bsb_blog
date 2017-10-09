/**
 * Created by arslan on 1/31/17.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardManageCommentController', DashboardManageCommentController);

    DashboardManageCommentController.$inject = ['$timeout', '$rootScope', 'NewsGetCommentAdmin', '$scope', 'deleteComment', 'ParseLinks', 'toaster', 'PaginationUtil', '$state'];

    function DashboardManageCommentController($timeout, $rootScope, NewsGetCommentAdmin, $scope, deleteComment, ParseLinks, toaster, PaginationUtil, $state) {
        var vm = this;

        vm.page = 1;

        vm.deleteCommentById = deleteCommentById;


        function loadAll() {
            NewsGetCommentAdmin.save({
                id: vm.article.id,
                page: vm.page - 1,
                size: 20,
                sort: ['id,asc']
            }, {}, onSuccess, onError);

            function onSuccess(data, headers) {
                //vm.links = ParseLinks.parse(headers('link'));
                vm.comments=[];
                data.content.forEach(function (item) {
                    if (item.type !== "delete") {
                        vm.comments.push(item);
                    }
                });
            }

            function onError(error) {
                console.log(error.data.message);
            }
        }

        loadAll();


        function deleteCommentById(commentId) {
            deleteComment.save({id: commentId}, {}, function (res) {
                toaster.pop('success', '', '删除成功');
                // $state.reload();
                $timeout(function () {
                    loadAll();
                }, 1000)
            }, function () {
                toaster.pop('error', '', '删除失败')
            })
        }
    }
})();
