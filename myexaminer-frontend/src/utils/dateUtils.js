export const timeDiffNow = (date) => date.getTime() - new Date().getTime();

export const isPresentTime = (date) => timeDiffNow(date) >= 0;

export const compareDates = (a, b) => a.getTime() - b.getTime();

// from date, duration in minutes
export const timeLeftInMinsAndSecs = (from, duration) => {
  let time = duration * 60000 - (new Date().getTime() - new Date(from).getTime());
  let minutes = ~~(time / 60000);
  let seconds = ~~((time % 60000) / 1000);

  return [minutes, seconds];
}