package net;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    Socket mySocket = null;
    int porta=6789;
    BufferedReader in;
    DataOutputStream out;
    BufferedReader keyboard;
    static int i=0;
    public boolean communicate(){
        try{
            //String message=keyboard.readLine();
            String received=in.readLine();
            System.out.printf(received+'\n');
            if(received.equals("Inserisci una lettera: ")){
                keyboard=new BufferedReader(new InputStreamReader(System.in));
                String message=keyboard.readLine();
                out.writeBytes(message + "\n");
                if (message.equals("No")) {
                    return false;
                }
            }

            return true;
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        return false;
    }

    public Socket connect(){
        try{
            System.out.println("[0]-Inizializzo il client....");
            Socket mySocket=new Socket(InetAddress.getLocalHost(),porta);
            System.out.println("[1]-Bonjour, Finesse");
            out=new DataOutputStream(mySocket.getOutputStream());
            in=new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        }
        catch(UnknownHostException e){
            System.err.println("Host sconosciuto");
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        return mySocket;

    }



    public static void main(String argv[]){
        Client c=new Client();
        c.connect();
        while(c.communicate()){};
    }
}
