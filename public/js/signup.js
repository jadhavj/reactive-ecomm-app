ShoppingApp.controller('signupController', ['$scope', '$rootScope', 'getAbsUrl', function($scope, $rootScope, getAbsUrl){
  $scope.user = {};
  $scope.card_types = ['VISA', 'Master', 'Maestro', 'Rupay'];

  $scope.user.role = "buyer";
  $scope.user.card_details = {};
  $scope.user.card_details.card_type = "VISA";

  var today = new Date();
  $scope.today = {};
  $scope.today.date = (today.getDate() > 10) ? today.getDate() : "0"+today.getDate();
  $scope.today.month = ((today.getMonth()+1) > 10) ? (today.getMonth()+1) : "0"+(today.getMonth()+1);
  $scope.today.year = today.getFullYear();
  $scope.today = $scope.today.year + "-" + $scope.today.month + "-" + $scope.today.date;
  $scope.max_date = ($scope.today.year + 15) + "-12-31"

  $scope.reset = function(form){
    if(form){
      form.$setPristine();
      form.$setUntouched();
    }
  }

  $scope.registerUser = function(){
    console.log("new user registered : ", $scope.user);
    var ws = new WebSocket($rootScope.wsBaseUrl + "/signup");
    ws.onopen = function()
    {
       ws.send(JSON.stringify($scope.user));
    };

    ws.onmessage = function (evt)
    {
       $scope.user = JSON.parse(evt.data);
       console.log("Congratulations...");
      //  $location.path("/login/")
      $('#registration-success').modal('show');
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };
  }

  $scope.navigateToLogin = function(){
    getAbsUrl.navigateTo('/login/');
  }
}]);
