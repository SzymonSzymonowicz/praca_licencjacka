import { isEmailUniqueUrl, isIndexUniqueUrl, loginUrl, registerUrl } from "router/urls";

const login = (email, password) => {
  return fetch(loginUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email: email,
      password: password,
    }),
  }).then(async (response) => {
    await response.clone().json().then((data) => {
      if (data.token) {
        localStorage.setItem("account", JSON.stringify(data));
      }
    });

    return response;
  });
};

const logout = () => {
  localStorage.removeItem("account");
};

const getCurrentAccount = () => {
  return JSON.parse(localStorage.getItem("account"));
};

const hasRole = (role) => {
  const currentRoles = getCurrentAccount()?.roles;

  return currentRoles?.includes(role);
};

const isLecturer = () => hasRole("ROLE_LECTURER");

const register = ({
  email, password,
  recoveryQuestion, recoveryAnswer,
  firstName, lastName, index,
  faculty, fieldOfStudy
}) => {
  return fetch(registerUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      email: email,
      password: password,
      recoveryQuestion: recoveryQuestion,
      recoveryAnswer: recoveryAnswer,
      firstName: firstName,
      lastName: lastName,
      index: index,
      faculty: faculty,
      fieldOfStudy: fieldOfStudy,
    })
  })
};

const checkIsEmailUnique = async (email) => {
  const isUnique = await fetch(isEmailUniqueUrl(email), {
    method: "GET"
  })
  .then(res => res.json())
  .catch(err => { console.error(err) })
  
  return isUnique || "Podany email jest już zajęty";
};

const checkIsIndexUnique = async (index) => {
  const isUnique = await fetch(isIndexUniqueUrl(index), {
    method: "GET"
  })
  .then(res => res.json())
  .catch(err => { console.error(err) })
  
  return isUnique || "Podany indeks jest zajęty, jeśli to twój skontaktuj się z Administratorem";
};

export { login, logout, getCurrentAccount, hasRole, register, isLecturer, checkIsEmailUnique, checkIsIndexUnique };
