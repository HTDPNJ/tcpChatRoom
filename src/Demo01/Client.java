package Demo01;

import java.io.*;
import java.net.Socket;

public class Client
{
    public static void main(String[] args)throws IOException
    {
        Socket client=new Socket("localhost",9999);
        //控制台输入
        BufferedReader console=new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream dos=new DataOutputStream(client.getOutputStream());
        String info=console.readLine();
        //输出流
        dos.writeUTF(info);
        dos.flush();
        //输入流
        DataInputStream dis=new DataInputStream(client.getInputStream());
        String msg=dis.readUTF();
        System.out.println(msg);
    }
}
