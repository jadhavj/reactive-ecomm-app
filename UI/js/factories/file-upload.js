ShoppingApp.service('fileUpload', ['$http', '$rootScope', function ($http, $rootScope) {
    this.uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);
        fd.append('sender',	JSON.stringify($rootScope.user));
        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
        .success(function(){
        })
        .error(function(){
        });
    }
}]);
