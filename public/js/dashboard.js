ShoppingApp.controller('merchantDashboardController',['$scope','$location', function($scope, $location){
  $scope.user_id = $location.url().split('/')[1];
  // $scope.items = {};

  $scope.getListOfProducts = function(){

    var ws = new WebSocket("ws://localhost:9000/products");
    ws.onopen = function()
    {
       ws.send(JSON.stringify({"username": $scope.user_id}));
    };

    ws.onmessage = function (evt)
    {
       var items = angular.copy(JSON.parse(evt.data));
       $scope.items = items;
       console.log("products : ", $scope.items);
       $scope.$apply();
       ws.close();
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };
  }

  $scope.getListOfProducts();

  $scope.top_sellers = top_sellers;
  $scope.yearly_revenues = yearly_revenues;

  var donut = c3.generate({
    bindto: '#top-sellers-donut',
    data: {
      // x: 'x',
      columns: $scope.top_sellers,
      type: 'donut'
    },
    donut:{
      title: 'Top Selling Products'
    }
  });

  var bar = c3.generate({
    bindto: '#yearly-sales-revenue',
    data: {
      x: 'x',
      columns: $scope.yearly_revenues,
      type: 'bar'
    },
    axis:{
      x: {
        type: 'category'
      }
    }
  });

  var historyObj = window.history;

    $scope.addNewItem = function(){
    console.log("Adding new item");
    var user_id = $location.url().split('/')[1];
    $location.path("/user-id="+user_id+"/add-item/");
  }

  // console.log(" browser history : ",historyObj);
}]);



ShoppingApp.controller('bulkUploadController', ['$scope', '$location', 'fileUpload', function($scope, $location, fileUpload){

    $scope.uploadFile = function(){
        var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        var absUrl = $location.absUrl().split('#');
        var uploadUrl = "http://localhost:9000/uploadProducts";
        console.log("uploadUrl : ",uploadUrl);
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };

}]);

ShoppingApp.controller('buyerDashboardController',['$scope', '$location', function($scope, $location){
  // currently accepting the items as the products for the buyer. need to have all the items from all the merchants in all items
  $scope.user_id = $location.url().split('/')[1];

  $scope.getRecommendedProducts = function(){
    var ws = new WebSocket("ws://localhost:9000/products");
    ws.onopen = function()
    {
       ws.send(JSON.stringify({"username": $scope.user_id}));
    };

    ws.onmessage = function (evt)
    {
       var items = angular.copy(JSON.parse(evt.data));
       $scope.items = items;
       $scope.recommendations = ($scope.items) ? $scope.items.slice(0,5) : [];
       console.log("recommended products : ", $scope.items);
       $scope.$apply();
       ws.close();
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };
  }

  $scope.getRecommendedProducts();

  console.log("recommendations ", $scope.recommendations  );

  $scope.search = function(){
    // $scope.searchResults = items;

    var ws = new WebSocket("ws://localhost:9000/searchProducts");
    ws.onopen = function()
    {
       ws.send(JSON.stringify({"search_string": $scope.searchProduct}));
    };

    ws.onmessage = function (evt)
    {
       $scope.searchResults = angular.copy(JSON.parse(evt.data));
       console.log("search products : ", $scope.items);
       $scope.$apply();
       ws.close();
    };

    ws.onclose = function(){
      console.log("closing the connection");
    };

    var search = document.getElementById('search-results');
    search.style.display = 'block';
  }


}]);
