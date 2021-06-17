const isNumeric = (num) => {
  return !isNaN(num);
};

const isWholeNumber = (num) => {
  num = parseInt(num);
  return Number.isInteger(num);
};

const isValidEmail = (email) => {
  var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email);
};

export { isNumeric, isWholeNumber, isValidEmail };
