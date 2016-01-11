ShoppingApp.controller('merchantDashboardController',['$scope', function($scope){
  $scope.items = items;
  $scope.top_sellers = top_sellers;
  $scope.yearly_revenues = yearly_revenues;
  // console.log("items : ",$scope.items);

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
  // console.log(" browser history : ",historyObj);
}]);



ShoppingApp.controller('bulkUploadController', ['$scope', '$location', 'fileUpload', function($scope, $location, fileUpload){

    $scope.uploadFile = function(){
        var file = $scope.myFile;
        console.log('file is ' );
        console.dir(file);
        var absUrl = $location.absUrl().split('#');
        var uploadUrl = absUrl[0]+"#/fileUpload";
        console.log("uploadUrl : ",uploadUrl);
        fileUpload.uploadFileToUrl(file, uploadUrl);
    };

}]);

ShoppingApp.controller('buyerDashboardController',['$scope', '$location', function($scope, $location){
  // currently accepting the items as the products for the buyer. need to have all the items from all the merchants in all items

  $scope.items = items.slice(0,5);
  $scope.url = $location.absUrl();
  console.log("items : ",$scope.items);
}]);
