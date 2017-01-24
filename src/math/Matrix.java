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

    public Matrix(int[][] matrix, int[] positions){
        this.matrix = matrix;
        positionNumbers = positions;
        columnNumbers = positions;
        rowNumbers = positions;
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
            for (int i = 0; i < matrix[0].length; i++) {
                try {
                    s += String.format("%4d,", columnNumbers[i]);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Cos poszlo nie tak");
                }
            }
        s+= "\n";

            for (int i = 0; i < matrix.length; i++) {
                try {
                s += String.format("%3d:%s", rowNumbers[i], "[");
                }catch(IndexOutOfBoundsException e){
                    System.out.println("Cos poszlo nie tak");
                }
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
