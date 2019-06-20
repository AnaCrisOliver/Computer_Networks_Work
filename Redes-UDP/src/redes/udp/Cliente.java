/**
 *
 * @author Igor Gabarra
 */
package redes.udp;


import java.io.*;
import java.net.*;
import java.util.Random;

class Cliente{
    
    public static int size = 100;
    
    public static void conexaoUDP() throws IOException{
        DatagramSocket clientSocket = new DatagramSocket();

        String servidor = "localhost";
        int porta = 12345;

        InetAddress IPAddress = InetAddress.getByName(servidor);

        byte[] sendData = new byte[100000];
        byte[] receiveData = new byte[100000];

        Random generator = new Random();

        String sentence = Integer.toString(generator.nextInt(100)) + ";" + Integer.toString(generator.nextInt(100)) + ";" + Integer.toString(generator.nextInt(8)+1) + ";";
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, porta);

        System.out.println("Enviando pacote UDP para " + servidor + ":" + porta);
        clientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        clientSocket.receive(receivePacket);
        System.out.println("Pacote UDP recebido...");

        String matrixCompressed = new String(receivePacket.getData());
        
        int matrixComplete[][] = transformMatrix(matrixCompressed);

        System.out.println("Parte da matriz recebida pelo servidor UDP:");
        
        for(int i=0; i<5; i++){
            for(int j=0; j<5; j++)
                System.out.print(" " + matrixComplete[i][j]);
            System.out.print("\n");
        }
        
        clientSocket.close();
        System.out.println("Socket cliente fechado!");
    }
    
    public static int[][] transformMatrix(String message){
        int matrix[][] = new int[size][size];
        int stringCount = 0;
        
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++){
                matrix[i][j] = Integer.parseInt(String.valueOf(message.charAt(stringCount)));
                stringCount+=2;
            }

        return matrix;
    }
    
    public static void fazRequisicoes(int quantidade) throws IOException{
        for(int i=0; i<quantidade; i++)
            conexaoUDP();
    }
    
    public static void main(String args[]) throws Exception {
        
        fazRequisicoes(10000);
    }
}
