package xadrez;
import xadrez.pieces.PromotionPiece;

public record Move(int fromRow, int fromCol, int toRow, int toCol, PromotionPiece promotionPiece) {

    // Construtor curto: a grande maioria dos lances não promove.
    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this(fromRow, fromCol, toRow, toCol, null);
    }

    // Records são imutáveis: "alterar" a promoção = criar um novo lance.
    public Move withPromotion(PromotionPiece piece) {
        return new Move(fromRow, fromCol, toRow, toCol, piece);
    }

    // Converte "e2 e4" em um Move. Devolve null se a entrada estiver malformada.
    // Valida apenas a FORMA (duas casas dentro do tabuleiro), nunca a legalidade.
    public static Move parse(String input) {
        if (input == null) {
            return null;
        }

        String[] parts = input.trim().toLowerCase().split("\\s+");
        if (parts.length != 2 || parts[0].length() != 2 || parts[1].length() != 2) {
            return null;
        }

        int fromRow = rowOf(parts[0]);
        int fromCol = colOf(parts[0]);
        int toRow   = rowOf(parts[1]);
        int toCol   = colOf(parts[1]);

        if (!inBoard(fromRow) || !inBoard(fromCol) || !inBoard(toRow) || !inBoard(toCol)) {
            return null;
        }
        return new Move(fromRow, fromCol, toRow, toCol);
    }

    // "e2" -> linha 6, pois a linha 8 do xadrez é o índice 0 da matriz.
    private static int rowOf(String square) { return '8' - square.charAt(1); }

    // "e2" -> coluna 4
    private static int colOf(String square) { return square.charAt(0) - 'a'; }

    private static boolean inBoard(int index) { return index >= 0 && index <= 7; }

    // Mesmo formato que o jogador digita: "e2 e4".
    @Override
    public String toString() {
        return squareName(fromRow, fromCol) + " " + squareName(toRow, toCol);
    }

    // "linha 6, coluna 4" -> "e2". Público porque o FEN precisa da mesma conversão,
    // e essa notação deve morar num lugar só.
    public static String squareName(int row, int col) {
        return "" + (char) ('a' + col) + (char) ('8' - row);
    }

}
