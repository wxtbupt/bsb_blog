(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('ViewerApply', ViewerApply);

    ViewerApply.$inject = ['$resource'];

    function ViewerApply($resource) {
        var service = $resource('api/ms-team/viewer/enter/:teamid', {}, {
            'get': {method: 'GET'}
        });

        return service;
    }
})();
