(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ParticipantController', ParticipantController);

    ParticipantController.$inject = ['$rootScope', '$state', 'RegistrationsEnrollTeamPlayer', 'toaster','CheckTeamName', 'SweetAlert', 'Principal',
                                    'RegisterService', 'GetMyPlayersInfo', 'AccountCurrent', 'NoticeCreate', 'GetPersonInfo', 'SenderGetAllNotice', 'SenderDeleteNotice'];

    function ParticipantController($rootScope, $state, RegistrationsEnrollTeamPlayer, toaster, CheckTeamName, SweetAlert, Principal,
                                   RegisterService, GetMyPlayersInfo, AccountCurrent, NoticeCreate, GetPersonInfo, SenderGetAllNotice, SenderDeleteNotice) {
        var vm = this;

        vm.idCards = [];
        vm.idGames = [];
        vm.phones = [];
        vm.emails = [];

        vm.submit = submit;

        vm.groupNum = 1;
        // 用来判断队员是否重复
        vm.playerPhones = [];
        vm.addPlayer = addPlayer;
        vm.changeName = changeName;
        vm.checkTeamName =checkTeamName;
        vm.loadPlayers = loadPlayers;
        vm.changeGroup = changeGroup;
        vm.isTeamNameSame = true;
        vm.isCheck = false;
        vm.lengthOK = false;
        vm.characterOK = false;

        vm.unauthorized = false;
        vm.hasNickName = false;
        // 队长信息有误
        vm.teamLeaderError = false;
        // 队员信息有误
        vm.teamMateError = false;
        vm.dataIsReady = false;

        vm.notices = [];

        vm.teamName = '';
        vm.teamLeader = {name: '', phone: '', idCard: '', idGame: '', email: '', fullPhone:''};


        AccountCurrent.get(function (response) {
            var result = response;
            // 代表着已经绑定
            if(result.nickName){
                vm.hasNickName = true;
                vm.teamLeader.idGame = result.nickName;
                vm.teamLeader.phone = result.phone;
                vm.teamLeader.fullPhone = result.fullPhone;
                vm.playerPhones.push(result.fullPhone);
            }
            GetPersonInfo.post(result.fullPhone, function(res){
                if(res.message != "没有个人信息") {
                    vm.personInfoStatus = true;
                    vm.teamLeader.idCard = res.idCard;
                    vm.teamLeader.email = res.email;
                    vm.teamLeader.name = res.name;
                    loadPlayers();
                }else{
                    vm.personInfoStatus = false;
                }

            },function(res){
                // 没填写 个人信息
                // vm.personInfoStatus = false;
            });

            GetMyPlayersInfo.get({},{},function onSuccess(res) {
                vm.isTeam = res.data;
                vm.canParticipant = false;
                vm.dataIsReady = true;
            },function onError(res) {
                // 没组队
                // console.log(res);
                vm.canParticipant = true;
                vm.dataIsReady = true;
            });

        },function (res) {
            // 没登录
            // console.log(res)
            vm.unauthorized = true;
            vm.dataIsReady = true;
        });
        // 限制队名，中文8个字，英文16个字
        String.prototype.gblen = function() {
            var len = 0;
            for (var i=0; i<this.length; i++) {
                if (this.charCodeAt(i)>127 || this.charCodeAt(i)==94) {
                    len += 2;
                } else {
                    len ++;
                }
            }
            return len;
        };

        function loadPlayers(){
            SenderGetAllNotice.get({},function (res) {
                // console.log(res);
                vm.notices = res.filter(function (item) {
                    return (item.notice.agree == 'A')
                });
                //  vm.players 是同意过的 队员
                vm.players = vm.notices.map(function (item) {
                    item.person.isReliable = item.person.user.reliable == 'true' ? true :false;
                    // console.log(item)
                    return item.person;
                });
                //  vm.playerPhones 是同意过的 队员 + 发送的电话（存在内存里）
                vm.playerPhones = vm.playerPhones.concat(vm.players.map(function(item) {return item.user.fullPhone;}));
                // console.log(vm.playerPhones);
            },function (res) {

            });
        }



        // 队员5人
        // vm.players = $rootScope.globalVariable.players ? $rootScope.globalVariable.players : [];
        vm.teamName = $rootScope.globalVariable.teamName || '';



        function checkTeamName(){
            // 保存 小队名
            $rootScope.globalVariable.teamName = vm.teamName;
            if(vm.teamName.gblen() <= 16){
                CheckTeamName.get({teamname:vm.teamName}, function (response) {
                    if (response.message == 'teamName被占用'){
                        vm.isTeamNameSame = true;
                        vm.isCheck = true;
                    } else {
                        vm.isTeamNameSame = false;
                        vm.isCheck = true;
                    }
                    if (vm.teamName.length <= 8 && vm.teamName.length > 0){
                        vm.lengthOK = true;
                    }else{
                        vm.lengthOK = false;
                    }
                    if(/^([\u4e00-\u9fa5]|\w)+$/.test(vm.teamName)){
                        vm.characterOK = true;
                    }else{
                        vm.characterOK = false;
                    }
                });
            }
        }
        function changeName(){
            vm.isTeamNameSame = false;
            vm.isCheck = false;
            vm.lengthOK = false;
        }

        function addPlayer(){
            // 去重
            // console.log(vm.players);
            if(vm.playerPhones.indexOf(vm.playerPhone) != -1) {
                toaster.pop('error','','不允许重复添加成员');
                vm.playerPhone = '';
                document.getElementById("add-player-input").focus();
                return;
            }
            if(vm.playerPhone){
                var playerPhone = vm.playerPhone;
                GetPersonInfo.post(vm.playerPhone,function(res){
                    console.log(res);
                    if(res.message != "没有个人信息") {
                        // 发送站内信
                        NoticeCreate.save(playerPhone, function(res){
                            // console.log(res);
                            if(vm.players.length >= 7){
                                toaster.pop('error','','队伍人数已达上线，无法继续添加');
                            }else{
                                if(res.message == '您添加的队员不符合参赛要求'){
                                    toaster.pop('error','','您添加的队员不符合参赛要求,未达到3500分的要求')
                                }else{
                                    vm.playerPhones.push(playerPhone);
                                    toaster.pop('success','','成功发送站内信')
                                }
                            }
                        },function (res) {
                            toaster.pop('error','','发送失败')
                        })

                    }else{
                        toaster.pop('error','','该队员个人信息没有完善')
                    }

                },function(res){
                    toaster.pop('error','','此人暂时没有注册')
                })
            }
            vm.playerPhone = '';
            document.getElementById("add-player-input").focus();
        }

        function submit(){
            var post = {};
            post.registration = {
                "captainNickName": vm.teamLeader.idGame,
                "email": vm.teamLeader.email,
                "idCard": vm.teamLeader.idCard,
                "idGame": vm.teamLeader.idGame,
                "name": vm.teamLeader.name,
                "phone": vm.teamLeader.fullPhone,
                "teamMate": JSON.stringify(vm.players),
                "teamName": vm.teamName,
                "type": "online"
            };
            var leader = {
                state: "captain",
                registration: post.registration,
                teamName : vm.teamName,
                battleTag: vm.teamLeader.idGame
            };
            post.teamPlayers = [leader];
            vm.players.forEach(function(item, index){
                post.teamPlayers[index+1] = {};
                post.teamPlayers[index+1].registration = post.registration;
                post.teamPlayers[index+1].battleTag= item.user.nickName;
                post.teamPlayers[index+1].teamName = vm.teamName;
                post.teamPlayers[index+1].state= "normal";
                // post.teamPlayers[index+1].user= item.user;
            });
            Principal.identity().then(function(account) {
                vm.account = account;
                // console.log(vm.account);
                if (account == null) {
                    // console.log('account == null');
                    RegisterService.open('signin', function success() {
                        $state.reload();
                    }, function fail() { });
                } else {
                    SweetAlert.swal({
                        title: "是否确认报名？",
                        text: "成功报名之后不能修改队伍信息",
                        type: "warning",
                        showCancelButton: true,
                        backgroundColor: "#292e3a",
                        confirmButtonColor: "#cb6228",
                        confirmButtonText: "确认报名",
                        //cancelButtonColor: "#2a2e39",
                        cancelButtonText: "取消"
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            RegistrationsEnrollTeamPlayer.post(post, function (res) {
                                // console.log(res);
                                if(res.message == '用户已经报名'){
                                    toaster.pop('success', " ", "当前用户已经报名，请勿重复报名");
                                }else if(res.message.indexOf('战网id不存在') !== -1){
                                    toaster.pop('error', " ", res.message);
                                }else if(res.message == '分数不足'){
                                    toaster.pop('error', " ", '分数不足，无法报名');
                                }else{
                                    toaster.pop('success', " ", "报名成功");
                                    toaster.pop('success', " ", "邮件会在72小时之内发送，请注意查收");
                                    $state.reload()
                                }
                            }, function (response) {
                                toaster.pop('error', " ", "当前无法报名");
                                // console.log(response);
                            });
                        }
                    });
                }
            });
        }

        function deletePlayer(noticeIndex) {
            // console.log(noticeIndex);
            var noticeId = vm.notices[noticeIndex].notice.id;
            // console.log(noticeId);
            SweetAlert.swal({
                    title: "是否删除人员？",
                    text: "删除之后仍然能再次添加。",
                    type: "warning",
                    showCancelButton: true,
                    backgroundColor: "#292e3a",
                    confirmButtonColor: "#cb6228",
                    confirmButtonText: "确认删除",
                    //cancelButtonColor: "#2a2e39",
                    cancelButtonText: "取消"
                },
                function (isConfirm) {
                    if (isConfirm) {
                        SenderDeleteNotice.post({noticeid:noticeId}, {}, function () {
                            toaster.pop('success','','删除成功');
                            $state.reload();
                        }, function () {
                            toaster.pop('error','','删除失败');
                        });
                    }
                });

        }

        function changeGroup(num){
            vm.groupNum = num;
            // $state.reload();
            // vm.link = 'https://www.bisaibang.com/widget/tournament/bracket/'+ (vm.groupNum + 806);
        }
    }
})();
