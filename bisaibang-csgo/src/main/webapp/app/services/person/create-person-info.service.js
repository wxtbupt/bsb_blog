/**
 * Created by gsy on 2017/5/6.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('CreatePersonInfo', CreatePersonInfo);

    CreatePersonInfo.$inject = ['$resource'];

    function CreatePersonInfo($resource) {
        var service = $resource('api/ms-person-info/update', {}, {
            'post': { method: 'POST'}
        });
        return service;
    }
})();
