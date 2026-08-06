import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PainelTabuleiro extends JPanel {

    private CasaButton[][] botoesTabuleiro = new CasaButton[8][8];

    // Constantes dos símbolos Unicode de xadrez
    private static final String REI_BRANCO = "/assets/rei_branco.png";   // ♔
    private static final String RAINHA_BRANCA = "/assets/rainha_branca.png";  // ♕
    private static final String TORRE_BRANCA = "/assets/torre_branca.png"; // ♖
    private static final String BISPO_BRANCO = "/assets/bispo_branco.png"; // ♗
    private static final String CAVALO_BRANCO = "/assets/cavalo_branco.png";// ♘
    private static final String PEAO_BRANCO = "/assets/peao_branco.png";  // ♙

    private static final String REI_PRETO = "/assets/rei_preto.png";   // ♚
    private static final String RAINHA_PRETA = "/assets/rainha_preta.png";  // ♛
    private static final String TORRE_PRETA = "/assets/torre_preta.png"; // ♜
    private static final String BISPO_PRETO = "/assets/bispo_preto.png"; // ♝
    private static final String CAVALO_PRETO = "/assets/cavalo_preto.png";// ♞
    private static final String PEAO_PRETO = "/assets/peao_preto.png";  // ♟

    public PainelTabuleiro() {
        setLayout(new BorderLayout());

        // Grade Central 8x8
        JPanel gridCenter = new JPanel(new GridLayout(8, 8));

        for (int l = 0; l < 8; l++) {
            for (int c = 0; c < 8; c++) {
                CasaButton casa = new CasaButton(l, c);
                botoesTabuleiro[l][c] = casa;
                gridCenter.add(casa);
            }
        }

        // Painéis laterais e superiores com coordenadas
        JPanel painelColunasTop = new JPanel(new GridLayout(1, 8));
        JPanel painelColunasBottom = new JPanel(new GridLayout(1, 8));
        JPanel painelLinhasLeft = new JPanel(new GridLayout(8, 1));
        JPanel painelLinhasRight = new JPanel(new GridLayout(8, 1));

        char[] colunas = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        for (int c = 0; c < 8; c++) {
            painelColunasTop.add(new JLabel(String.valueOf(colunas[c]), SwingConstants.CENTER));
            painelColunasBottom.add(new JLabel(String.valueOf(colunas[c]), SwingConstants.CENTER));
        }

        for (int l = 0; l < 8; l++) {
            int fileira = 8 - l;
            painelLinhasLeft.add(new JLabel(" " + fileira + " ", SwingConstants.CENTER));
            painelLinhasRight.add(new JLabel(" " + fileira + " ", SwingConstants.CENTER));
        }

        add(painelColunasTop, BorderLayout.NORTH);
        add(painelColunasBottom, BorderLayout.SOUTH);
        add(painelLinhasLeft, BorderLayout.WEST);
        add(painelLinhasRight, BorderLayout.EAST);
        add(gridCenter, BorderLayout.CENTER);

        configurarPecasIniciais();
    }

    private ImageIcon carregarIcone(String caminho) {
        URL imgURL = getClass().getResource(caminho);
        if (imgURL != null) {
            ImageIcon iconOriginal = new ImageIcon(imgURL);
            // Redimensiona a imagem para ficar proporcional ao botão de 70x70
            Image imagemRedimensionada = iconOriginal.getImage().getScaledInstance(55, 55, Image.SCALE_SMOOTH);
            return new ImageIcon(imagemRedimensionada);
        } else {
            System.err.println("Imagem não encontrada no caminho: " + caminho);
            return null;
        }
    }

    public void configurarPecasIniciais() {
        // Carregando os ícones redimensionados das peças
        ImageIcon tPreta  = carregarIcone(TORRE_PRETA);
        ImageIcon cPreto  = carregarIcone(CAVALO_PRETO);
        ImageIcon bPreto  = carregarIcone(BISPO_PRETO);
        ImageIcon dPreta  = carregarIcone(RAINHA_PRETA);
        ImageIcon rPreto  = carregarIcone(REI_PRETO);
        ImageIcon pPreto  = carregarIcone(PEAO_PRETO);

        ImageIcon tBranca = carregarIcone(TORRE_BRANCA);
        ImageIcon cBranco = carregarIcone(CAVALO_BRANCO);
        ImageIcon bBranco = carregarIcone(BISPO_BRANCO);
        ImageIcon dBranca = carregarIcone(RAINHA_BRANCA);
        ImageIcon rBranco = carregarIcone(REI_BRANCO);
        ImageIcon pBranco = carregarIcone(PEAO_BRANCO);

        // Linha 1 - Peças Pretas
        botoesTabuleiro[0][0].setIcon(tPreta);
        botoesTabuleiro[0][1].setIcon(cPreto);
        botoesTabuleiro[0][2].setIcon(bPreto);
        botoesTabuleiro[0][3].setIcon(dPreta);
        botoesTabuleiro[0][4].setIcon(rPreto);
        botoesTabuleiro[0][5].setIcon(bPreto);
        botoesTabuleiro[0][6].setIcon(cPreto);
        botoesTabuleiro[0][7].setIcon(tPreta);

        // Linha 2 - Peões Pretos PR
        for (int c = 0; c < 8; c++) {
            botoesTabuleiro[1][c].setIcon(pPreto);
        }

        // Linhas 3 a 6 (Casas vazias)
        for (int l = 2; l <= 5; l++) {
            for (int c = 0; c < 8; c++) {
                botoesTabuleiro[l][c].setIcon(null);
                botoesTabuleiro[l][c].setText("");
            }
        }

        // Linha 7 - Peões Brancos BR
        for (int c = 0; c < 8; c++) {
            botoesTabuleiro[6][c].setIcon(pBranco);
        }

        // Linha 8 - Peças Brancas BR
        botoesTabuleiro[7][0].setIcon(tBranca);
        botoesTabuleiro[7][1].setIcon(cBranco);
        botoesTabuleiro[7][2].setIcon(bBranco);
        botoesTabuleiro[7][3].setIcon(dBranca);
        botoesTabuleiro[7][4].setIcon(rBranco);
        botoesTabuleiro[7][5].setIcon(bBranco);
        botoesTabuleiro[7][6].setIcon(cBranco);
        botoesTabuleiro[7][7].setIcon(tBranca);
    }

    public CasaButton[][] getBotoesTabuleiro() {
        return botoesTabuleiro;
    }
}