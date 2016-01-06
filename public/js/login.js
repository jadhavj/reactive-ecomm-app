ShoppingApp.controller('loginController', ['$scope','$rootScope',function($scope, $rootScope){
  $scope.user = {};

  $scope.submit = function(){
    $scope.errorMessage = ($rootScope.authenticate($scope.user)) ? false : 'Invalid credentials entered. ';
  }

  $scope.clear = function(){
    $scope.errorMessage = null;
  }
}]);
