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

ShoppingApp.controller('masterController', ['$scope', '$rootScope', 'getAbsUrl' , function($scope, $rootScope, getAbsUrl){

	var appBaseUrl = getAbsUrl.url();
	var arr = appBaseUrl.split("/");
	$rootScope.appBaseUrl = arr[0] + "//" + arr[2];
	$rootScope.wsBaseUrl = "ws://" + arr[2];

  init()
  function init() {
    if (!store.enabled) {
      alert('Local storage is not supported by your browser.\n Please disable "Private Mode", or upgrade to a modern browser.\n Or switch browser')
      return
    }
  }

  var user = store.get('user');
  $rootScope.user = (user) ? user : {};
  var userLoggedIn = store.get('userLoggedIn');
  $scope.userLoggedIn = (userLoggedIn) ? true: false;
  var cartIsEmpty = store.get('cartIsEmpty');
  $scope.cartIsEmpty = (cartIsEmpty) ? true : false;
  // $scope.$apply();

  $scope.$on("cart-count", function(count){
    console.log("cart-count --> app.js", count);
    getAbsUrl.reload();
  });

  $scope.logout = function(){
    $rootScope.user = {};
    $scope.userLoggedIn = false;
    store.clear();
    getAbsUrl.navigateTo('/login/');
  }

  $scope.gotoCart = function(){
    getAbsUrl.navigateTo("/user-id=" + $rootScope.user.username + "/cart/");
  }

  $rootScope.authenticated = function(user){
    $scope.user = user;
    console.log("user : ",user);
    $scope.userLoggedIn = true;
    store.set('user', $rootScope.user);
    store.set('userLoggedIn', true);
  }
}]);
