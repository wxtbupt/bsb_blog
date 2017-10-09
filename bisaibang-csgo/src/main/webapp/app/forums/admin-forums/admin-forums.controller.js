(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('AdminForumsController', AdminForumsController);

    AdminForumsController.$inject = ['$state', 'pagingParams', 'deleteForumsThread', 'forumsGetAllThread', 'deleteThreadService', 'SearchForumsThread'];

    function AdminForumsController ($state, pagingParams, deleteForumsThread,forumsGetAllThread, deleteThreadService, SearchForumsThread) {
        var vm = this;
        vm.search = search;
        vm.deleteForumThread = deleteForumThread;

        vm.isShowNew = false;
        vm.createNewTheme = createNewTheme;
        vm.page = pagingParams.page;
         //console.log(vm.page);
        vm.transition = transition;

        vm.thread = {
            "title": ""
        };

        function deleteForumThread(id) {
            vm.threadId = id;
            //console.log(id);
            deleteThreadService.open(vm.threadId);
        }

        function search(content) {
           vm.thread.title = content;
            //console.log(vm.thread);
            $state.go('search-thread', {content:vm.thread.title});
            // SearchForumsThread.query(vm.thread,  function (response) {
            //     console.log(response);
            // }, function () {
            //
            // });

        }

        forumsGetAllThread.post({
            page: vm.page - 1,
            size: 10,
            sort: 'asc'
        },'1', function onSuccess(response) {
            var data = response.data;
             //console.log(data);
            vm.threadList = data.content;
            vm.totalItems = data.totalPages;
            vm.isFirst = data.first;
            vm.isLast = data.last;
        });

        function transition(isNext) {
            if(vm.page + isNext > 0) {
                $state.transitionTo($state.$current, {
                    page: vm.page + isNext
                });
            }
        }

        function createNewTheme() {
            vm.isShowNew = true;
        }


    }
})();
