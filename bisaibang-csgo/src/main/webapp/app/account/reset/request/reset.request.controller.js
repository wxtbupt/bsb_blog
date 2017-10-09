(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('RequestResetController', RequestResetController);

    RequestResetController.$inject = ['$state', '$timeout', '$interval', 'ResetRequest',
        'RegisterService', 'RegisterCountDown', 'resetSmsCodeSend'];

    function RequestResetController ($state, $timeout, $interval, ResetRequest,
                                     RegisterService,  RegisterCountDown, resetSmsCodeSend) {
        var vm = this;

        vm.error = null;
        vm.errorEmailNotExists = false;
        vm.requestReset = requestReset;
        vm.resetAccount = {};
        var phoneCodePassword = null;
        vm.success = null;
        vm.doNotMatch = null;
        vm.getCode = getCode;
        vm.getcodeIsdisable = false;
        vm.btntime = "获得验证码";

        function getCode() {
            vm.ALIDAYU_Error = null;
            vm.userError = null;
            vm.codeError = null;
            vm.sendSuccess = null;
            vm.doNotMatch = null;
            vm.error = null;
            vm.getcodeIsdisable = true;
            RegisterCountDown.set(60);
            if (vm.resetAccount.phone) {
                resetSmsCodeSend.save(vm.resetAccount.phone, function success() {
                    vm.sendSuccess = 'OK';
                    startCountDown();
                }, function error(response) {
                    vm.sendSuccess = null;
                    if (response.data.message === "未知原因,可能是号码格式错误") {
                        vm.ALIDAYU_Error = 'ERROR';
                        //console.log( vm.ALIDAYU_Error);
                    } else if (response.data.message === "无号码") {
                        vm.codeError = 'ERROR';
                        //console.log(vm.codeError);
                    } else if (response.data.message === "无用户") {
                        vm.userError = 'ERROR';
                        //console.log(vm.userError);
                    }
                    //console.log(vm.resetAccount.phone);
                    // console.log(response);
                });
            }
        }

        function startCountDown() {
            var countDown = $interval(function () {
                if (RegisterCountDown.get() <= 0) {
                    vm.getcodeIsdisable = false;
                    vm.btntime = "获得验证码";
                    $interval.cancel(countDown);
                } else {
                    vm.btntime = "等待" + RegisterCountDown.get() + "秒";
                    RegisterCountDown.set(RegisterCountDown.get() - 1);
                }
            }, 1000);
        }

        $timeout(function (){angular.element('#email').focus();});

        function requestReset () {
            vm.ALIDAYU_Error = null;
            vm.userError = null;
            vm.codeError = null;
            vm.sendSuccess = null;
            if (vm.resetAccount.newPassword !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.doNotMatch = null;
                vm.error = null;
                // phoneCodePassword = vm.resetAccount.phone.toString() + ','
                //                   + vm.resetAccount.code.toString() + ','
                //                   + vm.resetAccount.newPassword.toString();
                ResetRequest.save(vm.resetAccount, function success() {
                    vm.success='OK';
                    $timeout(function () {
                        $state.go('home');
                        RegisterService.open('signin', function success() {
                            $state.reload();
                        }, function fail() {

                        });
                    }, 3000);
                }, function error(response) {
                    // console.log(response);
                    if (response.data.message === "无号码") {
                        vm.codeError = 'ERROR';
                    } else if (response.data.message === "无用户") {
                        vm.userError = 'ERROR';
                    } else {
                        vm.error = true;
                    }
                });
                // Auth.resetPasswordInit(vm.resetAccount.email).then(function () {
                //     vm.success = 'OK';
                // }).catch(function (response) {
                //     vm.success = null;
                //     if (response.status === 400 && response.data === 'e-mail address not registered') {
                //         vm.errorEmailNotExists = 'ERROR';
                //     } else {
                //         vm.error = 'ERROR';
                //     }
                // });
            }

        }
    }
})();
