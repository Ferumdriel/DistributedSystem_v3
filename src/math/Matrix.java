package math;

import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

import java.util.ArrayList;

/**
 * Created by Binio on 2017-01-21.
 */
public class Matrix{
    private int[][] matrix;
    private int[] rowNumbers;
    private int[] columnNumbers;
    private int[] positionNumbers;
    ArrayList<Integer[]> list = new ArrayList<>();


    public Matrix(int[][] matrix){
        this.matrix = matrix;
        setDefaultNumbers();
    }

    public Matrix(int[][] matrix, int[] rows, int[] columns){
        this.matrix = matrix;
        columnNumbers = columns;
        rowNumbers = rows;
        positionNumbers = assignPositions();
    }

    private void setDefaultNumbers(){
        rowNumbers = new int[matrix.length];
        columnNumbers = new int[matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            rowNumbers[i] = i;
        }
        for(int i = 0; i < matrix[0].length; i++){
            columnNumbers[i] = i;
        }
    }

    public Matrix(Matrix m){
        copy(m);
    }

    private int[] copyInt(int[] copy){
        int[] tmp = new int[copy.length];
        for(int i = 0; i < copy.length; i++){
            tmp[i] = copy[i];
        }
        return tmp;
    }

    private int[] assignPositions(){
        if(rowNumbers.length == matrix.length){
            return columnNumbers;
        }else{
            return rowNumbers;
        }
    }

    private void copy(Matrix m){
        this.matrix = new int[m.matrix.length][m.matrix[0].length];
        rowNumbers = new int[matrix.length];
        columnNumbers = new int[matrix[0].length];
        for(int i = 0; i < matrix.length; i++){
            for(int j  = 0; j < matrix[0].length; j++){
                matrix[i][j] = m.matrix[i][j];
            }
        }
        this.rowNumbers = new int[m.rowNumbers.length];
        for(int i = 0; i < rowNumbers.length; i++){
            rowNumbers[i] = m.rowNumbers[i];
        }
        for(int i = 0; i < columnNumbers.length; i++){
            columnNumbers[i] = m.columnNumbers[i];
        }
    }

    public void listNumbers(){
        String s = "Rows(" + rowNumbers.length + "): ";
        for(int i = 0; i < rowNumbers.length; i++){
            s+= rowNumbers[i] + ", ";
        }
        s+= "\nColumns(" + columnNumbers.length + "): ";
        for(int i = 0; i < columnNumbers.length; i++){
            s+= columnNumbers[i] + ", ";
        }
        System.out.println(s);
    }

    public void display(){
        System.out.println(this);
    }

    public String toString(){
        String s = "";
        s+= String.format("%5s","");
        //prepare column numbers
            for (int i = 0; i < matrix[0].length; i++) {
//                s += String.format("%4d,", columnNumbers[i]);
                s += String.format("%4d,", columnNumbers[i]); //before adding it columnNumbers and rowNumbers need to be fixed (so each will represent properly)
            }
        s+= "\n";
            for (int i = 0; i < matrix.length; i++) {
                s += String.format("%3d:%s", rowNumbers[i], "["); //print proper row number before printing row values
                for (int j = 0; j < matrix[0].length; j++) {
                    s += String.format("%4d,", matrix[i][j]);
                }
                s += "]\n";
            }

        return s + "\n";
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getRows(){
        return matrix.length;
    }

    public int getColumns(){
        return matrix[0].length;
    }

    public int[] getRowNumbers() {
        return rowNumbers;
    }

    public int[] getColumnNumbers() {
        return columnNumbers;
    }
}
