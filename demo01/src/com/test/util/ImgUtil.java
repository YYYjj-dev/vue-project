package com.test.util;

import com.test.pojo.Additive;
import com.test.pojo.Cases;
import com.test.pojo.News;
import com.test.pojo.Shangpin;
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
    private static DiskFileItemFactory.Builder builder = new DiskFileItemFactory.Builder();
    private static DiskFileItemFactory diskFileItemFactory = builder.get();
    private static JakartaServletFileUpload servletFileUpload = new JakartaServletFileUpload(diskFileItemFactory);

    private static Shangpin shangpin = new Shangpin();
    private static Additive additive = new Additive();
    private static Cases cases = new Cases();
    private static News news = new News();

    private static Charset charset = Charset.forName("UTF-8");

    public static Additive updateAdditive(HttpServletRequest req) throws ServletException, IOException {
        try {
            List<FileItem> list = servletFileUpload.parseRequest(req);
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {
                    if ("typename".equals(fileItem.getFieldName())) {
                        additive.setTypename(fileItem.getString(charset));
                    } else if ("name".equals(fileItem.getFieldName())) {
                        additive.setName(fileItem.getString(charset));
                    } else if ("description".equals(fileItem.getFieldName())) {
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
                        if (!(fileItem.getString()).equals("")) {
                            additive.setId(Integer.parseInt(fileItem.getString()));
                        }
                    }
                } else {
                    String name = saveImage(fileItem, req);
                    additive.setImgpath(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return additive;
    }

    public static Cases updateCases(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //todo 做一个判断 furn为空就不处理
        try {
            List<FileItem> list = servletFileUpload.parseRequest(req);
            for (FileItem fileItem : list) {
                if (fileItem.isFormField()) {
                    if ("title".equals(fileItem.getFieldName())) {
                        cases.setTitle(fileItem.getString(charset));
                    } else if ("content".equals(fileItem.getFieldName())) {
                        cases.setContent(fileItem.getString(charset));
                    } else if ("grouptype".equals(fileItem.getFieldName())) {
                        cases.setGrouptype(fileItem.getString(charset));
                    } else if ("date".equals(fileItem.getFieldName())) {
                        cases.setDate(fileItem.getString(charset));
                    } else if ("id".equals(fileItem.getFieldName())) {
                        if (!(fileItem.getString()).equals("")) {
                            cases.setId(Integer.parseInt(fileItem.getString()));
                        }
                    }
                } else {
                    String name = saveImage(fileItem, req);
                    cases.setImgpath(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cases;
    }

    public static Shangpin updateShangpin(HttpServletRequest req) throws ServletException, IOException {
        //todo 做一个判断 furn为空就不处理
            JakartaServletFileUpload servletFileUpload =
                    new JakartaServletFileUpload(diskFileItemFactory);
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        if ("StoreId".equals(fileItem.getFieldName())) {
                            shangpin.setStoreId(Integer.parseInt(fileItem.getString()));
                        } else if ("group".equals(fileItem.getFieldName())) {
                            shangpin.setGruop(fileItem.getString(charset));
                        } else if ("type".equals(fileItem.getFieldName())) {
                            shangpin.setType(fileItem.getString(charset));
                        } else if ("description".equals(fileItem.getFieldName())) {
                            shangpin.setDescription(fileItem.getString(charset));
                        } else if ("standard".equals(fileItem.getFieldName())) {
                            shangpin.setStandard(fileItem.getString(charset));
                        } else if ("date".equals(fileItem.getFieldName())) {
                            shangpin.setNum(Integer.parseInt(fileItem.getString()));
                        } else if ("price".equals(fileItem.getFieldName())) {
                            shangpin.setPrice(Double.parseDouble(fileItem.getString(charset)));
                        } else if ("id".equals(fileItem.getFieldName())) {
                            if (!(fileItem.getString()).equals("")) {
                                shangpin.setId(Integer.parseInt(fileItem.getString()));
                            }
                        }
                    } else {
                        String name = saveImage(fileItem, req);
                        shangpin.setImgpath(name);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        return shangpin;
    }

    public static News updateNews(HttpServletRequest req) throws ServletException, IOException {
            JakartaServletFileUpload servletFileUpload =
                    new JakartaServletFileUpload(diskFileItemFactory);
            try {
                List<FileItem> list = servletFileUpload.parseRequest(req);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        if ("title".equals(fileItem.getFieldName())) {
                            news.setTitle(fileItem.getString(charset));
                        } else if ("content".equals(fileItem.getFieldName())) {
                            news.setContent(fileItem.getString(charset));
                        } else if ("type".equals(fileItem.getFieldName())) {
                            news.setType(fileItem.getString(charset));
                        } else if ("date".equals(fileItem.getFieldName())) {
                            news.setDate(fileItem.getString(charset));
                        } else if ("id".equals(fileItem.getFieldName())) {
                            if (!(fileItem.getString()).equals("")) {
                                news.setId(Integer.parseInt(fileItem.getString()));
                            }
                        }
                    } else {
                        String name = saveImage(fileItem, req);
                        news.setImg(name);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return news;
    }

    private static String saveImage(FileItem fileItem, HttpServletRequest req) throws Exception {
        String name = fileItem.getName();
        if (!"".equals(name)) {
            String filePath = "/" + "image";
            String fileRealPath =
                    req.getServletContext().getRealPath(filePath);
            File fileRealPathDirectory = new File(fileRealPath);
            if (!fileRealPathDirectory.exists()) {
                fileRealPathDirectory.mkdirs();
            }
            name = UUID.randomUUID().toString() + "_" + System.currentTimeMillis() + "_" + name;
            String fileFullPath = fileRealPathDirectory + "/" + name;
            Path path = Paths.get(fileFullPath);
            fileItem.write(path);
            fileItem.getOutputStream().close();
            //todo 删除原来旧的不用的图片
        }
        return name;
    }
}


