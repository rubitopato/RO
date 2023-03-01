
import java.net.*;
import java.util.*;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;


public class tcp3cli{

public static void main (String[] args){

if(args.length!=3 & args.length!=2){
            System.out.println("Incorrect number of arguments. Correct way: tcp3cli ip_address port_number [-u]");
            System.exit(-1);
}

String ip=args[0];
String port_num=args[1];

/////////////udp
if(args.length==3){
if(args[2].equals("-u")){
while(true){
System.out.println("Type the numbers you want to send. If first number is 0, the program will end\n");
String set_numbers = System.console().readLine();

if(set_numbers.equals("0")==true){
    System.exit(-1);
}

LinkedList<Integer> numbers= new LinkedList<Integer>();
numbers.add(Integer.parseInt(set_numbers));
/*for multiple numbers
while(set_numbers.equals("0")==false){
    numbers.add(Integer.parseInt(set_numbers));
    set_numbers = System.console().readLine();
}
*/




try{
InetSocketAddress host_ip = new InetSocketAddress(ip,Integer.parseInt(port_num));
DatagramChannel packet = DatagramChannel.open();

//packet.connect(host_ip);
ByteBuffer output = ByteBuffer.wrap(set_numbers.getBytes());
packet.send(output,host_ip);
ByteBuffer r = ByteBuffer.allocate(256);
packet.receive(r);

String p=new String(r.array()).trim();
System.out.println("Answer: " + p);
//packet.close();
}
catch(SocketTimeoutException e){
    System.out.println("A timeout has happened");
    System.exit(-1);
}

catch(Exception et){
    System.out.println(et);
    System.exit(-1);
}

}

}//if

else{
    System.out.println("Third attribute wrongly entered. Correct way: tcp3cli ip_address port_number [-u]");
    System.exit(-1);
}

}
////////////udp


//////////////tcp
else{
try{
InetSocketAddress host_ip = new InetSocketAddress(ip,Integer.parseInt(port_num));
SocketChannel socket = SocketChannel.open(host_ip);


while(true){
    System.out.println("Type the numbers you want to send. If first number is 0, the program will end\n");
    String set_numbers = System.console().readLine();
if(set_numbers.charAt(0)=='0'){
    socket.close();
    System.exit(-1);
}
LinkedList<Integer> numbers= new LinkedList<Integer>();
/*String[] final_numbers = set_numbers.split(" ");
for(int i=0; i<final_numbers.length; i++){
    int num=Integer.parseInt(final_numbers[i]);
    numbers.add(num);
}
muchos numeros*/
numbers.add(Integer.parseInt(set_numbers));


ByteBuffer output = ByteBuffer.wrap(set_numbers.getBytes());
socket.write(output);
ByteBuffer input = ByteBuffer.allocate(100);
socket.read(input);

String p=new String(input.array()).trim();
System.out.println("\nAnswer: " + p + "\n");


}//termina bucle

}
catch(SocketTimeoutException e){
    System.out.println("A timeout has happened");
    System.exit(-1);
}

catch(Exception et){
    System.out.println("An error has happened");
    System.exit(-1);
}

}
////////////////tcp


}//main


}//class