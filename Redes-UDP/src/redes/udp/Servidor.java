/**
 *
 * @author Igor Gabarra
 * 
 */
package redes.udp;

import java.io.*;
import java.net.*;
 
class Servidor {
    
    public static GUI gui = new GUI();

    public static int size = 100;
    // Matriz armazenada pelo servidor
    public static int matrix[][] = inicializeMatrix();
    
    // Inicializa matriz do servidor com todos os campos em branco
    private static int[][] inicializeMatrix(){
        int m[][] = new int[size][size];
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                m[i][j] = 2;
        
        return m;
    }
    
    // Transforma matriz em string contendo todos os seus valores, da esquerda superior ate direita inferior
    public static String matrixToString(){
        String stringMatrix = "";
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                stringMatrix += matrix[i][j] + ";";
        
        return stringMatrix + "\n";
    }
    public static void main(String args[]) throws Exception {

        int porta = 12345;
        int numConn = 1;

        DatagramSocket serverSocket = new DatagramSocket(porta);

        byte[] receiveData = new byte[100000];
        byte[] sendData = new byte[100000];
        gui.setVisible(true);
        int cont=1;
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Esperando por datagrama UDP na porta " + porta);
            serverSocket.receive(receivePacket);
            System.out.print("Datagrama UDP [" + numConn + "] recebido...");
            
            System.out.println("Cliente " + cont + "Conectado");

            String sentence = new String(receivePacket.getData());

            String dadosSeparados[] = new String[3];
            dadosSeparados = sentence.split(";");

            //GUI
            gui.set_collorspallet(Integer.parseInt(dadosSeparados[2]));
            gui.do_click(Integer.parseInt(dadosSeparados[0]), Integer.parseInt(dadosSeparados[1]));
               
            
            Servidor.matrix[Integer.parseInt(dadosSeparados[0])][Integer.parseInt(dadosSeparados[1])] = Integer.parseInt(dadosSeparados[2]);                        
            InetAddress IPAddress = receivePacket.getAddress();
            cont++;
            int port = receivePacket.getPort();

            String compressMatrix = matrixToString();

            sendData = compressMatrix.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

            System.out.print("Enviando...");
            serverSocket.send(sendPacket);
            System.out.println("OK\n");
        }
    }
}
