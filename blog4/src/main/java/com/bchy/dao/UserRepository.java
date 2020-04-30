package com.bchy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bchy.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUsernameAndPassword(String username,String password);
	
	@Query(value = "select * from t_user where username!=?1", nativeQuery = true)
	List<User> selectUserExcludeMe(String username);
	@Query(value="select count(id) from t_user",nativeQuery=true)
	int getAllCount();
}
