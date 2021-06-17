const isNumeric = (num) => {
  return !isNaN(num);
};

const isWholeNumber = (num) => {
  num = parseInt(num);
  return Number.isInteger(num);
};

const isValidEmail = (email) => {
  var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return re.test(email) || "Podana wartość nie jest adresem email";
};

const isValidIndex = (index) => {
  var re = /^\d{6}$/;
  return re.test(index) || "Indeks powinien składać się z 6 cyfr";
};

const isValidPassword = (password) => {
  /*
    This regex will enforce these rules:
    - at least one upper case English letter, (?=.*?[A-Z])
    - at least one lower case English letter, (?=.*?[a-z])
    - at least one digit, (?=.*?[0-9])
    - at least one special character, (?=.*?[#?!@$%^&*-])
    Minimum eight in length .{8,} (with the anchors)
  */
  var re = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/
  return re.test(password) || "Hasło musi mieć minimum:\n- jedną mała i wielką literę\n- jeden znak specjalny\n- jedną cyfrę\n- osiem znaków";
}

const requiredMessage = "Wypełnij to pole"

export { isNumeric, isWholeNumber, isValidEmail, isValidPassword, isValidIndex, requiredMessage };
