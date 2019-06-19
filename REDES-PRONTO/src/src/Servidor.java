package src;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Igor Gabarra
 * 
 */

public class Servidor extends Thread{
    
    public static int contException = 0;
    
    public static GUI gui = new GUI();
    // Numero de linhas e colunas da matriz
    public static int size = 100;
    
    // Matriz armazenada pelo servidor
    public static int matrix[][] = inicializeMatrix();
    
    // Armazenamento de comunicacao de clientes
    private static ArrayList<BufferedWriter> clientes;           
    
    // Socket do servidor
    private static ServerSocket server; 
    
    // Cliente solicitando conexao
    private Socket communication;
    
    // Stream de entrada para o servidor
    private InputStream input;  
    
    // Leitor de stream enviada pelo cliente
    private InputStreamReader inputReader;  
    
    // Leitor do buffer de stream enviada pelo cliente
    private BufferedReader buffer;
    
    // Conta o numero de clientes conectados
    public static int countClient = 1;
    
    public static int contador = 1;
    
    // Inicializa matriz do servidor com todos os campos em branco
    private static int[][] inicializeMatrix(){
        int m[][] = new int[size][size];
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                m[i][j] = 2;
        
        return m;
    }
    
    /**
     * Cores da matriz sao representadas como numeros no servidor
     * 
     * 1 -> Branco
     * 2 -> Cinza
     * 3 -> Vermelho
     * 4 -> Amarelo
     * 5 -> Verde Escuro
     * 6 -> Azul
     * 7 -> Roxo
     * 8 -> Preto
     *
     */
    
    public Servidor(Socket solicitaComunicacao){
        communication = solicitaComunicacao;
        try {
            input       = communication.getInputStream();
            inputReader = new InputStreamReader(input);
            buffer      = new BufferedReader(inputReader);
        } catch (IOException e) {}                          
    }
    
    @Override
    public synchronized void run(){                       
        try{              
            // Armazena mensagem recebida
            String message;
            // Comunicacao para receber do cliente
            OutputStream output         = communication.getOutputStream();
            Writer outputWriter         = new OutputStreamWriter(output);
            BufferedWriter bufferWriter = new BufferedWriter(outputWriter); 
            
            
            // Adiciona buffer de recebimento para cada cliente na lista de clientes
            clientes.add(bufferWriter);
            
            // Le o buffer para verificar se eh pedido de saida
            message = buffer.readLine();
            
            // Se comando recebido for de sair, diminui contador de clientes ativos
            if("Sair".equalsIgnoreCase(message)){
                //bufferWriter.close();
                //clientes.remove(bufferWriter);
                countClient--;
            }

            while(!"Sair".equalsIgnoreCase(message) && message != null){           
                // Le a mensagem contendo a matriz
                message = buffer.readLine();
                // Atualiza matriz com dados inseridos
                String dadosSeparados[] = new String[3];
                dadosSeparados = message.split(";");

                //GUI
                gui.set_collorspallet(Integer.parseInt(dadosSeparados[2]));
                gui.do_click(Integer.parseInt(dadosSeparados[0]), Integer.parseInt(dadosSeparados[1]));
                refreshMatrix(message);
                
                // Envia para todos a nova matriz
                sendToAll(bufferWriter);
                
                // Mostra mensagem recebida
                System.out.println("Mensagem " + contador++ + " recebida: " +message);
            }

        }catch (IOException e) {
            contException++;
            System.out.println("Exception in thread!");
        }                       
    }
   
    
    // Atualiza matriz baseada em string recebida pela comunicacao
    public void refreshMatrix(String matrixCode){
        String dadosSeparados[] = new String[3];
        dadosSeparados = matrixCode.split(";");
      
        Servidor.matrix[Integer.parseInt(dadosSeparados[0])][Integer.parseInt(dadosSeparados[1])] = Integer.parseInt(dadosSeparados[2]);
    }
    
    // Enviar mensagem para todos os clientes
    public synchronized void sendToAll(BufferedWriter bufferWriterSaida) throws  IOException 
    { 
        // Em toda a lista de clientes
        for(BufferedWriter bw : clientes){            
            //Se matriz atual for igual a matriz anterior, nada acontece
            if(!(bufferWriterSaida == bw)){ // Caso contrario
                // Escreve no buffer toda a string
                bw.write(matrixToString());
                //Limpa comunicacao
                bw.flush(); 
            }
        }          
    }
    
    // Transforma matriz em string contendo todos os seus valores, da esquerda superior ate direita inferior
    public String matrixToString(){
        String stringMatrix = "";
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++)
                stringMatrix += matrix[i][j] + ";";
        
        return stringMatrix + "\n";
    }
    
    
    // Main do servidor
    public static void main(String []args) {
        // Inicializa matriz com espacos em branco
        gui.setVisible(true);
        
        try{
            // Criacao de janela utilizada para inserir numero da porta a ser ouvida pelo servidor
            JLabel lblMessage = new JLabel("Porta a ser utilizada:");
            JTextField txtPorta = new JTextField("12345");
            Object[] texts = {lblMessage, txtPorta };  
            JOptionPane.showMessageDialog(null, texts);
            
            // Criacao do servidor para ouvir a porta inserida pelo usuario
            server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
            
            // Cria array de clientes para gerenciar envio de informacoes
            clientes = new ArrayList<BufferedWriter>();
            
            // Mostra que porta esta sendo ouvida
            JOptionPane.showMessageDialog(null,"Servidor ativo utilizando porta " + txtPorta.getText());

            // Espera conexao de clientes e cria thread para gerenciar troca de informacoes
            while(true){
                System.out.println("Esperando por conexao...");
                Socket comunicacao = server.accept();
                System.out.println("Cliente " + countClient++ + " conectado!");
                Thread t = new Servidor(comunicacao);
                t.start();
                System.out.println("ContException: " + contException);
            } 
            
        }catch (HeadlessException | IOException | NumberFormatException e) {}   
        
    }// Fim do método main                      
} //Fim da classe

