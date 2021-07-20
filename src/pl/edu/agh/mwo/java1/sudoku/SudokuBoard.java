package pl.edu.agh.mwo.java1.sudoku;

public class SudokuBoard {

	public static final int SIZE = 9;		//stala statyczna, przypisana do klasy (nie do obiektu), zawsze ma wartosc 9. Oszczedza pamiec 
											//(nie jest tworzona dla obiektu klasy) i odwolujemy siê do niej poprzez nazwe klasy SudokuBoard.SIZE
	private final int[][] board;			//pocz¹tkowa plansza wygenerowana

	private int[][] currentBoard;			//s³u¿y do przechowywania rozwi¹zywanej planszy
	
	public SudokuBoard(int[][] board) {
		super();
		this.board = board;
		currentBoard = board;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoardField(int rowNumber, int collumnNumber, int value) {
		currentBoard[rowNumber][collumnNumber] = value;
	}
}
