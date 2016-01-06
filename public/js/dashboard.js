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


ShoppingApp.controller('buyerDashboardController',['$scope', function($scope){

}]);
