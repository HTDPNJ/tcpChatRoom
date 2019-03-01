package Demo02;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable
{
    /*发送数据线程*/
    //控制台输入流
    private BufferedReader console;
    //管道输出流
    private DataOutputStream dos;
    //线程标识
    private boolean isRunning=true;
    public Send(){
        console=new BufferedReader(new InputStreamReader(System.in));
    }
    public Send(Socket client){
        this();
        try {
            dos=new DataOutputStream(client.getOutputStream());
        }
        catch (IOException e) {
            isRunning=false;
            CloseUtil.closeAll(dos,console);
            e.printStackTrace();
        }
    }
    //1、从控制台接收数据
    private String getMsgFromConsile(){
        try {
            return console.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    /*1、从控制台接收数据
    * 2、发送数据*/
    public void send(){
        String msg=getMsgFromConsile();
        if(msg!=null&&!msg.equals("")){
            try {
                dos.writeUTF(msg);
                dos.flush();
            }
            catch (IOException e) {
                isRunning=false;
                CloseUtil.closeAll(dos,console);
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run()
    {
        while (isRunning){
            send();
        }
    }
}
