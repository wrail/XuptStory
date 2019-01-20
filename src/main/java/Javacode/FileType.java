package Javacode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.io.InputStream;
//利用文件头来判断是那种文件类型
public class FileType {
    FileType() {

    }

  /*  public static String ToHexString(Byte[] str) {
        String Type = null;
        StringBuilder sb = new StringBuilder();
        if (str == null || str.length <= 0) {
            return null;
        }
        for (int i = 0; i < str.length; i++) {
            //int v=str[i]&0Xff;
            String v1 = Integer.toHexString(str[i] & 0xff);//转为16进制
            if (v1.length() < 2) {
                sb.append(0);
            }
            sb.append(v1);
        }
        Type = sb.toString();
        return Type;
    }

    private static String getContent(InputStream is) throws IOException {//获取文件头
        byte[] b ;
        b= new byte[4];
        InputStream inputStream = null;
        try {
            is.read(b, 0,b.length);//等价于read（）
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return ToHexString(b);
    }
*/
}




