(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('PersonController', PersonController);

    PersonController.$inject = ['$state', '$rootScope', 'Principal', 'AccountCurrent', 'RegisterService', 'AvatarUploadService', 'QiniuUploadAvatar',
        'toaster', 'CreatePersonInfo', 'DeleteBattleId','getRegistrationsInfo', 'SweetAlert'];

    function PersonController($state, $rootScope, Principal, AccountCurrent, RegisterService, AvatarUploadService, QiniuUploadAvatar,
                              toaster, CreatePersonInfo, DeleteBattleId,getRegistrationsInfo, SweetAlert) {
        var vm = this;
        vm.bindNickName = bindNickName;
        vm.changeAvatar = changeAvatar;
        vm.isLogIn = false;
        vm.isBindNickName = false;
        vm.isCaptain = undefined;
        vm.showAddPlayerCard = false;
        vm.isCreatePersonInfo = false;
        vm.isRegistration = false;
        vm.openAddPlayerCard = openAddPlayerCard;
        vm.closeAddPlayerCard = closeAddPlayerCard;
        vm.goRegister = goRegister;
        vm.addPlayerInfo = {};

        // vm.newPersonInfo.user = vm.personInfo;
        vm.updatePersonInfo = updatePersonInfo;

        vm.regPhone = regPhone;
        vm.regIDCard = regIDCard;
        vm.regEmail = regEmail;
        vm.regName = regName;
        vm.goForumsTeam = goForumsTeam;
        vm.removeBind = removeBind;


        Principal.identity().then(function (account) {
            if (account == null) {
                RegisterService.open('signin', function success() {
                    $state.reload();
                }, function fail() {

                });
            } else {
                vm.isLogIn = true;
                AccountCurrent.get(function (res) {
                    vm.personInfo = res;
                    //console.log(res)
                    vm.isBindNickName = typeof vm.personInfo.nickName == 'string' && vm.personInfo.nickName.length > 0;

                });
                getRegistrationsInfo.save(function (response) {
                    if (response.message === "已报名") {
                        vm.isRegistration = true;
                    }else{
                        vm.isRegistration = false;
                    }
                })

            }
        });


        function bindNickName() {
            updatePersonInfo();
            gotoBlizzard()
        }

        function gotoBlizzard() {
            console.log(1);
            var uri = 'https://www.battlenet.com.cn/oauth/authorize';
            var params = ['client_id=banmchv2ucnwzvst64fj6hhbj96xqnzz',
                'redirect_uri=https://owod.allied-esports.cn/api/ms-task/blizzard/callback',
                'response_type=code'];
            AccountCurrent.get(function (result) {
                var userId = result.id;
                params.push('state=' + userId);
                location.href = uri + '?' + params.join('&');
            });
        }

        function changeAvatar() {
            AvatarUploadService.open("ArticleImage", {aspectRatio: 1, compress: true, width: 80, isAvatar: true},
                function (result) {
                    if (result) {
                        if (result.indexOf('https://msone.bisaibang.com') == -1) vm.thumbnailUrl = 'https://msone.bisaibang.com/' + result;
                        else vm.thumbnailUrl = result;
                        QiniuUploadAvatar.save({}, vm.thumbnailUrl, function success(success) {
                            vm.personInfo.avatarUrl = vm.thumbnailUrl;
                            $rootScope.globalVariable.avatarUrl = vm.thumbnailUrl;
                            toaster.pop('success', " ", '修改成功');
                        }, function error(error) {
                            toaster.pop('success', " ", '修改失败');
                        });
                    }
                }, function () {
                    toaster.pop('error', " ", '发生虾米事情了?上传未成功');
                });
        }

        function openAddPlayerCard() {
            vm.showAddPlayerCard = true;
        }

        function closeAddPlayerCard() {
            vm.showAddPlayerCard = false;
        }

        // 验证手机号
        function regPhone(phone) {
            return (/^1(3|4|5|7|8)\d{9}$/.test(phone))
        }

        // 验证身份证号
        function regIDCard(idCard) {
            return (/^\d{17}([0-9]|X|x)$/.test(idCard))
        }

        // 验证邮箱
        function regEmail(email) {
            return (/^([\S])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/.test(email))
        }

        // 验证名字
        function regName(name) {
            if (name == '')
                return false;
            else
                return true;
        }

        function updatePersonInfo() {
            var newPersonInfo = {
                "name": vm.personInfo.firstName,
                "email": vm.personInfo.personalEmail,
                "personalId": vm.personInfo.personalID,
            };
            //console.log(newPersonInfo);
            CreatePersonInfo.post(newPersonInfo, function (res) {
                toaster.pop('success', '', '提交个人信息成功！')
            }, function (res) {
                // console.log(res);
            });
            vm.isCreatePersonInfo = true;
            //$state.reload();
        }

        function goRegister() {
            $state.go('ow-register');
        }

        function goForumsTeam() {
            $state.go('forums-team');
        }

        function removeBind() {

            // updatePersonInfo();

            SweetAlert.swal({
                    title: "您要解绑战网id吗？",
                    text: "解绑之后可再次绑定，申请中或队伍中不能解绑战网id",
                    type: "warning",
                    showCancelButton: true,
                    backgroundColor: "#292e3a",
                    confirmButtonColor: "#cb6228",
                    confirmButtonText: "确认解绑",
                    //cancelButtonColor: "#2a2e39",
                    cancelButtonText: "放弃"
                },
                function (isConfirm) {
                    if (isConfirm) {
                        DeleteBattleId.save({}, {}, function (res) {
                            if(res.message === "申请中或队伍中不能解绑战网id"){
                                toaster.pop('error', " ", '申请中或队伍中不能解绑战网id');
                            }
                            // vm.isBindNickName=false;
                            vm.personInfo = res;
                            vm.isBindNickName = typeof vm.personInfo.nickName == 'string' && vm.personInfo.nickName.length > 0;
                            $state.reload();
                        })
                    }
                }
            );

        }

    }
})();
