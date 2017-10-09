(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('CardController', CardController);

    CardController.$inject = ['GetMyCard', '$scope', 'Principal', 'LoginService', '$state', 'GetMyPlayersInfo'];

    function CardController (GetMyCard, $scope, Principal, LoginService, $state, GetMyPlayersInfo) {
        var vm = this;
        vm.cards = [];
        vm.firstCard = {};
        vm.topTeam = '';
        vm.topScore = '';
        vm.bottomTeam = '';
        vm.bottomScore = '';
        vm.firstIsTrue = true;
        vm.hadCards = true;
        vm.isTeam = true;


        GetMyPlayersInfo.get({},{},function onSuccess(res) {
            console.log(res.data.id);
            vm.isTeam = true;
            GetMyCard.query(res.data.id,function (res) {


                vm.cards = res.reverse();
                if(vm.cards.length == 0){
                    vm.hadCards = false;
                }
                vm.firstCard = vm.cards[0];
                console.log(vm.firstCard);

                //判断第一场比赛是不是 0:0
                // 如果是，说明第一场比赛还没打
                if(vm.firstCard.content.split(':')[0].split('-')[1] == 0 && vm.firstCard.content.split(':')[1].split('-')[0] == 0){
                    vm.firstIsTrue = true;
                    vm.cards = vm.cards.slice(1);
                } else {
                    vm.firstIsTrue = false;
                }

                vm.top = vm.firstCard.content.split(':')[0];
                vm.bottom = vm.firstCard.content.split(':')[1];
                console.log(vm.top);
                vm.topTeam = vm.top.split('-')[0];
                vm.topScore = vm.top.split('-')[1];
                vm.bottomTeam = vm.bottom.split('-')[1];
                vm.bottomScore = vm.bottom.split('-')[0]
            })
        },function onError(){
            vm.isTeam = false;
        })

    }
})();
