package Demo02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{/*
    客户端可以发送数据+接受数据 而且发送接受数据是相互独立的
    每一个客户端创建一个线程*/
    public static void main(String[] args)throws IOException
    {
        //1、创建服务器
        ServerSocket server=new ServerSocket(9999);
        Socket client=server.accept();
        //输入流
        DataInputStream dis=new DataInputStream(client.getInputStream());
        DataOutputStream dos=new DataOutputStream(client.getOutputStream());
        while (true){
            String msg=dis.readUTF();
            System.out.println(msg);
            dos.writeUTF("服务器-->>"+msg);
            dos.flush();
        }
    }
}
