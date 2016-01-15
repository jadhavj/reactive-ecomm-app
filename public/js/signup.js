ShoppingApp.controller('signupController', ['$scope', '$rootScope', '$location', '$window', function($scope, $rootScope, $location, $window){
  $scope.user.role = "buyer";

  $scope.reset = function(form){
    if(form){
      form.$setPristine();
      form.$setUntouched();
    }
  }

  $scope.registerUser = function(){
    // $scope.user.id = Math.floor((Math.random() * 100) + 1);
    $rootScope.newUser = angular.copy($scope.user);
    console.log("new user registered : ", $rootScope.user);

    var ws = new WebSocket("ws://localhost:9000/signup");
    ws.onopen = function(evt)
    {
       ws.send(JSON.stringify($scope.user));
    };

    ws.onmessage = function (evt)
    {
       $scope.user = JSON.parse(evt.data);
       console.log("Congratulations...");
      //  ($scope.user.login == 'valid') ? $scope.navigate() : $scope.throwLoginError();
       $location.path("/login/")
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };
    //
    // $http.post('http://localhost:9000/signup', $scope.user,{
    //     headers: { 'Content-Type': 'application/json; charset=UTF-8'
    //         }
    // }).success(function(responseData) {
    //     console.log("Congratulations..");
    // });

    //
    // var absUrl = $location.absUrl().split('#');
    // $window.location.replace(absUrl[0] + '#/'+$scope.user.id+'/dashboard/'+$scope.user.role+"/");
    // $window.location = absUrl+"#"+'/'+$scope.user.id+'/dashboard/'+$scope.user.role+"/";
  }
}]);
