package puzzlers.tasks;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Shortest path
 * <p>
 * Problem: Consider you've been given a matrix, m x n or 10 x 10, containing only 0s and 1s.
 * By convention, 1 means safe land, while 0 represents unsafe land.
 * More precisely, a 0 represents a sensor that should not be activated.
 * Moreover, all eight adjacent cells can activate the sensor.
 * Write a snippet of code that computes the shortest route from any cells of the first column to any cell of the last column.
 * You can only move one step at a time; either left, right, up, or down.
 * The resulting route (if its exists) should contain only values of 1.
 */

public class ShortestSafePath {

    // Create a board cell for convenience
    private static class Cell {

        int r;
        int c;
        // This is distance from the start cell
        int distance;

        Cell(int r, int c, int distance) {
            this.r = r;
            this.c = c;
            this.distance = distance;
        }
    }

    // Just to save time, hardcode board dimensions from the task
    private static final int M = 10;
    private static final int N = 10;

    // 4 possible movements from a cell (top, right, bottom, left)
    private static final int[] ROW_4 = {-1, 0, 0, 1};
    private static final int[] COL_4 = {0, -1, 1, 0};

    // 8 possible movements from a cell (top, right, bottom, left and diagonals)
    private static final int[] ROW_8 = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] COL_8 = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static int shortestPath(int[][] board) {

        // Mark cells and print the updated board
        markUnsafeCells(board);

        // Use BFS traversal to find the shortest path
        return findShortestPath(board);
    }

    private static void markUnsafeCells(int[][] board) {
        // Mark adjacent cells of sensors as unsafe
        // Using those 8 dimensions, so it can be skipped
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < ROW_8.length; k++) {
                    if (board[i][j] == 0 && isValid(i + ROW_8[k], j + COL_8[k])
                            && board[i + ROW_8[k]][j + COL_8[k]] == 1) {
                        board[i + ROW_8[k]][j + COL_8[k]] = -1;
                    }
                }
            }
        }

        // Another pass is needed to update it to 0 and print (this is optional for debugging)
        // Cannot set to 0 wight away since prev markup would fail
        for (int i = 0; i < M; i++) {
            System.out.println();
            for (int j = 0; j < N; j++) {
                if (board[i][j] == -1) {
                    board[i][j] = 0;
                }
                System.out.print(board[i][j] + "  ");
            }
        }
    }

    // Find minimum number of steps (shortest path or distance) required
    // to traverse the board from first to the last column
    private static int findShortestPath(int[][] board) {

        // Let's mark cells that are visited
        boolean[][] visited = new boolean[M][N];

        Queue<Cell> queue = new ArrayDeque<>();

        // Let's check entire first column cells, only safe ones of course
        for (int r1 = 0; r1 < M; r1++) {
            // if the cell is safe, mark it as visited and
            // enqueue it by assigning it distance as 0 from itself
            if (board[r1][0] == 1) {
                queue.add(new Cell(r1, 0, 0));
                visited[r1][0] = true;
            }
        }

        // Now iterate from cells from first column
        while (!queue.isEmpty()) {

            // Pop the front node from queue and process it
            int rIdx = queue.peek().r;
            int cIdx = queue.peek().c;
            int dist = queue.peek().distance;

            queue.poll();

            // If destination is found then return minimum distance
            if (cIdx == N - 1) {
                return (dist + 1);
            }

            // Check for all 4 possible movements from current cell and enqueue each valid movement
            for (int k = 0; k < ROW_4.length; k++) {
                if (isValid(rIdx + ROW_4[k], cIdx + COL_4[k])
                        && isSafe(board, visited, rIdx + ROW_4[k], cIdx + COL_4[k])) {

                    // Mark it as visited and push it into queue with (+1) distance
                    visited[rIdx + ROW_4[k]][cIdx + COL_4[k]] = true;
                    queue.add(new Cell(rIdx + ROW_4[k], cIdx + COL_4[k], dist + 1));
                }
            }
        }

        return -1;
    }

    // check if it is safe to go to position (r, c) from current position
    private static boolean isSafe(int[][] board, boolean[][] visited, int r, int c) {
        return (board[r][c] == 1 && !visited[r][c]);
    }

    // check if (r, c) is on the board
    private static boolean isValid(int r, int c) {
        return (r < M && c < N && r >= 0 && c >= 0);
    }
}
