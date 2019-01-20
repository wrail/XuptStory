package Javacode;
import org.json.JSONObject;
public class JsonCode {
    public static String JsonMess(String mess, String tip){
        JSONObject jo = new JSONObject();
        jo.put("mess", mess);//数字代表相应的状态
        jo.put("tip", tip);//success 或者 fail
        //jo.put("");
       // response.getWriter().print(jo.toString());
        return jo.toString();
    }
}
