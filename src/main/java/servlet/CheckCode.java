package servlet;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
@WebServlet( name="CheckCode",urlPatterns = "/CheckCode")
public class CheckCode extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
            BufferedImage bi = new BufferedImage(110, 45, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = (Graphics2D) bi.getGraphics();
        graphics.setColor(new Color(116, 159, 197));
           // graphics.setColor(new Color(100, 230, 200));
            graphics.fillRect(0, 0, 110, 45);
            StringBuffer sb=new StringBuffer();
            char[] codeChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456".toCharArray();
            Random random = new Random();
            graphics.setFont(new Font("TimesRoman",Font.BOLD,22));
            for(int i = 0; i < 4; i++) {
                int index = new Random().nextInt(codeChar.length);
                graphics.setColor(new Color(random.nextInt(150), random.nextInt(200), random.nextInt(255)));
                graphics.drawString(codeChar[index] + "", (i * 13) + 15, 20);
                sb.append(codeChar[index]);
            }
            graphics.dispose();
            request.getSession().setAttribute("code", sb.toString());
            OutputStream out = response.getOutputStream();
            ImageIO.write(bi, "jpeg", out);
            out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request,response);
    }
}
