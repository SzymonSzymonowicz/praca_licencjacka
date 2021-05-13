export const timeDiffNow = (date) => date.getTime() - new Date().getTime();

export const isPresentTime = (date) => timeDiffNow(date) >= 0;