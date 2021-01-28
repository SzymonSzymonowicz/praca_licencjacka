package com.myexaminer.service;

import com.myexaminer.model.Role;
import com.myexaminer.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    public Role returnRoleByName(String name){
        return roleRepository.findByName(name);
    }

    public void roleSave(Role role){
        roleRepository.save(role);
    }
}
