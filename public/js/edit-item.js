ShoppingApp.controller("editItemController", ['$scope', 'dataTransfer', '$http', function($scope, dataTransfer, $http){
  console.log("in editItemController");
  $scope.obj = dataTransfer.get();

  $scope.updateItemInfo = function(){
	    console.log('Edit item : ',$scope.obj);
	    $http.post('http://localhost:9000/editProduct', $scope.obj,{
	        headers: { 'Content-Type': 'application/json; charset=UTF-8'
	        		}
	    }).success(function(responseData) {
	        var absUrl = $location.absUrl().split('#')[0];
	        $window.location = absUrl+"#/"+$rootScope.user.username+"/dashboard/"+$rootScope.user.role+"/";
	        console.log("successfully edited product");
	    });   
  };

}]);
