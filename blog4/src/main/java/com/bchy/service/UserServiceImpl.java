package com.bchy.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bchy.dao.UserRepository;
import com.bchy.entity.User;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public User checkUser(String username, String password) {
		User user=userRepository.findByUsernameAndPassword(username, password);
		return user;
	}

	@Override
	public List<User> findUserExcludeMe(String username) {
		List<User> userList=userRepository.selectUserExcludeMe(username);
		return userList;
	}

	@Override
	public User addUser(User user) {
		user.setUpdateTime(new Date());
		user.setCreateTime(new Date());
		user.setType(1);	
		return  userRepository.save(user);
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> listUser(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User save(User user1) {
		return userRepository.save(user1);
	}

	@Override
	public int getAllCount() {
		return userRepository.getAllCount();
	}

}
