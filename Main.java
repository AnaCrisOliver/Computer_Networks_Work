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

    private JButton botaoEsquerdo;  //BOTÃO PARA CONFIGURAR O ALINHAMENTO À ESQUERDA
    private JButton botaoDireito;   //BOTÃO PARA CONFIGURAR O ALINHAMENTO À DIREITA
    private JButton botaoCentral;
    private Container container;
    private JPanel matriz;
    private JPanel RGB_options;
    public int coluna;
    public int linha;
    JLabel pickcolor_message = new JLabel("Olá! Escolha a cor que deseja usar para pintar o quadrado: ");
    MouseHandler acaoDoMouse = new MouseHandler();
    MouseHandlerRGB acao_ok_button = new MouseHandlerRGB();
    JButton[][] botoes = new JButton[20][20];
    JButton botao_ok = new JButton("OK");
    
    JInternalFrame rgb_options = new JInternalFrame();
    JFrame pick_color = new JFrame();
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
        pick_color.setVisible(true);

        container = getContentPane();

        setLayout(new BorderLayout(20, 0));
        //CONFIGURA OPÇÕES DO RGB
        rgb_options.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        rgb_options.setBackground(new java.awt.Color(102, 0, 0));
        rgb_options.setVisible(true);
        javax.swing.GroupLayout quadroComBotoesLayout = new javax.swing.GroupLayout(rgb_options.getContentPane());
        rgb_options.getContentPane().setLayout(quadroComBotoesLayout);


        add(rgb_options, BorderLayout.PAGE_START);

        //CONFIGURA MATRIZ
        matriz = new JPanel();
        matriz.setSize(500, 400);
        matriz.setLayout(new GridLayout(20, 20, 5, 5));
        matriz.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        matriz.setBackground(new java.awt.Color(0, 0, 0));

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                final int curRow = i;
                final int curCol = j;
                botoes[i][j] = new JButton();
                botoes[i][j].setPreferredSize(new Dimension(8, 8));
                botoes[i][j].addMouseListener(acaoDoMouse);
                botoes[i][j].setBackground(new java.awt.Color(255, 255, 255));
                botoes[i][j].setForeground(new java.awt.Color(230, 230, 230));
                //    botoes[i][j].addKeyListener(enter);
                botoes[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 51, 51), null, null));
                botoes[i][j].addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        switch (e.getKeyCode()) {
                            case KeyEvent.VK_UP:
                                if (curRow > 0) {
                                    botoes[curRow - 1][curCol].requestFocus();
                                    botoes[curRow - 1][curCol].setBorderPainted(true);
                                    botoes[curRow - 1][curCol].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
                                    botoes[curRow][curCol].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 51, 51), null, null));
                                }

                                break;
                            case KeyEvent.VK_DOWN:
                                if (curRow < botoes.length - 1) {
                                    botoes[curRow + 1][curCol].requestFocus();
                                    botoes[curRow + 1][curCol].setBorderPainted(true);
                                    botoes[curRow + 1][curCol].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
                                    botoes[curRow][curCol].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 51, 51), null, null));
                                }
                                break;
                            case KeyEvent.VK_LEFT:
                                if (curCol > 0) {
                                    botoes[curRow][curCol - 1].requestFocus();
                                    botoes[curRow][curCol - 1].setBorderPainted(true);
                                    botoes[curRow][curCol - 1].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
                                    botoes[curRow][curCol].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 51, 51), null, null));
                                }
                                break;
                            case KeyEvent.VK_RIGHT:
                                if (curCol < botoes[curRow].length - 1) {
                                    botoes[curRow][curCol + 1].requestFocus();
                                    botoes[curRow][curCol + 1].setBorderPainted(true);
                                    botoes[curRow][curCol + 1].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null));
                                    botoes[curRow][curCol].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 51, 51), null, null));
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });

                matriz.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                matriz.add(botoes[i][j]);

            }
        }

        add(matriz, BorderLayout.CENTER);

    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }

    class MouseHandler extends MouseAdapter {

        @Override

        public void mouseClicked(MouseEvent event) {

            if (event.isMetaDown() == false) //se for com o botao esquerdo
            {

                if (event.getClickCount() < 2) //Procura o botao que foi clicado
                {
                    for (int i = 0; i < 20; i++) {
                        for (int j = 0; j < 20; j++) {
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
