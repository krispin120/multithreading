package com.sebastian.holamundo;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Main
{
    public static class Entero{
        public int valor = 0;
    }

    public static Entero total = new Entero();

    public static void main(String[] args) throws Exception
    {
        int[] numeros = new int[10000];

        System.out.println("\nNumeros del array: ");
        for (int i = 0; i < numeros.length; i++)
        {
            numeros[i] = (int) (Math.random() * 10);

            System.out.print(numeros[i] + ", ");
        }
        int nThreads = Runtime.getRuntime().availableProcessors() * 2;

        System.out.println("\n\nCantidad de hilos: " + nThreads + "\n");
        ArrayList<Thread> hilos = new ArrayList<>();

        int size = numeros.length/nThreads;
        
        for (int i = 0; i < numeros.length; i+= size)
        {
            int ini = i;
            int fin = Math.min(ini + size - 1, numeros.length - 1);
            Thread t = new Thread( () -> {
                int aux = sumarArray(ini, fin, numeros);
                synchronized(total){
                    total.valor += aux;
                }
            });
            t.start();
            hilos.add( t );
        }
        

        for (Thread hilo : hilos)
        {
          hilo.join();
        }
        
        System.out.println("Total: " + total.valor);

    }

    public static int sumarArray(int inicio, int fin, int[] array){
        if (inicio == fin){
            return array[inicio];
        } 
        else {
            int mitad = (inicio + fin)/2;
            int x = sumarArray(inicio, mitad, array);
            int y = sumarArray(mitad + 1, fin, array);
            return x + y;
        }
    }

}

//     public static void main(String[] args) throws Exception{
//         Object alarma = new Object();

//         // Iniciar hilo despertador
//         new Thread( () -> {
//           try {
//             Thread.sleep(5000);
//             System.out.println("Despertando hilo principal");
//             synchronized(alarma){
//                 alarma.notify();
//             }
//           } catch (Exception e) {
//             e.printStackTrace();
//           }
//         }).start();
        
//         // Esperar a que me notifiquen
//         System.out.println("Esperando...");
//         synchronized(alarma){
//             alarma.wait();
//         }
//         System.out.println("Me despertaron");
//     }
// }

// public class Main
// {
//     private static void atenederRequest(Socket socket) throws IOException, Exception {

//         BufferedReader br = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
//         while ( !br.readLine().trim().isEmpty() );

//         BufferedWriter bw = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );
//         bw.write("HTTP/1.1 200 OK\r\n\r\n<html><body><h1>Hola mundo!!!!</h1></body></html>");
//         bw.flush();
//         socket.close();
//         Thread.sleep(5000);
//     }

//     public static void main(String[] args) throws Exception
//     {
//         ServerSocket serverSocket = new ServerSocket(8080);

//         System.out.println("Esperando requests...");
//         while (true){
//             Socket socket = serverSocket.accept();
//             new Thread ( () -> {
//                 try{
//                     atenederRequest(socket);
//                 }
//                 catch (Exception e){
//                     throw new RuntimeException(e);
//                 }
//             }).start();
//         }
//         //serverSocket.close();
//     }
// }
