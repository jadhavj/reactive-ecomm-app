ShoppingApp.controller("editItemController", ['$scope', 'dataTransfer', function($scope, dataTransfer){
  console.log("in editItemController");
  $scope.obj = dataTransfer.get();

  $scope.updateItemInfo = function(){
    console.log("data updated to : ",$scope.obj);
  };

}]);
