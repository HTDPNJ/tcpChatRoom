package Demo04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

//问题：输入流和输出在一个线程内先后执行
public class Client
{
    public static void main(String[] args)throws IOException
    {
        System.out.println("请输入名称：");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String name=br.readLine();
        if(name.equals("")){
            return;
        }
        Socket client=new Socket("localhost",7654);
        new Thread(new Send(client,name)).start();
        new Thread(new Receive(client)).start();
    }
}
