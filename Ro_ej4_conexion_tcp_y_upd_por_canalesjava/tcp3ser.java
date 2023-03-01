
import java.net.*;
import java.nio.*;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class tcp3ser{

    public static void main(String[] args){



        if(args.length>1 || args.length<=0){
            System.out.println("Incorrect number of arguments. Correct way: tcp3ser port_number");
            System.exit(-1);
        }

int acum_udp=0;

try{//desde este try la copia de seguridad

    Selector selector=Selector.open();
    ServerSocketChannel serchan = ServerSocketChannel.open();
    InetSocketAddress port = new InetSocketAddress(Integer.parseInt(args[0]));
    serchan.bind(port);
    serchan.configureBlocking(false);

    DatagramChannel datachan=DatagramChannel.open();
    datachan.bind(port);
    datachan.configureBlocking(false);


    String data_ident="d";
    serchan.register(selector,SelectionKey.OP_ACCEPT,null);
    datachan.register(selector,SelectionKey.OP_READ ,data_ident); //creas estos dos canales asignandoles esas selection key q usaremos para saber q operacion hacer con cada uno
    //tambien al datachan le asigno una string para luego identificar si usar udp o tcp
    
    
    while(true){

        selector.select();//cuando un canal esta listo para algo, esto lo elije y empieza el proceso

        Set<SelectionKey> selKeys = selector.selectedKeys(); //se extraen las key del canal seleccionado
        Iterator<SelectionKey> it = selKeys.iterator(); //se crea un iterator para navegar por las key
        
        while(it.hasNext()){
            
                    SelectionKey key = it.next();
                    if (key.isAcceptable()) {
                        
                        
                        SocketChannel cliente=serchan.accept();
                        
                        cliente.configureBlocking(false);
                        int acum=0;
                        cliente.register(selector,SelectionKey.OP_READ,acum); //se acepta un canal tcp q busca conectarse y se registra con la read key para leer y enviar mesnsaje
                        //ademas le asigno un attached object(int) que sera el contador individual de cada canal
                        
                        
                    }
                    
                    if(key.isReadable() && ((Object)key.attachment()).getClass().getSimpleName().trim().equals("String")){//compruebo la clase del object attached pa decidir si usar tcp o udp

                        DatagramChannel client=(DatagramChannel) key.channel();
                        ByteBuffer receive = ByteBuffer.allocate(8); //proceso para recibir y enviar los datos que llegan por el canal udp. Vienen y van en forma de string contenidos en un buffer
                        SocketAddress address = client.receive(receive);
                        String number=new String(receive.array()).trim();
                        if(number.equals("")){
                            client.close();
                            
                        }
                        else{int bufer1=Integer.parseInt(number);
                            
                            acum_udp=acum_udp+bufer1; //acum_udp es el acumulador comun de todos los canales udp
                            System.out.println("Accumulator udp: " + acum_udp);
                            String sending = acum_udp+"";
                            client.send(ByteBuffer.wrap(sending.getBytes()),address); //con udp es necesario indicar a que direccion mandar el paquete ya q es un sistema donde no hay una conexion clara y directa
                            
                        }
                        
                    }
                    
                    if(key.isReadable() && ((Object)key.attachment()).getClass().getSimpleName().trim().equals("Integer")){ //tcp seleccionado
                        
                            int acum_tcp=(int)key.attachment(); //de esta manera recuperamos el contador individual de cada canal
                            SocketChannel cliente=(SocketChannel) key.channel();
                            ByteBuffer r = ByteBuffer.allocate(256);
                            cliente.read(r);
                            r.flip();
                            String number=new String(r.array()).trim();
                            if(number.equals("")){
                                cliente.close();
                                
                            }
                            else{int bufer1=Integer.parseInt(number);
                                
                                acum_tcp=acum_tcp+bufer1;
                                
                                System.out.println("Accumulator tcp: " + acum_tcp);
                                String sending = acum_tcp+"";
                                cliente.write(ByteBuffer.wrap(sending.getBytes()));
                                key.attach(acum_tcp); //esta es la unica manera (que conozco) para actualizar el contador una vez modificado sin afectar al de otros canales
                            }
                    }
                        it.remove();//vamos quitando las keys del set de selection keys segun las vamos procesando porque sino seria un bucle sin fin
                    
                    

                    

        }

    }



    } catch(Exception e){

        System.out.println(e);
    }

    }//main


        


}//class
