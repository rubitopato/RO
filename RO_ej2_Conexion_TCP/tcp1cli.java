
import java.net.*;
import java.util.*;
import java.io.*;
import java.nio.*;

public class tcp1cli{

public static void main (String[] args){

if(args.length>2 || args.length<=0 || args.length==1){
            System.out.println("Incorrect number of arguments. Correct way: tcp1cli ip_address port_number");
            System.exit(-1);
}

String ip=args[0];
String port_num=args[1];

try{
InetAddress host_ip = InetAddress.getByName(ip);
Socket socket = new Socket(host_ip,Integer.parseInt(port_num));

while(true){
    System.out.println("Type the numbers you want to send. If first number is 0, the program will end\n");
    String set_numbers = System.console().readLine();
if(set_numbers.charAt(0)=='0'){
    socket.close();
    System.exit(-1);
}
LinkedList<Integer> numbers= new LinkedList<Integer>();
String[] final_numbers = set_numbers.split(" ");
for(int i=0; i<final_numbers.length; i++){
    int num=Integer.parseInt(final_numbers[i]);
    numbers.add(num);
}


ByteBuffer mensaje = ByteBuffer.allocate(4*numbers.size());
int s = numbers.size();
for(int i=0;i<s;i++){
    mensaje.putInt(numbers.removeFirst());
}

byte[] mensaje_final = mensaje.array();

byte[] respuesta = new byte[100];

OutputStream output = socket.getOutputStream();
output.write(mensaje_final);
InputStream input = socket.getInputStream();
input.read(respuesta);


ByteBuffer r = ByteBuffer.wrap(respuesta);
int p=r.getInt();
System.out.println("\n" + p + "\n");


}//termina bucle

}
catch(SocketTimeoutException e){
    System.out.println("A timeout has happened");
    System.exit(-1);
}

catch(Exception et){
    System.out.println(et);
    System.exit(-1);
}



}//main


}//class