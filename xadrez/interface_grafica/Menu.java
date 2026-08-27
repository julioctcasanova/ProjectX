package xadrez.interface_grafica;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Menu extends JFrame {
	protected static final int Y_OPCOES = 400, LARGURA_OPCOES = 200, ALTURA_OPCOES = 134;
	protected static final int Y_RETORNO = 10, LARGURA_RETORNO = 90, ALTURA_RETORNO = 40;
	protected static String mode;
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
			caminhoImagem = "xadrez/assets/menu/png/windows/play_button.png";
		} else if (os.contains("mac")) {
			caminhoImagem = "xadrez/assets/menu/png/mac/play_button_linux.png";
		} else if (os.contains("nux") || os.contains("nix")) {
			caminhoImagem = "xadrez/assets/menu/png/linux/play_button_linux.png";
		}
	}
	
	ImageIcon imagemModoDeJogo = new ImageIcon(caminhoImagem);
	ImageIcon imagemRobo = new ImageIcon("xadrez/assets/menu/png/vsia_button.png");
	ImageIcon imagemPlayer = new ImageIcon("xadrez/assets/menu/png/vsplayer_button.png");
	ImageIcon imagemCreditos = new ImageIcon("xadrez/assets/menu/png/credits_button.png");
	ImageIcon imagemBotFacil = new ImageIcon("xadrez/assets/menu/png/vsia_button.png");
	ImageIcon imagemBotDificil = new ImageIcon("xadrez/assets/menu/png/vsia_button.png");
	ImageIcon imagemVoltarCreditos = new ImageIcon("xadrez/assets/menu/png/return_button_short.png");
	ImageIcon imagemVoltarModoDeJogo = new ImageIcon("xadrez/assets/menu/png/return_button_short.png");
	ImageIcon imagemVoltarMenuBots = new ImageIcon("xadrez/assets/menu/png/return_button_short.png");

	JButton botaoModoDeJogo = new JButton(imagemModoDeJogo);
	JButton botaoCreditos = new JButton(imagemCreditos);
	JButton botaoRobo = new JButton(imagemRobo);
	JButton botaoPlayer = new JButton(imagemPlayer);
	JButton botaoBotFacil = new JButton(imagemBotFacil);
	JButton botaoBotDificil = new JButton(imagemBotDificil);
	JButton botaoVoltarCreditos = new JButton("voltar");
	JButton botaoVoltarModoDeJogo = new JButton("voltar");
	JButton botaoVoltarMenuBots = new JButton("voltar");

	Menu() {
		base.add(menuInicial, "menuInicial");
		base.add(menuModoDeJogo, "menuModoDeJogo");
		base.add(creditos, "creditos");
		base.add(menuBots, "menuBots");

		menuInicial.setLayout(null);
		menuModoDeJogo.setLayout(null);
		menuBots.setLayout(null);
		creditos.setLayout(null);

		botaoModoDeJogo.setBorderPainted(false);
		botaoModoDeJogo.setContentAreaFilled(false);
		botaoModoDeJogo.setFocusPainted(false);
		botaoModoDeJogo.setMargin(new Insets(0, 0, 0, 0));
		botaoModoDeJogo.setBounds(100, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);

		botaoCreditos.setBorderPainted(false);
		botaoCreditos.setContentAreaFilled(false);
		botaoCreditos.setFocusPainted(false);
		botaoCreditos.setMargin(new Insets(0, 0, 0, 0));
		botaoCreditos.setBounds(500, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);
		
		botaoPlayer.setBorderPainted(false);
		botaoPlayer.setContentAreaFilled(false);
		botaoPlayer.setFocusPainted(false);
		botaoPlayer.setMargin(new Insets(0, 0, 0, 0));
		botaoPlayer.setBounds(100, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);

		botaoRobo.setBorderPainted(false);
		botaoRobo.setContentAreaFilled(false);
		botaoRobo.setFocusPainted(false);
		botaoRobo.setMargin(new Insets(0, 0, 0, 0));
		botaoRobo.setBounds(500, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);

		botaoBotFacil.setBorderPainted(false);
		botaoBotFacil.setContentAreaFilled(false);
		botaoBotFacil.setFocusPainted(false);
		botaoBotFacil.setMargin(new Insets(0, 0, 0, 0));
		botaoBotFacil.setBounds(100, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);

		botaoBotDificil.setBorderPainted(false);
		botaoBotDificil.setContentAreaFilled(false);
		botaoBotDificil.setFocusPainted(false);
		botaoBotDificil.setMargin(new Insets(0, 0, 0, 0));
		botaoBotDificil.setBounds(500, Y_OPCOES, LARGURA_OPCOES, ALTURA_OPCOES);

		botaoVoltarCreditos.setBorderPainted(false);
		botaoVoltarCreditos.setContentAreaFilled(false);
		botaoVoltarCreditos.setFocusPainted(false);
		botaoVoltarCreditos.setMargin(new Insets(0, 0, 0, 0));
		botaoVoltarCreditos.setBounds(10, Y_RETORNO, LARGURA_RETORNO, ALTURA_RETORNO);

		botaoVoltarModoDeJogo.setBorderPainted(false);
		botaoVoltarModoDeJogo.setContentAreaFilled(false);
		botaoVoltarModoDeJogo.setFocusPainted(false);
		botaoVoltarModoDeJogo.setMargin(new Insets(0, 0, 0, 0));
		botaoVoltarModoDeJogo.setBounds(10, Y_RETORNO, LARGURA_RETORNO, ALTURA_RETORNO);

		botaoVoltarMenuBots.setBorderPainted(false);
		botaoVoltarMenuBots.setContentAreaFilled(false);
		botaoVoltarMenuBots.setFocusPainted(false);
		botaoVoltarMenuBots.setMargin(new Insets(0, 0, 0, 0));
		botaoVoltarMenuBots.setBounds(10, Y_RETORNO, LARGURA_RETORNO, ALTURA_RETORNO);

		menuInicial.add(botaoModoDeJogo);
		menuInicial.add(botaoCreditos);

		creditos.add(botaoVoltarCreditos);
		
		menuModoDeJogo.add(botaoRobo);
		menuModoDeJogo.add(botaoPlayer);
		menuModoDeJogo.add(botaoVoltarModoDeJogo);
		
		menuBots.add(botaoBotFacil);
		menuBots.add(botaoBotDificil);
		menuBots.add(botaoVoltarMenuBots);



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
}
