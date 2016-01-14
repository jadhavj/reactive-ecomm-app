ShoppingApp.directive('xorItemSnapshot', ['$location', 'dataTransfer', function($location, dataTransfer){
  return {
    restrict: 'E',
    templateUrl: 'directives/html/item-snapshot.html',
    scope: {
      obj: '=obj'
    },
    link: function($scope, element, attrs){
      var user_id = $location.url().split('/')[1];
      $scope.edit = function(){
        dataTransfer.set($scope.obj);
        $location.path("/user-id=" + user_id + "/edit-item/item-id="+$scope.obj.id+"/");
      }
    }
  };
}]);
