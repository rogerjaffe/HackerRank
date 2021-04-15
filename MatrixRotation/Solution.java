import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Solution {

    static final int WEST = 0, SOUTH = 1, EAST = 2, NORTH = 3;
    
    public static void matrixRotation(List<List <Integer> > matrix, int r) {
        int startRow = 0;
        int startCol = 0;
        int endRow = matrix.size()-1;
        int endCol = matrix.get(0).size()-1;
        for (int i = 0; i < r; i++)
            Solution.rotate(matrix, startRow, startCol, endRow, endCol);
            
        printMatrix(matrix);
    }
    
    public static void rotate(List<List <Integer> > matrix, 
        int startRow, int startCol, int endRow, int endCol) {
        
        // Check to see if we have any interior matrix left to rotate
        if ((startRow > endRow) || (startCol > endCol)) 
            return;

        // Set up row and column boundaries
        int row = startRow, col = startCol;
        int rowMax = matrix.size() - 1;
        int colMax = matrix.get(0).size() - 1;
        int[][] directions = { 
            {0,1},  // WEST
            {1,0},  // SOUTH
            {0,-1}, // EAST
            {-1,0}  // NORTH
        };
        
        // Start by heading west and save the first item to restore at the end
        int[] direction = directions[Solution.WEST];
        int temp = matrix.get(row).get(col);
        
        // Start the rotation...
        do {
            // Get the next position
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];
            
            // Copy the next position to this position
            matrix.get(row).set(col, matrix.get(nextRow).get(nextCol));
            
            // Check for direction change
            if (nextRow == startRow && nextCol == endCol) 
                direction = directions[Solution.SOUTH];
            if (nextRow == endRow && nextCol == endCol) 
                direction = directions[Solution.EAST];
            if (nextRow == endRow && nextCol == startCol)
                direction = directions[Solution.NORTH];
                
            // If we're done put in the first element
            if (nextRow == startRow && nextCol == startCol) 
                matrix.get(row).set(col, temp);
                
            // Update the row, col
            row = nextRow;
            col = nextCol;
        } while ((row != startRow) || (col != startCol));
        
        // Adjust the boundaries and rotate the next interior matrix
        Solution.rotate(matrix, startRow+1, startCol+1, endRow-1, endCol-1);
    }
    
    public static void printMatrix(List<List <Integer> > matrix) {
        for (int row = 0; row < matrix.size(); row++) {
            for (int col = 0; col < matrix.get(0).size(); col++) {
                System.out.print(matrix.get(row).get(col)+" ");
            }
            System.out.println();
        }      
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input2.txt"));

        String[] mnr = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int m = Integer.parseInt(mnr[0]);

        int n = Integer.parseInt(mnr[1]);

        int r = Integer.parseInt(mnr[2]);

        List<List<Integer>> matrix = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            String[] matrixRowTempItems = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

            List<Integer> matrixRowItems = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                int matrixItem = Integer.parseInt(matrixRowTempItems[j]);
                matrixRowItems.add(matrixItem);
            }

            matrix.add(matrixRowItems);
        }

        matrixRotation(matrix, r);

        bufferedReader.close();
    }
}