(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('YoukuUtils', YoukuUtils);

    YoukuUtils.$inject = [];

    function YoukuUtils() {

        var service = {
            getVideoIdFromURL: getVideoIdFromURL,
            getFrame: getFrameFromURL
        };

        function getVideoIdFromURL(href) {
            var l = document.createElement("a");
            l.href = href;
            return l.pathname.substr(l.pathname.lastIndexOf('/') + 4).replace(/\..+$/, '');
        }

        function getFrameFromURL(href) {
            return angular.element('<iframe src="https://player.youku.com/embed/' + getVideoIdFromURL(href) + '" frameborder=0 allowfullscreen></iframe>')
        }

        return service;
    }

})();
