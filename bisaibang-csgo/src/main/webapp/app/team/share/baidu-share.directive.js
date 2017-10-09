/**
 * Created by OlyLis on 2016/12/5.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .directive('baiduShare', baiduShare);

    baiduShare.$inject = [];

    function baiduShare() {
        var directive;
        directive = {
            restrict: 'EA',
            templateUrl: 'app/team/share/baidu-share.html',
            scope: {
                teamMemberState:"="
            },
            replace: true,
            controller: 'BaiduShareController',
            controllerAs: 'vm',
            bindToController: true
        };

        return directive;
    }
})();
