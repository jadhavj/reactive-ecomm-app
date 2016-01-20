var ShoppingApp = angular.module("ShoppingApp",['ngRoute']);

ShoppingApp.config(['$routeProvider', function($routeProvider){
  $routeProvider.
  when("/signup/",{
    templateUrl: 'views/signup.html',
  }).
  when("/login/",{
    templateUrl: 'views/login.html',
  }).
  when("/:id/dashboard/seller/",{
    templateUrl: 'views/merchant-dashboard.html',
  }).
  when("/:id/dashboard/buyer/",{
    templateUrl: 'views/buyer-dashboard.html',
  }).
  when("/user-id=:id/edit-item/item-id=:id/",{
    templateUrl: 'views/edit-item.html',
  }).
  when("/user-id=:id/add-item/",{
    templateUrl: 'views/add-Product.html',
  }).
  when("/user-id=:id/buyer/id=:id/",{
    templateUrl: 'views/detailed-product-description.html',
  }).
  when("/user-id=:id/cart/",{
    templateUrl: 'views/buyer-cart.html',
  }).
  when("/user-id=:id/checkout/",{
    templateUrl: 'views/buyer-checkout.html',
  }).
  otherwise({
    redirectTo: '/login/'
  })
}]);

ShoppingApp.controller('masterController', ['$scope', '$rootScope', '$location', '$window' , function($scope, $rootScope, $location, $window){

	var appBaseUrl = $location.absUrl();
	var arr = appBaseUrl.split("/");
	$rootScope.appBaseUrl = arr[0] + "//" + arr[2];
	$rootScope.wsBaseUrl = "ws://" + arr[2];
	console.log("appBaseUrl", $rootScope.appBaseUrl);
	console.log("wsBaseUrl", $rootScope.wsBaseUrl);

  $rootScope.user = {};
  $scope.userLoggedIn = $scope.userLoggedIn || false;


  $scope.logout = function(){
    var absUrl = $location.absUrl().split('#');
    $rootScope.user = {};
    $scope.userLoggedIn = false;
    $window.location.replace(absUrl[0] + '#/login/');
  }

  $scope.gotoCart = function(){
    var absUrl = $location.absUrl().split('#')[0];
    $window.location =  absUrl + "#/user-id=" + $rootScope.user.username + "/cart/";
  }

  $rootScope.authenticated = function(user){
      $scope.user = user;
      console.log("user : ",user);
      $scope.userLoggedIn = true;
  }
}]);
