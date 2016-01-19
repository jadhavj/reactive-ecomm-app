ShoppingApp.controller('addNewItemController', ['$scope', '$http', '$location', '$window', '$rootScope', function($scope, $http, $location, $window, $rootScope) {

  console.log("in addNewItemController");

  $scope.addItem = function(){
    console.log('Adding item : ',$scope.product);
    $http.post($rootScope.appBaseUrl + '/addProduct', $scope.product,{
        headers: { 'Content-Type': 'application/json; charset=UTF-8'
        		}
    }).success(function(responseData) {
        var absUrl = $location.absUrl().split('#')[0];
        $window.location = absUrl+"#/"+$rootScope.user.username+"/dashboard/"+$rootScope.user.role+"/";
        console.log("successfully added product");
    });   
  }

}]);

