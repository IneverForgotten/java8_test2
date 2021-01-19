package com.fanlm.nio;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.tomcat.util.buf.ByteChunk;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @program: java8_test
 * @description: FileChannel demo
 * @author: flm
 * @create: 2021-01-13 15:19
 **/
public class MyFileChannel {

    public static void main(String[] args) throws IOException {
//        method1();
        selector();
    }


    public static void method1(){
        RandomAccessFile aFile = null;
        try{
            aFile = new RandomAccessFile("F:\\tool\\MobaXterm.log","rw");
//            FileChannel channel = FileInputStream.getChannel();
            FileChannel fileChannel = aFile.getChannel();
            ByteBuffer buf = ByteBuffer.allocate(1024);//分配空间
            int bytesRead = fileChannel.read(buf); //写入数据到Buffer
            System.out.println(bytesRead);
            while(bytesRead != -1)
            {
                buf.flip();//注意 buf.flip() 的调用，首先读取数据到Buffer，然后反转Buffer,接着再从Buffer中读取数据
                while(buf.hasRemaining())
                {
                    System.out.print((char)buf.get());//从Buffer中读取数据
                }
                buf.compact();//一旦读完Buffer中的数据，需要让Buffer准备好再次被写入。可以通过clear()或compact()方法来完成。
                // compact()方法将所有未读的数据拷贝到Buffer起始处。然后将position设到最后一个未读元素正后面。limit属性依然像clear()方法一样，设置成capacity。
                //如果调用的是clear()方法，position将被设回0，limit被设置成 capacity的值
                bytesRead = fileChannel.read(buf);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile != null){
                    aFile.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
//   scatter   注意buffer首先被插入到数组，然后再将数组作为channel.read() 的输入参数。read()方法按照buffer在数组中的顺序将从channel中读取的数据写入到buffer，当一个buffer被写满后，channel紧接着向另一个buffer中写。
//
//Scattering Reads在移动下一个buffer前，必须填满当前的buffer，这也意味着它不适用于动态消息(译者注：消息大小不固定)。换句话说，如果存在消息头和消息体，消息头必须完成填充（例如 128byte），Scattering Reads才能正常工作。
    public static void scatter() throws IOException {
        RandomAccessFile aFile = null;
        aFile = new RandomAccessFile("F:\\tool\\MobaXterm.log","rw");
//            FileChannel channel = FileInputStream.getChannel();
        FileChannel fileChannel = aFile.getChannel();
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = { header, body };

        fileChannel.read(bufferArray);

    }
    //gather
//    buffers数组是write()方法的入参，write()方法会按照buffer在数组中的顺序，将数据写入到channel，注意只有position和limit之间的数据才会被写入。因此，如果一个buffer的容量为128byte，但是仅仅包含58byte的数据，那么这58byte的数据将被写入到channel中。因此与Scattering Reads相反，Gathering Writes能较好的处理动态消息。
    public static void getehr() throws IOException {
        RandomAccessFile aFile = null;
        aFile = new RandomAccessFile("F:\\tool\\MobaXterm.log","rw");
//            FileChannel channel = FileInputStream.getChannel();

        FileChannel fileChannel = aFile.getChannel();
        ByteBuffer header = ByteBuffer.allocate(128);
        ByteBuffer body   = ByteBuffer.allocate(1024);
        ByteBuffer[] bufferArray = { header, body };

        fileChannel.write(bufferArray);

    }

//通道间的传输
    public static void transFileChaanel() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("fromFile.txt", "rw");
        FileChannel      fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile("toFile.txt", "rw");
        FileChannel      toChannel = toFile.getChannel();

        long position = 0;
        long count = fromChannel.size();

        toChannel.transferFrom(fromChannel,position, count);//FileChannel的transferFrom()方法可以将数据从源通道传输到FileChannel中
        fromChannel.transferTo(position, count, toChannel);//transferTo()方法将数据从FileChannel传输到其他的channel中。
    }

//selector
    public static void selector() throws IOException {
        SocketChannel channel = SocketChannel.open();
        Selector selector = Selector.open();

        channel.configureBlocking(false);

        SelectionKey key1 = channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_ACCEPT);

        while(true) {

            int readyChannels = selector.select();

            if(readyChannels == 0) continue;

            Set selectedKeys = selector.selectedKeys();

            Iterator keyIterator = selectedKeys.iterator();

            while(keyIterator.hasNext()) {

                SelectionKey key = (SelectionKey) keyIterator.next();

                if(key.isAcceptable()) {
                    // a connection was accepted by a ServerSocketChannel.

                }
                if (key.isConnectable()) {

                    // a connection was established with a remote server.

                }
                if (key.isReadable()) {

                    // a channel is ready for reading

                }
                if (key.isWritable()) {

                    // a channel is ready for writing
                }

                keyIterator.remove();

            }

        }

    }


}
