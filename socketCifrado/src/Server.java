import java.io.IOException;
import java.net.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Server {
public static void main(String[] args) throws IOException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException
{
    int port=5678;
    String miclave = "alvaromourazonov";
    byte[] keyS = miclave.getBytes();
   
    SecretKeySpec secretKey = new SecretKeySpec(keyS, "AES");
    DatagramSocket socket = new DatagramSocket(port);
    DatagramPacket packet = null;
    byte[] data = null;
    String desencriptado = "";
    System.out.println("Esperando conexiones...");
    while(!desencriptado.contentEquals("EXIT")){
        data = new byte[1024];
        packet = new DatagramPacket(data, data.length);
        socket.receive(packet);            
        InetAddress address = packet.getAddress();            
        String message=new String(packet.getData());
        System.out.println("Conexión extablecida con "+address );
        System.out.println("MENSAJE CIFRADO: "+ message);
        
   

        try
        {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE,secretKey, cipher.getParameters());
            byte[] mensajeDesencriptado = cipher.doFinal(packet.getData(), packet.getOffset(), packet.getLength());
            desencriptado = new String(mensajeDesencriptado);
            System.out.println("Mensaje DESCIFRADO: "+desencriptado);

        }
             
        catch(Exception e)
        {
            e.printStackTrace();
        }
     
    }       
    System.out.println("The End...");
  }
}