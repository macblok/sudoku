package pl.edu.agh.mwo.java1.sudoku;

public class SudokuBoardWriter {

	public void writeBoard(SudokuBoard board) {

		for (int i = 0; i < SudokuBoard.SIZE; i++) {
			for (int j = 0; j < SudokuBoard.SIZE; j++) {
				System.out.print(board.getBoard()[i][j] + " ");
			}
			System.out.println();
		}
	}

}
