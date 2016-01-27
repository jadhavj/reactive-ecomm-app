ShoppingApp.controller('addNewItemController', ['$scope', '$http', 'getAbsUrl', '$rootScope','dataTransfer',function($scope, $http, getAbsUrl, $rootScope,dataTransfer) {
	this.addProduct = function () {
		var fd = new FormData();
	    fd.append('file', $scope.myFile);
	    $scope.product.username = $rootScope.user.username;
	    fd.append('product', JSON.stringify($scope.product));
	    var uploadUrl = $rootScope.appBaseUrl + "/addProduct";
	    $http.post(uploadUrl, fd, {
	        transformRequest: angular.identity,
	        headers: {'Content-Type': undefined}
	    })
	    .success(function(){
	        console.log("successfully added product");
	        getAbsUrl.navigateTo("/"+$rootScope.user.username+"/dashboard/"+$rootScope.user.role+"/")
	    })
	    .error(function(){
	    });
	}
}]);
