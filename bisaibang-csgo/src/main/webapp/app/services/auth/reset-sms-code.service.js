/**
 * Created by gsy on 2017/4/24.
 */
/**
 * Created by Administrator on 2016/8/8.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('resetSmsCodeSend', resetSmsCodeSend);

    resetSmsCodeSend.$inject = ['$resource'];

    function resetSmsCodeSend($resource) {
        var service = $resource('api/forget/send_sms_code', {}, {
            'get': { method: 'GET'},
            'save': { method: 'POST' }
        });
        return service;
    }
})();

