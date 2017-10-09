(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('IntroductionController', IntroductionController);

    IntroductionController.$inject = ['GetMyCard', '$scope', 'Principal', 'LoginService', '$state', 'GetMyPlayersInfo'];

    function IntroductionController(GetMyCard, $scope, Principal, LoginService, $state, GetMyPlayersInfo) {
        var vm = this;

        // 设置轮播图图片间隔
        // vm.myInterval = 5000;
        // 轮播图数据初始化
        vm.slides = [
            {
                image: 'https://img.alicdn.com/tfs/TB1WNE9SFXXXXb8aXXXXXXXXXXX-520-280.jpg_q90_.webp',
                active: false
            },
            {
                image: 'https://img.alicdn.com/simba/img/TB1XgXnSXXXXXclXVXXSutbFXXX.jpg',
                active: true
            },
            {
                image: 'https://img.alicdn.com/tfs/TB1zMZLSFXXXXX9XpXXXXXXXXXX-520-280.jpg_q90_.webp',
                active: false
            }
        ];
        vm.toNowImg = toNowImg;

        function toNowImg(index) {
            $("#myCarousel").carousel(index);
            // vm.slides.forEach(function (item,idx) {
            //     item.active = index == idx ? true : false;
            // })
        }
    }
})();
