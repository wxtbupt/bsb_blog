(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ChatController', ChatController);

    ChatController.$inject = ['$scope', '$rootScope'];

    function ChatController($scope, $rootScope) {
        var vm = this;
        var client;
        vm.messages = [];

        vm.connectWs = connectWs;
        vm.sendMessage = sendMessage;

        function connectWs() {
            client = Stomp.over(new SockJS('/wschat'));
            client.connect({}, function () {
                client.subscribe('/messenger/global', showMessage);
                client.subscribe('/messenger/' + $rootScope.globalVariable.id, showMessage);
            });

            //用于关掉console log信息
            client.debug = null;
        }

        function sendMessage() {
            client.send("/app/wschat/global", {}, JSON.stringify({
                message: vm.inputMessage
            }));
            vm.inputMessage = '';
        }

        function showMessage(message) {
            vm.messages.push(JSON.parse(message.body));
            $scope.$apply();
        }
    }
})();
