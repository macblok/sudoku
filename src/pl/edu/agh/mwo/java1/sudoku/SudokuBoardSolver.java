package pl.edu.agh.mwo.java1.sudoku;

public class SudokuBoardSolver {

	public SudokuBoard initialBoard;
	private SudokuBoard board;
	
	SudokuBoardChecker checker;
	SudokuBoardWriter writer;

	public SudokuBoardSolver(SudokuBoard board) {

		initialBoard = new SudokuBoard(new int[9][9]);
		
		for (int i = 0; i < SudokuBoard.SIZE; i++) {
			for (int j = 0; j < SudokuBoard.SIZE; j++) {
				initialBoard.setBoardField(i, j, board.getBoard()[i][j]);
			}
		}

		this.board = board;

		checker = new SudokuBoardChecker();
		writer = new SudokuBoardWriter();
		
	}

	public void solve() {
		
		if (trySolve()) {
			System.out.println("Plansza sudoku zosta³a rozwi¹zana!");
			writer.writeBoard(board);
			checker.fullCheckBoard(board);

		} else {
			System.out.println("Nie da siê rozwi¹zaæ tej planszy!");
		}
	}

	public boolean trySolve() { // solver - wykorzystujemy recursive backtracking algorytm

		for (int i = 0; i < SudokuBoard.SIZE; i++) {
			for (int j = 0; j < SudokuBoard.SIZE; j++) {
				if (board.getBoard()[i][j] == 0) {
					for (int value = 1; value < SudokuBoard.SIZE + 1; value++) {
						if (fullCheckIsIn(i, j, value)) {
							board.setBoardField(i, j, value);

							if (trySolve()) {
								return true;
							} else {
								board.setBoardField(i, j, 0);
							}
						}
					}
					
					return false;

				}
			}
		}

		return true;

	}

	private boolean fullCheckIsIn(int rowNumber, int collumnNumber, int value) { // jedna funkcja do sprawdzenia wszystkich regu³ Sudoku
		if (isInCrossCheck(rowNumber, collumnNumber, value) | isInLocal3x3(rowNumber, collumnNumber, value)) {
			return false;
		}

		return true;

	}

	private boolean isInCrossCheck(int rowNumber, int collumnNumber, int value) { // sprawdzamy czy liczba value jest ju¿ w danym wierszu/kolumnie
		for (int i = 0; i < SudokuBoard.SIZE; i++) {
			if (board.getBoard()[rowNumber][i] == value || board.getBoard()[i][collumnNumber] == value) {
				return true;
			}
		}

		return false;

	}

	private boolean isInLocal3x3(int rowNumber, int collumnNumber, int value) { // sprawdza czy liczba value jest ju¿ w którejœ z lokalnych plansz 3x3
		int localRow = rowNumber - (rowNumber % 3);
		int localCollumn = collumnNumber - (collumnNumber % 3);

		for (int i = localRow; i < localRow + 3; i++) {
			for (int j = localCollumn; j < localCollumn + 3; j++) {
				if (board.getBoard()[i][j] == value) {
					return true;
				}
			}
		}

		return false;

	}

}





