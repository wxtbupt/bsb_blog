/**
 * Created by gsy on 2017/5/6.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('GetPersonInfo', GetPersonInfo);

    GetPersonInfo.$inject = ['$resource'];

    function GetPersonInfo($resource) {
        var service = $resource('/api/get/person-info', {}, {
            'post': { method: 'POST'}
        });
        return service;
    }
})();
