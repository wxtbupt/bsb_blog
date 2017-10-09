(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('PersonRegisterController', PersonRegisterController);

    PersonRegisterController.$inject = ['$state', 'PersonRegister', 'toaster', 'Principal', 'RegisterService', 'AccountCurrent', 'PersonUpdateInfo'];

    function PersonRegisterController($state, PersonRegister, toaster, Principal, RegisterService, AccountCurrent, PersonUpdateInfo) {
        var vm = this;
        vm.isLogIn = false;
        vm.cityName = "";
        vm.cityId = "";
        vm.warn = false;
        vm.chooseCity = chooseCity;
        vm.testID = testID;
        vm.testEmail = testEmail;
        vm.submit = submit;
        vm.personInfoWarn = {
            name: false,
            ID: false,
            email: false,
            account: false
        };

        /*检测是不是登录*/
        Principal.identity().then(function (account) {
            if (account === null) {
                RegisterService.open('signin', function success() {
                    $state.reload();
                }, function fail() {

                });
            } else {
                vm.isLogIn = true;
                getPersonInfo();
            }
        });

        /*得到手机号*/
        function getPersonInfo() {
            AccountCurrent.get(function (response) {
                vm.personPhone = response.phone.substring(0, 3) + "****" + response.phone.substring(7, 11);
            }, function (res) {

            });
        }

        function chooseCity(city) {
            vm.cityName = city.name;
            vm.cityId = city.id;
        };

        function testID() {
            if (vm.ID) {
                if (/^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(vm.ID)) {
                    vm.personInfoWarn.ID = false;
                    return true;
                } else {
                    vm.personInfoWarn.ID = true;
                }
            } else {
                vm.personInfoWarn.ID = false;
            }
        };

        function testEmail() {
            if (vm.email) {
                if (/^([\S])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(vm.email)) {
                    vm.personInfoWarn.email = false;
                    return true;
                } else {
                    vm.personInfoWarn.email = true;
                }
            } else {
                vm.personInfoWarn.email = false;
            }
        };

        function personUpdateInfo() {
            var personInfoData = {
                email: vm.email,
                name: vm.name,
                nickname: vm.account,
                personalId: vm.ID
            };
            PersonUpdateInfo.save(personInfoData, function (response) {
                toaster.pop("success", "", "报名成功");
                $state.go("competingInfo", {resigterState: 'person'});
            }, function (error) {

            })
        };


        function submit() {
            if (vm.cityName && vm.name && testID() && testEmail() && vm.account) {
                vm.warn = false;
                PersonRegister.save({cityid: vm.cityId}, null, function (response) {
                    if (response.message === "请登录") {
                        toaster.pop("error", "", "请登录");
                    } else if (response.message === "成功") {
                        personUpdateInfo();
                    }
                }, function (err) {
                    toaster.pop("error", "", "报名失败");
                });
            } else {
                vm.warn = true;
            }
        };

        vm.cities = [
            {id: 1, name: "大连"},
            {id: 2, name: "沈阳"},
            {id: 3, name: "北京"},
            {id: 4, name: "天津"},
            {id: 5, name: "武汉"},
            {id: 6, name: "长沙"},
            {id: 7, name: "石家庄"},
            {id: 8, name: "太原"},
            {id: 9, name: "青岛"},
            {id: 10, name: "上海"},
            {id: 11, name: "南京"},
            {id: 12, name: "杭州"},
            {id: 13, name: "西安"},
            {id: 14, name: "成都"},
            {id: 15, name: "重庆"},
            {id: 16, name: "贵阳"},
            {id: 17, name: "广州"},
            {id: 18, name: "深圳"},
            {id: 19, name: "厦门"},
            {id: 20, name: "福州"}
        ]
    }
})();
