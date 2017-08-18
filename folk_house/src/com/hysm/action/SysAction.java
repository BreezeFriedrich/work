package com.hysm.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hysm.domain.Hotel;
import com.hysm.pojo.Category;
import com.hysm.pojo.House;
import com.hysm.pojo.Lock;
import com.hysm.pojo.Manager;
import com.hysm.pojo.Merchants;
import com.hysm.pojo.Order;
import com.hysm.pojo.PageBean;
import com.hysm.pojo.Supporting;
import com.hysm.pojo.Tag;
import com.hysm.pojo.User;
import com.hysm.util.ImagesTool;
import com.hysm.util.Upload;
import com.opensymphony.xwork2.ActionContext;

public class SysAction extends BaseAction {
	Logger logger = Logger.getLogger(HouseAction.class);
	private String id, c_name, pn, name, pwd, city, search_id, tag_name, parent_id, domains_id, sel, type;
	private int state, user_id;
	private Manager manager;
	private Tag tag;
	private int sup_type;
	private File[] file;
	private String[] fileFileName;
	private String sup_name1, sup_type1;
	private String sup_img2, sup_name2;
	private String stime, etime, merchants_id, m_state;
	private String lock_name, is_idcard, is_pwd;

	public void login() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = sysService.regs_name(name);
			if (manager != null) {
				System.out.println(pwd);
				manager = sysService.regs_pwd(pwd);
				if (manager != null) {
					session.setAttribute("manager", manager);
					out.print("success");
				} else {
					out.print("error_pwd");
				}
			} else {
				out.print("error_name");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}

	}

	/**
	 * 类目
	 * 
	 * @return
	 */
	public String category_list() {
		manager = (Manager) session.getAttribute("manager");
		if (manager != null) {
			int pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > pageNum) {
					pageNum = Integer.parseInt(pn);
				}
			}
			PageBean<Category> pb = sysService.get_category_list(pageNum);
			List<Category> list = pb.getList();
			request.setAttribute("pb", pb);
			request.setAttribute("list", list);
			return "category";
		} else {
			return "login";
		}

	}

	/**
	 * 验证类目名称
	 */
	public void get_categoey_name() {
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				List<Category> list = sysService.get_categoey_name();
				JSONArray jsonArray = JSONArray.fromObject(list);
				out.print(jsonArray);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 添加类目
	 */
	public void add_category() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				int i = sysService.add_category(c_name);
				if (i == 1) {
					out.print("success");
				} else {
					out.print("error");
				}
			} else {
				out.print("login");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 删除类目
	 */
	public void del_category() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("")) {
					int i = sysService.del_category(Integer.parseInt(id));
					if (i == 1) {
						out.print("success");
					} else {
						out.print("error");
					}
				} else {
					out.print("error");
				}
			} else {
				out.print("login");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 得到类目信息
	 */
	public void get_category() {
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			Category category = new Category();
			if (manager != null) {
				if (id != null && !id.equals("")) {
					category = sysService.get_category(Integer.parseInt(id));
					if (category != null) {
						category.setData("success");
					}
				}
			} else {
				category.setData("login");
			}
			JSONArray jsonArray = JSONArray.fromObject(category);
			out.print(jsonArray);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 更新类目
	 */
	public void edit_category() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("")) {
					int i = sysService.edit_category(c_name, state,
							Integer.parseInt(id));
					if (i == 1) {
						out.print("success");
					} else {
						out.print("error");
					}
				} else {
					out.print("error");
				}
			} else {
				out.print("login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 标签列表
	 * 
	 * @return
	 */
	public String tag_list() {
		manager = (Manager) session.getAttribute("manager");
		if (manager != null) {
			int pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > pageNum) {
					pageNum = Integer.parseInt(pn);
				}
			}
			System.out.println(search_id);
			PageBean<Tag> pb = sysService
					.get_tag_list(pageNum, city, search_id);
			List<Tag> list = pb.getList();
			List<Tag> list1 = new ArrayList<Tag>();
			List<Tag> list2 = new ArrayList<Tag>();
			List<Tag> list3 = new ArrayList<Tag>();
			for (Tag t : list) {
				if (t.getParent_id() == 0) {
					Tag tag = new Tag();
					tag.setDomains_id(t.getDomains_id());
					tag.setD_name(t.getD_name());
					tag.setParent_id(t.getParent_id());
					tag.setState(t.getState());
					tag.setTag_id(t.getTag_id());
					tag.setTag_name(t.getTag_name());
					list1.add(tag);
				}
			}

			String tag_name = "";
			int count = 0;
			for (Tag t1 : list1) {
				for (Tag t : list) {
					Tag tag = new Tag();
					tag.setFtag_name(t1.getTag_name());
					tag.setTag_id(t1.getTag_id());
					if (t.getParent_id() == t1.getTag_id()) {
						for (Tag t2 : list) {
							count++;
							if (t2.getDomains_id() == t.getDomains_id()) {
								if (t2.getTag_id() == t.getTag_id()) {
									tag.setDomains_id(t.getDomains_id());
									tag.setD_name(t.getD_name());
									tag.setParent_id(t.getParent_id());
									tag.setState(t.getState());
									tag.setTag_id(t.getTag_id());
									tag.setTag_name(t.getTag_name());
									list2.add(tag);
								}
							}
						}
					}
				}
			}

			for (Tag t1 : list2) {
				for (Tag t : list) {
					if (t.getParent_id() == t1.getTag_id()) {
						Tag tag = new Tag();
						tag.setDomains_id(t.getDomains_id());
						tag.setD_name(t.getD_name());
						tag.setParent_id(t.getParent_id());
						tag.setState(t.getState());
						tag.setTag_id(t.getTag_id());
						tag.setTag_name(t.getTag_name());
						list3.add(tag);
					}
				}
			}

			request.setAttribute("pb", pb);
			request.setAttribute("list1", list1);
			request.setAttribute("list2", list2);
			request.setAttribute("list3", list3);
			return "tag";
		} else {
			return "login";
		}
	}

	/**
	 * 子标签
	 */
	public void get_tag_z() {
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("")) {
					List<Tag> list = sysService.get_tag_z(Integer.parseInt(id));
					JSONArray jsonArray = JSONArray.fromObject(list);
					out.print(jsonArray);
				}
			} else {
				out.print("login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 添加标签
	 */
	public void add_tag() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			int parent_id1 = 0;
			if (manager != null) {
				System.out.println("tag_name:" + tag_name);
				if (parent_id != null && !parent_id.equals("")) {
					parent_id1 = Integer.parseInt(parent_id);
				} else {
					parent_id1 = 0;
				}
				String domains_id1 = "";
				if (sel != null && !sel.equals("")) {
					if (Integer.parseInt(sel) == 2) {
						domains_id1 = domains_id;
					} else {
						domains_id1 = "";
					}
				}
				int i = sysService.add_tag(tag_name, parent_id1, domains_id1);
				if (i == 1) {
					out.print("success");
				} else {
					out.print("error");
				}
			} else {
				out.print("login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 得到标签
	 */
	public void get_tag() {
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			Tag tag1 = new Tag();
			if (manager != null) {
				if (id != null && !id.equals("")) {
					tag1 = sysService.get_tag(Integer.parseInt(id), type);
					if (tag1 != null) {
						tag1.setData("success");
					}
				}
			} else {
				tag1.setData("login");

			}
			JSONArray jsonArray = JSONArray.fromObject(tag1);
			out.print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 更新标签
	 */
	public void edit_tag() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("")) {
					System.out.println(domains_id + "&&&&&"
							+ domains_id.length());
					String domains_id1 = "0";
					if (domains_id != null && !domains_id.equals("")) {
						domains_id1 = domains_id;
					}
					int i = sysService.edit_tag(Integer.parseInt(id), tag_name,
							state, domains_id1);
					if (i == 1) {
						out.print("success");
					} else {
						out.print("error");
					}
				} else {
					out.print("error");
				}
			} else {
				out.print("login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 删除标签
	 */
	public void del_tag() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("")) {
					int i = sysService.del_tag(Integer.parseInt(id));
					if (i == 1) {
						out.print("success");
					} else {
						out.print("error");
					}
				} else {
					tag.setData("error");
				}
			} else {
				out.print("login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 设施配套列表
	 * 
	 * @return
	 */
	public String get_supporting_list() {
		manager = (Manager) session.getAttribute("manager");
		if (manager != null) {
			int pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > pageNum) {
					pageNum = Integer.parseInt(pn);
				}
			}
			if (sup_type == 0) {
				sup_type = 1;
			}
			PageBean<Supporting> pb = sysService.get_supporting_list(pageNum, sup_type);
			List<Supporting> list = pb.getList();
			request.setAttribute("pb", pb);
			request.setAttribute("list", list);
			request.setAttribute("sup_type", sup_type);
			return "supproting";
		} else {
			return "login";
		}
	}

	/**
	 * 添加配套、设施
	 * 
	 * @return
	 */
	public String add_sup() {
		manager = (Manager) session.getAttribute("manager");
		if (manager != null) {
			Upload upload = new Upload();
			String url = upload.upload_url(file, "supp_img", fileFileName);
			String ready_url = url.replaceAll("\\\\", "/");
			String ext = ready_url.substring(ready_url.lastIndexOf("."));// 后缀
			long time = System.currentTimeMillis();
			String saveurl = ServletActionContext.getServletContext().getRealPath("") + "\\supp_img\\" + time + ext;
			System.out.println("saveurl:" + saveurl);
			ImagesTool.createThumbnail(ready_url, saveurl.replaceAll("\\\\", "/"), 40, 40);
			if (sup_type1 != null && !sup_type1.equals("")) {
				System.out.println(sup_type1);
				sysService.insert_supporting(Integer.parseInt(sup_type1), sup_name1, "\\supp_img\\" + time + ext);
				sup_type = Integer.parseInt(sup_type1);
			}
			return get_supporting_list();
		} else {
			return "login";
		}

	}

	public void del_sup() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("")) {
					System.out.println(id);
					int i = sysService.del_sup(Integer.parseInt(id));
					if (i == 1) {
						out.print("success");
					} else {
						out.print("error");
					}
				} else {
					out.print("error");
				}
			} else {
				out.print("login");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 得到配套设施
	 */
	public void get_sup() {
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			Supporting sup = new Supporting();
			if (manager != null) {
				if (id != null && !id.equals("")) {
					sup = sysService.get_sup(Integer.parseInt(id));
					if (sup != null) {
						sup.setData("success");
					}
				}
			} else {
				sup.setData("login");
			}
			JSONArray jsonArray = JSONArray.fromObject(sup);
			out.print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	public String update_sup() {
		manager = (Manager) session.getAttribute("manager");
		String img_url = "";
		System.out.println(sup_img2);
		if (manager != null) {
			if (sup_img2 != null && !sup_img2.equals("")) {
				img_url = sup_img2;
			} else {
				Upload upload = new Upload();
				String url = upload.upload_url(file, "supp_img", fileFileName);
				String ready_url = url.replaceAll("\\\\", "/");
				String ext = ready_url.substring(ready_url.lastIndexOf("."));// 后缀
				long time = System.currentTimeMillis();
				String saveurl = ServletActionContext.getServletContext().getRealPath("") + "\\supp_img\\" + time + ext;
				ImagesTool.createThumbnail(ready_url, saveurl.replaceAll("\\\\", "/"), 25, 25);
				img_url = "\\supp_img\\" + time + ext;
			}
			if (id != null && !id.equals("")) {
				sysService.update_sup(sup_name2, img_url, sup_type, state, Integer.parseInt(id));
			}
			return get_supporting_list();
		} else {
			return "login";
		}

	}

	/**
	 * 校验配套设施名称
	 */
	public void get_sup_name() {
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("")) {
					List<Supporting> sup = sysService.get_sup_name(Integer.parseInt(id));
					JSONArray jsonArray = JSONArray.fromObject(sup);
					out.print(jsonArray);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 订单列表
	 * 
	 * @return
	 */
	public String get_order_list() {
		manager = (Manager) session.getAttribute("manager");

		if (manager != null) {
			int pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > pageNum) {
					pageNum = Integer.parseInt(pn);
				}
			}
			PageBean<Order> pb = sysService.get_order_list(pageNum, stime, etime, merchants_id, state, user_id);
			List<Order> list = pb.getList();
			// List<Merchants> merchants=sysService.get_merchants();
			// 得到酒店
			List<Hotel> hotels = sysService.get_hotel_list();
			JSONArray jsonArray = JSONArray.fromObject(list);
			request.setAttribute("json", jsonArray);
			request.setAttribute("pb", pb);
			request.setAttribute("list", list);
			request.setAttribute("hotels", hotels);
			request.setAttribute("stime", stime);
			request.setAttribute("etime", etime);
			request.setAttribute("state", state);
			request.setAttribute("merchants_id", merchants_id);
			if (state == 2) {
				return "pay_order";
			} else if (state == 3) {
				return "in_order";
			} else if (state == 4) {
				return "out_order";
			} else if (state == 5) {
				return "reserve_order";
			}
		}

		return "login";
	}

	/**
	 * 商户列表
	 * 
	 * @return
	 */
	public String get_merchants_list() {
		manager = (Manager) session.getAttribute("manager");
		if (manager != null) {
			int pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > pageNum) {
					pageNum = Integer.parseInt(pn);
				}
			}
			PageBean<Merchants> pb = sysService.get_merchants_list(pageNum, stime, etime, m_state);
			List<Merchants> list = pb.getList();
			request.setAttribute("pb", pb);
			request.setAttribute("list", list);
			request.setAttribute("stime", stime);
			request.setAttribute("etime", etime);
			request.setAttribute("m_state", m_state);
			return "merchants";
		}
		return "login";
	}

	/**
	 * 修改商户状态
	 */
	public void edit_merchants() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("") && m_state != null && !m_state.equals("")) {
					int i = sysService.edit_merchants(Integer.parseInt(id), Integer.parseInt(m_state));
					if (i == 1) {
						out.print("success");
					} else {
						out.print("error");
					}
				} else {
					out.print("error");
				}
			} else {
				out.print("login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 用户列表
	 * 
	 * @return
	 */
	public String get_user_list() {
		manager = (Manager) session.getAttribute("manager");
		if (manager != null) {
			int pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > pageNum) {
					pageNum = Integer.parseInt(pn);
				}
			}
			PageBean<User> pb = sysService.get_user_list(pageNum, stime, etime);
			List<User> list = pb.getList();
			request.setAttribute("pb", pb);
			request.setAttribute("list", list);
			request.setAttribute("stime", stime);
			request.setAttribute("etime", etime);
			return "user";
		}
		return "login";
	}

	/**
	 * 房间管理
	 * 
	 * @return
	 */
	public String queryList() {
		manager = (Manager) session.getAttribute("manager");
		if (manager != null) {
			int pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > pageNum) {
					pageNum = Integer.parseInt(pn);
				}
			}
			int id = 0;
			if (merchants_id != null && !merchants_id.equals("") && !merchants_id.equals("-99")) {
				id = Integer.parseInt(merchants_id);
			}
			PageBean<House> pb = houseService.queryHouse1(id, pageNum, stime, etime);
			List<House> list = pb.getList();
			List<Merchants> merchants = sysService.get_merchants();
			request.setAttribute("pb", pb);
			request.setAttribute("list", list);
			request.setAttribute("stime", stime);
			request.setAttribute("etime", etime);
			request.setAttribute("merchants", merchants);
			request.setAttribute("merchants_id", merchants_id);
			return "houselist";
		}
		return "login";
	}

	public void add_lock() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			int parent_id1 = 0;
			if (manager != null) {
				int i = sysService.add_tag(lock_name, is_idcard, is_pwd);
				if (i == 1) {
					out.print("success");
				} else {
					out.print("error");
				}
			} else {
				out.print("login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 锁类型管理
	 * 
	 * @return
	 */
	public String get_lock_list() {
		manager = (Manager) session.getAttribute("manager");
		if (manager != null) {
			int pageNum = 1;
			if (pn != null && !pn.equals("")) {
				if (Integer.parseInt(pn) > pageNum) {
					pageNum = Integer.parseInt(pn);
				}
			}
			PageBean<Lock> pb = sysService.lock_list(pageNum);
			List<Lock> list = pb.getList();
			request.setAttribute("pb", pb);
			request.setAttribute("list", list);
			return "lock";
		}
		return "login";
	}

	/**
	 * 删除锁类型
	 */
	public void del_lock() {
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("")) {
					int i = sysService.del_lock(Integer.parseInt(id));
					if (i == 1) {
						out.print("success");
					} else {
						out.print("error");
					}
				}
			} else {
				out.print("login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 得到锁信息
	 */
	public void get_lock() {
		PrintWriter out = null;
		try {
			response.setContentType("text/html; charset=utf-8");
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			Lock lock = new Lock();
			if (manager != null) {
				if (id != null && !id.equals("")) {
					lock = sysService.get_lock(Integer.parseInt(id));
					if (lock != null) {
						lock.setData("success");
					}
				}
			} else {
				lock.setData("login");
			}
			JSONArray jsonArray = JSONArray.fromObject(lock);
			out.print(jsonArray);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	/**
	 * 更新锁
	 */
	public void edit_lock() {
		System.out.println("进入");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			manager = (Manager) session.getAttribute("manager");
			if (manager != null) {
				if (id != null && !id.equals("")) {
					int i = sysService.edit_lock(lock_name, is_idcard, is_pwd, state, Integer.parseInt(id));
					if (i == 1) {
						out.print("success");
					} else {
						out.print("error");
					}
				} else {
					out.print("error");
				}
			} else {
				out.print("login");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}

	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}

	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

	public String getSearch_id() {
		return search_id;
	}
	public void setSearch_id(String search_id) {
		this.search_id = search_id;
	}

	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getDomains_id() {
		return domains_id;
	}
	public void setDomains_id(String domains_id) {
		this.domains_id = domains_id;
	}

	public int getSup_type() {
		return sup_type;
	}
	public void setSup_type(int sup_type) {
		this.sup_type = sup_type;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}
	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File[] getFile() {
		return file;
	}
	public void setFile(File[] file) {
		this.file = file;
	}

	public String getSup_name1() {
		return sup_name1;
	}
	public void setSup_name1(String sup_name1) {
		this.sup_name1 = sup_name1;
	}

	public String getSup_type1() {
		return sup_type1;
	}
	public void setSup_type1(String sup_type1) {
		this.sup_type1 = sup_type1;
	}

	public String getSup_img2() {
		return sup_img2;
	}
	public void setSup_img2(String sup_img2) {
		this.sup_img2 = sup_img2;
	}

	public String getSup_name2() {
		return sup_name2;
	}
	public void setSup_name2(String sup_name2) {
		this.sup_name2 = sup_name2;
	}

	public String getSel() {
		return sel;
	}
	public void setSel(String sel) {
		this.sel = sel;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}

	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getMerchants_id() {
		return merchants_id;
	}
	public void setMerchants_id(String merchants_id) {
		this.merchants_id = merchants_id;
	}

	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getM_state() {
		return m_state;
	}
	public void setM_state(String m_state) {
		this.m_state = m_state;
	}

	public String getIs_idcard() {
		return is_idcard;
	}
	public void setIs_idcard(String is_idcard) {
		this.is_idcard = is_idcard;
	}

	public String getIs_pwd() {
		return is_pwd;
	}
	public void setIs_pwd(String is_pwd) {
		this.is_pwd = is_pwd;
	}

	public String getLock_name() {
		return lock_name;
	}
	public void setLock_name(String lock_name) {
		this.lock_name = lock_name;
	}

}