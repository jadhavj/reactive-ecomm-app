ShoppingApp.factory('getAbsUrl',['$location', '$window', function($location, $window){
  function url(){
    var path = $location.absUrl().split('#')[0] + "#";
    return path;
  }

  function navigateTo(goto){
    var absUrl = url();
    var gotoPath = absUrl + goto;
    $window.location = gotoPath;
  }

  function reload(){
    $window.location.reload();
  }

  return{
    url: url,
    navigateTo: navigateTo,
    reload: reload
  }

}]);
