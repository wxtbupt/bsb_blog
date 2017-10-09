/**
 * Created by OlyLis on 2017/3/18.
 */
(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .filter('forumsState', forumsState);

    forumsState.$inject = [];

    function forumsState () {
        return function forumsState(forumsState) {
            if (forumsState === "1") {
                return "forums-general({page:1})";
            }
            if (forumsState === "2") {
                return "forums-team({page:1})";
            }
            if (forumsState === "3") {
                return "forums-report({page:1})";
            }
        };
    }
})();
