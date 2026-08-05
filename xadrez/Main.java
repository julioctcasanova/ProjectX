package xadrez;
import java.util.concurrent.CountDownLatch;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Board board = new Board();
        board.fillBoard();
        GameRules rules = new GameRules(board);
        BoardPrinter printer = new BoardPrinter();
        Scanner sc = new Scanner(System.in);
	new Menu();
	Menu.modeSelected.await();
        //printMenu();

	String modo = Menu.mode;

        Player[] players;              // players[0] = brancas, players[1] = pretas
        StockfishPlayer engine = null; // guardado à parte só para poder encerrar no fim

        if (modo.equals("3")) {
            try {
                engine = new StockfishPlayer(20);
                players = new Player[]{ new HumanPlayer(sc), engine };
            } catch (IOException e) {
                // Binário ausente ou fora do PATH: avisa e cai num modo que sempre funciona.
                System.out.println("NAO FOI POSSIVEL INICIAR O STOCKFISH: " + e.getMessage());
                System.out.println("VERIFIQUE SE O BINARIO ESTA INSTALADO. USANDO HUMANO x HUMANO.");
                players = new Player[]{ new HumanPlayer(sc), new HumanPlayer(sc) };
            }
        } else if (modo.equals("2")) {
            players = new Player[]{ new HumanPlayer(sc), new RandomPlayer() };
        } else {
            players = new Player[]{ new HumanPlayer(sc), new HumanPlayer(sc) };
        }

        boolean whiteTurn = true;
        String status = "";   // mensagem sob o tabuleiro; sobrevive à limpeza da tela

        while (true) {
            printer.clear();
            printer.printBoard(board, whiteTurn);
            if (!status.isEmpty()) {
                System.out.println(status);
            }

            if (rules.isCheckmate(whiteTurn)) {
                System.out.println((whiteTurn ? "BRANCAS" : "PRETAS") + " EM XEQUE-MATE. \nFIM DE JOGO.");
                break;
            }
            if (rules.isStalemate(whiteTurn)) {
                System.out.println("AFOGAMENTO. EMPATE.\n");
                break;
            }

            System.out.println(whiteTurn ? "BRANCAS JOGAM" : "PRETAS JOGAM");
            if (rules.isInCheck(whiteTurn)) {
                System.out.println("EM XEQUE!");
            }

            Player current = players[whiteTurn ? 0 : 1];
            Move move = current.chooseMove(board, rules, whiteTurn);

            if (move == null) {
                System.out.println("PARTIDA ENCERRADA PELO JOGADOR.");
                break;
            }

            // As mensagens viram "status" em vez de println: como a tela é limpa no
            // topo do laço, qualquer coisa impressa aqui seria apagada antes de ser lida.
            if (rules.movePiece(move.fromRow(), move.fromCol(), move.toRow(), move.toCol(),
                    whiteTurn, move.promotionPiece())) {
                status = current.name() + " JOGOU: " + move;
                whiteTurn = !whiteTurn;
            } else {
                status = "MOVIMENTO ILEGAL OU PEÇA INCORRETA. TENTE NOVAMENTE.";
            }
        }

        if (engine != null) {
            engine.close();   // senão o processo do Stockfish fica órfão
        }
        sc.close();
    }

    // Menu inicial dentro de uma moldura. Largura interna fixa em 42 colunas —
    // se mudar algum texto, ajuste o preenchimento para as bordas continuarem alinhadas.
    /*
    private static void printMenu() {
        System.out.println("+------------------------------------------+");
        System.out.println("|               X A D R E Z                |");
        System.out.println("+------------------------------------------+");
        System.out.println("|                                          |");
        System.out.println("|   1 - Humano x Humano                    |");
        System.out.println("|   2 - Humano x Computador (mega facil)   |");
        System.out.println("|   3 - Humano x Stockfish (ultra dificil) |");
        System.out.println("|                                          |");
        System.out.println("+------------------------------------------+");
        System.out.print("  ESCOLHA O MODO: ");
    }
    */
}
