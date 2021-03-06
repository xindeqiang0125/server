package com.xcs.server.web.repository.user;

import com.xcs.server.web.domain.user.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup,Integer> {
    List<UserGroup> findByNameContaining(String name);
}
