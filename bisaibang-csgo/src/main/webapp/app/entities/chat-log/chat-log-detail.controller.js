(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ChatLogDetailController', ChatLogDetailController);

    ChatLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChatLog', 'User'];

    function ChatLogDetailController($scope, $rootScope, $stateParams, previousState, entity, ChatLog, User) {
        var vm = this;

        vm.chatLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bsbmsoneApp:chatLogUpdate', function(event, result) {
            vm.chatLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
