package com.ys.intelligentlock.LanUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by admin on 2016/12/27.
 */

public  class UDPSend {
    public String hostName="255.255.255.255";   // 广播
    public int port=18000;

    public DatagramSocket socket;
    public void sendData() {
        try {
            socket=new DatagramSocket(port);
            socket.setBroadcast(true);
            String s="first";
            byte[] b=s.getBytes();
            DatagramPacket datagramPacket=new DatagramPacket(b,b.length,InetAddress.getByName(hostName),port);
            socket.send(datagramPacket);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(socket!=null){
                socket.close();
            }
        }
    }

}
