/**
 * Created by OlyLis on 2017/3/3.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('newTheme', newTheme);

    function newTheme() {
        var directive = {
            templateUrl: 'app/forums/util/new/new-theme.html',
            restrict: 'EA',
            scope: {
                isShow: '=',
                forumsId: '='
            },
            controller: 'ForumsNewThemeController',
            controllerAs: 'vm',
            bindToController: true
        };
        return directive;
    }
})();
