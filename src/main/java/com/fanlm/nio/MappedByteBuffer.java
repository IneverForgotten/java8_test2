package com.fanlm.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @program: java8_test
 * @description: NIO超大文件读写
 * @author: flm
 * @create: 2021-01-25 10:50
 **/
public class MappedByteBuffer {

    public static void main(String[] args) {
        method3();
        System.out.println("=============");
        method4();
    }

    private static String path = "";

    public static void method4(){
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try{
            aFile = new RandomAccessFile(path,"rw");
            fc = aFile.getChannel();
            long timeBegin = System.currentTimeMillis();
            ByteBuffer buff = ByteBuffer.allocate((int) aFile.length());
            buff.clear();
            fc.read(buff);
            //System.out.println((char)buff.get((int)(aFile.length()/2-1)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)+1));
            long timeEnd = System.currentTimeMillis();
            System.out.println("Read time: "+(timeEnd-timeBegin)+"ms");
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile!=null){
                    aFile.close();
                }
                if(fc!=null){
                    fc.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public static void method3(){
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try{
            aFile = new RandomAccessFile(path,"rw");
            fc = aFile.getChannel();
            long timeBegin = System.currentTimeMillis();
            java.nio.MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());
            // System.out.println((char)mbb.get((int)(aFile.length()/2-1)));
            // System.out.println((char)mbb.get((int)(aFile.length()/2)));
            //System.out.println((char)mbb.get((int)(aFile.length()/2)+1));
            long timeEnd = System.currentTimeMillis();
            System.out.println("Read time: "+(timeEnd-timeBegin)+"ms");
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(aFile!=null){
                    aFile.close();
                }
                if(fc!=null){
                    fc.close();
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
