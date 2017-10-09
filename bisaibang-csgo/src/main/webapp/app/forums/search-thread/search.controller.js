/**
 * Created by gsy on 2017/3/11.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ForumsSearchController', ForumsSearchController);

    ForumsSearchController.$inject = ['$state', '$stateParams', 'SearchForumsThread'];

    function ForumsSearchController ($state, $stateParams, SearchForumsThread) {
        var vm = this;
        vm.thread = {
            "title": ""
        };

        vm.searchContent = $stateParams.content;
        // console.log( vm.searchContent);
        vm.thread.title = vm.searchContent;
        SearchForumsThread.query(vm.thread,  function (response) {
            //console.log(response);
            vm.threadList = response.data;
        }, function () {

        });


    }
})();
