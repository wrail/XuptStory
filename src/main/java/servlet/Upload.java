package servlet;
import Javacode.JsonCode;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import java.io.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(name="Upload",urlPatterns = "/Upload")
public class Upload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       PrintWriter p;


        //将上传的文件存放于WEB-INF目录下
        String realPath = this.getServletContext().getRealPath("/upload");
        System.out.println(realPath);

        //设置临时目录。上传文件大于缓冲区则先放于临时目录中
        String tempPath = "E:\\tempPath";
        System.out.println("临时文件存放位置:" + tempPath);
        //判断存放上传文件的目录是否存在（不存在则创建）
        File f = new File(realPath);
        if (!f.exists() && !f.isDirectory()) {
            System.out.println("目录或文件不存在! 创建目标目录。");
            f.mkdir();
        }
        //判断临时目录是否存在（不存在则创建）
        File f1 = new File(tempPath);
        if (!f1.isDirectory()) {
            System.out.println("临时文件目录不存在! 创建临时文件目录");
            f1.mkdir();
        }
        //创建一个DiskFileItemFactory工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();

        //设置上传文件的临时目录
        factory.setRepository(f1);

        //创建一个文件上传解析器。
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解决上传文件名的中文乱码
        upload.setHeaderEncoding("UTF-8");
        //判断提交上来的数据是否是上传表单的数据
        if (!ServletFileUpload.isMultipartContent(request)) {
            System.out.println("不是上传文件，终止");
            return;
        }
        //使用ServletFileUpload解析器解析上传数据,解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
        try {
            List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));
            for (FileItem item : items) {
                //如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String filedName = item.getFieldName();//普通输入项数据的名
                    //解决普通输入项的数据的中文乱码
                    String filedValue = item.getString("UTF-8");//普通输入项的值
                    System.out.println("普通字段:" + filedName + "==" + filedValue);
                } else {
                    String fileName = item.getName();//上传文件的名
                    //多个文件上传输入框有空的异常处理
                    if (fileName == null || "".equals(fileName.trim())) {  //去空格是否为空
                        continue;// 为空，跳过当次循环，  第一个没输入则跳过可以继续输入第二个
                    }
                    //处理上传文件的文件名的路径，截取字符串只保留文件名部分。//截取留最后一个"\"之后，+1截取向右移一位（"\a.txt"-->"a.txt"）
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                    //存放路径+上传的文件名
                    String filePath = realPath + "\\" + fileName;
                    //构建输入输出流
                    InputStream in = item.getInputStream(); //获取item中的上传文件的输入流
                    OutputStream out = new FileOutputStream(filePath); //创建一个文件输出流
                    //创建一个缓冲区
                    byte b[] = new byte[1024];
                    //输入流中的数据是否已经读完的标识
                    int len = -1;
                    //循环将输入流读入到缓冲区当中
                    while ((len = in.read(b)) != -1) {  //没数据了返回-1
                        out.write(b, 0, len);
                    }
                    //关闭流
                    out.close();
                    in.close();
                    //删除临时文件
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    item.delete();//删除处理文件上传时生成的临时文件
                    System.out.println("文件上传成功");
                    //用json返回给前端我的存放地址？？？
                    String s=JsonCode.JsonMess(realPath,"success");//success代表上传成功,给传入路径
                    p=response.getWriter();
                    p.print(s);
                    p.close();
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
            String s=JsonCode.JsonMess("0","fail");//0代表上传异常
            p=response.getWriter();
            p.print(s);
            p.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
