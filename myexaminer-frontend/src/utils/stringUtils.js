export const generateShortcut = (str) =>
  str
    .split(" ")
    .slice(0, 3)
    .reduce((prev, curr) => {
      return prev + curr.charAt(0).toUpperCase();
    }, "");

export const generateHexColor = (str) => {
  var hash = 0;
  for (var i = 0; i < str.length; i++) {
    hash = str.charCodeAt(i) + ((hash << 5) - hash);
  }

  var c = (hash & 0x00ffffff).toString(16).toUpperCase();

  return "#" + "00000".substring(0, 6 - c.length) + c;
};

export const getColorFromRedToGreenByPercentage = (value) => {
  const hue = Math.round(value);
  return ["hsl("+hue+", 50%, 50%)"].join("");
}
