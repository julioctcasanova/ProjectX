Trabalho da disciplina de Programação Orientada a Objetos ministrada pelo professor doutor Guilherme Freire...


COMO RODAR

	find -name "*.java" > source.txt
	javac @source.txt && java xadrez.Main

MODOS DE JOGO

	1 - Humano x Humano
		Os dois lados são controlados pelo teclado, revezando o turno.

	2 - Humano x Computador (mega fácil)
		O computador sorteia um lance qualquer.

	3 - Humano x Stockfish (ultra difícil)
		O computador é o motor Stockfish, um dos mais fortes do mundo. Exige o
		binário "stockfish" instalado (em Arch: sudo pacman -S stockfish).
		-> fallback no modo 1. Você joga de brancas.
		A profundidade de busca está fixa em 12 no Main — quanto maior, mais forte.

ORGANIZAÇÃO DO CÓDIGO

	Board          estado puro: a matriz 8x8 e o acesso a ela
	GameRules      legalidade, roque, en passant, xeque, mate, afogamento e FEN
	MoveValidator  geometria do movimento: alcance, cor do alvo, caminho livre
	BoardPrinter   exibição no terminal
	Move           um lance (origem, destino e promoção)
	Player         interface dos jogadores — HumanPlayer, RandomPlayer, StockfishPlayer
	Main           montagem dos objetos e laço da partida
	pieces/        Piece e as seis peças, cada uma declarando seus movimentos


	Refatoração do Main -> tava grande para um krl, dei uma pequena refatorada...
	-> -> tem pontos no código muito condensada, parece até código em C,
	    principalmente a Main e GameRules, precisamos refatorar ambos e gerar
	    mais classes... pensei em transgormar GameRules em uma pasta com cada
	    regra sendo uma classe, seria melhor para a leitura da estrutura


IMPLEMENTAÇÃO INICIAL

- movimentações:
	[X] movimentação básica peão
	[X] movimentação básica torre
	[X] movimentação básica cavalo
	[X] movimentação básica bispo
	[X] movimentação básica rainha
	[X] movimentação básica rei
	[X] validador de movimento (MoveValidator): geometria + cor + MoveType + caminho livre

- interface gráfica:
	[x] representação no terminal do tabuleiro
		[x] cli advanced...
	[] representação gráfica do tabuleiro
		[] desenhar modelo peças
		[] implementação tabuleiro
		[] implementação peças
		[] implementação movimentações

- correção de bugs:
	[x] movimentação cavalo


IMPLEMENTAÇÕES INTERMEDIÁRIAS

	[x] jogabilidade do xadrez
	[x] revezamento entre players
	[x] promoção de peão (com escolha da peça)
	[x] geração de lances legais


IMPLEMENTAÇÕES FINAIS

	[x] mecanismos do ROQUE (curto e longo, com todas as restrições)
	[x] verificação de XEQUE, xeque-mate e afogamento
	[x] EN PASSANT (inclusive a recusa quando expõe o próprio rei)
	[x] modo contra computador aleatório
	[x] modo contra o Stockfish via UCI


LIMITAÇÕES CONHECIDAS

	- O gerador de lances legais não produz roque nem en passant.
	- Não há empate por repetição nem pela regra dos 50 lances.

ESPERA-SE deixar as semanas restantes para DEBUGGING do código...
futuramente o repositorio ficará aberto para caso algum dos membros
queiram fazer adições complementares.


QUESTÃO:

    -> o STOCKFISH precisa baixar o binario para rodar, senão não funciona, se quiserem eu posso subir uma api no meu server e deixar rodando até o freire testar e liberar as notas... só para evitar dele ter problema quando for testar, e para vcs poderem testar tbm sem ter que ficar baixando o binario do stockfisk
