ShoppingApp.controller("detailedProductViewController", ['$scope', 'dataTransfer', function($scope, dataTransfer){
  console.log("in detailedProductViewController");
  $scope.obj = dataTransfer.get();

  console.log("get deta : ", $scope.obj);
  // $scope.obj.price = '&#8377' + $scope.obj.price;

  $scope.addToCart = function(){
    console.log($scope.obj.name+" added to cart..");
    
    $('#notification-add-to-cart').modal('show');
  }

}]);
