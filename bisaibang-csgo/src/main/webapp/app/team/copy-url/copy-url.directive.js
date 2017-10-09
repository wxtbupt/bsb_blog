/**
 * Created by OlyLis on 2017/4/24.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('copyUrl', copyUrl);

    copyUrl.$inject = [];

    function copyUrl() {

        var directive;

        directive = {
            restrict: 'EA',
            templateUrl: 'app/team/copy-url/copy-url.html',
            scope: {},
            controller: 'CopyUrlController',
            controllerAs: 'vm',
            bindToController: true
        };

        return directive;
    }
})();
