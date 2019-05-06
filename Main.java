/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

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
 * @author ana
 */
public class Main extends JFrame {


    private Container container;
    private JPanel matriz;
    private JPanel RGB_options;
    
    public int coluna;
    public int linha;
    JLabel pickcolor_message = new JLabel("Olá! Escolha a cor que deseja usar para pintar o quadrado: ");
    JLabel title_pallet = new JLabel("Olá! Escolha a cor que deseja usar para pintar o quadrado: ");
    MouseHandler acaoDoMouse = new MouseHandler();
    MouseHandlerRGB acao_ok_button = new MouseHandlerRGB();
    MouseHandlerPallet acao_pallet_button = new MouseHandlerPallet();
    JButton[][] botoes = new JButton[40][40];
    JButton[] botoes_pallet = new JButton[16];
    int []red_botoes_pallet = new int[16];
    int []green_botoes_pallet = new int[16];
    int []blue_botoes_pallet = new int[16];
    

    JButton botao_ok = new JButton("Aplicar");
    
    JInternalFrame rgb_options = new JInternalFrame();
    JFrame pick_color = new JFrame();
    JFrame color_pallet = new JFrame();
    JPanel collor_pallet_panel = new JPanel();
    JPanel collor_pallet_buttons_panel = new JPanel();
    JTextField textfield1 = new JTextField(3);
    JTextField textfield2 = new JTextField("R:", 2);
    JTextField textfield3 = new JTextField("G:", 2);
    JTextField textfield4 = new JTextField("B:", 2);
    String r_string,g_string,b_string;
    int r_int, g_int, b_int;
    
    Main() {
        super("Pick a Color!");
        initComponents();
    } //Constructor

    void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 700);
        setResizable(false);
        //Configurando paleta de cores (Vetores lógicos)
        //branco
        
        red_botoes_pallet[0] = 255;
        green_botoes_pallet[0] = 255;
        blue_botoes_pallet[0] = 255;
        //cinza claro
        red_botoes_pallet[1] = 220;
        green_botoes_pallet[1] = 220;
        blue_botoes_pallet[1] = 220;
        //cinza escuro
        red_botoes_pallet[2] = 150;
        green_botoes_pallet[2] = 150;
        blue_botoes_pallet[2] = 150;
        //preto
        red_botoes_pallet[3] = 0;
        green_botoes_pallet[3] = 0;
        blue_botoes_pallet[3] = 0;
        //rosa
        red_botoes_pallet[4] = 255;
        green_botoes_pallet[4] = 153;
        blue_botoes_pallet[4] = 153;
        //vermelho
        red_botoes_pallet[5] = 255;
        green_botoes_pallet[5] = 0;
        blue_botoes_pallet[5] = 0;
        //cor de terra
        red_botoes_pallet[6] = 255;
        green_botoes_pallet[6] = 128;
        blue_botoes_pallet[6] = 0;
        //marrom
        red_botoes_pallet[7] = 102;
        green_botoes_pallet[7] = 51;
        blue_botoes_pallet[7] = 0;
        //amarelo
        red_botoes_pallet[8] = 255;
        green_botoes_pallet[8] = 255;
        blue_botoes_pallet[8] = 51;
        //verde claro
        red_botoes_pallet[9] = 150;
        green_botoes_pallet[9] = 255;
        blue_botoes_pallet[9] = 150;
        //verde escuro
        red_botoes_pallet[10] = 0;
        green_botoes_pallet[10] = 255;
        blue_botoes_pallet[10] = 0;
        //azul claro
        red_botoes_pallet[11] = 100;
        green_botoes_pallet[11] = 100;
        blue_botoes_pallet[11] = 255;
        //azul médio
        red_botoes_pallet[12] = 50;
        green_botoes_pallet[12] = 50;
        blue_botoes_pallet[12] = 255;
        //azul escuro
        red_botoes_pallet[13] = 0;
        green_botoes_pallet[13] = 0;
        blue_botoes_pallet[13] = 255;
        //magenta
        red_botoes_pallet[14] = 255;
        green_botoes_pallet[14] = 0;
        blue_botoes_pallet[14] = 127;
        //roxo
        red_botoes_pallet[15] = 102;
        green_botoes_pallet[15] = 0;
        blue_botoes_pallet[15] = 204;
            
        //Configurando a caixa de diálogo (pick_color) onde a cor será escolhida
        
        botao_ok.addMouseListener(acao_ok_button);
        //   pick_color.setLayout(new FlowLayout());
        pick_color.setSize(550, 300);
        pick_color.setResizable(false);
        pick_color.setLayout(new GridLayout(5, 1, 5, 5));
        pick_color.add(pickcolor_message);
        pick_color.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pick_color.add(textfield2);
        pick_color.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pick_color.add(textfield3);
        pick_color.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pick_color.add(textfield4);
        pick_color.add(botao_ok);
       // pick_color.setVisible(true);

        //Configurando a paleta de cores (color_pallet) onde a cor será escolhida
       
        //Configurando os botoes de cores
        
        collor_pallet_panel.setLayout(new BorderLayout(20, 0));//Panel para por na janela inteira
        collor_pallet_panel.add(title_pallet);
        color_pallet.setSize(500, 160); //Janela em si
        color_pallet.setResizable(false);
        color_pallet.add(collor_pallet_panel, BorderLayout.PAGE_START);
        collor_pallet_buttons_panel.setLayout(new GridLayout(2, 8, 5, 5));//Panel de botões
        for (int i = 0; i < 16; i++) {
         
      
            botoes_pallet[i] = new JButton();
            botoes_pallet[i].addMouseListener(acao_pallet_button);
            botoes_pallet[i].setPreferredSize(new Dimension(6, 6));
            botoes_pallet[i].setBackground(new java.awt.Color(red_botoes_pallet[i],green_botoes_pallet[i],blue_botoes_pallet[i]));
            botoes_pallet[i].setForeground(new java.awt.Color(230, 230, 230));
            botoes_pallet[i].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 51, 51), null, null));

            collor_pallet_buttons_panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            collor_pallet_buttons_panel.add(botoes_pallet[i]);    
        }
        color_pallet.add(collor_pallet_buttons_panel, BorderLayout.CENTER);
        color_pallet.setVisible(true);
        
        container = getContentPane();

        setLayout(new BorderLayout(20, 0));
        //CONFIGURA OPÇÕES DO RGB
        rgb_options.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        rgb_options.setBackground(new java.awt.Color(102, 0, 0));
      //  rgb_options.setVisible(true);
        javax.swing.GroupLayout quadroComBotoesLayout = new javax.swing.GroupLayout(rgb_options.getContentPane());
        rgb_options.getContentPane().setLayout(quadroComBotoesLayout);


        add(rgb_options, BorderLayout.PAGE_START);

        //CONFIGURA MATRIZ ONDE OS PIXELS FICARAO
        matriz = new JPanel();
        matriz.setSize(500, 500);
        matriz.setLayout(new GridLayout(40, 40, 2, 2));
        matriz.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        matriz.setBackground(new java.awt.Color(200,200,200));

        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
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

    public static void main(String[] args) {
        new Main().setVisible(true);
    }

     class MouseHandlerPallet extends MouseAdapter {

        @Override

        public void mouseClicked(MouseEvent event) {

            if (event.isMetaDown() == false) //se for com o botao esquerdo
            {

                
                    for (int i = 0; i < 16; i++) {
                        
                        if (event.getSource() == botoes_pallet[i]) {
                            r_int = red_botoes_pallet[i];
                            g_int = green_botoes_pallet[i];
                            b_int = blue_botoes_pallet[i];
                        }
                        
                    }
                   
                
            }

        }
     }
    
    class MouseHandler extends MouseAdapter {

        @Override

        public void mouseClicked(MouseEvent event) {

            if (event.isMetaDown() == false) //se for com o botao esquerdo
            {

                
                    for (int i = 0; i < 40; i++) {
                        for (int j = 0; j < 40; j++) {
                            if (event.getSource() == botoes[i][j]) {
                                linha = i;
                                coluna = j;
                            }
                        }
                    }
                    //seta o botão com a cor selecionada aqui
                    botoes[linha][coluna].setBackground(new java.awt.Color(r_int, g_int, b_int));
                
            }

        }

    }
    
    
    //CLASSE PARA LIDAR COM OS EVENTOS DO MOUSE DO BOTAO OK DA JANELA DE ESCOLHA DO RGB
     class MouseHandlerRGB extends MouseAdapter {

        @Override

        public void mouseClicked(MouseEvent event) {
            if (event.isMetaDown() == false) //se for com o botao esquerdo
            {
               r_string = textfield2.getText();
               g_string = textfield3.getText();
               b_string = textfield4.getText();
               r_int = Integer.parseInt(r_string);       
               g_int = Integer.parseInt(g_string);
               b_int = Integer.parseInt(b_string);
            }
        }
    }

    
}
