import javax.swing.*;
import java.awt.*;

public class CasaButton extends JButton {
    private final int linha;
    private final int coluna;

    public CasaButton(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;

        // Estilização de cada casa
        setPreferredSize(new Dimension(70, 70));
        setMargin(new Insets(0, 0, 0, 0)); // Ajusta as bordas da casa
        setFocusPainted(false);
        setContentAreaFilled(true);

        // Alternância de cores claras e escuras
        Color corClara = new Color(240, 236, 210);
        Color corEscura = new Color(118, 150, 86);

        if ((linha + coluna) % 2 == 0) {
            setBackground(corClara);
        } else {
            setBackground(corEscura);
        }
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }
}