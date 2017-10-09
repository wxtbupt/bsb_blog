/**
 * Created by OlyLis on 2017/4/24.
 */
(function() {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .controller('CopyUrlController', CopyUrlController);

    CopyUrlController.$inject = ['toaster'];

    function CopyUrlController(toaster) {
        var vm = this;

        vm.newCopyAddress = newCopyAddress;

        function newCopyAddress() {
            var url = document.location.href.indexOf('admin') !== -1 ? document.location.href.replace('admin\/', '') : document.location.href;

            new Clipboard('.copy-url', {
                text: function() {
                    return url;
                }
            });
            toaster.pop('success', '', '链接已复制到剪切板');
        }
    }
})();
