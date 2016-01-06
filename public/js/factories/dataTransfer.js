ShoppingApp.factory('dataTransfer',function(){
  var savedData = {}

  // console.log('in dataTransfer, savedData : ',savedData);

  function set(data){
    savedData = data;
    // console.log("saved data : ",savedData);
  }

  function get(){
    // console.log("getting data : ",savedData);
    return savedData;
  }

  return{
    set: set,
    get: get
  }
});
