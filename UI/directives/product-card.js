ShoppingApp.directive('xorProductCard', ['getAbsUrl', 'dataTransfer', '$rootScope', function(getAbsUrl, dataTransfer, $rootScope){
  return {
    restrict: 'E',
    templateUrl: 'directives/html/product-card.html',
    scope: {
      obj: '=obj'
    },
    link: function($scope, element, attrs){
      // var user_id = $location.url().split('/')[1];
      $scope.goToProduct = function(){
        dataTransfer.set($scope.obj);
        // console.log($location.absUrl());
        getAbsUrl.navigateTo("/user-id=" + $rootScope.user.username + "/buyer/id="+$scope.obj._id+"/");
      }
    }
  };
}]);
