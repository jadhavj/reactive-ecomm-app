ShoppingApp.controller('addNewItemController', ['$scope', function($scope){

  console.log("in addNewItemController");

  $scope.addItem = function(){
    console.log('Adding items : ',$scope.product);
  }

}]);
