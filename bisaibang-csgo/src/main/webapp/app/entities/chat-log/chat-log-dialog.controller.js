(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ChatLogDialogController', ChatLogDialogController);

    ChatLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChatLog', 'User'];

    function ChatLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChatLog, User) {
        var vm = this;

        vm.chatLog = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chatLog.id !== null) {
                ChatLog.update(vm.chatLog, onSaveSuccess, onSaveError);
            } else {
                ChatLog.save(vm.chatLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bsbmsoneApp:chatLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
