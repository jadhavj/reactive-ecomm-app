ShoppingApp.directive('validate-email', function(){
  var EMAIL_REGEXP = /[a-zA-Z0-9._-]+@\w[a-zA-Z0-9_]+(\.\w[a-z0-9.])/g;

  return {
    require: 'ngModel',
    restrict: '',
    link: function(scope, elm, attrs, ctrl) {
      // only apply the validator if ngModel is present and Angular has added the email validator
      if (ctrl && ctrl.$validators.email) {

        // this will overwrite the default Angular email validator
        ctrl.$validators.email = function(modelValue) {
          return ctrl.$isEmpty(modelValue) || EMAIL_REGEXP.test(modelValue);
        };
      }
    }
  };
});
