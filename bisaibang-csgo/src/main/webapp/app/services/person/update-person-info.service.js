/**
 * Created by gsy on 2017/5/6.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('UpdatePersonInfo', UpdatePersonInfo);

    UpdatePersonInfo.$inject = ['$resource'];

    function UpdatePersonInfo($resource) {
        var service = $resource('/api/update/person-info', {}, {
            'post': { method: 'POST'}
        });
        return service;
    }
})();
