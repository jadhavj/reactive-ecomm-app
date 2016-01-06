ShoppingApp.directive('xorItemSnapshot', ['$location', 'dataTransfer', function($location, dataTransfer){
  return {
    restrict: 'E',
    // transclude: true,
    templateUrl: 'directives/html/item-snapshot.html',
    scope: {
      obj: '=obj'
    },
    link: function($scope, element, attrs){
      // console.log("dataTransfer : ",dataTransfer);
      var user_id = $location.url().split('/')[1];
      $scope.edit = function(){
        // console.log("Edit item : ",$scope.obj);
        // console.log("attrs : ",attrs);
        dataTransfer.set($scope.obj);
        $location.path("/user-id=" + user_id + "/edit-item/item-id="+$scope.obj.id);
        // <edit-item obj=scope.obj></edit-item>
      }
    }
  };
}]);
