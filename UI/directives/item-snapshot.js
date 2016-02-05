ShoppingApp.directive('xorItemSnapshot', ['getAbsUrl', 'dataTransfer', "$rootScope", function(getAbsUrl, dataTransfer, $rootScope){
  return {
    restrict: 'E',
    templateUrl: 'views/item-snapshot.html',
    scope: {
      obj: '=obj'
    },
    link: function($scope, element, attrs){
      // var user_id = $location.url().split('/')[1];
      $scope.edit = function(){
        dataTransfer.set($scope.obj);
        getAbsUrl.navigateTo("/user-id=" + $rootScope.user.username + "/edit-item/item-id="+$scope.obj._id+"/");
      }
    }
  };
}]);
