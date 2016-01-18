ShoppingApp.controller('signupController', ['$scope', '$rootScope', '$location', '$window', function($scope, $rootScope, $location, $window){
  $scope.user.role = "buyer";

  $scope.reset = function(form){
    if(form){
      form.$setPristine();
      form.$setUntouched();
    }
  }

  $scope.registerUser = function(){
    $rootScope.newUser = angular.copy($scope.user);
    console.log("new user registered : ", $rootScope.user);

    var ws = new WebSocket("ws://localhost:9000/signup");
    ws.onopen = function()
    {
       ws.send(JSON.stringify($scope.user));
    };

    ws.onmessage = function (evt)
    {
       $scope.user = JSON.parse(evt.data);
       console.log("Congratulations...");
       $location.path("/login/")
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };

  }
}]);
