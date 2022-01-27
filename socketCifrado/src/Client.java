
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Client {

	public static String line;
	
public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException{

    int port=5678;
    byte[] ipAddr = new byte[] { 127,0,0,1 };
    InetAddress address = InetAddress.getByAddress(ipAddr);
    DatagramSocket socket = new DatagramSocket();
    String miclave = "alvaromourazonov";
    byte[] keyS = miclave.getBytes();
    //byte[] keyS = {0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79};
    
    SecretKeySpec secretKey = new SecretKeySpec(keyS, "AES");
    Cipher c = Cipher.getInstance("AES");  
    c.init(Cipher.ENCRYPT_MODE, secretKey);
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in,"UTF-8"));
    System.out.println("Mensaje:");
    line =in.readLine();
    
    while(!line.equals("EXIT")){
    	
    byte[] mensajeEncriptado = c.doFinal(line.getBytes());
    String encriptado = new String(mensajeEncriptado);
    System.out.println("Mensaje Encriptado:");
    System.out.println(encriptado);
    DatagramPacket packet1=new DatagramPacket(mensajeEncriptado, mensajeEncriptado.length, address, port);//bytes      
    socket.send(packet1);
    System.out.println("Mensaje:");
    
    line =in.readLine();
    
    }
    
    byte[] mensajeEncriptado = c.doFinal(line.getBytes());
    DatagramPacket packet1=new DatagramPacket(mensajeEncriptado, mensajeEncriptado.length, address, port);//bytes      
    socket.send(packet1);
    socket.close();
    System.out.println("The End...");
	}   
}