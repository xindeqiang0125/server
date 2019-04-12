package com.xcs.server.web.repository.user;

import com.xcs.server.web.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findByNameContainingOrTelContaining(String name, String tel, Pageable pageable);

    User findFirstByTelAndPassword(String tel, String password);

    @Modifying
    @Query("update User u set u.name = :name,u.password = :password where u.tel = :tel")
    void updateNameAndPassword(@Param(value = "tel") String tel, @Param(value = "name") String name, @Param(value = "password") String password);
}
