package com.hasan.cplcrypt.services;


import com.hasan.cplcrypt.models.Role;
import com.hasan.cplcrypt.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role saveRole(Role role){
        return roleRepository.save(role);
    }

    public Role findById(long id){
        return roleRepository.findById(id);
    }
}
