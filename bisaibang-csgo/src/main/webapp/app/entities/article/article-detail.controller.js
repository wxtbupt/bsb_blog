(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('ArticleDetailController', ArticleDetailController);

    ArticleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Article', 'Term', 'User'];

    function ArticleDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Article, Term, User) {
        var vm = this;

        vm.article = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bsbmsoneApp:articleUpdate', function(event, result) {
            vm.article = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
