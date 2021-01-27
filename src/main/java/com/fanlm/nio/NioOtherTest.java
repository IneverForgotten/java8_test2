package com.fanlm.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @program: java8_test
 * @description: nio others
 * @author: flm
 * @create: 2021-01-25 11:26
 **/
public class NioOtherTest {
    //Scatter/Gatter

    /**
     * 聚集（gather）写入Channel是指在写操作时将多个buffer的数据写入同一个Channel，因此，Channel 将多个Buffer中的数据“聚集（gather）”后发送到Channel。
     */
    public static void gather()
    {
        ByteBuffer header = ByteBuffer.allocate(10);
        ByteBuffer body = ByteBuffer.allocate(10);
        byte [] b1 = {'0', '1'};
        byte [] b2 = {'2', '3'};
        header.put(b1);
        body.put(b2);
        ByteBuffer [] buffs = {header, body};
        try
        {
            FileOutputStream os = new FileOutputStream("src/scattingAndGather.txt");
            FileChannel channel = os.getChannel();
            channel.write(buffs);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        methodpip();
    }

//Java NIO 管道是2个线程之间的单向数据连接。Pipe有一个source通道和一个sink通道。数据会被写到sink通道，从source通道读取。
    public static void methodpip(){
        Pipe pipe = null;
        ExecutorService exec = Executors.newFixedThreadPool(2);
        try{
            pipe = Pipe.open();
            final Pipe pipeTemp = pipe;
            exec.submit(new Callable<Object>(){
                @Override
                public Object call() throws Exception
                {
                    Pipe.SinkChannel sinkChannel = pipeTemp.sink();//向通道中写数据
                    while(true){
                        TimeUnit.SECONDS.sleep(1);
                        String newData = "Pipe Test At Time "+System.currentTimeMillis();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        buf.clear();
                        buf.put(newData.getBytes());
                        buf.flip();
                        while(buf.hasRemaining()){
                            System.out.println(buf);
                            sinkChannel.write(buf);
                        }
                    }
                }
            });
            exec.submit(new Callable<Object>(){
                @Override
                public Object call() throws Exception
                {
                    Pipe.SourceChannel sourceChannel = pipeTemp.source();//向通道中读数据
                    while(true){
                        TimeUnit.SECONDS.sleep(1);
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        buf.clear();
                        int bytesRead = sourceChannel.read(buf);
                        System.out.println("bytesRead="+bytesRead);
                        while(bytesRead >0 ){
                            buf.flip();
                            byte b[] = new byte[bytesRead];
                            int i=0;
                            while(buf.hasRemaining()){
                                b[i]=buf.get();
                                System.out.printf("%X",b[i]);
                                i++;
                            }
                            String s = new String(b);
                            System.out.println("=================||"+s);
                            bytesRead = sourceChannel.read(buf);
                        }
                    }
                }
            });
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            exec.shutdown();
        }
    }





    //FileChannel的transferFrom()方法可以将数据从源通道传输到FileChannel中。
    public static void method1(){
        RandomAccessFile fromFile = null;
        RandomAccessFile toFile = null;
        try
        {
            fromFile = new RandomAccessFile("src/fromFile.xml","rw");
            FileChannel fromChannel = fromFile.getChannel();
            toFile = new RandomAccessFile("src/toFile.txt","rw");
            FileChannel toChannel = toFile.getChannel();
            long position = 0;
            long count = fromChannel.size();
            System.out.println(count);
            toChannel.transferFrom(fromChannel, position, count);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally{
            try{
                if(fromFile != null){
                    fromFile.close();
                }
                if(toFile != null){
                    toFile.close();
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }



    //Java NIO中的DatagramChannel是一个能收发UDP包的通道。因为UDP是无连接的网络协议，所以不能像其它通道那样读取和写入。它发送和接收的是数据包。
    public static void  reveive(){
        DatagramChannel channel = null;
        try{
            channel = DatagramChannel.open();
            channel.socket().bind(new InetSocketAddress(8888));
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.clear();
            channel.receive(buf);
            buf.flip();
            while(buf.hasRemaining()){
                System.out.print((char)buf.get());
            }
            System.out.println();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(channel!=null){
                    channel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void send(){
        DatagramChannel channel = null;
        try{
            channel = DatagramChannel.open();
            String info = "I'm the Sender!";
            ByteBuffer buf = ByteBuffer.allocate(1024);
            buf.clear();
            buf.put(info.getBytes());
            buf.flip();
            int bytesSent = channel.send(buf, new InetSocketAddress("10.10.195.115",8888));
            System.out.println(bytesSent);
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(channel!=null){
                    channel.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }


}
