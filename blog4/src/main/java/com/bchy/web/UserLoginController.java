package com.bchy.web;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bchy.entity.Blog;
import com.bchy.entity.Collection;
import com.bchy.entity.Gallery;
import com.bchy.entity.Log;
import com.bchy.entity.ThumbsUp;
import com.bchy.entity.User;
import com.bchy.service.BlogService;
import com.bchy.service.CollectionService;
import com.bchy.service.GalleryService;
import com.bchy.service.LogService;
import com.bchy.service.NoticeService;
import com.bchy.service.TagService;
import com.bchy.service.TypeService;
import com.bchy.service.UserService;
import com.bchy.utils.FileUtil;

@Controller
@RequestMapping("/user")
public class UserLoginController {

	@Autowired
	private UserService userService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private TagService tagService;
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private LogService logService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private GalleryService galleryService;

	/**
	 * 跳转到登录界面，删除session内容
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping("/toLoginPage")
	public String toLoginPage(HttpSession session) {
		session.removeAttribute("user");
		return "login";
	}

	@RequestMapping("/toRegisterPage")
	public String toRegisterPage() {
		return "register";
	}

	/**
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @param attributes
	 * @return
	 */

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	public String webLogin(String username, String password, HttpSession session, RedirectAttributes attribute,
			@PageableDefault(value = 5, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
			Model model) {
		User user = userService.checkUser(username, password);
		model.addAttribute("page", blogService.listBlog(pageable));
		model.addAttribute("types", typeService.listTypeTop(6));
		model.addAttribute("tags", tagService.listTagTop(10));
		model.addAttribute("recommendBlogs", blogService.listBlogTop(6));
		if (user != null) {
			model.addAttribute("notices", noticeService.getNoticeByNameId(user.getId()));
			// 登录日志
			Log log = new Log(user, 0, true, new Date());
			logService.addLog(log);
			// 密码设置为空，前台获取不到密码，保证账户安全
			user.setPassword(null);
			session.setAttribute("user", user);
			return "index";
		} else {
			attribute.addFlashAttribute("message", "用户名或密码错误！");
			return "redirect:toLoginPage";
		}

	}

	/**
	 * 个人中心界面-----个人中心界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/userInfo")
	public String userInfo() {
		return "user/userInfo";
	}
	
	
	@RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
	public String updateUserInfo(User user, Model model, @RequestParam("file") MultipartFile file,
			HttpServletRequest request,HttpSession session) throws IllegalStateException, IOException {
		User user1=(User)session.getAttribute("user");
		String avatar="";
		if(!file.isEmpty()){
			String filename = file.getOriginalFilename();
			//检查是否为IE浏览器，修改filename路径为文件名
			int unixSep = filename.lastIndexOf('/');	
			// Check for Windows-style path		
			int winSep = filename.lastIndexOf('\\');
			// Cut off at latest possible point		
			int pos = (winSep > unixSep ? winSep : unixSep);
			if (pos != -1)  {			
					
				filename = filename.substring(pos + 1);	
				}
			 // 存放上传图片的文件夹
	        File fileDir = FileUtil.getImgDirFile();
	        File newFile = new File(fileDir.getAbsolutePath() + File.separator + filename);       
	        file.transferTo(newFile);	       
	        //截取地址保存至数据库
	        String realFile=fileDir.getAbsolutePath() + File.separator + filename;
	        int begin=realFile.indexOf("\\upload");
	        int end=realFile.length();
	        avatar=realFile.substring(begin, end);
		}
		User u=userService.getUserById(user1.getId());
	    user1.setAvatar(avatar);
		user1.setPassword(u.getPassword());
		user1.setUsername(user.getUsername());
		user1.setNickname(user.getNickname());
		user1.setEmail(user.getEmail());
		userService.addUser(user1);
		return "redirect:/user/userInfo";
	}

	/**
	 * 个人中心界面-----修改密码界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updatePassword")
	public String updatePassword() {
		return "user/updatePassword";
	}

	@RequestMapping(value = "/updatePasswordByName", method = RequestMethod.POST)
	public String updatePasswordByName(HttpSession session, RedirectAttributes attribute,
			@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword1") String newPassword1,
			@RequestParam("newPassword2") String newPassword2) {
		User user = (User) session.getAttribute("user");
		Long userId = user.getId();
		User user1 = userService.getUserById(userId);
		if (!user1.getPassword().equals(oldPassword)) {
			attribute.addFlashAttribute("message", "原始密码输入错误！");
		} else if (!newPassword1.equals(newPassword2)) {
			attribute.addFlashAttribute("message", "两次密码输入不一致！");
		} else {
			user1.setPassword(newPassword1);
			User user2 = userService.save(user1);
			if (user2 != null) {
				attribute.addFlashAttribute("success", "恭喜您，操作成功！");
			}
		}
		return "redirect:/user/updatePassword";
	}

	/**
	 * 个人中心界面-----我发布的文章
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "getMyBlogs")
	public String getMyBlogs(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Blog> blogs = blogService.getBlogByUserId(user.getId());
		model.addAttribute("blogs", blogs);
		return "user/myBlogs";
	}

	/**
	 * 个人中心界面-----草稿列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "noPublishedBlogs")
	public String noPublishedBlogs(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Blog> blogs = blogService.getDraftBlogByUserId(user.getId());
		model.addAttribute("blogs", blogs);
		return "user/draft";
	}

	@RequestMapping(value = "publishedBlog")
	public String publishBlog() {

		return "redirect:/user/noPublishedBlogs";
	}

	/**
	 * 个人中心界面-----收藏列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "collectionList")
	public String collectionList(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		// 创建集合存储博客集合
		List<Blog> blogs = new ArrayList<Blog>();
		List<Collection> collectionList = collectionService.getAllCollections(user.getId());
		for (Collection collection : collectionList) {
			blogs.add(collection.getBlog());
		}
		model.addAttribute("blogs", blogs);
		return "user/collection";
	}

	/**
	 * 个人中心界面-----个人相册列表
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "galleryList")
	public String galleryList(HttpSession session, Model model) {
		User user=(User)session.getAttribute("user");
		List<Gallery> galleryList=galleryService.findAllByUser(user.getId());
		model.addAttribute("galleryList", galleryList);
		return "user/gallery";
	}

	/**
	 * 会员填写博客，跳转发布界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAddBlogPage")
	public String toAddBlogPage(Model model) {
		model.addAttribute("types", typeService.allType());
		model.addAttribute("tags", tagService.allTag());
		model.addAttribute("blog", new Blog());
		return "blog_input";
	}

	/**
	 * 发布文章
	 * 
	 * @param blog
	 * @param typeId
	 * @param tagIds
	 * @param moel
	 * @param session
	 * @return
	 */
	@Transactional
	@RequestMapping(value = "/addBlog", method = RequestMethod.POST)
	public String addBlog(Blog blog, long typeId, String tagIds, Model moel, HttpSession session) {
		blog.setUser((User) session.getAttribute("user"));
		blog.setType(typeService.getType(typeId));
		blog.setTags(tagService.listTag(blog.getTagIds()));
		blogService.addBlog(blog);
		return "redirect:/user";
	}

	/**
	 * 进入博客详情页
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/blog/{id}")
	public String blogInfo(@PathVariable Long id, Model model) {
		// 获取上下文中session中用户信息
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		User user = (User) request.getSession().getAttribute("user");
		// 获取博客点赞情况，并判断用户是否已经点赞
		ThumbsUp currentThumbsUp = null;
		Blog blog = blogService.getBlog(id);
		List<ThumbsUp> thumbsUps = blog.getThumbsUps();
		if (user != null) {
			for (ThumbsUp thumbsUp : thumbsUps) {
				System.out.println(thumbsUp.toString());
				if (thumbsUp.getUser().getUsername() != "" && thumbsUp.getUser().getUsername() != null) {

					if (thumbsUp.getUser().getUsername().equals(user.getUsername())) {
						currentThumbsUp = thumbsUp;
					} else {
						currentThumbsUp = null;
					}
				}

			}
		}

		// 获取收藏情况，判断用户是否已经收藏
		long userId = user.getId();
		long blogId = id;
		Collection c = collectionService.getCollectionByUserAndBlog(userId, blogId);
		boolean currentCollection = false;
		if (c != null) {
			currentCollection = true;
		} else {
			currentCollection = false;
		}
		model.addAttribute("blog", blogService.getBlogConvertHtml(id));
		model.addAttribute("currentThumbsUp", currentThumbsUp);
		model.addAttribute("currentCollection", currentCollection);
		return "blog";
	}

}
