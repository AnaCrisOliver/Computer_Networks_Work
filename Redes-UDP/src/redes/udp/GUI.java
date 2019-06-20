/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes.udp;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
/**
 *
 * @author Ana Cristina
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class GUI extends JFrame {


    public Container container;
    public JPanel matriz;
    
    public int coluna;
    public int linha;
    public static int line_size = 100;
    public static int column_size = 100;
 
    MouseHandler acaoDoMouse = new MouseHandler();
   
    public JButton[][] botoes = new JButton[line_size][column_size];
    
    public int colors_pallet=8;
   
    JFrame pick_color = new JFrame();
    JFrame color_pallet = new JFrame();

    String r_string,g_string,b_string;
    int r_int, g_int, b_int;
    
    public GUI() {
        super("Pick a Color!");
        initComponents();
    } //Constructor

    void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 900);
        setResizable(false);
        colors_pallet = 0;

 
        container = getContentPane();

        setLayout(new BorderLayout(20, 0));
       

        //CONFIGURA MATRIZ ONDE OS PIXELS FICARAO
        matriz = new JPanel();
        matriz.setSize(500, 500);
        matriz.setLayout(new GridLayout(line_size, column_size, 2, 2));
        matriz.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        matriz.setBackground(new java.awt.Color(200,200,200));

        for (int i = 0; i < line_size; i++) {
            for (int j = 0; j < column_size; j++) {
                final int curRow = i;
                final int curCol = j;
                botoes[i][j] = new JButton();
                botoes[i][j].setPreferredSize(new Dimension(4, 4));
                botoes[i][j].addMouseListener(acaoDoMouse);
                botoes[i][j].setBackground(new java.awt.Color(255, 255, 255));
                botoes[i][j].setForeground(new java.awt.Color(230, 230, 230));
                //    botoes[i][j].addKeyListener(enter);
                botoes[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 51, 51), null, null));
                matriz.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                matriz.add(botoes[i][j]);

            }
        }

        add(matriz, BorderLayout.CENTER);

    }

    public void set_collorspallet(int i)
    {
        this.colors_pallet = i;
    }
    public void do_click(int i, int j)
    {
        botoes[i][j].doClick();

        //seta o botÃ£o com a cor selecionada aqui
        /*1- branco
        * 2- cinza
        * 3- vermelho
        * 4- Amarelo
        * 5- Verde escuro
        * 6- Azul
        * 7- Roxo
        * 8- Preto
        */
        linha = i;
        coluna = j;
        System.out.println("botao foi clicado");
        if(colors_pallet == 0)
        {
            botoes[linha][coluna].setBackground(new java.awt.Color(255, 255, 255));

        }else if(colors_pallet == 1)
        {
            botoes[linha][coluna].setBackground(new java.awt.Color(255, 255, 255));

        }
        else if(colors_pallet == 2)
        {
            botoes[linha][coluna].setBackground(new java.awt.Color(150, 150, 150));

        }
        else if(colors_pallet == 3)
        {
            botoes[linha][coluna].setBackground(new java.awt.Color(240, 0, 0));

        }else if(colors_pallet == 4)
        {
            botoes[linha][coluna].setBackground(new java.awt.Color(255,255, 51));

        }else if(colors_pallet == 5)
        {
            botoes[linha][coluna].setBackground(new java.awt.Color(0, 255, 0));

        }else if(colors_pallet == 6)
        {
            botoes[linha][coluna].setBackground(new java.awt.Color(0, 0, 255));

        }else if(colors_pallet == 7)
        {
            botoes[linha][coluna].setBackground(new java.awt.Color(102, 0, 204));
        }
        else if(colors_pallet == 8)
        {
            botoes[linha][coluna].setBackground(new java.awt.Color(0, 0, 0));
        }
        System.out.println("clicou");
    }
    
    public static void main(String[] args) {
        new GUI().setVisible(true);
    }

    
    class MouseHandler extends MouseAdapter {

        @Override

        public void mouseClicked(MouseEvent event) {

            if (event.isMetaDown() == false) //se for com o botao esquerdo
            {

                
                    for (int i = 0; i < line_size; i++) {
                        for (int j = 0; j < column_size; j++) {
                            if (event.getSource() == botoes[i][j]) {
                                linha = i;
                                coluna = j;
                            }
                        }
                    }
                    //seta o botÃ£o com a cor selecionada aqui
                    /*1- branco
                    * 2- cinza
                    * 3- vermelho
                    * 4- Amarelo
                    * 5- Verde escuro
                    * 6- Azul
                    * 7- Roxo
                    * 8- Preto
                    */
                    System.out.println("botao foi clicado");
                    if(colors_pallet == 0)
                    {
                        botoes[linha][coluna].setBackground(new java.awt.Color(255, 255, 255));
                        
                    }else if(colors_pallet == 1)
                    {
                        botoes[linha][coluna].setBackground(new java.awt.Color(255, 255, 255));
                        
                    }
                    else if(colors_pallet == 2)
                    {
                        botoes[linha][coluna].setBackground(new java.awt.Color(150, 150, 150));
                        
                    }
                    else if(colors_pallet == 3)
                    {
                        botoes[linha][coluna].setBackground(new java.awt.Color(240, 0, 0));
                        
                    }else if(colors_pallet == 4)
                    {
                        botoes[linha][coluna].setBackground(new java.awt.Color(255,255, 51));
                        
                    }else if(colors_pallet == 5)
                    {
                        botoes[linha][coluna].setBackground(new java.awt.Color(0, 255, 0));
                        
                    }else if(colors_pallet == 6)
                    {
                        botoes[linha][coluna].setBackground(new java.awt.Color(0, 0, 255));
                        
                    }else if(colors_pallet == 7)
                    {
                        botoes[linha][coluna].setBackground(new java.awt.Color(102, 0, 204));
                    }
                    else if(colors_pallet == 8)
                    {
                        botoes[linha][coluna].setBackground(new java.awt.Color(0, 0, 0));
                    }
                  //  botoes[linha][coluna].setBackground(new java.awt.Color(r_int, g_int, b_int));
                
            }

        }

    }
      
}

