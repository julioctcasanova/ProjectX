package xadrez.pieces;

public class Rook extends Piece {
    public Rook(boolean isWhite) {
        // movement_loop = true porque a torre tem alcance dinâmico (desliza).
        super(isWhite, true);

        // 4 direções ortogonais (cima, baixo, esquerda, direita).
        for (int dx = -1; dx <= 1; dx++) {
            if (dx != 0) baseMovements.add(new Movement(dx, 0));
        }
        for (int dy = -1; dy <= 1; dy++) {
            if (dy != 0) baseMovements.add(new Movement(0, dy));
        }
    }
    @Override
    public String toString() {
        return "r";
    }

    @Override
    public char fenLetter() {
        return 'r'; 
    }
}
