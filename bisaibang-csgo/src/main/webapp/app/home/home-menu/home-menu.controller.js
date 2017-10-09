(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('HomeMenuController', HomeMenuController);

    HomeMenuController.$inject = ['$state'];

    function HomeMenuController($state) {
        var vm = this;
        // console.log($state);

        vm.activeItem = $state.$current.name;
        // console.log(vm.activeItem);

        if ($state.current.url === '/main') {
            angular.element(window).scroll(function () {
                if (angular.element(window).scrollTop() >= 608) {
                    angular.element('.scroll-navbar')
                        .addClass('isFixed')
                } else {
                    angular.element('.scroll-navbar')
                        .removeClass('isFixed')
                }
            });
        } else {
            angular.element(window).scroll(function () {
                if (angular.element(window).scrollTop() >= 50) {
                    angular.element('.scroll-navbar')
                        .addClass('isFixed')
                } else {
                    angular.element('.scroll-navbar')
                        .removeClass('isFixed')
                }
            });
        }

    }
})();
