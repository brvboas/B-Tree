/*
 * Bruno Villas Boas da Costa RA: 317527
 * Julio Macumoto             RA: 344915
 * Wagner Takeshi Obara       RA: 317365
 */
package graphic;

import arvore.ArvoreB;
import arvore.No;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class PanelArvoreB extends JPanel {

    Dimension ScreenSize;
    ArvoreB arvore;
    final int lNumero = 20;
    final int lEntreChaves = 5;
    final int hNo = 20;
    No busca;
    int keyBusca;
    

    public PanelArvoreB(ArvoreB bt) {
        super();
        ScreenSize = new Dimension(600, 600);
        arvore = bt;
        
    }



    public void setBusca(No busca) {
        this.busca = busca;
    }

    public void setKeyBusca(int keyBusca) {
        this.keyBusca = keyBusca;
    }




    public boolean busca(int k){
        busca = arvore.BuscaChave(arvore.getRaiz(), k);
        if (busca!=null){
            keyBusca=k;
            return true;
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        paintComponent(g);
        if (arvore.getRaiz().getN() != 0) {
            g.setFont(new Font("Verdana", Font.PLAIN, 12));
            arvore.getRaiz().updateCoordenates(null, 0 );
            //arvore.getRaiz().updateCoordenates(null, this.getSize().width/2-(arvore.getRaiz().getN()*(lEntreChaves+lNumero))/2 );
            drawNode(arvore.getRaiz(), g);
            updateCanvas();
        }
    }

    private void drawNode(No no, Graphics g) {

        g.drawRoundRect(no.getX(), no.getY(), no.computeSize(), hNo, 25, 25);
        if (no==busca){
            int pos=0;
            for(int i=0 ; i < no.getN() ; i++){
                if(no.getChave().get(i)==keyBusca)
                    pos=i;
            }
            if (pos==0)
                g.drawOval(no.getX(), no.getY(), 25, 25);
            else
                g.drawOval(no.getX()+pos*(lEntreChaves+lNumero), no.getY(), 25, 25);


        }
        for (int i = 0; i < no.getN(); i++) {
            int atual = no.getChave().get(i);
            int x = no.getX() + i * (lNumero + lEntreChaves)
                    + 5;
            g.drawString(Integer.toString(atual), x, no.getY() + 15);
        }
        if (!no.isFolha()) {
            for (int i = 0; i < no.getN() + 1; i++) {
                No filho = no.getFilho().get(i);
                g.drawLine(no.getX() + (i * 25) + 3, no.getY() + 7
                        * hNo / 8, filho.getX()
                        + (filho.computeSize() / 2), filho.getY());
                drawNode(filho, g);
            }
        }
        //busca=null;
        //keyBusca=0;
    }

    public void updateCanvas() {
        int size = arvore.getRaiz().UpdateLFilho();

        setPreferredSize(new Dimension(size, 400));
        revalidate();
    }
}
