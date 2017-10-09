angular
    .module('bsbmsoneApp')
    .directive('homeFooter',homeFooter);

function homeFooter(){
    var directive={
        templateUrl: 'app/home/home-footer/home-footer.html',
        restrict: 'EA'
    };
    return directive;
}
