package xadrez.interface_grafica;
import java.awt.*;
import javax.swing.*;

public class Imagens extends JPanel {
	private Image imagem;
	
	public Imagens(String caminhoDoArquivo) {
		imagem = new ImageIcon(caminhoDoArquivo).getImage();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
	}
}

