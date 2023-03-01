
import java.net.*;
import java.io.*;
import java.nio.*;

public class tcp1ser{

    public static void main(String[] args){

        int accumulator=0;

        if(args.length>1 || args.length<=0){
            System.out.println("Incorrect number of arguments. Correct way: tcp1ser port_number");
            System.exit(-1);
        }

        
    
        
        try{

            ServerSocket sersocket = new ServerSocket(Integer.parseInt(args[0]));

            while(true){
            try{
                Socket socket = sersocket.accept();
            accumulator=0;
                while(socket.isConnected()){

        

        byte[] bufer = new byte[500];
      
        InputStream input = socket.getInputStream();
        input.read(bufer);
        
        for(int i=0;i<bufer.length-4;i=i+4){

            byte[] test = {bufer[i],bufer[i+1],bufer[i+2],bufer[i+3]};
            ByteBuffer teste = ByteBuffer.wrap(test);
            int q=teste.getInt();
            if(q!=0){
                accumulator=accumulator+q;
                System.out.println("Acumulador:" + accumulator);
            }
            

        }

        
        ByteBuffer mensaje = ByteBuffer.allocate(4);
        mensaje.putInt(accumulator);
        byte[] mensaje_final = mensaje.array();
        OutputStream output = socket.getOutputStream();
        output.write(mensaje_final);

        
                        }//second while
                }catch(Exception e){

                System.out.println("Connection lost. Accumulator set to 0\n");
                } 
                }//first while
        } catch(Exception e){//first try

            System.out.println("Problems during the creation of the server\n");
        }

        }//main


        


    }//class





