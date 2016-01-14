ShoppingApp.controller('loginController', ['$scope','$rootScope', '$location', '$http', '$window', function($scope, $rootScope, $location, $http, $window){
  $scope.userCredentials = {};

  $scope.navigate = function(){
    $rootScope.authenticated($scope.user);
    console.log("Welcome ",$scope.user.username);
    var url = "/"+$scope.user.username+"/dashboard/"+$scope.user.role+"/";
    var absUrl = $location.absUrl().split('#')[0];
    $window.location = absUrl+"#"+url;
  }

  $scope.throwLoginError = function(){
    $scope.user = {};
    $scope.errorMessage = "something went wrong.. \n Either is Invalid \n 1. username \n 2. password";
    alert($scope.errorMessage);
  }

  $scope.submit = function(){

    var ws = new WebSocket("ws://localhost:9000/login");
    ws.onopen = function(evt)
    {
       ws.send(JSON.stringify($scope.userCredentials));
    };

    ws.onmessage = function (evt)
    {
       $scope.user = JSON.parse(evt.data);
       ($scope.user.login == 'valid') ? $scope.navigate() : $scope.throwLoginError();
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };
  }

  $scope.clear = function(){
    $scope.userCredentials = {};
    $scope.errorMessage = null;
  }

  $scope.signup = function(){
    $location.path('/signup')
  }
}]);
