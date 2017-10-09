(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ChatLogDeleteController',ChatLogDeleteController);

    ChatLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChatLog'];

    function ChatLogDeleteController($uibModalInstance, entity, ChatLog) {
        var vm = this;

        vm.chatLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChatLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
