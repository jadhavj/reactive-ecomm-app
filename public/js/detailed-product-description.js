ShoppingApp.controller("detailedProductViewController", ['$scope', 'dataTransfer', function($scope, dataTransfer){
  console.log("in detailedProductViewController");
  $scope.obj = dataTransfer.get();

  console.log("get deta : ", $scope.obj);
  // $scope.obj.price = '&#8377' + $scope.obj.price;



}]);
