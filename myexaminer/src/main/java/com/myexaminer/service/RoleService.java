package com.myexaminer.service;

import com.myexaminer.enums.RoleEnum;
import com.myexaminer.entity.Role;
import com.myexaminer.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(RoleEnum roleEnum) {
        return roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new EntityNotFoundException("Role of name (enum): " + roleEnum + " was not found."));
    }
}
