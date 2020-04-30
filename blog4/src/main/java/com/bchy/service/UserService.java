package com.bchy.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bchy.entity.User;

public interface UserService {
	User checkUser(String username,String password);

	List<User> findUserExcludeMe(String username);

	User addUser(User user);

	List<User> getAll();

	Page<User> listUser(Pageable pageable);

	User getUserById(Long id);

	User save(User user1);

	int getAllCount();
}
