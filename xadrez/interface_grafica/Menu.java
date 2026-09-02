package xadrez.interface_grafica;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class Menu extends JFrame {
	protected static final int Y_OPCOES = 400, LARGURA_OPCOES = 200, ALTURA_OPCOES = 134;
	protected static final int X_RETORNO = 10, Y_RETORNO = 10, LARGURA_RETORNO = 90, ALTURA_RETORNO = 40;
	public static String mode;
	public static final CountDownLatch modeSelected = new CountDownLatch(1);
	String caminhoImagem;

	CardLayout cardLayout = new CardLayout();
	JPanel base = new JPanel(cardLayout);

	JPanel menuInicial = new Imagens("xadrez/assets/menu/png/background.png");
	JPanel menuModoDeJogo = new Imagens("xadrez/assets/menu/png/background.png");
	JPanel menuBots = new Imagens("xadrez/assets/menu/png/background.png");
	JPanel creditos = new Imagens("xadrez/assets/menu/png/background.png");

	{
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			caminhoImagem = "xadrez/assets/menu/png/play_button.png";
		} else if (os.contains("mac")) {
			caminhoImagem = "xadrez/assets/menu/png/play_button_linux.png";
		} else if (os.contains("nux") || os.contains("nix")) {
			caminhoImagem = "xadrez/assets/menu/png/play_button_linux.png";
		}
	}
	
	ImageIcon imagemModoDeJogo = new ImageIcon(caminhoImagem);
	ImageIcon imagemRobo = new ImageIcon("xadrez/assets/menu/png/vsia_button.png");
	ImageIcon imagemPlayer = new ImageIcon("xadrez/assets/menu/png/vsplayer_button.png");
	ImageIcon imagemCreditos = new ImageIcon("xadrez/assets/menu/png/credits_button.png");
	ImageIcon imagemBotFacil = new ImageIcon("xadrez/assets/menu/png/easy_button.png");
	ImageIcon imagemBotDificil = new ImageIcon("xadrez/assets/menu/png/hard_button.png");
	ImageIcon imagemVoltarCreditos = new ImageIcon("xadrez/assets/menu/png/return_button_short.png");
	ImageIcon imagemVoltarModoDeJogo = new ImageIcon("xadrez/assets/menu/png/return_button_short.png");
	ImageIcon imagemVoltarMenuBots = new ImageIcon("xadrez/assets/menu/png/return_button_short.png");

	JButton botaoModoDeJogo = new JButton(imagemModoDeJogo);
	JButton botaoCreditos = new JButton(imagemCreditos);
	JButton botaoRobo = new JButton(imagemRobo);
	JButton botaoPlayer = new JButton(imagemPlayer);
	JButton botaoBotFacil = new JButton(imagemBotFacil);
	JButton botaoBotDificil = new JButton(imagemBotDificil);
	JButton botaoVoltarCreditos = new JButton(imagemVoltarCreditos);
	JButton botaoVoltarModoDeJogo = new JButton(imagemVoltarModoDeJogo);
	JButton botaoVoltarMenuBots = new JButton(imagemVoltarMenuBots);
	JButton botaoSair = new JButton("fechar");

	public Menu() {
		base.add(menuInicial, "menuInicial");
		base.add(menuModoDeJogo, "menuModoDeJogo");
		base.add(creditos, "creditos");
		base.add(menuBots, "menuBots");

		menuInicial.setLayout(null);
		menuModoDeJogo.setLayout(null);
		menuBots.setLayout(null);
		creditos.setLayout(null);

		starter(botaoModoDeJogo, 100, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);
		starter(botaoCreditos, 500, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);
		starter(botaoPlayer,100, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);
		starter(botaoRobo,500, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);
		starter(botaoBotFacil, 100, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);
		starter(botaoBotDificil, 500, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);
		starter(botaoVoltarCreditos, X_RETORNO, Y_RETORNO, LARGURA_RETORNO, ALTURA_RETORNO);
		starter(botaoVoltarModoDeJogo, X_RETORNO, Y_RETORNO, LARGURA_RETORNO, ALTURA_RETORNO);
		starter(botaoVoltarMenuBots, X_RETORNO, Y_RETORNO, LARGURA_RETORNO, ALTURA_RETORNO);
		starter(botaoSair, X_RETORNO, Y_RETORNO, LARGURA_RETORNO, ALTURA_RETORNO);


		menuInicial.add(botaoModoDeJogo);
		menuInicial.add(botaoCreditos);
		menuInicial.add(botaoSair);

		creditos.add(botaoVoltarCreditos);
		
		menuModoDeJogo.add(botaoRobo);
		menuModoDeJogo.add(botaoPlayer);
		menuModoDeJogo.add(botaoVoltarModoDeJogo);
		
		menuBots.add(botaoBotFacil);
		menuBots.add(botaoBotDificil);
		menuBots.add(botaoVoltarMenuBots);


		botaoSair.addActionListener((e) -> {
			dispose();
			System.exit(0);
		});
		
		botaoModoDeJogo.addActionListener((e) -> {
			cardLayout.show(base, "menuModoDeJogo");
		});

		botaoCreditos.addActionListener((e) -> {
			cardLayout.show(base, "creditos");
		});
		
		botaoPlayer.addActionListener((e) -> {
			mode = "1";
			modeSelected.countDown();
			dispose();
		});

		botaoRobo.addActionListener((e) -> {
			cardLayout.show(base, "menuBots");
		});

		botaoBotFacil.addActionListener((e) -> {
			mode = "2";
			modeSelected.countDown();
			dispose();
		});

		botaoBotDificil.addActionListener((e) -> {
			mode = "3";
			modeSelected.countDown();
			dispose();
		});

		botaoVoltarCreditos.addActionListener((e) -> {
			cardLayout.show(base, "menuInicial");
		});

		botaoVoltarModoDeJogo.addActionListener((e) -> {
			cardLayout.show(base, "menuInicial");
		});

		botaoVoltarMenuBots.addActionListener((e) -> {
			cardLayout.show(base, "menuModoDeJogo");
		});

		add(base);
		setSize(800, 800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	// Função só para inicializar os JButtons
	private void starter(JButton button, int posXInicial, int posYInicial, int larguraBotao, int alturaBotao) {
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setBounds(posXInicial, posYInicial, larguraBotao, alturaBotao);
	}
}
