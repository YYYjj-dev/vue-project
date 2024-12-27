package com.test.util;

import com.test.pojo.Additive;
import com.test.pojo.Cases;
import com.test.pojo.Shangpin;
import com.test.service.AdditiveService;
import com.test.service.InfoService;
import com.test.service.impl.AdditiveServiceImpl;
import com.test.service.impl.InfoServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


@SuppressWarnings("all")
public class ImgUtil {


    public static Additive updateAdditive(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如果你的表单是enctype="multipart/form-data", req.getParameter("id") 得不到id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到对应furn对象[从db中获取]
        DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
        DiskFileItemFactory diskFileItemFactory = builder.get();
        AdditiveService additiveService = new AdditiveServiceImpl();

        Additive additive = additiveService.findAdditiveById(id);
        if(additive == null)
        {
            additive = new Additive();
        }
        //todo 做一个判断 furn为空就不处理
        //1. 判断是不是文件表单(enctype="multipart/form-data")
        if (JakartaServletFileUpload.isMultipartContent(req)) {
            //2. 创建 DiskFileItemFactory 对象, 用于构建一个解析上传数据的工具对象
//            DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
//            DiskFileItemFactory diskFileItemFactory = builder.get();
            //3. 创建一个解析上传数据的工具对象
            JakartaServletFileUpload servletFileUpload =
                    new JakartaServletFileUpload(diskFileItemFactory);
            //解决接收到文件名是中文乱码问题
            // JakartaServletFileUpload.setHeaderEncoding("utf-8");

            //4. 关键的地方, servletFileUpload 对象可以把表单提交的数据text / 文件
            //   将其封装到 FileItem 文件项中
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                //遍历，并分别处理=> 自然思路
                for (FileItem fileItem : list) {
                    //判断是不是一个文件=> 你是OOP程序员
                    if (fileItem.isFormField()) {//如果是true就是文本 input text(普通的表单字段)
                        Charset charset = Charset.forName("UTF-8");
                        if ("typename".equals(fileItem.getFieldName())) {//家居名
                            additive.setTypename(fileItem.getString(charset));
                        } else if ("name".equals(fileItem.getFieldName())) {//制造商
                            additive.setName(fileItem.getString(charset));
                        } else if ("description".equals(fileItem.getFieldName())) {//价格
                            //additive.setDescription(new BigDecimal(fileItem.getString()));
                            additive.setDescription(fileItem.getString(charset));
                        } else if ("usestandardInternal".equals(fileItem.getFieldName())) {
                            additive.setUsestandardInternal(fileItem.getString(charset));
                        } else if ("usestandardInternational".equals(fileItem.getFieldName())) {
                            additive.setUsestandardInternational(fileItem.getString(charset));
                        } else if ("analysis".equals(fileItem.getFieldName())) {
                            additive.setAnalysis(fileItem.getString(charset));
                        } else if ("nature".equals(fileItem.getFieldName())) {
                            additive.setNature(fileItem.getString(charset));
                        } else if ("id".equals(fileItem.getFieldName())) {
                           if(!(fileItem.getString()).equals("")){
                               additive.setId(Integer.parseInt(fileItem.getString()));
                           }
                        }
                    }
                    else {//是一个文件

                        //获取上传的文件的名字
                        String name = fileItem.getName();

                        //如果用户没有选择新的图片, name = ""
                        if (!"".equals(name)) {
                            //1.指定一个目录 , 就是我们网站工作目录下
                            String filePath = "/" + "image";
                            //2. 获取到完整目录 [io/servlet基础]
                            String fileRealPath =
                                    req.getServletContext().getRealPath(filePath);

                            //3. 创建这个上传的目录=> 创建目录?=> Java基础
                            File fileRealPathDirectory = new File(fileRealPath);
                            if (!fileRealPathDirectory.exists()) {//不存在，就创建
                                fileRealPathDirectory.mkdirs();//创建
                            }

                            //4. 将文件拷贝到fileRealPathDirectory目录
                            //   构建一个上传文件的完整路径 ：目录+文件名
                            //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
                            name = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + name;
                            String fileFullPath = fileRealPathDirectory + "/" + name;
                            Path path = Paths.get(fileFullPath);
                            System.out.println(fileFullPath);
                            fileItem.write(path); //保存


                            additive.setImgpath(name);

                            fileItem.getOutputStream().close();//关闭流
                            //更新家居的图片路径
                            //furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + name);
                            //todo 删除原来旧的不用的图片
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单...");
        }

        return additive;
    }

    public static Cases updateCases(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如果你的表单是enctype="multipart/form-data", req.getParameter("id") 得不到id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到对应furn对象[从db中获取]
        DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
        DiskFileItemFactory diskFileItemFactory = builder.get();
        InfoService infoService= new InfoServiceImpl();

        Cases cases = infoService.findCasesById(id);
        if(cases == null)
        {
            cases = new Cases();
        }
        //todo 做一个判断 furn为空就不处理
        //1. 判断是不是文件表单(enctype="multipart/form-data")
        if (JakartaServletFileUpload.isMultipartContent(req)) {
            //2. 创建 DiskFileItemFactory 对象, 用于构建一个解析上传数据的工具对象
//            DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
//            DiskFileItemFactory diskFileItemFactory = builder.get();
            //3. 创建一个解析上传数据的工具对象
            JakartaServletFileUpload servletFileUpload =
                    new JakartaServletFileUpload(diskFileItemFactory);
            //解决接收到文件名是中文乱码问题
            // JakartaServletFileUpload.setHeaderEncoding("utf-8");

            //4. 关键的地方, servletFileUpload 对象可以把表单提交的数据text / 文件
            //   将其封装到 FileItem 文件项中
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                //遍历，并分别处理=> 自然思路
                for (FileItem fileItem : list) {
                    //判断是不是一个文件=> 你是OOP程序员
                    if (fileItem.isFormField()) {//如果是true就是文本 input text(普通的表单字段)
                        Charset charset = Charset.forName("UTF-8");
                        if ("title".equals(fileItem.getFieldName())) {
                            cases.setTitle(fileItem.getString(charset));
                        } else if ("content".equals(fileItem.getFieldName())) {
                            cases.setContent(fileItem.getString(charset));
                        } else if ("grouptype".equals(fileItem.getFieldName())) {
                            cases.setGrouptype(fileItem.getString(charset));
                        } else if ("date".equals(fileItem.getFieldName())) {
                            cases.setDate(fileItem.getString(charset));
                        } else if ("id".equals(fileItem.getFieldName())) {
                            if(!(fileItem.getString()).equals("")){
                                cases.setId(Integer.parseInt(fileItem.getString()));
                            }
                        }
                    }
                    else {//是一个文件

                        //获取上传的文件的名字
                        String name = fileItem.getName();

                        //如果用户没有选择新的图片, name = ""
                        if (!"".equals(name)) {
                            //1.指定一个目录 , 就是我们网站工作目录下
                            String filePath = "/" + "image";
                            //2. 获取到完整目录 [io/servlet基础]
                            String fileRealPath =
                                    req.getServletContext().getRealPath(filePath);

                            //3. 创建这个上传的目录=> 创建目录?=> Java基础
                            File fileRealPathDirectory = new File(fileRealPath);
                            if (!fileRealPathDirectory.exists()) {//不存在，就创建
                                fileRealPathDirectory.mkdirs();//创建
                            }

                            //4. 将文件拷贝到fileRealPathDirectory目录
                            //   构建一个上传文件的完整路径 ：目录+文件名
                            //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
                            name = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + name;
                            String fileFullPath = fileRealPathDirectory + "/" + name;
                            Path path = Paths.get(fileFullPath);
                            System.out.println(fileFullPath);
                            fileItem.write(path); //保存


                            cases.setImgpath(name);

                            fileItem.getOutputStream().close();//关闭流
                            //更新家居的图片路径
                            //furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + name);
                            //todo 删除原来旧的不用的图片
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单...");
        }
        return cases;
    }

    public static Shangpin updateShangpin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如果你的表单是enctype="multipart/form-data", req.getParameter("id") 得不到id
        int id = DataUtils.parseInt(req.getParameter("id"), 0);
        //获取到对应furn对象[从db中获取]
        DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
        DiskFileItemFactory diskFileItemFactory = builder.get();
        InfoService infoService= new InfoServiceImpl();

        Shangpin shangpin = new Shangpin();
        //todo 做一个判断 furn为空就不处理
        //1. 判断是不是文件表单(enctype="multipart/form-data")
        if (JakartaServletFileUpload.isMultipartContent(req)) {
            //2. 创建 DiskFileItemFactory 对象, 用于构建一个解析上传数据的工具对象
//            DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
//            DiskFileItemFactory diskFileItemFactory = builder.get();
            //3. 创建一个解析上传数据的工具对象
            JakartaServletFileUpload servletFileUpload =
                    new JakartaServletFileUpload(diskFileItemFactory);
            //解决接收到文件名是中文乱码问题
            // JakartaServletFileUpload.setHeaderEncoding("utf-8");

            //4. 关键的地方, servletFileUpload 对象可以把表单提交的数据text / 文件
            //   将其封装到 FileItem 文件项中
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                //遍历，并分别处理=> 自然思路
                for (FileItem fileItem : list) {
                    //判断是不是一个文件=> 你是OOP程序员
                    if (fileItem.isFormField()) {//如果是true就是文本 input text(普通的表单字段)
                        Charset charset = Charset.forName("UTF-8");
                        if ("StoreId".equals(fileItem.getFieldName())) {
                            shangpin.setStoreId(Integer.parseInt(fileItem.getString()));
                        } else if ("group".equals(fileItem.getFieldName())) {
                            shangpin.setGruop(fileItem.getString(charset));
                        } else if ("type".equals(fileItem.getFieldName())) {
                            shangpin.setType(fileItem.getString(charset));
                        } else if ("description".equals(fileItem.getFieldName())) {
                            shangpin.setDescription(fileItem.getString(charset));
                        }  else if ("standard".equals(fileItem.getFieldName())) {
                            shangpin.setStandard(fileItem.getString(charset));
                        }  else if ("date".equals(fileItem.getFieldName())) {
                            shangpin.setNum(Integer.parseInt(fileItem.getString()));
                        }  else if ("price".equals(fileItem.getFieldName())) {
                            shangpin.setPrice(Double.parseDouble(fileItem.getString(charset)));
                        } else if ("id".equals(fileItem.getFieldName())) {
                            if(!(fileItem.getString()).equals("")){
                                shangpin.setId(Integer.parseInt(fileItem.getString()));
                            }
                        }
                    }
                    else {//是一个文件

                        //获取上传的文件的名字
                        String name = fileItem.getName();

                        //如果用户没有选择新的图片, name = ""
                        if (!"".equals(name)) {
                            //1.指定一个目录 , 就是我们网站工作目录下
                            String filePath = "/" + "image";
                            //2. 获取到完整目录 [io/servlet基础]
                            String fileRealPath =
                                    req.getServletContext().getRealPath(filePath);

                            //3. 创建这个上传的目录=> 创建目录?=> Java基础
                            File fileRealPathDirectory = new File(fileRealPath);
                            if (!fileRealPathDirectory.exists()) {//不存在，就创建
                                fileRealPathDirectory.mkdirs();//创建
                            }

                            //4. 将文件拷贝到fileRealPathDirectory目录
                            //   构建一个上传文件的完整路径 ：目录+文件名
                            //   对上传的文件名进行处理, 前面增加一个前缀，保证是唯一即可, 不错
                            name = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + name;
                            String fileFullPath = fileRealPathDirectory + "/" + name;
                            Path path = Paths.get(fileFullPath);
                            System.out.println(fileFullPath);
                            fileItem.write(path); //保存


                            shangpin.setImg(name);

                            fileItem.getOutputStream().close();//关闭流
                            //更新家居的图片路径
                            //furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + name);
                            //todo 删除原来旧的不用的图片
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是文件表单...");
        }
        return shangpin;
    }
}


