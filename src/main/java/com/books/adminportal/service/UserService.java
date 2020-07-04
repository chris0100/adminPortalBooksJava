package com.books.adminportal.service;


import com.books.adminportal.domain.User;
import com.books.adminportal.domain.security.UserRole;

import java.util.Set;

public interface UserService {

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);
}
