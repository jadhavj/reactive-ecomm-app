ShoppingApp.controller('addNewItemController', ['$scope','$http', function($scope,$http){

  console.log("in addNewItemController");

  $scope.addItem = function(){
    console.log('Adding item : ',$scope.product);
    $http.post('http://localhost:9000/addProduct', $scope.product,{
        headers: { 'Content-Type': 'application/json; charset=UTF-8'
        		}
    }).success(function(responseData) {
        console.log("successfully added product");
    });   
  }

}]);

