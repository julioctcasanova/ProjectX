import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipal extends JFrame {

    private PainelTabuleiro painelTabuleiro;
    private JPanel painelSuperior;
    private JPanel painelInferior;

    private JLabel labelTurno;
    private JLabel labelStatus;
    private JTextField campoLog;

    private JButton botaoDesistir;

    public JanelaPrincipal() {
        super("Jogo de Xadrez - POO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // 1. Painel Superior (Turno)
        painelSuperior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        labelTurno = new JLabel("Turno: Brancas (BR)");
        labelTurno.setFont(new Font("Arial", Font.BOLD, 14));

        painelSuperior.add(labelTurno);

        // 2. Painel Central (Tabuleiro)
        painelTabuleiro = new PainelTabuleiro();

        // 3. Painel Inferior (Status, Log e Desistência)
        painelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        labelStatus = new JLabel("Status: Aguardando jogada...");
        campoLog = new JTextField("Início da partida", 20);
        campoLog.setEditable(false);

        botaoDesistir = new JButton("Desistir");
        botaoDesistir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(
                        JanelaPrincipal.this,
                        "O jogador atual desistiu da partida!",
                        "Desistência",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        painelInferior.add(labelStatus);
        painelInferior.add(campoLog);
        painelInferior.add(botaoDesistir);

        // Montando a janela principal
        add(painelSuperior, BorderLayout.NORTH);
        add(painelTabuleiro, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JanelaPrincipal janela = new JanelaPrincipal();
                janela.setVisible(true);
            }
        });
    }
}