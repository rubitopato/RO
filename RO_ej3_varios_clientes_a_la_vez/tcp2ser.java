
import java.net.*;
import java.io.*;
import java.nio.*;

public class tcp2ser{


    tcp2ser(){

    }

    public static void main(String[] args){

        int num=0;

        if(args.length>1 || args.length<=0){
            System.out.println("Incorrect number of arguments. Correct way: tcp1ser port_number");
            System.exit(-1);
        }

        
        tcp2ser servidor=new tcp2ser();
        
        

           
        try{
            ServerSocket sersocket = new ServerSocket(Integer.parseInt(args[0]));
            while(true){
            
                
                Socket socket = sersocket.accept();
                
                num++;
                Connection_to_client c=servidor.new Connection_to_client(socket,num);
                Thread t=new Thread(c);
                t.start();
                }//first while
       }
       catch(Exception e){

                System.out.println("Connection lost\n");
                System.out.println(e);
                } 
            
        }//main


        public class Connection_to_client implements Runnable{

            public Socket socket;
            public int accumulator;
            public int n;
            public Connection_to_client(Socket s,int num){
                accumulator=0;
                socket=s;
                n=num;
            }

            public void run(){
                try{
                while(true){

                    

                    byte[] bufer = new byte[500];
                  
                    InputStream input = socket.getInputStream();
                    input.read(bufer);
                    
                    for(int i=0;i<bufer.length-4;i=i+4){
            
                        byte[] test = {bufer[i],bufer[i+1],bufer[i+2],bufer[i+3]};
                        ByteBuffer teste = ByteBuffer.wrap(test);
                        int q=teste.getInt();
                        if(q!=0){
                            accumulator=accumulator+q;
                            System.out.println("Acumulador " + n + ":  " + accumulator);
                        }
                        
            
                    }
            
                    
                    ByteBuffer mensaje = ByteBuffer.allocate(4);
                    mensaje.putInt(accumulator);
                    byte[] mensaje_final = mensaje.array();
                    OutputStream output = socket.getOutputStream();
                    output.write(mensaje_final);
                
            }//while
            }//try
            catch(Exception e){
                //System.out.println("2");
                System.out.println("Connection from client " + n + " lost\n");

            }
//System.out.println("3");
        }//void run


        }//inner class


    }//class





