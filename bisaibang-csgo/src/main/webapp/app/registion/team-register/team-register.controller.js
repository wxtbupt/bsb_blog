(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('TeamRegisterController', TeamRegisterController);

    TeamRegisterController.$inject = ['$state','TeamRegister', 'toaster','Principal','RegisterService','AccountCurrent','PersonUpdateInfo'];

    function TeamRegisterController($state,TeamRegister, toaster,Principal,RegisterService,AccountCurrent,PersonUpdateInfo) {
        var vm = this;
        var memberLength = 6;
        vm.isLogIn = false;
        vm.warn = false;
        vm.disabled = false;
        vm.submit = submit;
        vm.captain = {
            teamName: "",
            name: "",
            ID: "",
            email: "",
            account: ""
        };
        vm.member = [
            {title: "*队员1", name: "", phone: "", ID: "", email: "", account: ""},
            {title: "*队员2", name: "", phone: "", ID: "", email: "", account: ""},
            {title: "*队员3", name: "", phone: "", ID: "", email: "", account: ""},
            {title: "*队员4", name: "", phone: "", ID: "", email: "", account: ""},
            {title: "替补1", name: "", phone: "", ID: "", email: "", account: ""},
            {title: "替补2", name: "", phone: "", ID: "", email: "", account: ""}
        ];

        vm.teamInfo = {
            name: "",
            teamPlayer: [
                {
                    mail: "string",
                    name: "string",
                    nickName: "string",
                    personalId: "string",
                    phone: "string",
                    team: {
                        type: "string"
                    }
                }
            ]
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
                vm.personPhone=response.phone.substring(0,3)+"****"+response.phone.substring(7,11);
            }, function (res) {

            });
        }

        function testMemberLenth() {
            memberLength = 6;
            vm.member.forEach(function (item) {
                if (item.title.indexOf("替补") != -1) {
                    if (item.name === "" && item.phone === "" && item.ID === "" && item.email === "" && item.account === "") {
                        memberLength--;
                    }
                }
            });
        };

        function testInputError() {
            testMemberLenth();
            for (var item in vm.captain) {
                if (!vm.captain[item]) {
                    return true;
                }
            }

            for (var i = 0; i < memberLength; i++) {
                if (!vm.member[i].name || !vm.member[i].phone || !vm.member[i].ID || !vm.member[i].email || !vm.member[i].account) {
                    return true;
                }
            }
        }

        function submit() {
            vm.teamInfo.name = vm.captain.teamName;
            vm.teamInfo.teamPlayer = [];
            if (!testInputError()) {
                vm.warn = false;
                for (var i = 0; i < memberLength; i++) {
                    var obj = {
                        mail: vm.member[i].email,
                        name: vm.member[i].name,
                        nickName: vm.member[i].account,
                        personalId: vm.member[i].ID,
                        phone: vm.member[i].phone,
                        team: {
                            type: "string"
                        }
                    };
                    vm.teamInfo.teamPlayer.push(obj);
                }
                TeamRegister.save(vm.teamInfo, function (response) {
                    if (response.message === "请登录") {
                        toaster.pop("error", "", response.message);
                    } else if (response.message === "创建小队成功") {
                        personUpdateInfo();
                    } else if (response.message === "小队名已存在") {
                        toaster.pop("error", "", response.message);
                    }
                }, function (error) {

                })
            } else {
                vm.warn = true;
            }
        }

        function personUpdateInfo() {
            var personInfoData = {
                email: vm.captain.email,
                name: vm.captain.name,
                nickname: vm.captain.account,
                personalId: vm.captain.ID
            };
            PersonUpdateInfo.save(personInfoData, function (response) {
                toaster.pop("success", "", "小队报名成功");
                vm.disabled = true;
                $state.go("competingInfo",{resigterState:'team'});
            }, function (error) {

            })
        };
    }
})();
