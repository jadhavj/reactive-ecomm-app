ShoppingApp.directive('xorProductCard', [function(){
  return {
    restrict: 'E',
    // transclude: true,
    templateUrl: 'directives/html/product-card.html',
    scope: {
      item: '=obj'
    }
  };
}]);
