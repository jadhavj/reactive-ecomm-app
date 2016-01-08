var ShoppingApp = angular.module("ShoppingApp",['ngRoute']);

ShoppingApp.config(['$routeProvider', function($routeProvider){
  $routeProvider.
  when("/signup",{
    templateUrl: 'views/signup.html',
    controller: 'signupController'
  }).
  when("/login",{
    templateUrl: 'views/login.html',
    controller: 'loginController'
  }).
  when("/:id/dashboard/merchant",{
    templateUrl: 'views/merchant-dashboard.html',
    controller: 'merchantDashboardController'
  }).
  when("/:id/dashboard/buyer",{
    templateUrl: 'views/buyer-dashboard.html',
    controller: 'buyerDashboardController'
  }).
  when("/user-id=:id/edit-item/item-id=:id",{
    templateUrl: 'views/edit-item.html',
    controller: 'editItemController'
  }).
  otherwise({
    redirectTo: '/login'
  })
}]);

ShoppingApp.controller('masterController', ['$scope', '$rootScope', '$location', '$window' , function($scope, $rootScope, $location, $window){
  var tempUser = {
    id: '1',
    username: 'sachin@xoriant.com',
    password: 'sachin',
    name: 'Sachin',
    role: 'merchant'
    // role: 'buyer'
  }

//  $scope.user = $rootScope.$watch($rootScope.user);
  $rootScope.user = {};
  $scope.userLoggedIn = $scope.userLoggedIn || false;

  $rootScope.authenticate = function(user){
    if((user.username == tempUser.username) && (user.password == tempUser.password)){
      // console.log('user found, login successful');
      $rootScope.user = $scope.user = tempUser;
      $location.path("/"+$scope.user.id+"/dashboard/"+$scope.user.role);
      $scope.userLoggedIn = true;
      console.log("Welcome ",$scope.user.name);
      return true;
    }
    else{
      alert("Either the username or password is invalid..");
      return false;
    }
  }

  $scope.logout = function(){
    // window.history = null;
    var absUrl = $location.absUrl().split('#');
    // console.log("location : ",absUrl[0]);
    $rootScope.user = {};
    $scope.userLoggedIn = false;
    // $location.path("/login");
    $window.location.replace(absUrl[0] + '#/login');
  }
}]);
