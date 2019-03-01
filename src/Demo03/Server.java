package Demo03;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server
{/*
    客户端可以发送数据+接受数据 而且发送接受数据是相互独立的
    每一个客户端创建一个线程*/
    private List<MyChannel> all=new ArrayList<MyChannel>();
    public static void main(String[] args)throws IOException
    {
        new Server().start();
    }
    public void start()throws  IOException{
        //1、创建服务器
        ServerSocket server=new ServerSocket(7654);
        while(true){
            Socket client=server.accept();
            MyChannel channel=new MyChannel(client);
            all.add(channel);
            new Thread(channel).start();
        }
    }
    private class MyChannel implements Runnable{
        private DataInputStream dis;
        private DataOutputStream dos;
        private boolean isRunning=true;
        public MyChannel(Socket client){
            try{
            dis=new DataInputStream(client.getInputStream());
            dos=new DataOutputStream(client.getOutputStream());
            }catch(IOException e){
                e.printStackTrace();
                CloseUtil.closeAll(dis,dos);
                isRunning=false;
            }
        }
        private String receive(){
            String msg="";
            try{
                msg=dis.readUTF();
            }catch (IOException e){
                e.printStackTrace();
                CloseUtil.closeAll(dis);
                isRunning=false;
                all.remove(this);
            }
            return msg;
        }
        private void send(String msg){
            if(null==msg||msg.equals("")){
                return;
            }
            try {
                dos.writeUTF(msg);
                dos.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
                CloseUtil.closeAll(dos);
                isRunning=false;
                all.remove(this);
            }
        }
        /*发送给其他客户端*/
        private void sendOther(){
            String msg=this.receive();
            for(MyChannel other:all){
                if(other==this){
                    continue;
                }
                other.send(msg);
            }
        }
        @Override
        public void run()
        {
            while (isRunning){
                sendOther();
            }
        }
    }

}
