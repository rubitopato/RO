
import java.net.*;
import java.nio.*;

public class udpser{

    public static void main(String[] args){

        int accumulator=0;

        if(args.length>1 || args.length<=0){
            System.out.println("Incorrect number of arguments");
            System.exit(-1);
        }


        try{


        
        DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));
        while(true){
        byte[] bufer = new byte[500];
        DatagramPacket llegada = new DatagramPacket(bufer, bufer.length);
        socket.receive(llegada);
        
        byte[] b = llegada.getData();

        for(int i=0;i<bufer.length-4;i=i+4){

            byte[] test = {b[i],b[i+1],b[i+2],b[i+3]};
            ByteBuffer teste = ByteBuffer.wrap(test);
            int q=teste.getInt();
            if(q!=0){
                accumulator=accumulator+q;
                System.out.println(accumulator);
            }
            

        }

        int o=0;
        o=1;
   
        if(o==1){
        ByteBuffer mensaje = ByteBuffer.allocate(4);
        mensaje.putInt(accumulator);
        byte[] mensaje_final = mensaje.array();
        DatagramPacket answer = new DatagramPacket(mensaje_final, mensaje_final.length, llegada.getAddress(), llegada.getPort());
        socket.send(answer);

        }

        }//while


        } catch(Exception e){

            System.out.println(e);
        }


    }//main




}//class
