/**
 * Created by szzz on 2016/12/19.
 */

(function () {

    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('BaiduShareController', BaiduShareController);

    BaiduShareController.$inject = ['$scope', '$timeout', '$location'];

    function BaiduShareController($scope, $timeout, $location) {
        var vm = this;
        vm.bdText = '';

        $scope.$watch("vm.teamMemberState", function (nv, ov) {
            loadCanParticipate();
        });

        function loadCanParticipate() {
            if (vm.teamMemberState.isCaptain) {
                vm.bdText = "我在守望先锋公开争霸赛中创建了小队：" + vm.teamMemberState.teamName + "，快来与我一起制霸";
            } else if (vm.teamMemberState.isMember || vm.teamMemberState.cancelApply) {
                vm.bdText = "我在守望先锋公开争霸赛中加入了小队：" + vm.teamMemberState.teamName + "，快来与我一起辅佐队长";
            }
            loadBaiduShare();
        }

        function loadBaiduShare() {

            vm.copyLocation = $location.absUrl();
            if (vm.copyLocation.indexOf('admin') !== -1) {
                vm.copyLocation = vm.copyLocation.replace('admin\/', '');
                //console.log(vm.copyLocation);
            }

            $timeout(function () {
                window._bd_share_config = {
                    //此处添加分享具体设置
                    "common": {
                        "bdText": vm.bdText,
                        "bdDesc": vm.bdText,
                        "bdUrl": vm.copyLocation,
                        "bdPic": '',
                        "bdMini": "2",
                        "bdMiniList": false,
                        "bdStyle": "0",
                        "bdSize": "24",
                        "bdSign": "off"
                    },
                    "share": {}
                };

                if (window._bd_share_main != undefined) {
                    window._bd_share_main.init();
                }
            });
        }

    }
})();
