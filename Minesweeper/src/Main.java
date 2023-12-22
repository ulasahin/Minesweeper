import java.util.Random;
import java.util.Scanner;

class MineSweeper {
    private int row;
    private int col;
    private int[][] mineField;
    private boolean[][] revealed;
    private int totalMines;

    public MineSweeper(int row, int col) {
        this.row = row;
        this.col = col;
        this.mineField = new int[row][col];
        this.revealed = new boolean[row][col];
        this.totalMines = row * col / 4;

        initializeMineField();
    }

    private void initializeMineField() {
        if (row <= 0 || col <= 0) {
            System.out.println("Geçersiz oyun alanı boyutu. Satır ve sütun sayıları pozitif olmalı.");
            System.exit(0);
        }

        Random random = new Random();


        for (int i = 0; i < totalMines; i++) {
            int randomRow = random.nextInt(row);
            int randomCol = random.nextInt(col);


            while (mineField[randomRow][randomCol] == -1) {
                randomRow = random.nextInt(row);
                randomCol = random.nextInt(col);
            }

            mineField[randomRow][randomCol] = -1;
        }


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (mineField[i][j] == -1) {
                    updateAdjacentCell(i - 1, j - 1);
                    updateAdjacentCell(i - 1, j);
                    updateAdjacentCell(i - 1, j + 1);
                    updateAdjacentCell(i, j - 1);
                    updateAdjacentCell(i, j + 1);
                    updateAdjacentCell(i + 1, j - 1);
                    updateAdjacentCell(i + 1, j);
                    updateAdjacentCell(i + 1, j + 1);
                }
            }
        }
    }

    private void updateAdjacentCell(int row, int col) {
        if (row >= 0 && row < this.row && col >= 0 && col < this.col && mineField[row][col] != -1) {
            mineField[row][col]++;
        }
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Mayın Tarlası Oyunun
a Hoşgeldiniz !");

        while (true) {
            printGameField();

            System.out.print("Satır Giriniz: ");
            int selectedRow = scanner.nextInt();
            System.out.print("Sütun Giriniz: ");
            int selectedCol = scanner.nextInt();

            if (isValidMove(selectedRow, selectedCol)) {
                if (mineField[selectedRow][selectedCol] == -1) {
                    System.out.println("Game Over!!");
                    break;
                } else {
                    revealCell(selectedRow, selectedCol);

                    if (checkWin()) {
                        System.out.println("Oyunu Kazandınız !");
                        printGameField();
                        break;
                    }
                }
            } else {
                System.out.println("Geçersiz bir hamle, lütfen tekrar deneyin.");
            }
        }

        scanner.close();
    }

    private void printGameField() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (revealed[i][j]) {
                    System.out.print(mineField[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
        System.out.println("===========================");
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < this.row && col >= 0 && col < this.col && !revealed[row][col];
    }

    private void revealCell(int row, int col) {
        revealed[row][col] = true;

        if (mineField[row][col] == 0) {

            revealAdjacentCells(row - 1, col - 1);
            revealAdjacentCells(row - 1, col);
            revealAdjacentCells(row - 1, col + 1);
            revealAdjacentCells(row, col - 1);
            revealAdjacentCells(row, col + 1);
            revealAdjacentCells(row + 1, col - 1);
            revealAdjacentCells(row + 1, col);
            revealAdjacentCells(row + 1, col + 1);
        }
    }

    private void revealAdjacentCells(int row, int col) {
        if (row >= 0 && row < this.row && col >= 0 && col < this.col && !revealed[row][col]) {
            revealed[row][col] = true;

            if (mineField[row][col] == 0) {

                revealAdjacentCells(row - 1, col - 1);
                revealAdjacentCells(row - 1, col);
                revealAdjacentCells(row - 1, col + 1);
                revealAdjacentCells(row, col - 1);
                revealAdjacentCells(row, col + 1);
                revealAdjacentCells(row + 1, col - 1);
                revealAdjacentCells(row + 1, col);
                revealAdjacentCells(row + 1, col + 1);
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!revealed[i][j] && mineField[i][j] != -1) {
                    return false;
                }
            }
        }
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Lütfen oyun tahtası boyutunu seçiniz (Satır): ");
        int row = scanner.nextInt();
        System.out.print("Lütfen oyun tahtası boyutunu seçiniz (Sütun): ");
        int col = scanner.nextInt();

        MineSweeper game = new MineSweeper(row, col);
        game.playGame();
    }
}
