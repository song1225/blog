package com.bchy.dao;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bchy.entity.Blog;

/**
 * 1.继承使用JPA中的现成语句
 * 2.使用JPA中现成的条件查询语句
 * @author liusong
 *
 * @date 2019年11月28日
 */

public interface BlogRepository extends JpaRepository<Blog, Long>,JpaSpecificationExecutor<Blog>{
     @Query("select b from Blog b where b.recommend=true")
	 List<Blog> findTop(Pageable pageable);
     
     @Query("select b from Blog b where b.title like ?1 or b.content like ?1 or b.description like ?1")
	 Page<Blog> searchList(Pageable pageable, String search);
     
     @Transactional
     @Modifying
     @Query("update Blog b set b.views=b.views+1 where b.id=?1")
	 void updateViews(Long id);

	Page<Blog> findBloyByTypeId(Pageable pageable, Long id);
     
	@Query(value="select DATE_FORMAT(b.update_time,'%Y')as y from t_blog b GROUP BY y;",nativeQuery=true)
	//@Query("select function('data_format',b.updateTime,'%Y') as year from Blog b group by function('data_format',b.updateTime,'%Y') order by year desc")
	List<String> findBlogUpdateYear();
    
	@Query("select b from Blog b where DATE_FORMAT(b.updateTime,'%Y') =?1")
	List<Blog> findBlogByYear(String year);

	 @Query("select b from Blog b where b.user.id=?1")
	List<Blog> findBlogByUserId(Long id);
	 
	@Query("select b from Blog b where b.published=true")
	Page<Blog> findAllPublishBlogs(Pageable pageable);
    
	 @Query("select b from Blog b where b.user.id=?1 and b.published=false")
	List<Blog> findDraftBlogByUserId(Long id);
	 @Query("select count(id) from Blog")
	int getAllCount();
	 @Query("select sum(views) from Blog")
	int getSumViews();
}
