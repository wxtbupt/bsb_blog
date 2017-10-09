(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['$stateParams', '$scope', '$state'];

    function DashboardController ($stateParams, $scope, $state) {
        var vm = this;
        vm.stateType = $stateParams.stateType;

        $scope.$watch('vm.selectedTab', function (){
            $state.go('.', {stateType: vm.selectedTab}, {notify: false});
        });

        if (vm.stateType !== null && vm.stateType !== undefined) {
            vm.selectedTab = vm.stateType;
        } else {
            vm.selectedTab = 'index';
        }
        vm.selectedVideoTag = null;
    }
})();
