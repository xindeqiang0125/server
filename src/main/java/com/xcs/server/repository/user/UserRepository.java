package com.xcs.server.repository.user;

import com.xcs.server.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    Page<User> findByNameContainingOrTelContaining(String name,String tel,Pageable pageable);

    User findFirstByTelAndPassword(String tel,String password);
}
