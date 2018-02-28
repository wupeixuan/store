package com.wpx.web.servlet;

import com.wpx.domain.Category;
import com.wpx.domain.Product;
import com.wpx.service.CategoryService;
import com.wpx.service.ProductService;
import com.wpx.utils.BeanFactory;
import com.wpx.utils.UUIDUtils;
import com.wpx.utils.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 后台商品管理
 */
public class AdminProductServlet extends BaseServlet {
    /**
     * 查询所有商品
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.调用service 查询所有 返回一个list
        ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
        List<Product> list = ps.findAll();
        //2.将list放入request域中 请求转发
        request.setAttribute("list", list);
        return "/admin/product/list.jsp";
    }

    /**
     * 跳转到添加商品页面
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //查询所有的分类 返回list
        CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
        List<Category> clist = cs.findAll();

        //将list放入request
        request.setAttribute("clist", clist);
        return "/admin/product/add.jsp";
    }

    /**
     * 商品添加
     *
     * @param request
     * @param response
     * @throws Exception
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //0.创建map 放入前台传递的数据
            HashMap<String, Object> map = new HashMap<>();
            //创建磁盘文件项
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建核心上传对象
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解析request
            List<FileItem> list = upload.parseRequest(request);
            //遍历集合
            for (FileItem fi : list) {
                //判断是否是普通的上传组件
                if (fi.isFormField()) {
                    //普通上传组件
                    map.put(fi.getFieldName(), fi.getString("utf-8"));
                } else {
                    //文件上传组件
                    //获取文件名称
                    String name = fi.getName();

                    //获取文件的真实名称    xxxx.xx
                    String realName = UploadUtils.getRealName(name);
                    //获取文件的随机名称
                    String uuidName = UploadUtils.getUUIDName(realName);

                    //获取文件的存放路径
                    String path = this.getServletContext().getRealPath("/products/1");

                    //获取文件流
                    InputStream is = fi.getInputStream();
                    //保存图片
                    FileOutputStream os = new FileOutputStream(new File(path, uuidName));

                    IOUtils.copy(is, os);
                    os.close();
                    is.close();

                    //删除临时文件
                    fi.delete();

                    //在map中设置文件的路径
                    map.put(fi.getFieldName(), "products/1/" + uuidName);

                }

            }
            //1.封装参数
            Product p = new Product();
            BeanUtils.populate(p, map);

            //1.1 商品id
            p.setPid(UUIDUtils.getId());

            //1.2 商品时间
            p.setPdate(new Date());

            //1.3 封装cateogry
            Category c = new Category();
            c.setCid((String) map.get("cid"));

            p.setCategory(c);

            //2.调用service完成添加
            ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
            ps.add(p);

            //3.页面重定向
            response.sendRedirect(request.getContextPath() + "/adminProduct?method=findAll");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "商品添加失败~");
            request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
            return;
        }
    }

    /**
     * 通过pid获取商品
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //查询所有的分类 返回list
        CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
        List<Category> clist = cs.findAll();
        //将list放入request
        request.setAttribute("clist", clist);
        //1.接受pid
        String pid = request.getParameter("pid");
        //2 调用service完成 查询操作 返回值:product
        ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
        Product product = ps.getById(pid);
        //3.将product放入request域中, 请求转发  /admin/product/edit.jsp
        request.setAttribute("bean", product);
        return "/admin/product/edit.jsp";
    }

    /**
     * 更新商品信息
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //0.创建map 放入前台传递的数据
            HashMap<String, Object> map = new HashMap<>();
            //创建磁盘文件项
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //创建核心上传对象
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解析request
            List<FileItem> list = upload.parseRequest(request);
            //遍历集合
            for (FileItem fi : list) {
                //判断是否是普通的上传组件
                if (fi.isFormField()) {
                    //普通上传组件
                    map.put(fi.getFieldName(), fi.getString("utf-8"));
                } else {
                    //文件上传组件
                    //获取文件名称
                    String name = fi.getName();

                    //获取文件的真实名称    xxxx.xx
                    String realName = UploadUtils.getRealName(name);
                    //获取文件的随机名称
                    String uuidName = UploadUtils.getUUIDName(realName);

                    //获取文件的存放路径
                    String path = this.getServletContext().getRealPath("/products/1");

                    //获取文件流
                    InputStream is = fi.getInputStream();
                    //保存图片
                    FileOutputStream os = new FileOutputStream(new File(path, uuidName));

                    IOUtils.copy(is, os);
                    os.close();
                    is.close();

                    //删除临时文件
                    fi.delete();

                    //在map中设置文件的路径
                    map.put(fi.getFieldName(), "products/1/" + uuidName);

                }

            }
            //1.封装参数
            Product p = new Product();
            BeanUtils.populate(p, map);

            //1.3 封装cateogry
            Category c = new Category();
            c.setCid((String) map.get("cid"));

            p.setCategory(c);

            //2.调用service完成添加
            ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
            ps.update(p);

            //3.页面重定向
            response.sendRedirect(request.getContextPath() + "/adminProduct?method=findAll");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "商品修改失败失败~");
            request.getRequestDispatcher("/jsp/msg.jsp").forward(request, response);
            return;
        }
    }

    /**
     * 删除商品
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //1.获取pid
        String pid = request.getParameter("pid");
        //3.调用service 完成删除操作
        ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
        ps.delete(pid);
        //4.重定向 查询所有
        response.sendRedirect(request.getContextPath() + "/adminProduct?method=findAll");
        return null;
    }
}
