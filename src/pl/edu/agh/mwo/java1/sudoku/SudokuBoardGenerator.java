package pl.edu.agh.mwo.java1.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SudokuBoardGenerator {
	
	private SudokuBoardSolver solver;

	private int[][] createEmptyArray() {

		int[][] board = new int[SudokuBoard.SIZE][SudokuBoard.SIZE];
		for (int i = 0; i < SudokuBoard.SIZE; i++) {
			for (int j = 0; j < SudokuBoard.SIZE; j++) {
				board[i][j] = 0;
			}
		}

		return board;
	}

	public SudokuBoard generateEmptyBoard() {
		return new SudokuBoard(createEmptyArray());
	}

	public SudokuBoard generateRandomBoard() {
		SudokuBoard randomBoard = tryGenerateRandomBoard();
		solver = new SudokuBoardSolver(randomBoard);
		while (!solver.trySolve()) {
			generateRandomBoard();

		}

		return solver.initialBoard;
		
	}
	
	private SudokuBoard tryGenerateRandomBoard() { // losowo generuje zawsze poprawn¹ planszê
		int[][] board = createEmptyArray();

		int randomField; // s³u¿y do przechowania indeksu boardField
		int pickUp; // s³u¿y do przechowywania wylosowanego pola planszy
		int pickUpIndex; // s³u¿y do przechowywania wylosowanego indeksu tablicy currentRange
		int matrixDensity = 7; //s³u¿y do ograniczenia wype³niania macierzy, aby zwiêkszyæ szansê wygenerowania planszy mo¿liwej do rozwi¹zania
		
		int[] currentRange = new int[SudokuBoard.SIZE + matrixDensity];

		for (int i = 0; i < matrixDensity; i++) {
			currentRange[SudokuBoard.SIZE + i] = 0;
		}
		
		Random random = new Random();

		ArrayList<Integer> boardField = new ArrayList<>(); // lista do losowania pola planszy

		fillBoardField(boardField); // wype³niany listê wszystkimi polami planszy

		while (boardField.size() > 0) {

			randomField = random.nextInt(boardField.size());

			pickUp = boardField.get(randomField); // wylosowane pole planszy

			int[] coordinates = intToBoardCoordinates(pickUp); // zamiana wylosowanej liczby na tablicê wspó³rzêdnych [x][y]

			fillFullRange(currentRange); // resetuje zakres losowania do pe³nej tablicy od 1 do 9

			crossRemoveDuplicates(board, currentRange, coordinates[0], coordinates[1]); // usuwamy wartoœci znajduj¹ce siê ju¿ w danym wierszu/kolumnie

			remove3x3Duplicates(board, currentRange, coordinates[0], coordinates[1]); // usuwamy z currentRange wartoœci, które ju¿ s¹ w lokalnych planszach 3x3

			pickUpIndex = random.nextInt(SudokuBoard.SIZE + matrixDensity); // losuje index elementu tabeli current Range
			
			board[coordinates[0]][coordinates[1]] = currentRange[pickUpIndex]; // przypisuje wylosowany element
			boardField.remove(randomField); // usuwa z listy pól aktualnie wylosowane pole, aby drugi raz nie bra³o udzia³u w losowaniu
			
		}

		return new SudokuBoard(board);

	}

	private void fillBoardField(ArrayList<Integer> boardField) { // wype³nia listê wartoœciami od 0 do 88 dla planszy 9x9
		for (int i = 0; i < (SudokuBoard.SIZE - 1) * 10 + SudokuBoard.SIZE; i++) {
			boardField.add(i);
		}

		for (int i = 0; i < boardField.size(); i++) { // usuwamy z listy wartoœci dla których %10 = 9, np. 19; 29, bo zakres pó³ koñczy siê na 8
			if (boardField.get(i) % 10 >= SudokuBoard.SIZE) {
				boardField.remove(i);
				i--;
			}
		}
	}

	private void fillFullRange(int[] fullRange) { // resetuje zakres losowania do pe³nego zakresu od 1 do 9
		for (int i = 0; i < SudokuBoard.SIZE; i++) {
			fullRange[i] = i + 1;
		}
	}

	private void crossRemoveDuplicates(int[][] board, int[] currentRange, int rowNumber, int collumnNumber) { // usuwa wartoœci wystêpuj¹ce ju¿ w danym wiersz/kolumnie

		for (int i = 0; i < SudokuBoard.SIZE; i++) {

			if (board[rowNumber][i] > 0) {
				currentRange[(board[rowNumber][i]) - 1] = 0; // sprawdza jaki wartoœci w wierszu ju¿ wystêpuj¹ i zamienia je w currentRange na 0, dziêki temu wartoœci nie powtórz¹ siê w wierszu
			}
			if (board[i][collumnNumber] > 0) {
				currentRange[(board[i][collumnNumber]) - 1] = 0; // sprawdza jaki wartoœci w kolumnie ju¿ wystêpuj¹ i zamienia je w currentRange na 0, dziêki temu wartoœci nie powtórz¹ siê w kolumnie
			}
		}
	}

	private void remove3x3Duplicates(int[][] board, int[] currentRange, int rowNumber, int collumnNumber) { // usuwa wartoœci wystêpuj¹ce ju¿ w lokalnych planszach 3x3

		int[] boardFieldLocal = new int[9]; // do przechowania wartosci wystepujacych w lokalnym kwadracie 3x3

		int indexLocal = 0;

		int localRow = rowNumber - (rowNumber % 3);
		int localCollumn = collumnNumber - (collumnNumber % 3);
		
		for (int i = localRow; i < localRow + 3; i++) {
			for (int j = localCollumn; j < localCollumn + 3; j++) {
				boardFieldLocal[indexLocal] = board[i][j];

				indexLocal++;
			}
		}

		Arrays.sort(boardFieldLocal);

		if (boardFieldLocal[8] == 0) {
			return;
		}

		for (int i = 1; i < 9; i++) {
			if (boardFieldLocal[i] > 0) {
				currentRange[boardFieldLocal[i] - 1] = 0;

			}
		}

	}

	private int[] intToBoardCoordinates(int pickUp) { // zamienia wylosowan¹ liczbê na wspó³rzêdne planszy, np 57 na rowNumber = 5, collumnNumber = 7

		int rowNumber;
		int temp;
		int collumnNumber;

		rowNumber = (pickUp / 10);
		temp = rowNumber * 10;
		collumnNumber = pickUp - temp;

		int[] coordinates = new int[] { rowNumber, collumnNumber };
		return coordinates;
	}

	public SudokuBoard generateSample1Board() {
		int[][] board = createEmptyArray();

		board[0][2] = 9;
		board[0][6] = 4;

		board[1][0] = 6;
		board[1][3] = 4;
		board[1][7] = 2;

		board[2][0] = 8;
		board[2][1] = 4;
		board[2][5] = 1;
		board[2][7] = 9;

		board[3][2] = 8;
		board[3][5] = 7;
		board[3][8] = 1;

		board[4][0] = 5;
		board[4][4] = 6;
		board[4][8] = 3;

		board[5][0] = 1;
		board[5][1] = 6;
		board[5][3] = 8;
		board[5][6] = 7;

		board[6][1] = 7;
		board[6][3] = 2;
		board[6][4] = 9;
		board[6][8] = 5;

		board[7][1] = 2;
		board[7][5] = 5;
		board[7][8] = 4;

		board[8][2] = 5;
		board[8][6] = 9;

		return new SudokuBoard(board);

	}

	public SudokuBoard generateSample2Board() {
		int[][] board = createEmptyArray();

		board[0][2] = 9;
		board[0][3] = 8;
		board[0][6] = 6;
		board[0][7] = 3;

		board[1][0] = 3;
		board[1][4] = 9;
		board[1][6] = 2;
		board[1][8] = 1;

		board[2][1] = 8;
		board[2][2] = 1;
		board[2][3] = 3;
		board[2][4] = 2;
		board[2][5] = 4;
		board[2][8] = 5;

		board[3][0] = 5;
		board[3][1] = 7;
		board[3][3] = 1;
		board[3][4] = 8;

		board[4][1] = 1;
		board[4][2] = 2;
		board[4][3] = 7;
		board[4][4] = 6;
		board[4][5] = 5;
		board[4][7] = 9;
		board[4][8] = 3;

		board[5][0] = 9;
		board[5][4] = 4;
		board[5][5] = 3;
		board[5][6] = 5;
		board[5][7] = 1;

		board[6][2] = 7;
		board[6][6] = 3;

		board[7][2] = 6;
		board[7][6] = 7;

		board[8][1] = 3;
		board[8][2] = 4;
		board[8][4] = 7;

		return new SudokuBoard(board);

	}

	public SudokuBoard generateSample3Board() {
		int[][] board = createEmptyArray();

		board[0][2] = 4;
		board[0][5] = 2;
		board[0][7] = 9;

		board[1][5] = 9;

		board[2][0] = 9;
		board[2][2] = 3;
		board[2][4] = 7;
		board[2][5] = 1;
		board[2][7] = 6;
		board[2][8] = 2;

		board[3][2] = 6;
		board[3][3] = 2;

		board[4][1] = 2;
		board[4][2] = 8;
		board[4][7] = 3;
		board[4][8] = 6;

		board[5][2] = 9;
		board[5][5] = 6;
		board[5][6] = 2;
		board[5][7] = 5;

		board[6][0] = 8;
		board[6][2] = 7;
		board[6][4] = 2;
		board[6][5] = 3;
		board[6][7] = 1;

		board[7][1] = 1;
		board[7][3] = 7;
		board[7][5] = 8;
		board[7][7] = 2;
		board[7][8] = 3;

		board[8][0] = 3;
		board[8][5] = 4;
		board[8][6] = 5;

		return new SudokuBoard(board);

	}

	public SudokuBoard generateSample4Board() {
		int[][] board = createEmptyArray();

		board[0][4] = 1;
		board[0][7] = 3;

		board[1][0] = 5;
		board[1][3] = 9;
		board[1][5] = 7;
		board[1][8] = 1;

		board[2][1] = 4;
		board[2][3] = 5;
		board[2][7] = 7;

		board[3][1] = 1;
		board[3][5] = 8;
		board[3][6] = 9;
		board[3][7] = 4;
		board[3][8] = 6;

		board[4][2] = 2;

		board[5][0] = 3;
		board[5][1] = 8;
		board[5][2] = 4;
		board[5][3] = 6;
		board[5][4] = 9;
		board[5][5] = 5;
		board[5][7] = 1;

		board[6][4] = 3;
		board[6][5] = 6;
		board[6][8] = 8;

		board[7][1] = 3;

		board[8][7] = 2;
		board[8][8] = 7;

		return new SudokuBoard(board);

	}

	public SudokuBoard generateWrong1Board() {
		int[][] board = createEmptyArray();

		board[0][4] = 1;
		board[0][7] = 1;

		board[1][0] = 5;
		board[1][3] = 9;
		board[1][5] = 7;
		board[1][8] = 9;

		board[2][1] = 4;
		board[2][3] = 5;
		board[2][7] = 7;

		board[3][1] = 1;
		board[3][5] = 8;
		board[3][6] = 9;
		board[3][8] = 1;

		board[4][2] = 2;

		board[5][0] = 3;
		board[5][1] = 8;
		board[5][2] = 4;
		board[5][3] = 8;
		board[5][4] = 9;
		board[5][5] = 5;
		board[5][7] = 1;

		board[6][4] = 3;
		board[6][5] = 8;
		board[6][8] = 8;

		board[7][1] = 8;

		board[8][7] = 2;
		board[8][8] = 7;

		return new SudokuBoard(board);

	}

	public SudokuBoard generateWrong2Board() {
		int[][] board = createEmptyArray();

		board[0][2] = 4;
		board[0][5] = 2;
		board[0][7] = 9;

		board[1][1] = 4;

		board[2][0] = 9;
		board[2][2] = 3;
		board[2][4] = 7;
		board[2][5] = 1;
		board[2][7] = 6;
		board[2][8] = 2;

		board[3][2] = 6;
		board[3][3] = 2;

		board[4][1] = 2;
		board[4][2] = 8;
		board[4][7] = 3;
		board[4][8] = 6;

		board[5][2] = 9;
		board[5][5] = 6;
		board[5][6] = 2;
		board[5][7] = 5;

		board[6][0] = 8;
		board[6][2] = 7;
		board[6][4] = 2;
		board[6][5] = 3;
		board[6][7] = 1;

		board[7][1] = 1;
		board[7][3] = 7;
		board[7][5] = 8;
		board[7][7] = 2;
		board[7][8] = 3;

		board[8][0] = 3;
		board[8][5] = 4;
		board[8][6] = 5;

		return new SudokuBoard(board);

	}

	public SudokuBoard generateWrong3Board() {
		int[][] board = createEmptyArray();

		board[0][2] = 9;
		board[0][6] = 9;

		board[1][0] = 6;
		board[1][3] = 4;
		board[1][7] = 2;

		board[2][0] = 8;
		board[2][1] = -4;
		board[2][5] = 8;
		board[2][7] = 9;

		board[3][2] = 8;
		board[3][5] = -7;
		board[3][8] = 1;

		board[4][0] = 5;
		board[4][4] = 6;
		board[4][8] = 3;

		board[5][0] = 1;
		board[5][1] = 6;
		board[5][3] = 8;
		board[5][6] = 7;

		board[6][1] = 7;
		board[6][3] = 2;
		board[6][4] = -6;
		board[6][8] = 5;

		board[7][1] = 2;
		board[7][5] = 5;
		board[7][8] = 4;

		board[8][4] = 6;
		board[8][6] = 9;

		return new SudokuBoard(board);

	}

}
