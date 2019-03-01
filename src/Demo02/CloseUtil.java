package Demo02;

import java.io.Closeable;
import java.io.IOException;

public class CloseUtil
{/*关闭流的方法*/

    public static void closeAll(Closeable... io)
    {
        for(Closeable temp:io){
            if(null!=temp){
                try {
                    temp.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
