import { groupIsUniqueNameUrl } from "router/urls";
import authHeader from "services/auth-header";

export const checkIsGroupNameUnique = async (name) => {
  const isUnique = await fetch(groupIsUniqueNameUrl(name), {
    method: "GET",
    headers: authHeader()
  })
  .then(res => res.json())
  .catch(err => { console.error(err) })
  
  return isUnique || "Podana nazwa grupy jest już zajęta, podaj inną nazwę";
};