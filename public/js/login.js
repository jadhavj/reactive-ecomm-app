ShoppingApp.controller('loginController', ['$scope','$rootScope', '$location',function($scope, $rootScope, $location){
  $scope.user = {};

  $scope.submit = function(){
    $scope.errorMessage = ($rootScope.authenticate($scope.user)) ? false : 'Invalid credentials entered. ';
  }

  $scope.clear = function(){
    $scope.errorMessage = null;
  }

  $scope.signup = function(){
    $location.path('/signup')
  }
}]);
