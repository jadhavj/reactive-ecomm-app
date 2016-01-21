ShoppingApp.controller('checkoutController',["$scope", "$rootScope", "$location", "$window", "dataTransfer", function($scope, $rootScope, $location, $window, dataTransfer){

  $scope.user = $rootScope.user;
  $scope.cart = dataTransfer.get();
  console.log("cart : ", $scope.cart);

  $scope.doCheckout = function(){
    var ws = new WebSocket($rootScope.wsBaseUrl + "/checkout");
    ws.onopen = function()
    {
       ws.send(JSON.stringify({"username": $rootScope.user.username}));
    };

    ws.onmessage = function (evt)
    {
      console.log("evt.data : ", evt.data);
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };
  }

  $scope.doCheckout();

  $scope.gotoDashboard = function(){
    var url = $location.absUrl().split('#')[0] + "#/" + $rootScope.user.username + "/dashboard/" + $rootScope.user.role + "/";
    $window.location = url;
  }

}]);
