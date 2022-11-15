package com.company;

import java.util.Scanner;
import java.util.ArrayList;

public class MineSweeper {
    int x;
    int y;
    int size;
    int col;
    int row;
    char dif;
    Boolean fin;
    String flag;
    char[][] oriboard;
    char[][] modiboard;

    public MineSweeper() {
        fin = true;

        Scanner input = new Scanner(System.in);
        System.out.println("::WELCOME::");
        System.out.print("Please enter the sizes of the board (m x n):");
        String size = input.nextLine();

        col = Integer.parseInt(size.substring(0, size.indexOf(" ")));
        row = Integer.parseInt(size.substring(size.indexOf("x") + 2));

        oriboard = new char[col][row];
        modiboard = new char[col][row];

        this.size = (col) * (row);

        System.out.print("Please select the difficulty (E,M,H):");
        dif = input.next().charAt(0);

        for (int i = 0; i < oriboard.length; i++) {
            for (int j = 0; j < oriboard[i].length; j++) {
                oriboard[i][j] = '0';
                modiboard[i][j] = '0';
            }
        }

        for (int j = 0; j < numMines(); j++) {
            int index = (int) (Math.random() * this.size);
            if ((index / col) < col && (index % col) < row) {
                if (oriboard[index / (col)][index % (col)] == 'X')
                    j--;
                if (index / col == 0 && index % col == 0)
                    j--;
                else
                    oriboard[index / (col)][index % (col)] = 'X';
            }
            else
                j--;
        }

        for (int i = 0; i < oriboard.length; i++) {
            for (int j = 0; j < modiboard[i].length; j++) {
                if (modiboard[i][j] == '0')
                    System.out.print("o ");
            }
            System.out.println();
        }
        input.nextLine();

        int countZero = 0;
        int countFlag = 0;

        while (oriboard[x][y] != 'X' || flag.equals("F") || flag.equals("U")) {

            if (numMines() == (countZero + countFlag))
                break;
            System.out.print("Please make a move:");
            String move = input.nextLine();
            move = move + "";
            if (move.indexOf("F") > 0) {
                x = Integer.parseInt(move.substring(0, move.indexOf(",")));
                y = Integer.parseInt(move.substring(move.indexOf(",") + 1, move.indexOf(" ")));
                flag = move.substring(move.indexOf("F"));
                x = oriboard.length - x;
                y = y - 1;
                revealHelper(x, y, new ArrayList<Integer>(), new ArrayList<Integer>());
            } else if (move.indexOf("U") > 0) {
                x = Integer.parseInt(move.substring(0, move.indexOf(",")));
                y = Integer.parseInt(move.substring(move.indexOf(",") + 1, move.indexOf(" ")));
                flag = move.substring(move.indexOf("U"));
                x = oriboard.length - x;
                y = y - 1;
                revealHelper(x, y, new ArrayList<Integer>(), new ArrayList<Integer>());
            } else {
                x = Integer.parseInt(move.substring(0, move.indexOf(",")));
                y = Integer.parseInt(move.substring(move.indexOf(",") + 1));
                flag = " ";
                x = oriboard.length - x;
                y = y - 1;
                revealHelper(x, y, new ArrayList<Integer>(), new ArrayList<Integer>());
            }
            if (oriboard[x][y] == 'X' && flag.equals(" "))
                break;
            countZero = 0;
            countFlag = 0;
            for (int i = 0; i < oriboard.length; i++) {
                for (int j = 0; j < modiboard[i].length; j++) {
                    if (modiboard[i][j] == '0')
                        System.out.print("o ");
                    else
                        System.out.print(modiboard[i][j] + " ");

                    if (modiboard[i][j] == '0' && modiboard[i][j] != 'F')
                        countZero++;
                    if (modiboard[i][j] == 'F')
                        countFlag++;

                }
                System.out.println();
            }

        }
        if (oriboard[x][y] == 'X') {
            for (int i = 0; i < oriboard.length; i++) {
                for (int j = 0; j < modiboard[i].length; j++) {
                    if (oriboard[i][j] == 'X')
                        modiboard[i][j] = 'X';
                    if (modiboard[i][j] == '0')
                        System.out.print("o ");
                    else
                        System.out.print(modiboard[i][j] + " ");
                }

                System.out.println();
            }
            System.out.println("You lost, better luck next time.");
        } else if (numMines() == (countZero + countFlag))
            System.out.println("Congratulations, you won.");

    }

    boolean outBounds(int x, int y) {
        return x < 0 || y < 0 || x > col-1 || y > row-1;
    }

    public int reveal(int curX, int curY) {
        int sum = 0;

        if (!outBounds(curX, curY))
            if (oriboard[curX][curY] == 'X')
                sum++;
        if (!outBounds(curX - 1, curY))
            if (oriboard[curX - 1][curY] == 'X')
                sum++;
        if (!outBounds(curX, curY - 1))
            if (oriboard[curX][curY - 1] == 'X')
                sum++;
        if (!outBounds(curX - 1, curY - 1))
            if (oriboard[curX - 1][curY - 1] == 'X')
                sum++;
        if (!outBounds(curX + 1, curY))
            if (oriboard[curX + 1][curY] == 'X')
                sum++;
        if (!outBounds(curX, curY + 1))
            if (oriboard[curX][curY + 1] == 'X')
                sum++;
        if (!outBounds(curX + 1, curY + 1))
            if (oriboard[curX + 1][curY + 1] == 'X')
                sum++;
        if (!outBounds(curX - 1, curY + 1))
            if (oriboard[curX - 1][curY + 1] == 'X')
                sum++;
        if (!outBounds(curX + 1, curY - 1))
            if (oriboard[curX + 1][curY - 1] == 'X')
                sum++;

        if (!outBounds(curX, curY))
            if (sum == 0) {
                modiboard[curX][curY] = '-';
            } else if(modiboard[curX][curY] == 'F')
                modiboard[curX][curY] = 'F';
            else if (oriboard[curX][curY] == 'X')
                modiboard[curX][curY] = '0';
            else
                modiboard[curX][curY] = (char) ('0' + sum);
        if (!outBounds(curX, curY))
            if (modiboard[curX][curY] == '0')
                this.fin = false;

        return sum;
    }

    public void revealHelper(int x, int y, ArrayList<Integer> visitedX, ArrayList<Integer> visitedY) {

        boolean isVisited = false;

        for (int i = 0; i < visitedX.size(); i++)
            if (visitedX.get(i) == x && visitedY.get(i) == y)
                isVisited = true;


        if (!isVisited) {
            visitedX.add(x);
            visitedY.add(y);

            if (!outBounds(x, y) && flag.equals("F") && modiboard[x][y] == '0' && visitedX.size() == 1)
                modiboard[x][y] = 'F';
            else if (!outBounds(x, y) && flag.equals("F") && modiboard[x][y] != '0' && modiboard[x][y] != 'F')
                System.out.println("Open cells cannot be flagged.");
            else if (!outBounds(x, y) && flag.equals("U") && modiboard[x][y] == 'F') {
                modiboard[x][y] = '0';
            } else if (!outBounds(x, y) && flag.equals("U") && modiboard[x][y] != 'F')
                System.out.println("Only flagged cells can be unflagged.");
            else if (!outBounds(x, y) && modiboard[x][y] == 'F' && flag.equals(" "))
                System.out.println("Flagged cells cannot be opened.");
            else if (!outBounds(x, y) && modiboard[x][y] != '0'&& flag.equals(" ")  && visitedX.size() == 1)
                System.out.println("Cell is already open.");
            else if (!outBounds(x, y) && flag.equals("F") && modiboard[x][y] == 'F')
                System.out.println("The cell is already flagged.");
            else {
                if (!outBounds(x, y))
                    reveal(x, y);
                if (!outBounds(x + 1, y))
                    if (reveal(x + 1, y) == 0)
                        revealHelper(x + 1, y, visitedX, visitedY);
                if (!outBounds(x, y + 1))
                    if (reveal(x, y + 1) == 0)
                        revealHelper(x, y + 1, visitedX, visitedY);
                if (!outBounds(x + 1, y + 1))
                    if (reveal(x + 1, y + 1) == 0)
                        revealHelper(x + 1, y + 1, visitedX, visitedY);
                if (!outBounds(x - 1, y))
                    if (reveal(x - 1, y) == 0)
                        revealHelper(x - 1, y, visitedX, visitedY);
                if (!outBounds(x, y - 1))
                    if (reveal(x, y - 1) == 0)
                        revealHelper(x, y - 1, visitedX, visitedY);
                if (!outBounds(x - 1, y - 1))
                    if (reveal(x - 1, y - 1) == 0)
                        revealHelper(x - 1, y - 1, visitedX, visitedY);
                if (!outBounds(x - 1, y + 1))
                    if (reveal(x - 1, y + 1) == 0)
                        revealHelper(x - 1, y + 1, visitedX, visitedY);
                if (!outBounds(x + 1, y - 1))
                    if (reveal(x + 1, y - 1) == 0)
                        revealHelper(x + 1, y - 1, visitedX, visitedY);
            }
        }
    }

    public int numMines() {
        if (dif == 'E')
            return size * 15 / 100;
        else if (dif == 'M')
            return size * 25 / 100;
        else if (dif == 'H')
            return size * 40 / 100;
        else
            return -1;
    }

    public static void main(String[] args) {
        MineSweeper a = new MineSweeper();
    }
}
