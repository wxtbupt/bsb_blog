/**
 * Created by OlyLis on 2017/3/13.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .filter('forumsId', forumsId);

    forumsId.$inject = [];

    function forumsId () {
        return  function forumsId(forumsId) {
            if (forumsId === "1") {
                return "交流互动专区";
            }
            if (forumsId === "2") {
                return "参赛配队专区";
            }
            if (forumsId === "3") {
                return "赛事投诉及举报专区";
            }
        };
    }
})();
