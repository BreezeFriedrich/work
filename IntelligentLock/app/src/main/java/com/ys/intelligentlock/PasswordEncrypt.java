package com.ys.intelligentlock;

import java.io.UnsupportedEncodingException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import android.os.Environment;

/**
 * Created by admin on 2017/1/12.
 */

public class PasswordEncrypt {

    static {
        System.loadLibrary("lockjni");
    }

    private native static byte[] decryptprocess(byte[] instr, int inlen);
    private native static byte[] encryptprocess(String instr, int inlen);
    private native static byte[] decryptsingle(byte[] instr, int inlen);
    private native static byte[] encryptsingle(String instr, int inlen);
    private native static byte[] encryptbyteprocess(byte[] instr, int inlen);


    public static byte[] decryptprocessout(byte[] instr, int inlen)
    {
        return decryptprocess(instr, inlen);
    }
    public static byte[] encryptbyteprocessout(byte[] instr, int inlen)
    {
        return encryptbyteprocess(instr, inlen);
    }
    public static byte[] encryptprocessout(String instr, int inlen)
    {
        return encryptprocess(instr, inlen);
    }

    public static byte[] decryptPassword(byte[] instr, int inlen)
    {
        return decryptsingle(instr, inlen);
    }
    public static byte[] encryptPassword(String instr, int inlen)
    {
        return encryptsingle(instr, inlen);
    }


    public static Map<String,Object> gwcom(int type, String gatewayCode)
    {
        Map<String,Object> map=new HashMap<>();
        int backret=0;
        String recvmessage="";
        String gatewayLanIp=null;
        if (type==1)
        {
            System.out.println("type    "+type);

            //String gatewayCode="11111111111111111111111111111111";
            byte[] stogw=new byte[1024];
            stogw[0]=(byte)0x01;
            stogw[3]=(byte)0x00;stogw[4]=(byte)0x00;stogw[5]=(byte)0x00;stogw[6]=(byte)0x00;
            byte[] bdstr = com.ys.intelligentlock.PasswordEncrypt.encryptprocessout(gatewayCode, 32);

            /**
            //byte[] bdstr=new byte[40];
            byte[] gatewayBytes=gatewayCode.getBytes();
            for(int i=0;i<8;i++){
                bdstr[i]=0x00;
            }
            for(int i=0;i<32;i++){
                bdstr[i+8]=gatewayBytes[i];
            }
            **/

            int blen=bdstr.length;
            if (blen>40) blen=40;
            for (int i=0;i<blen;i++)
            {
                stogw[7+i]=bdstr[i];
            }

            byte[] enbody=new byte[1024];
            enbody[0]=(byte)0x01;

            byte[] enbodyout = com.ys.intelligentlock.PasswordEncrypt.encryptbyteprocessout(enbody, 16);
            stogw[1]=0;
            stogw[2]=16+8;
            for (int i=0;i<24;i++)
            {
                stogw[47+i]=enbodyout[i];
            }

            DatagramSocket ds=null;
            //        SocketAddress localAddr;
            byte[] buf = new byte[1000];

            DatagramPacket dp11 = new DatagramPacket(buf, buf.length);
            try {
                //			localAddr.
                ds = new DatagramSocket(2020);
                ds.setBroadcast(true);
                DatagramPacket dp;
                dp = new DatagramPacket(stogw,
                        47+24,
                        InetAddress.getByName("255.255.255.255"), 2018);
                System.out.println("come into MotionEvent.ACTION_UP 1, stogw="+printHexString(stogw));
                ds.send(dp);
                System.out.println("come into MotionEvent.ACTION_UP 2 1");
                ds.setSoTimeout(5000);
                //	        DatagramPacket pack=null;
                System.out.println("come into MotionEvent.ACTION_UP 2 2");
                ds.receive(dp11);
                System.out.println("come into MotionEvent.ACTION_UP 2 3");
                byte[] rdata=dp11.getData();

                System.out.println("come into MotionEvent.ACTION_UP 1, rdata len="+rdata.length);
                System.out.println("come into MotionEvent.ACTION_UP 1, gatewayCode="+printHexString(rdata));
                ds.close();
                System.out.println("come into MotionEvent.ACTION_UP 2 4");
                if (rdata[0]==(byte)0x01)
                {
                    int rlen=rdata[1]*256+rdata[2];
                    System.out.println("receive rlen: "+rlen);
                    byte[] gwen=new byte[40];
                    //				byte[] gwde=new byte[40];
                    for (int i=0;i<40;i++)
                    {
                        gwen[i]=rdata[7+i];
                    }
                    byte[] gwde=decryptprocessout(gwen, 32);
                    System.out.println("recvmessage Hex="+printHexString(gwde));
                    //String recvmessage="";
                    try {
                        recvmessage = new String(gwde,"UTF-8");
                    } catch (UnsupportedEncodingException e){
                        e.printStackTrace();
                    }//"UTF8");
                    System.out.println("come into MotionEvent.ACTION_UP 3, recvmessage="+recvmessage);

                    //writeFile("recvmessage   "+recvmessage);
                    backret=1;
                    byte[] gwbody=new byte[1024];
                    for (int i=0;i<rlen;i++)
                    {
                        gwbody[i]=rdata[47+i];
                    }
                    byte[] gwbodyde=decryptprocessout(gwbody, rlen-8);
                    System.out.println("come into MotionEvent.ACTION_UP 1, gwbodyde="+printHexString(gwbodyde));
                    if (gwbodyde[0]==(byte)0x02) backret=1;
                }

            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if(ds!=null){
                    ds.close();
                }
            }

        }
        if (type==3)
        {
            System.out.println("type    "+type);
            //String gatewayCode="11111111111111111111111111111111";
            byte[] stogw=new byte[1024];
            stogw[0]=(byte)0x01;
            stogw[3]=(byte)0x00;stogw[4]=(byte)0x00;stogw[5]=(byte)0x00;stogw[6]=(byte)0x00;
            byte[] bdstr = com.ys.intelligentlock.PasswordEncrypt.encryptprocessout(gatewayCode, 32);

            //byte[] bdstr=new byte[40];
            /**
            byte[] gatewayBytes=gatewayCode.getBytes();
            for(int i=0;i<8;i++){
                bdstr[i]=0x00;
            }
            for(int i=0;i<32;i++){
                bdstr[i+8]=gatewayBytes[i];
            }
             **/

            int blen=bdstr.length;
            if (blen>40) blen=40;
            for (int i=0;i<blen;i++)
            {
                stogw[7+i]=bdstr[i];
            }

            byte[] enbody=new byte[1024];
            enbody[0]=(byte)0x03;

            byte[] enbodyout = com.ys.intelligentlock.PasswordEncrypt.encryptbyteprocessout(enbody, 16);
            stogw[1]=0;
            stogw[2]=16+8;
            for (int i=0;i<24;i++)
            {
                stogw[47+i]=enbodyout[i];
            }


            DatagramSocket ds=null;
            //        SocketAddress localAddr;
            byte[] buf = new byte[1000];

            DatagramPacket dp11 = new DatagramPacket(buf, buf.length);
            try {
                //			localAddr.
                ds = new DatagramSocket(2020);
                ds.setBroadcast(true);
                DatagramPacket dp;
                dp = new DatagramPacket(stogw,
                        47+24,
                        InetAddress.getByName("255.255.255.255"), 2018);
                ds.send(dp);
                System.out.println("come into MotionEvent.ACTION_UP 2 1");
                ds.setSoTimeout(5000);
                //	        DatagramPacket pack=null;
                System.out.println("come into MotionEvent.ACTION_UP 2 2");
                ds.receive(dp11);
                System.out.println("come into MotionEvent.ACTION_UP 2 3");
                byte[] rdata=dp11.getData();
                System.out.println("come into MotionEvent.ACTION_UP 1, gatewayCode="+printHexString(rdata));
                gatewayLanIp=dp11.getAddress().getHostAddress();
                System.out.println("gatewayLanIp    "+gatewayLanIp);
                ds.close();
                System.out.println("come into MotionEvent.ACTION_UP 2 4");
                if (rdata[0]==(byte)0x01)
                {
                    int rlen=rdata[1]*256+rdata[2];
                    System.out.println("receive rlen: "+rlen);
                    byte[] gwen=new byte[40];
                    //				byte[] gwde=new byte[40];
                    for (int i=0;i<40;i++)
                    {
                        gwen[i]=rdata[7+i];
                    }
                    byte[] gwde=decryptprocessout(gwen, 32);
                    recvmessage="";
                    try {
                        recvmessage = new String(gwde,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }//"UTF8");
                    System.out.println("come into MotionEvent.ACTION_UP 3, recvmessage="+recvmessage);

                    byte[] gwbody=new byte[1024];
                    for (int i=0;i<rlen;i++)
                    {
                        gwbody[i]=rdata[47+i];
                    }
                    System.out.println("come into MotionEvent.ACTION_UP 1, gwbody="+printHexString(gwbody));
                    byte[] gwbodyde=decryptprocessout(gwbody, rlen-8);
                    //byte[] gwbodyde=gwbody;
                    System.out.println("come into MotionEvent.ACTION_UP 1, gwbodyde="+printHexString(gwbodyde));
                    if (gwbodyde[0]==(byte)0x04)
                    {
                        backret=1;
                        byte[] yzm=new byte[10];
                        for (int i=0;i<10;i++)
                        {
                            yzm[i]=gwbodyde[1+i];
                        }
                        try {
                            recvmessage = new String(yzm,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }//"UTF8");
                        System.out.println("come into MotionEvent.ACTION_UP 3, yzm="+recvmessage);
                    }
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SocketException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally {
                if(ds!=null){
                    ds.close();
                }
            }

        }
        if (type==5)
        {
            System.out.println("type    "+type);
            //String gatewayCode="11111111111111111111111111111111";
            byte[] stogw=new byte[1024];
            stogw[0]=(byte)0x01;
            stogw[3]=(byte)0x00;stogw[4]=(byte)0x00;stogw[5]=(byte)0x00;stogw[6]=(byte)0x00;
            byte[] bdstr = com.ys.intelligentlock.PasswordEncrypt.encryptprocessout(gatewayCode, 32);
            int blen=bdstr.length;
            if (blen>40) blen=40;
            for (int i=0;i<blen;i++)
            {
                stogw[7+i]=bdstr[i];
            }

            /**
            //byte[] bdstr=new byte[40];
            byte[] gatewayBytes=gatewayCode.getBytes();
            for(int i=0;i<8;i++){
                bdstr[i]=0x00;
            }
            for(int i=0;i<32;i++){
                bdstr[i+8]=gatewayBytes[i];
            }
             **/

            byte[] enbody=new byte[1024];
            enbody[0]=(byte)0x05;

            byte[] enbodyout = com.ys.intelligentlock.PasswordEncrypt.encryptbyteprocessout(enbody, 16);
            stogw[1]=0;
            stogw[2]=16+8;
            for (int i=0;i<24;i++)
            {
                stogw[47+i]=enbodyout[i];
            }


            DatagramSocket ds=null;
            //        SocketAddress localAddr;
            byte[] buf = new byte[1000];

            DatagramPacket dp11 = new DatagramPacket(buf, buf.length);
            try {
                //			localAddr.
                ds = new DatagramSocket(2020);
                ds.setBroadcast(true);
                DatagramPacket dp;
                dp = new DatagramPacket(stogw,
                        47+24,
                        InetAddress.getByName("255.255.255.255"), 2018);
                ds.send(dp);
                System.out.println("come into MotionEvent.ACTION_UP 2 1");
                ds.setSoTimeout(5000);
                //	        DatagramPacket pack=null;
                System.out.println("come into MotionEvent.ACTION_UP 2 2");
                ds.receive(dp11);
                System.out.println("come into MotionEvent.ACTION_UP 2 3");
                byte[] rdata=dp11.getData();
                System.out.println("come into MotionEvent.ACTION_UP 1, gatewayCode="+printHexString(rdata));
                ds.close();
                System.out.println("come into MotionEvent.ACTION_UP 2 4");
                if (rdata[0]==(byte)0x01)
                {
                    int rlen=rdata[1]*256+rdata[2];
                    System.out.println("receive rlen: "+rlen);
                    byte[] gwen=new byte[40];
                    //				byte[] gwde=new byte[40];
                    for (int i=0;i<40;i++)
                    {
                        gwen[i]=rdata[7+i];
                    }
                    byte[] gwde=decryptprocessout(gwen, 32);
                    recvmessage="";
                    try {
                        recvmessage = new String(gwde,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }//"UTF8");
                    System.out.println("come into MotionEvent.ACTION_UP 3, recvmessage="+recvmessage);

                    byte[] gwbody=new byte[1024];
                    for (int i=0;i<rlen;i++)
                    {
                        gwbody[i]=rdata[47+i];
                    }
                    byte[] gwbodyde=decryptprocessout(gwbody, rlen-8);
                    //byte[] gwbodyde=gwbody;
                    System.out.println("come into MotionEvent.ACTION_UP 1, gwbodyde="+printHexString(gwbodyde));
                    if (gwbodyde[0]==(byte)0x06)
                    {
                        backret=1;
                        byte[] yzm=new byte[10];
                        for (int i=0;i<10;i++)
                        {
                            yzm[i]=gwbodyde[1+i];
                        }
                        try {
                            recvmessage = new String(yzm,"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }//"UTF8");
                        System.out.println("come into MotionEvent.ACTION_UP 3, yzm="+recvmessage);
                    }
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SocketException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            finally {
                if(ds!=null){
                    ds.close();
                }
            }
        }
        map.put("backret",backret);
        map.put("recvmessage",recvmessage);
        if(!(gatewayLanIp==null)){
            map.put("gatewayLanIp",gatewayLanIp);
        }
        return map;
    }
    public static String printHexString( byte[] b)
    {
        String bstr="";
//		if (logflag==1)
        {
            for (int i = 0; i < b.length; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                bstr=bstr+" "+hex.toUpperCase();
//		     System.out.print(hex.toUpperCase() );
            }
        }
        return bstr;
    }
    public static void writeFile(String sb) {
//		if (logflag==1)
        {
            FileWriter fw = null;
            BufferedWriter bw = null;
            String datetime = "";
            try {
                SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd" + " "
                        + "hh:mm:ss");
                datetime = tempDate.format(new java.util.Date()).toString();
                fw = new FileWriter("/sdcard/yishu/yishu.txt", true);//
                // 创建FileWriter对象，用来写入字符流
                bw = new BufferedWriter(fw); // 将缓冲对文件的输出
                String myreadline = datetime + "[]" + sb;
                bw.write(myreadline + "\n"); // 写入文件
                bw.newLine();
                bw.flush(); // 刷新该流的缓冲
                bw.close();
                fw.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
