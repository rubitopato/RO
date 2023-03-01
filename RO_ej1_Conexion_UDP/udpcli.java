
import java.net.*;
import java.util.*;
import java.nio.*;

public class udpcli{

public static void main (String[] args){

if(args.length>2 || args.length<=0 || args.length==1){
            System.out.println("Incorrect number of arguments");
            System.exit(-1);
}

String ip=args[0];
String port_num=args[1];



System.out.println("Type a number and then press enter. For ending the messaje, type 0 and press enter");


String set_numbers = System.console().readLine();

if(set_numbers.equals("0")==true){
    System.exit(-1);
}

LinkedList<Integer> numbers= new LinkedList<Integer>();

while(set_numbers.equals("0")==false){
    numbers.add(Integer.parseInt(set_numbers));
    set_numbers = System.console().readLine();
}


ByteBuffer mensaje = ByteBuffer.allocate(4*numbers.size());
int s = numbers.size();
for(int i=0;i<s;i++){
    mensaje.putInt(numbers.removeFirst());
}

byte[] mensaje_final = mensaje.array();


try{
InetAddress host_ip = InetAddress.getByName(ip);
DatagramPacket packet = new DatagramPacket(mensaje_final,mensaje_final.length,host_ip,Integer.parseInt(port_num));
byte[] respuesta = new byte[100];
DatagramPacket receive = new DatagramPacket(respuesta, respuesta.length);
DatagramSocket socket = new DatagramSocket();//


socket.send(packet);

socket.setSoTimeout(10000);
socket.receive(receive);

byte[] b= receive.getData();
ByteBuffer r = ByteBuffer.wrap(b);
int p=r.getInt();
System.out.println(p);
socket.close();
}
catch(SocketTimeoutException e){
    System.out.println("A timeout has happened");
    System.exit(-1);
}

catch(Exception et){
    System.out.println(et);
    System.exit(-1);
}



System.exit(-1);

}//main





}//class