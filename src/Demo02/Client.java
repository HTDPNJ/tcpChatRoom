package Demo02;

import java.io.*;
import java.net.Socket;

//问题：输入流和输出在一个线程内先后执行
public class Client
{
    public static void main(String[] args)throws IOException
    {
        Socket client=new Socket("localhost",9999);
        new Thread(new Send(client)).start();
        new Thread(new Receive(client)).start();
    }
}
