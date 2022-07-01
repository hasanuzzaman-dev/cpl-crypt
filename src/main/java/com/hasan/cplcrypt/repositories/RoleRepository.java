package com.hasan.cplcrypt.repositories;


import com.hasan.cplcrypt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findById(long id);
}
