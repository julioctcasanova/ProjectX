package xadrez;
import xadrez.pieces.*;
import java.util.ArrayList;
import java.util.List;

// Legalidade da jogada, roque, xeque, xeque-mate e afogamento.
public class GameRules {
    private final Board board;
    private final MoveValidator moveValidator = new MoveValidator();

    // Casa "pulada" pelo último avanço duplo de peão — o alvo de uma captura en passant.
    // -1 quando o lance anterior não foi avanço duplo. Mesma convenção do FEN.
    private int enPassantRow = -1;
    private int enPassantCol = -1;

    public GameRules(Board board) {
        this.board = board;
    }

    // Tenta mover a peça da posição [fromRow][fromCol] para a posição [toRow][toCol].
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol, boolean whiteTurn) {
        return movePiece(fromRow, fromCol, toRow, toCol, whiteTurn, null);
    }

    // Tenta mover a peça da posição [fromRow][fromCol] para a posição [toRow][toCol].
    // Retorna true se o movimento for bem-sucedido, false caso contrário.
    public boolean movePiece(int fromRow, int fromCol, int toRow, int toCol, boolean whiteTurn, PromotionPiece promotionChoice) {
        Piece[][] grid = board.getBoard();
        // Verifica se as posições são válidas e se há uma peça na posição de origem
        if(board.ValidPosition(fromRow, fromCol) && board.ValidPosition(toRow, toCol) && grid[fromRow][fromCol] != null) {
            Piece piece = grid[fromRow][fromCol];

            if (piece.isWhite() != whiteTurn) {
                return false;
            }

            // Roque: rei que nunca moveu tentando fazer roque... chama a função de roque (tryCastle())
            if (piece instanceof King && !piece.isMoved() && fromRow == toRow && Math.abs(toCol - fromCol) == 2) {
                return tryCastle(fromRow, fromCol, toCol);
            }

            // En passant: peão indo na diagonal para a casa-alvo, que está vazia.
            // Precisa vir antes do canMoveTo, que rejeitaria (diagonal do peão é
            // CAPTURE_ONLY e o destino está vazio).
            if (piece instanceof Pawn && isEnPassantTarget(toRow, toCol)
                    && Math.abs(toCol - fromCol) == 1 && grid[toRow][toCol] == null) {
                return tryEnPassant(fromRow, fromCol, toRow, toCol, whiteTurn);
            }

            // Verifica se o movimento é válido para a peça
            if (moveValidator.canMoveTo(piece, grid, new int[]{fromRow, fromCol}, new int[]{toRow, toCol})) {
                // Rejeita jogada que deixaria o próprio rei em xeque
                if (moveLeavesKingInCheck(fromRow, fromCol, toRow, toCol, whiteTurn)) {
                    return false;
                }
                grid[toRow][toCol] = piece;
                grid[fromRow][fromCol] = null;
                piece.setMoved();

                // Se a peça movida for um peão e alcançar a linha de promoção, realiza a promoção
                if (piece instanceof Pawn) {
                    Pawn pawn = (Pawn) piece;
                    if (pawn.canPromote(toRow)) {
                        PromotionPiece type = (promotionChoice != null) ? promotionChoice : PromotionPiece.QUEEN; // fallback para rainha, mas add um loop na main para evitar de ter entradas inválidas
                        grid[toRow][toCol] = pawn.promoteTo(type);
                    }
                }
                updateEnPassantTarget(piece, fromRow, fromCol, toRow);
                return true;
            }
        }
        // Se qualquer verificação falhar, o movimento é inválido
        return false;
    }

    // Tenta executar o roque do rei...
    // Só é chamado quando movePiece já confirmou: peça é rei, nunca moveu.
    private boolean tryCastle(int row, int kingCol, int destinyCol) { // 'destiny' -> easter egg
        Piece[][] grid = board.getBoard();
        Piece king = grid[row][kingCol];

        // Lado do roque: destino à direita = torre da coluna 7 (curto),
        // à esquerda = torre da coluna 0 (longo).
        boolean kingSide = (destinyCol > kingCol);
        int rookCol = kingSide ? 7 : 0;
        Piece rook = grid[row][rookCol];

        // Verifica a existência duma torre da mesma cor que nunca se moveu
        if (!(rook instanceof Rook) || rook.isWhite() != king.isWhite() || rook.isMoved()) {
            return false;
        }

        // Verifica se as casas entre o rei e a torre são vazias
        int step = kingSide ? 1 : -1;
        for (int c = kingCol + step; c != rookCol; c += step) {
            if (grid[row][c] != null) {
                return false;
            }
        }

        // Estar fora de check, nem cair em check durante a movimentação.
        boolean enemyColor = !king.isWhite();
        for (int c = kingCol; c != destinyCol + step; c += step) {
            if (isSquareAttacked(row, c, enemyColor)) {
                return false;
            }
        }

        // Tudo validado; faz as movimentações de ambas as peças
        int rookTargetCol = destinyCol - step;   // torre fica na casa que o rei acabou de cruzar
        grid[row][destinyCol] = king;
        grid[row][kingCol] = null;
        grid[row][rookTargetCol] = rook;
        grid[row][rookCol] = null;
        king.setMoved();
        rook.setMoved();
        updateEnPassantTarget(king, row, kingCol, row);
        return true;
    }

    // A casa [row][col] é o alvo de en passant registrado no lance anterior?
    private boolean isEnPassantTarget(int row, int col) {
        return row == enPassantRow && col == enPassantCol;
    }

    // Registra a casa pulada por um avanço duplo de peão; qualquer outro lance limpa
    // o registro. Deve ser chamado ao fim de 
    private void updateEnPassantTarget(Piece piece, int fromRow, int fromCol, int toRow) {
        if (piece instanceof Pawn && Math.abs(toRow - fromRow) == 2) {
            enPassantRow = (fromRow + toRow) / 2;
            enPassantCol = fromCol;
        } else {
            enPassantRow = -1;
            enPassantCol = -1;
        }
    }

    // Tenta executar a captura en passant.
    // Só é chamado quando movePiece já confirmou: peça é peão, o destino é a casa-alvo
    // registrada, o deslocamento é diagonal de uma coluna e o destino está vazio.
    private boolean tryEnPassant(int fromRow, int fromCol, int toRow, int toCol, boolean whiteTurn) {
        Piece[][] grid = board.getBoard();
        Piece pawn = grid[fromRow][fromCol];

        // O peão avança exatamente uma linha, no sentido da sua cor.
        int dir = pawn.isWhite() ? -1 : 1;
        if (toRow - fromRow != dir) {
            return false;
        }

        // O peão capturado fica AO LADO do destino: na linha de onde partimos.
        Piece captured = grid[fromRow][toCol];
        if (!(captured instanceof Pawn) || captured.isWhite() == pawn.isWhite()) {
            return false;
        }

        // Simula o lance COMPLETO, inclusive a remoção do peão capturado — que não está
        // na casa de destino. Por isso não dá para reusar o moveLeavesKingInCheck:
        // ele só sabe mexer em duas casas, e aqui são três.
        grid[toRow][toCol] = pawn;
        grid[fromRow][fromCol] = null;
        grid[fromRow][toCol] = null;

        if (isInCheck(whiteTurn)) {
            grid[fromRow][fromCol] = pawn;      // desfaz
            grid[toRow][toCol] = null;
            grid[fromRow][toCol] = captured;
            return false;
        }

        pawn.setMoved();
        updateEnPassantTarget(pawn, fromRow, fromCol, toRow);
        return true;
    }

    // Verifica se a casa [row][col] está sob ataque de alguma peça da cor oposta....
    // Varre o tabuleiro inteiro por força bruta, para cada peça inimiga.
    public boolean isSquareAttacked(int row, int col, boolean opstColor) {
        Piece[][] grid = board.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = grid[i][j];
                // pula casa vazia e peças que não são da cor atacante
                if (p == null || p.isWhite() != opstColor) {
                    continue;
                }

                // O peão ataca diferente de como anda... logo precisa de uma verificação diferente
                if (p instanceof Pawn) {
                    int dir = p.isWhite() ? -1 : 1;
                    if (i + dir == row && (j - 1 == col || j + 1 == col)) {
                        return true;
                    }
                    continue;
                }

                // Demais peças atacam normal, verificação normal
                if (moveValidator.canMoveTo(p, grid, new int[]{i, j}, new int[]{row, col})) {
                    return true;
                }
            }
        }
        // Nenhuma peça inimiga alcança a casa... logo, está livre
        return false;
    }

    // Localiza o rei da cor pedida, e retorna sua posição.
    private int[] findKing(boolean color) {
        Piece[][] grid = board.getBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = grid[i][j];
                if (p instanceof King && p.isWhite() == color) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    // O rei da cor está sob ataque de alguma peça inimiga?
    public boolean isInCheck(boolean color) {
        int[] k = findKing(color);
        if (k == null) return false;
        return isSquareAttacked(k[0], k[1], !color);
    }

    // Executa a jogada temporariamente, checa o próprio rei, e DESFAZ tudo.
    // Serve para verificar se a posição que ele vair ir é permitido, ou seja, não entra em check
    private boolean moveLeavesKingInCheck(int fromRow, int fromCol, int toRow, int toCol, boolean white) {
        Piece[][] grid = board.getBoard();
        Piece moving   = grid[fromRow][fromCol];
        Piece captured = grid[toRow][toCol];

        // Simula o movimento
        grid[toRow][toCol] = moving;
        grid[fromRow][fromCol] = null;

        boolean inCheck = isInCheck(white);

        // Desfaz o movimento
        grid[fromRow][fromCol] = moving;
        grid[toRow][toCol] = captured;

        return inCheck;
    }

    // Todos os lances legais da cor pedida. Força bruta: testa cada peça da cor
    // contra cada casa do tabuleiro, e descarta o que deixaria o próprio rei em xeque.
    // NÃO gera roque — ver comentário em movePiece.
    public List<Move> generateLegalMoves(boolean color) {
        Piece[][] grid = board.getBoard();
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = grid[i][j];
                if (p == null || p.isWhite() != color) continue;

                for (int r = 0; r < 8; r++) {
                    for (int c = 0; c < 8; c++) {
                        if (moveValidator.canMoveTo(p, grid, new int[]{i, j}, new int[]{r, c})
                                && !moveLeavesKingInCheck(i, j, r, c, color)) {
                            moves.add(buildMove(p, i, j, r, c));
                        }
                    }
                }
            }
        }
        return moves;
    }

    // Peão chegando na última linha vira promoção; o resto é lance simples.
    // Só geramos promoção para dama: subpromoção é legal, mas raríssima, e
    // multiplicaria a lista por 4 sem ganho prático.
    private Move buildMove(Piece piece, int fromRow, int fromCol, int toRow, int toCol) {
        if (piece instanceof Pawn && ((Pawn) piece).canPromote(toRow)) {
            return new Move(fromRow, fromCol, toRow, toCol, PromotionPiece.QUEEN);
        }
        return new Move(fromRow, fromCol, toRow, toCol);
    }

    // Xeque-mate e afogamento só precisam saber SE existe jogada, não quais.
    private boolean hasAnyLegalMove(boolean color) {
        return !generateLegalMoves(color).isEmpty();
    }

    // Xeque-mate = está em xeque && não tem nenhuma jogada legal para escapar.
    public boolean isCheckmate(boolean white) {
        return isInCheck(white) && !hasAnyLegalMove(white);
    }


    
    // Afogamento (stalemate) = NÃO está em xeque, mas nenhuma peça tem jogada legal.
    public boolean isStalemate(boolean white) {
        return !isInCheck(white) && !hasAnyLegalMove(white);
    }

    // Serializa a posição no formato FEN, que é o que o Stockfish consome.
    // Os dois últimos campos (relógio de 50 lances e número do lance) não são
    // rastreados; mandamos "0 1" fixo. O motor joga normalmente assim, só não
    // detecta empate pela regra dos 50 lances — que este projeto também não implementa.
    public String toFen(boolean whiteTurn) {
        StringBuilder fen = new StringBuilder();
        Piece[][] grid = board.getBoard();

        // 1) Peças, da linha 8 (índice 0) até a linha 1 (índice 7) — a ordem do FEN
        //    coincide com a ordem da matriz, então não é preciso inverter nada.
        for (int row = 0; row < 8; row++) {
            int emptyRun = 0;
            for (int col = 0; col < 8; col++) {
                Piece p = grid[row][col];
                if (p == null) {
                    emptyRun++;
                    continue;
                }
                if (emptyRun > 0) {          // fecha a sequência de casas vazias
                    fen.append(emptyRun);
                    emptyRun = 0;
                }
                char letter = p.fenLetter();
                fen.append(p.isWhite() ? Character.toUpperCase(letter) : letter);
            }
            if (emptyRun > 0) {
                fen.append(emptyRun);
            }
            if (row < 7) {
                fen.append('/');
            }
        }

        // 2) Cor de quem joga.
        fen.append(whiteTurn ? " w " : " b ");

        // 3) Direitos de roque.
        fen.append(castlingRights());

        // 4) Casa-alvo de en passant.
        fen.append(' ').append(enPassantRow < 0 ? "-" : Move.squareName(enPassantRow, enPassantCol));

        // 5 e 6) Não rastreados.
        fen.append(" 0 1");

        return fen.toString();
    }

    private String castlingRights() {
        StringBuilder rights = new StringBuilder();
        if (hasCastlingRight(7, 7)) rights.append('K');   // brancas, lado do rei
        if (hasCastlingRight(7, 0)) rights.append('Q');   // brancas, lado da dama
        if (hasCastlingRight(0, 7)) rights.append('k');   // pretas, lado do rei
        if (hasCastlingRight(0, 0)) rights.append('q');   // pretas, lado da dama
        return rights.isEmpty() ? "-" : rights.toString();
    }

    // O direito existe enquanto rei e torre daquele lado nunca tiverem se movido.
    // O FEN registra o DIREITO, não se o roque é jogável agora — casas ocupadas
    // ou atacadas não entram aqui, isso é problema do tryCastle.
    private boolean hasCastlingRight(int row, int rookCol) {
        Piece king = board.getPiece(row, 4);
        Piece rook = board.getPiece(row, rookCol);
        return king instanceof King && !king.isMoved()
            && rook instanceof Rook && !rook.isMoved();
    }
}
