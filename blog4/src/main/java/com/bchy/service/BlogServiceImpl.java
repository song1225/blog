package com.bchy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.bchy.dao.BlogRepository;
import com.bchy.entity.Blog;
import com.bchy.entity.ThumbsUp;
import com.bchy.entity.Type;
import com.bchy.entity.User;
import com.bchy.utils.MarkdownUtil;

import javassist.NotFoundException;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;

	@Override
	public Blog getBlog(Long id) {
		return blogRepository.findOne(id);
	}

	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		return blogRepository.findAllPublishBlogs(pageable);
	}

	@Override
	public Page<Blog> searchBlog(Pageable pageable, Blog blog) {
		return blogRepository.findAll(new Specification<Blog>() {

			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<>();
				if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
					predicates.add(cb.like(root.<String> get("title"), "%" + blog.getTitle() + "%"));
				}
				if (blog.getType().getId() != null) {
					predicates.add(cb.equal(root.<Type> get("type").get("id"), blog.getType().getId()));
				}
				if (blog.isRecommend()) {
					predicates.add(cb.equal(root.<Boolean> get("recommend"), blog.isRecommend()));
				}
				cq.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}

	@Override
	public Blog addBlog(Blog blog) {
		blog.setCreateTime(new Date());
		blog.setUpdateTime(new Date());
		blog.setViews(0);
		return blogRepository.save(blog);
	}

	@Override
	public Blog updateBlog(Long id, Blog blog) {
		Blog b = blogRepository.findOne(id);
		if (b == null) {
			try {
				throw new NotFoundException("该博客不存在！");
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		BeanUtils.copyProperties(blog, b);
		return blogRepository.save(b);
	}

	@Override
	public void deleteBlog(Long id) {
		blogRepository.delete(id);
	}

	@Override
	public List<Blog> getAll() {
		return blogRepository.findAll();
	}

	@Override
	public boolean updateById(Long id, Integer thumbsUp) {
		Blog b = blogRepository.findOne(id);
		Blog res = blogRepository.save(b);
		if (res != null) {
			return true;
		}
		return false;
	}

	@Override
	public List<Blog> listBlogTop(Integer size) {
		Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
		Pageable pageable = new PageRequest(0, size, sort);
		return blogRepository.findTop(pageable);
	}

	@Override
	public Page<Blog> searchList(Pageable pageable, String search) {
		return blogRepository.searchList(pageable, search);
	}

	@Override
	public Blog getBlogConvertHtml(Long id) {
		Blog blog = blogRepository.findOne(id);
		Blog b = new Blog();
		BeanUtils.copyProperties(blog, b);
		String content = b.getContent();
		b.setContent(MarkdownUtil.markdownToHtmlExtensions(content));
		blogRepository.updateViews(id);
		return b;
	}

	@Override
	public Page<Blog> listBlogByTypeId(Pageable pageable, Long id) {
		return blogRepository.findBloyByTypeId(pageable, id);
	}

	@Override
	public Page<Blog> listBlogByTagId(Pageable pageable, Long id) {
		return blogRepository.findBloyByTypeId(pageable, id);
	}

	@Override
	public Map<String, List<Blog>> archiveBlog() {
		List<String> years = blogRepository.findBlogUpdateYear();
		Map<String, List<Blog>> map = new HashMap<String, List<Blog>>();
		for (String year : years) {
			map.put(year, blogRepository.findBlogByYear(year));
		}
		return map;
	}

	@Override
	public long getBlogCount() {
		return blogRepository.count();
	}

	/**
	 * 点赞功能
	 */
	@Override
	public Blog addThumbsUp(Long id) {
		Blog blog = blogRepository.findOne(id);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		User user=(User) request.getSession().getAttribute("user");
		ThumbsUp thumbsUp=new ThumbsUp(user);
		boolean isExist =blog.addThumbsUp(thumbsUp);
		if (isExist) {
			throw new IllegalArgumentException("该用户已经点过赞了");
		}
		return blogRepository.save(blog);
	}

	/**
	 * 取消点赞
	 */
	@Override
	public void removeThumbsUp(Long blogId, Long thumbsUpId) {
		Blog blog = blogRepository.findOne(blogId);
		blog.removeThumbsUp(thumbsUpId);
		blogRepository.save(blog);
	}

	@Override
	public List<Blog> getBlogByUserId(Long id) {
		return blogRepository.findBlogByUserId(id);
	}

	@Override
	public List<Blog> getDraftBlogByUserId(Long id) {
		
		return blogRepository.findDraftBlogByUserId(id);
	}

	@Override
	public int getAllCount() {
		return blogRepository.getAllCount();
	}

	@Override
	public int sumViews() {
		return blogRepository.getSumViews();
	}

}
