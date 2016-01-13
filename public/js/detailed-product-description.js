ShoppingApp.controller("itemDescriptionController", ['$scope', 'dataTransfer', function($scope, dataTransfer){
  console.log("in itemDescriptionController");
  $scope.obj = dataTransfer.get();
  // $scope.obj.price = '&#8377' + $scope.obj.price;

  $scope.updateItemInfo = function(){
    console.log("data updated to : ",$scope.obj);
    // console.log("price : ", $scope.price.split('&#8377')[1]);
  };

}]).
directive("editItem", ['$scope', function($scope){
  return{
    restrict: 'E',
    templateUrl: 'directives/html/edit-item.html',
    scope: {
      obj:'=obj'
    }
  }
}]);
