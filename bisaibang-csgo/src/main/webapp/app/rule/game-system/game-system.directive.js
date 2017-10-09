angular
    .module('bsbmsoneApp')
    .directive('gameSystem',gameSystem);

function gameSystem(){
    var directive={
        templateUrl: 'app/rule/game-system/game-system.html',
        restrict: 'EA'
    };
    return directive;
}
