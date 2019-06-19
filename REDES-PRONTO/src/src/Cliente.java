package src;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Igor Gabarra
 * 
 */

public class Cliente extends JFrame{
    private int size = 100;
    public static int matrix[][];   
    private static int numClients = 100;
    private static final long serialVersionUID = 1L;
    private JTextArea texto;
    private JTextField txtMsg;
    private JButton btnSend;
    private JButton btnSair;
    private JLabel lblHistorico;
    private JLabel lblMsg;
    private JPanel pnlContent;
    private Socket socket;
    private OutputStream output;
    private Writer outputWrite; 
    private BufferedWriter bufferWrite;
    private JTextField txtIP;
    private JTextField txtPorta;
    private JTextField txtNome;
    
    
    public Cliente(String ip, String port, String username){
        txtIP             = new JTextField(ip);
        txtPorta          = new JTextField(port);
        txtNome           = new JTextField(username);
    }
        

    // Realiza conexao entre socket e servidor. Retorna IO Exception em caso de erro
    public synchronized void conectar() throws IOException{
        // Faz conexao socket-servidor, utilizando ip e porta digitadas pelo usuario
        socket      = new Socket(txtIP.getText(),Integer.parseInt(txtPorta.getText()));
        
        // Comunicacao para enviar ao servidor
        output      = socket.getOutputStream();
        outputWrite = new OutputStreamWriter(output);
        bufferWrite = new BufferedWriter(outputWrite);
    }
    
    // Envia mensagem para o servidor
    public int count = 1;
    public synchronized void enviarMensagem(String message) throws IOException{
        // Se mensagem a ser enviada eh Sair, manda Desconnected para o servidor
        bufferWrite.write(message);     
        bufferWrite.flush();
        System.out.println("Mensagem " + count++ + " enviada: " + message);
    }
    
    // Recebe mensagem do servidor
    public synchronized void escutar() throws IOException{
        // Estabelece conexao com servidor
        new Thread(){
            
            InputStream input           = socket.getInputStream();
            InputStreamReader inputRead = new InputStreamReader(input);
            BufferedReader bufferRead   = new BufferedReader(inputRead);
            String message              = "";

            // Se mensagem nao for "Sair", le a proxima 
            @Override
            public void run()
            {
                while(!"Sair".equalsIgnoreCase(message)){
                    try {
                        if(bufferRead.ready()){
                            message = bufferRead.readLine();
                            Cliente.matrix = refreshMatrix(message);
                        }   } catch (IOException ex) {
                        Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            }
           
        }.start();
            
    }
    
    public int[][] refreshMatrix(String message){
        int m[][] = new int[size][size];
        
        int stringCount = 0;
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++){
                m[i][j] = Integer.parseInt(String.valueOf(message.charAt(stringCount)));
                stringCount+=2;
            }
        
        /*
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++)
                System.out.print(matrix[i][j] + " ");
            
            System.out.print("\n");
        }
        */
        
        return m; 
    }
    
    
    // Usuario seleciona opcao de sair do programa
    public void sair() throws IOException{
        enviarMensagem("Sair\n");
        bufferWrite.close();
        outputWrite.close();
        output.close();
        socket.close();
    }
    
    public synchronized void sendMessages(int times) throws IOException, InterruptedException{
        Random generator = new Random();
        String send = "0;2;3;";
        
        enviarMensagem(send + "\n");
        
        for(int i=0; i<times; i++){
            send = Integer.toString(generator.nextInt(100)) + ";" + Integer.toString(generator.nextInt(100)) + ";" + Integer.toString(generator.nextInt(8)+1) + ";";
            enviarMensagem(send + "\n");
        }       
    }

    
    public static void main(String []args) throws IOException, InterruptedException{
        //Cliente app = new Cliente();
      //  ArrayList<Cliente>clients = new ArrayList<>();
      Cliente []clientes = new Cliente[numClients];
         
      for(int i=0; i<numClients; i++){
        clientes[i] = new Cliente("127.0.0.1", "12345", "Igor");  
        clientes[i].conectar();
        clientes[i].sendMessages(1);
        clientes[i].escutar();
        
       // clientes[i].escutar();
       // app.conectar();
       // app.sendMessages(1);
       // app.escutar();  
      }
    }
}
