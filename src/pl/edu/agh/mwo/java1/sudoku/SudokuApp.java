package pl.edu.agh.mwo.java1.sudoku;

public class SudokuApp {

	public static void main(String[] args) {

		SudokuBoardGenerator generator = new SudokuBoardGenerator();

		SudokuBoard emptyBoard = generator.generateEmptyBoard();
		SudokuBoard sample1Board = generator.generateSample1Board();
		SudokuBoard sample2Board = generator.generateSample2Board();
		SudokuBoard sample3Board = generator.generateSample3Board();
		SudokuBoard sample4Board = generator.generateSample4Board();
		SudokuBoard wrong1Board = generator.generateWrong1Board();
		SudokuBoard wrong2Board = generator.generateWrong2Board();
		SudokuBoard wrong3Board = generator.generateWrong3Board();
	
		SudokuBoard randomBoard = generator.generateRandomBoard();

		SudokuBoardWriter writer = new SudokuBoardWriter();
		SudokuBoardChecker checker = new SudokuBoardChecker();


		writer.writeBoard(emptyBoard);
		System.out.println("-----------------");
		checker.fullCheckBoard(emptyBoard);
		System.out.println();
		System.out.println();

		writer.writeBoard(sample1Board);
		System.out.println("-----------------");
		checker.fullCheckBoard(sample1Board);
		System.out.println();
		System.out.println();

		writer.writeBoard(sample2Board);
		System.out.println("-----------------");
		checker.fullCheckBoard(sample2Board);
		System.out.println();
		System.out.println();

		writer.writeBoard(sample3Board);
		System.out.println("-----------------");
		checker.fullCheckBoard(sample3Board);
		System.out.println();
		System.out.println();

		writer.writeBoard(sample4Board);
		System.out.println("-----------------");
		checker.fullCheckBoard(sample4Board);
		System.out.println();
		System.out.println();

		writer.writeBoard(wrong1Board);
		System.out.println("-----------------");
		checker.fullCheckBoard(wrong1Board);
		System.out.println();
		System.out.println();

		writer.writeBoard(wrong2Board);
		System.out.println("-----------------");
		checker.fullCheckBoard(wrong2Board);
		System.out.println();
		System.out.println();

		writer.writeBoard(wrong3Board);
		System.out.println("-----------------");
		checker.fullCheckBoard(wrong3Board);
		System.out.println();
		System.out.println();

		writer.writeBoard(randomBoard);
		System.out.println("-----------------");
		System.out.println("Ta plansza jest wygenerowana losowo.");
		checker.fullCheckBoard(randomBoard);
		SudokuBoardSolver solver = new SudokuBoardSolver(randomBoard);
		solver.solve();

		
		

		
	}

}
