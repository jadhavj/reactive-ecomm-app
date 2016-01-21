ShoppingApp.controller("cartController", ["$scope", "$rootScope", "$window", "$location", function($scope, $rootScope, $window, $location){

  $scope.username = $rootScope.user.username;
  $scope.cart = [];
  $scope.url = $location.absUrl().split('#')[0];

  $scope.getMyCart = function(){
    var ws = new WebSocket($rootScope.wsBaseUrl + "/cart");
    ws.onopen = function()
    {
       ws.send(JSON.stringify({"username": $rootScope.user.username}));
    };

    ws.onmessage = function (evt)
    {
       $scope.cart = angular.copy(JSON.parse((evt.data == '{}' || evt.data == '{ }') ? null : evt.data));
       $scope.cartIsEmpty = ($scope.cart == {} || $scope.cart == null) ? true : false;
       $scope.$apply();
       ws.close();
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };
  }

  $scope.total = 0;
  console.log("cart status : ", $scope.cartIsEmpty);

  $scope.getMyCart();


  $scope.removeFromCart = function(item){
    console.log("removeFromCart : ", item);
    $scope.obj = item;
    var ws = new WebSocket($rootScope.wsBaseUrl + "/removeFromCart");
    ws.onopen = function()
    {
       ws.send(JSON.stringify({"username": $rootScope.user.username, "product_id": item._id}));
    };

    ws.onmessage = function (evt)
    {
      var output = angular.copy(JSON.parse(evt.data));
      (output.result == 'success') ? $('#notification-removed-from-cart').modal('show') : $('#notification-removed-from-cart-failure').modal('show');

      $scope.getMyCart();
       ws.close();
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };

  }

  $scope.checkout = function(){
    $window.location = $scope.url + "#/user-id="+ $rootScope.user.username +"/checkout/";
  }

}]);
