package pl.edu.agh.mwo.java1.sudoku;

import java.util.Arrays;

public class SudokuBoardChecker {

	public SudokuBoardChecker() {
	}

	private boolean verifyBoardStructure(SudokuBoard board) {

		boolean isStructureCorrect = true;

		for (int i = 0; i < SudokuBoard.SIZE; i++) {
			for (int j = 0; j < SudokuBoard.SIZE; j++) {
				if (board.getBoard()[i][j] < 0 || board.getBoard()[i][j] > 9) { // tablica int[][] zapewnia, ¿e tylko int bêd¹ w niej, wiêc wysatrczy sprawdziæ czy s¹ w zakresie <0, 9>
					isStructureCorrect = false;

					return isStructureCorrect;
				}
			}
		}

		return isStructureCorrect;

	}

	public boolean verifyBoard(SudokuBoard board) {

		boolean isBoardCorrect = true;

		int[] tempHorizontal = new int[SudokuBoard.SIZE];
		int[] tempVertical = new int[SudokuBoard.SIZE];

		int[] boardFieldGlobal = new int[SudokuBoard.SIZE * SudokuBoard.SIZE]; // do wpisania wartoœci wszystkich pól planszy
		int[] boardFieldLocal = new int[9]; // do przechowania wartoœci lokalnych pól 3x3
		int index = 0;

		for (int i = 0; i < SudokuBoard.SIZE; i++) {
			for (int j = 0; j < SudokuBoard.SIZE; j++) {
				boardFieldGlobal[index] = board.getBoard()[i][j];
				index++;
			}
		}

		int indexLocal; // index tablicy z wartoœciami lokalnej planszy 3x3

		for (int i = 0; i < SudokuBoard.SIZE; i += 3) { // wype³nia lokaln¹ planszê 3x3
			for (int j = 0; j < SudokuBoard.SIZE; j += 3) {
				indexLocal = 0;

				for (int i1 = 0; i1 < 3; i1++) {
					for (int j1 = 0; j1 < 3; j1++) {
						boardFieldLocal[indexLocal] = board.getBoard()[i + i1][j + j1];
						indexLocal++;

					}

				}

				Arrays.sort(boardFieldLocal);

				if (checkDuplicates(boardFieldLocal) == false) {
					isBoardCorrect = false;
					return isBoardCorrect;

				}

			}

		}

		for (int i = 0; i < SudokuBoard.SIZE; i++) {
			for (int j = 0; j < SudokuBoard.SIZE; j++) {
				tempHorizontal[j] = board.getBoard()[i][j];
				tempVertical[j] = board.getBoard()[j][i];

			}

			if (checkDuplicates(tempHorizontal) == false) {
				isBoardCorrect = false;
				return isBoardCorrect;
			}

			if (checkDuplicates(tempVertical) == false) {
				isBoardCorrect = false;
				return isBoardCorrect;

			}

		}
		return isBoardCorrect;

	}

	private boolean checkDuplicates(int[] arrayToCheck) {

		Arrays.sort(arrayToCheck);

		boolean isDuplicate = true;

		if (arrayToCheck[arrayToCheck.length - 1] == 0) {
			return isDuplicate;
		}

		for (int i = 1; i < arrayToCheck.length; i++) {
			while (arrayToCheck[i] == 0 && i < arrayToCheck.length - 1) {
				i++;
			}

			if (arrayToCheck[i] == arrayToCheck[i - 1]) {
				isDuplicate = false;
				return isDuplicate;

			}
		}

		return isDuplicate;

	}

	public void fullCheckBoard(SudokuBoard board) {
		if (verifyBoardStructure(board) == true) {
			System.out.println("Struktura planszy jest poprawna.");
		} else {
			System.out.println("Struktura planszy jest niepoprawna.");
			return; // wyjœcie z metody, bo niepoprawna struktura implikuje niepoprawn¹ planszê
		}

		if (verifyBoard(board) == true) {
			System.out.println("Plansza jest poprawna.");
		} else {
			System.out.println("Plansza jest niepoprawna.");
		}

	}

}
