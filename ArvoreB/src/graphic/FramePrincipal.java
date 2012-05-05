/*
 * Bruno Villas Boas da Costa RA: 317527
 * Julio Macumoto             RA: 344915
 * Wagner Takeshi Obara       RA: 317365
 */
package graphic;

import arvore.ArvoreB;
import arvore.No;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FramePrincipal extends JApplet implements ActionListener {

    private ArvoreB mytree;          //arvore binaria
    private JButton btInsert;           //botao para insercao de chaves na arvore
    private JButton btBuscar;
    private JLabel lbOrdem;
    private JButton btRemover;
    private JTextField tfInsert;        //caixa de texto onde o usuario digitarah
    //  a chave a ser inserida
    private PanelArvoreB pArvoreB;   //painel de visualizacao da arvore
    private JMenuBar menu;              //cria a barra de op��es (menu)

    /*
     * Esta classe representa a janela principal da aplicacao.
     */
    public void init() {
        FramePrincipal();
    }

    public void FramePrincipal() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int alturaTela = d.height;
	int comprimentoTela = d.width;

        configureMenu();
        this.setLayout(new BorderLayout());

        //Cria o botao com rotulo "Insere"
        btInsert = new JButton("Insere");
        btBuscar = new JButton("Buscar");
        btRemover = new JButton("Remover");

        //Cria uma area de texto de tamanho 10
        tfInsert = new JTextField(10);

        //Cria um painel para se colocado na janela
        JPanel panel = new JPanel();


        String s = JOptionPane.showInputDialog(this, "Digite a ordem:", "Ordem", JOptionPane.QUESTION_MESSAGE);
        int n = Integer.parseInt(s);

        //Adiciona o botao e a caixa de texto para
        // a janela


        lbOrdem = new JLabel("Ordem: " + s + "        ");
        panel.add(lbOrdem);
        panel.add(tfInsert);
        panel.add(btInsert);
        panel.add(btBuscar);
        panel.add(btRemover);

        //Cria a arvore b
        mytree = new ArvoreB(n);

        //Cria o painel para a visualizacao da
        // arvore b
        pArvoreB = new PanelArvoreB(mytree);
        //pArvoreB.setPosIni(comprimentoTela/2);

        //Cria uma barra de rolagem
        JScrollPane jsp = new JScrollPane();

        //Adiciona a barra de rolagem na janela
        this.getContentPane().add(jsp);

        //Adiciona o painel com o botao e a caixa
        //  de texto para a area sul (SOUTH) da
        //  janela (conforme o layout de borda)
        this.add(panel, BorderLayout.SOUTH);

        //Associa a barra de rolagem com o painel
        //  de visualizacao da arvore
        jsp.setViewportView(pArvoreB);

        //Associa o click do botao com sua
        //  classe ouvinte. Neste caso, a
        //  propria classe FrameBinaryTree
        btInsert.addActionListener(this);
        btBuscar.addActionListener(this);
        btRemover.addActionListener(this);

    }


    /*
     * Configura o menu
     */
    public void configureMenu() {
        JMenu menu;             //variavel para o armazenamento do menu
        JMenuBar menuBar;       //variavel para o armazenamento da barra de menus
        JMenuItem item;         //variavel para o armazenamento os itens de cada menu

        //Instancia a barra de menus, nas quais serao colocados os
        //  menus
        menuBar = new JMenuBar();
        //Cria o menu 'Arquivo' e o adiciona � barra de menus
        menu = new JMenu("Arquivo");
        menuBar.add(menu);

        item = new JMenuItem("Mudar ordem");

        //Indica o gerenciador de eventos para este menu e o inclui
        //  no menu 'Arquivo'.
        item.setActionCommand("Mudar ordem");
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("Limpar Arvore-B");

        //Indica o gerenciador de eventos para este menu e o inclui
        //  no menu 'Arquivo'.
        item.setActionCommand("Limpar Arvore-B");
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("Inserir Aleatorio");

        //Indica o gerenciador de eventos para este menu e o inclui
        //  no menu 'Arquivo'.
        item.setActionCommand("Inserir Aleatorio");
        item.addActionListener(this);
        menu.add(item);

        //Para o menu 'Arquivo', cria o item 'Fechar'.
        item = new JMenuItem("Fechar");

        //Indica o gerenciador de eventos para este menu e o inclui
        //  no menu 'Arquivo'.
        item.setActionCommand("Fechar");
        item.addActionListener(this);
        menu.add(item);


        //Cria o menu 'Ajuda' e o adiciona � barra de menus
        menu = new JMenu("Ajuda");
        menuBar.add(menu);

        //Para o menu 'Ajuda', cria os itens 'Como usar...' e 'Sobre'.
        //  Em seguida, indica as classes ouvintes e adiciona os itens
        //  no menu.
        item = new JMenuItem("Como usar...");
        item.setActionCommand("Como usar...");
        item.addActionListener(this);
        menu.add(item);

        item = new JMenuItem("Sobre");
        item.setActionCommand("Sobre");
        item.addActionListener(this);
        menu.add(item);


        //Adiciona a barra de menus na janela principal da aplica��o.
        this.setJMenuBar(menuBar);
    }

    //Este metodo deve ser implementado por todas
    //   as classes ouvintes de eventos de acao
    //   (como o ato de pressionar um botao)
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btInsert) {
            int k = Integer.parseInt(tfInsert.getText());
            if (k >= 0 && k < 100) {
                mytree.insere(k);
                pArvoreB.setBusca(null);
                pArvoreB.setKeyBusca(0);
            } else {
                JOptionPane.showMessageDialog(this, "Digite uma chave entre 0 e 99!!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
            tfInsert.setText("");
            tfInsert.requestFocus();
        }
        
        if (e.getSource() == btBuscar) {
            int chave = Integer.parseInt(tfInsert.getText());
            if (!pArvoreB.busca(chave)) {
                JOptionPane.showMessageDialog(this, "A chave não existe!!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }

            tfInsert.setText("");
            tfInsert.requestFocus();


        }

        if (e.getSource() == btRemover) {
            int chave = Integer.parseInt(tfInsert.getText());
            mytree.Remove(chave);
            tfInsert.setText("");
            tfInsert.requestFocus();
            pArvoreB.setBusca(null);
            pArvoreB.setKeyBusca(0);
        }

        if (e.getActionCommand().equals("Mudar ordem")) {
            String s = JOptionPane.showInputDialog(this, "Digite a ordem:", "Ordem", JOptionPane.QUESTION_MESSAGE);
            int n = Integer.parseInt(s);
            mytree.LimparArvore(mytree.getRaiz(), n);
            mytree.setOrdem(n);
            lbOrdem.setText("Ordem: " + s + "        ");
            pArvoreB.setBusca(null);
            pArvoreB.setKeyBusca(0);
        }
        if (e.getActionCommand().equals("Limpar Arvore-B")) {
            if (JOptionPane.showConfirmDialog(this, "Deseja excluir todos os elementos da árvore?", "Alerta", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                mytree.LimparArvore(mytree.getRaiz(), mytree.getOrdem());
                pArvoreB.setBusca(null);
                pArvoreB.setKeyBusca(0);
            }
        }

        if (e.getActionCommand().equals("Inserir Aleatorio")) {
            String s = JOptionPane.showInputDialog(this, "Digite a Quantidade de chaves (0-" + Integer.toString(100 - mytree.getnElementos()) + "):", "Aleatorio", JOptionPane.QUESTION_MESSAGE);
            int n = Integer.parseInt(s);
            if (n > 100 - mytree.getnElementos()) {
                JOptionPane.showMessageDialog(this, "Digite um número de 0 a " + Integer.toString(100 - mytree.getnElementos()), "Erro", JOptionPane.ERROR_MESSAGE);

            } else {
                for (int i = 0; i < n; i++) {
                    int c = (int) (Math.random() * 100);
                    if (mytree.BuscaChave(mytree.getRaiz(), c) == null) {
                        mytree.insere(c);
                    } else {
                        i--;
                    }
                }
            }

            pArvoreB.setBusca(null);
            pArvoreB.setKeyBusca(0);

        }

        if (e.getActionCommand().equals("Fechar")) {
            System.exit(0);
        } //Para os outros casos (abaixo), somente abra uma caixa de di�logo
        //  com instru��es para o uso do sistema por parte do usu�rio.
        else if (e.getActionCommand().equals("Sobre")) {
            JOptionPane.showMessageDialog(this,
                    "      ----Trabalho 1 - Estrutura de Dados 2----      \n\n"
                    + "Alunos:\n  Bruno Villas Boas da Costa RA: 317527\n"
                    + "  Julio Macumoto                      RA: 344915\n"
                    + "  Wagner Takeshi Obara         RA: 317365. \n\n\n"
                    + "                          06/10/2010",
                    "Sobre",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getActionCommand().equals("Como usar...")) {
            JOptionPane.showMessageDialog(this,
                    "Este programa eh um exemplo de aplicacao visual\n"
                    + "utilizando a linguagem de programacao Java. Nesta\n"
                    + "aplicacao, o usuario pode incluir valores em uma\n"
                    + "Arvore B.\n"
                    + "\tPara utilizar este programa, basta digitar um \n"
                    + "numero inteiro na caixa de texto na parte de\n"
                    + "baixo da tela e aperte o botao \"Insere\".\n",
                    "Como utilizar este programa.",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        pArvoreB.revalidate();
        pArvoreB.repaint();
    }
}
