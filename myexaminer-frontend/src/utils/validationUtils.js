const isNumeric = (num) => {
  return !isNaN(num);
};

const isWholeNumber = (num) => {
  num = parseInt(num);
  return Number.isInteger(num);
};

export { isNumeric, isWholeNumber };
