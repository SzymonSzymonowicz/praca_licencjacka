package com.myexaminer.service;

import com.myexaminer.model.User;
import com.myexaminer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){this.userRepository = userRepository;}

    public void userSave(User user) { userRepository.save(user); }

    public boolean userExistsById(User user){
        Optional<User> userById = userRepository.findByIdUser(user.getIdUser());

        if(userById.isPresent()){
            return true;
        }

        return false;
    }
}
