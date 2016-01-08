ShoppingApp.controller('signupController', ['$scope', '$rootScope', '$location', '$window', function($scope, $rootScope, $location, $window){
  $scope.user.role = "buyer";

  $scope.reset = function(form){
    if(form){
      form.$setPristine();
      form.$setUntouched();
    }
  }

  $scope.registerUser = function(){
    $scope.user.id = Math.floor((Math.random() * 100) + 1);
    $rootScope.newUser = angular.copy($scope.user);
    console.log("new user registered : ", $rootScope.user);
    var absUrl = $location.absUrl().split('#');
    $window.location.replace(absUrl[0] + '#/'+$scope.user.id+'/dashboard/'+$scope.user.role);
  }
}]);
