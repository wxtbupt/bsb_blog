(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('CompetingInfoController', CompetingInfoController);

    CompetingInfoController.$inject = ['$stateParams', 'toaster','SweetAlert', 'PersonUpdateInfo', 'Principal', 'RegisterService', 'CheckCreateTeam', 'CheckCityMember', 'GetAllPlayer','TeamUpdate'];

    function CompetingInfoController($stateParams, toaster,SweetAlert, PersonUpdateInfo, Principal, RegisterService, CheckCreateTeam, CheckCityMember,GetAllPlayer,TeamUpdate) {
        var vm = this;

        vm.isLogIn = false;
        vm.isPersonJoin = true;
        vm.isTeamJoin = true;
        vm.teamDisabled = true;
        vm.teamid = "";
        /*个人页面*/
        vm.isPersonRegister = true;
        vm.personCityName = "";
        vm.cityId = "";
        vm.personWarn = false;
        /*团队页面*/
        var memberLength = 6;
        vm.warn = false;
        /*个人页面*/
        vm.personChooseCity = personChooseCity;
        vm.personTestID = personTestID;
        vm.personTestEmail = personTestEmail;
        vm.personSubmit = personSubmit;
        /*团队页面*/
        vm.submit = submit;

        /*判断状态*/
        /*检测是不是登录*/
        Principal.identity().then(function (account) {
            if (account === null) {
                RegisterService.open('signin', function success() {
                    $state.reload();
                }, function fail() {

                });
            } else {
                vm.isLogIn = true;
                getMyInfo();
            }
        });


        /*得到小队信息 并根据url判断情况*/
        function getMyInfo() {
            CheckCityMember.save({}, function (response) {
                vm.isPersonJoin = true;
                vm.isPersonRegister = true;
                vm.personCityName = response.city.name;
                vm.name = response.user.firstName;
                vm.personPhone=response.user.phone.substring(0,3)+"****"+response.user.phone.substring(7,11);
                vm.ID = response.user.personalID;
                vm.email = response.user.personalEmail;
                vm.account = response.user.nickName;
                resigterState();
            }, function (error) {
                vm.isPersonJoin = false;
                vm.isPersonRegister = false;
            });
            CheckCreateTeam.save({}, function (response) {
                vm.isTeamJoin = true;
                vm.captain = {
                    teamName: response.name,
                    name: response.captain.firstName,
                    ID: response.captain.personalID,
                    email: response.captain.personalEmail,
                    account: response.captain.nickName
                };
                vm.captainPhone = response.captain.phone.substring(0,3)+"****"+response.captain.phone.substring(7,11);
                vm.teamid = response.id;
                getAllPlayer(response.id);
                resigterState();
            }, function (error) {
                vm.isTeamJoin = false;
            });
        }


        function resigterState() {
            if ($stateParams.resigterState == "person") {
                vm.isPersonRegister = true;
            } else if ($stateParams.resigterState == "team") {
                vm.isPersonRegister = false;
            }
        }


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
        ];

        vm.personInfoWarn = {
            name: false,
            ID: false,
            email: false,
            account: false
        };


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
            team: {
                name: "",
                type: "string"
            },
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


        /*个人页面的方法*/
        function personChooseCity(city) {
            vm.personCityName = city.name;
            vm.cityId = city.id;
        };

        function personTestID() {
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

        function personTestEmail() {
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


        function personSubmit() {
            if (vm.personCityName && vm.name && personTestID() && personTestEmail() && vm.account) {
                vm.personWarn = false;
                SweetAlert.swal({
                        title: "您要修改个人参赛信息吗？",
                        text: "",
                        type: "warning",
                        showCancelButton: true,
                        backgroundColor: "#292e3a",
                        confirmButtonColor: "#cb6228",
                        confirmButtonText: "确认修改",
                        //cancelButtonColor: "#2a2e39",
                        cancelButtonText: "放弃"
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            var personInfoData = {
                                email: vm.email,
                                name: vm.name,
                                nickname: vm.account,
                                personalId: vm.ID
                            };
                            PersonUpdateInfo.save(personInfoData, function (response) {
                                toaster.pop("success", "", "修改成功");
                            }, function (error) {
                                toaster.pop("error", "", "修改失败");
                            });
                        }

                    }
                );
            } else {
                vm.personWarn = true;
            }
        };

        /*团队页面的方法*/
        function getAllPlayer(teamid) {
            GetAllPlayer.get({teamid:teamid},null,function (response) {
                var memberPlayer=response.filter(function (item) {
                    return item.state === "member";
                });
                vm.member.forEach(function (item,index) {
                    if(memberPlayer[index]){
                        item.name=memberPlayer[index].name;
                        item.phone=memberPlayer[index].phone;
                        item.email=memberPlayer[index].mail;
                        item.account=memberPlayer[index].nickName;
                        item.ID=memberPlayer[index].personalId;
                    }
                });
            },function (error) {

            });
        };

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
            vm.teamInfo.team.name = vm.captain.teamName;
            vm.teamInfo.teamPlayer = [];
            if (!testInputError()) {
                vm.warn = false;
                SweetAlert.swal({
                        title: "您要修改队伍参赛信息吗？",
                        text: "",
                        type: "warning",
                        showCancelButton: true,
                        backgroundColor: "#292e3a",
                        confirmButtonColor: "#cb6228",
                        confirmButtonText: "确认修改",
                        //cancelButtonColor: "#2a2e39",
                        cancelButtonText: "放弃"
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            teamUpdate();
                        }
                    }
                );
            } else {
                vm.warn = true;
            }
        }

        function teamUpdate() {
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
            TeamUpdate.save({teamid:vm.teamid},vm.teamInfo, function (response) {
                if (response.message === "修改信息成功") {
                    var personInfoData = {
                        email: vm.captain.email,
                        name: vm.captain.name,
                        nickname: vm.captain.account,
                        personalId: vm.captain.ID
                    };
                    PersonUpdateInfo.save(personInfoData, function (response) {
                        toaster.pop("success", "", "修改成功");
                    }, function (error) {

                    });
                }
            }, function (error) {
                toaster.pop("error", "", "修改失败");
            })
        }
    }
})();
