ShoppingApp.controller("detailedProductViewController", ['$scope', '$rootScope', '$location','dataTransfer', function($scope, $rootScope, $location, dataTransfer){
  console.log("in detailedProductViewController");
  $scope.obj = dataTransfer.get();
  $scope.user_id = $rootScope.user.username;

  console.log("get deta : ", $scope.obj);
  // $scope.obj.price = '&#8377' + $scope.obj.price;

  $scope.addToCart = function(){
    console.log($scope.obj.name+" added to cart..");

    var ws = new WebSocket($rootScope.wsBaseUrl + "/products");
    ws.onopen = function()
    {
       ws.send(JSON.stringify({"username": $scope.user_id, "product_id": $scope.obj._id}));
    };

    ws.onmessage = function (evt)
    {
       var output = angular.copy(JSON.parse(evt.data));
       ws.close();
       (output.result == 'success') ? $('#notification-add-to-cart').modal('show') : $('#notification-add-to-cart-failure').modal('show');
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };
  }

}]);
