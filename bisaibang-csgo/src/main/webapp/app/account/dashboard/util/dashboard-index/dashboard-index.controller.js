/**
 * Created by arslan on 1/31/17.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardIndexController', DashboardIndexController);

    DashboardIndexController.$inject = ['CreateCard', 'GetTaskData', 'TaskRemove', 'toaster', 'RemoveTeam','$state'];

    function DashboardIndexController (CreateCard, GetTaskData, TaskRemove, toaster, RemoveTeam, $state) {
        var vm = this;
        vm.title = '欢迎使用仪表盘';
        vm.taskRemove = taskRemove;
        vm.teamRemove = teamRemove;
        vm.createCard = createCard;
        vm.cardRemove = cardRemove;
        vm.card = {};
        vm.topTeam = '';
        vm.topScore = '';
        vm.bottomTeam = '';
        vm.bottomScore = '';



        GetTaskData.get({}, function (res) {
            console.log(res);
            vm.content = res;
        }, function () {

        });

        // 解除nickname
        function taskRemove(phone) {
            TaskRemove.update(phone, function (res) {
              // console.log(res);
                toaster.pop('success', " ", '解除成功');
                $state.reload();
            }, function (res) {
                toaster.pop('error', " ", '解除失败');
                // console.log(res);
            })
        }

        // 解散小队
        function teamRemove(zhanwangdi) {
            RemoveTeam.save(zhanwangdi, function (res) {
              // console.log(res);
                toaster.pop('success', " ", '解散成功');
                $state.reload();
            }, function (res) {
                toaster.pop('error', " ", '解除失败');
                // console.log(res);
            })
        }

        function createCard(){
            vm.card.content = vm.topTeam + '-' + vm.topScore +':'+vm.bottomScore +'-'+vm.bottomTeam;
            console.log(vm.card);

            CreateCard.save(vm.card, function (res) {
                // console.log(res);
                toaster.pop('success', " ", '更新成功');
                $state.reload();
            }, function (res) {
                toaster.pop('error', " ", '更新失败');
                // console.log(res);
            })
        }
        function cardRemove(cardId){
            CreateCard.save(cardId, function (res) {
                // console.log(res);
                toaster.pop('success', " ", '删除成功');
                $state.reload();
            }, function (res) {
                toaster.pop('error', " ", '删除失败');
                // console.log(res);
            })
        }


    }
})();
