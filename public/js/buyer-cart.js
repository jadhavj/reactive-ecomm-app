ShoppingApp.controller("cartController", ["$scope", "$rootScope", "$location", function($scope, $rootScope, $location){

  $scope.username = $rootScope.user.username;
  $scope.cart = [];

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

}]);
