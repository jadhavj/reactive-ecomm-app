ShoppingApp.directive('xorProductCard', ['$location', 'dataTransfer', function($location, dataTransfer){
  return {
    restrict: 'E',
    templateUrl: 'directives/html/product-card.html',
    scope: {
      obj: '=obj'
    },
    link: function($scope, element, attrs){
      var user_id = $location.url().split('/')[1];
      $scope.goToProduct = function(){
        dataTransfer.set($scope.obj);
        console.log($location.absUrl());
        //user-id=:id/buyer/id=:id"
        $location.path("/user-id=" + user_id + "/buyer/id="+$scope.obj._id+"/");
      }
    }
  };
}]);


// "569c88819558c412365434fc"
