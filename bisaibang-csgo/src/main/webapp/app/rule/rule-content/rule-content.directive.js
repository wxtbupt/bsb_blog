angular
    .module('bsbmsoneApp')
    .directive('ruleContent',ruleContent);

function ruleContent() {
    var directive = {
        templateUrl: 'app/rule/rule-content/rule-content.html',
        restrict: 'EA'
    };
    return directive;
}
