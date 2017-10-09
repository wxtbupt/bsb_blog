/**
 * Created by HJQYSF on 2017/7/14.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('RevertEssentialThread', RevertEssentialThread);

    RevertEssentialThread.$inject = ['$resource'];

    function RevertEssentialThread($resource) {
        var service = $resource('api/ms-singlethread/revert-essential/thread/:threadid', {}, {
            'save': { method: 'POST'}
        });
        return service;
    }
})();
