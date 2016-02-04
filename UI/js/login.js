ShoppingApp.controller('loginController', ['$scope','$rootScope', 'getAbsUrl', '$http', function($scope, $rootScope, getAbsUrl, $http){
  $scope.userCredentials = {};

  $scope.navigate = function(){
    $rootScope.authenticated($scope.user);
    console.log("Welcome ",$scope.user.username);
    path = "/"+$scope.user.username+"/dashboard/"+$scope.user.role+"/";
    getAbsUrl.navigateTo(path)
  }

  $scope.throwLoginError = function(){
    $scope.user = {};
    $scope.errorMessage = "something went wrong.. \n Either is Invalid \n 1. username \n 2. password";
    alert($scope.errorMessage);
  }

  $scope.submit = function(){

    var ws = new WebSocket($rootScope.wsBaseUrl + "/login");
    ws.onopen = function(evt)
    {
       ws.send(JSON.stringify($scope.userCredentials));
    };

    ws.onmessage = function (evt)
    {
       $scope.user = JSON.parse(evt.data);
       $rootScope.user = $scope.user;
       $scope.$apply();
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
    getAbsUrl.navigateTo('/signup');
  }
}]);
