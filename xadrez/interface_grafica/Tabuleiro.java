package xadrez.interface_grafica;
import javax.swing.*;

public class Tabuleiro extends JFrame {
    JPanel tabuleiro = new Imagens("xadrez/assets/tabuleiro/png/tabuleiro.png");

    public Tabuleiro() {
        add(tabuleiro);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(1000, 1000);
    }    
}
