(function () {
    'use strict';

    angular
        .module('bsbmsoneApp')
        .factory('BilibiliUtils', BilibiliUtils);

    BilibiliUtils.$inject = [];

    function BilibiliUtils() {

        var service = {
            getVideoIdFromURL: getVideoIdFromURL,
            getFrame: getFrameFromURL
        };
        // https://www.bilibili.com/video/av13671125/
        function getVideoIdFromURL(href) {
            var arr = href.split('av');
            return arr[1].slice(0,-1)
        }

        function getFrameFromURL(href) {
            return angular.element('<embed type="application/x-shockwave-flash" scale="noborder"    ' +
                'src="http://static.hdslb.com/miniloader.swf?aid=' + getVideoIdFromURL(href) + '" ' +
                'allowscriptaccess="never" menu="false" loop="true" play="true" womode="transparent" ' +
                'pluginspage="http://www.macromedia.com/go/getflashplayer" allowfullscreen="true" ' +
                'flashvars="playMovie=true&amp;auto=1&amp;adss=0&amp;isAutoPlay=true" style="visibility: visible; display: block;"></embed>')
        }

        return service;
    }

})();
