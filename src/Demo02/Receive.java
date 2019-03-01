package Demo02;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class Receive implements Runnable
{
    private DataInputStream dis;
    private boolean isRunning=true;
    public Receive(){
    }
    public Receive(Socket client){
        try{
        dis=new DataInputStream(client.getInputStream());
        }catch(IOException e){
            isRunning=false;
            CloseUtil.closeAll(dis);
            e.printStackTrace();
        }
    }
    public String receive(){
        String msg="";
        try {
            msg=dis.readUTF();
        }
        catch (IOException e) {
            isRunning=false;
            CloseUtil.closeAll(dis);
            e.printStackTrace();
        }
        return msg;
    }
    /*接收数据线程*/
    @Override
    public void run()
    {
        while (isRunning){
            System.out.println(receive());
        }
    }
}
