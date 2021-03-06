package math;

import java.util.ArrayList;

/**
 * Created by Binio on 2017-01-24.
 */
public class MatrixDivider {
    private Matrix m1;
    private Matrix m2;
    private ArrayList<int[][]> pairs; // {X,Y} where X and Y are values from m1 (row, position Z) and m2 (column, position V)
    private ArrayList<int[]> positions; // {Z,V} Z is final row and V final column in Matrix
    //every position[] is directly connected with pairs[][] as their multiplication result should be put into position coords

    public MatrixDivider(){
        pairs = new ArrayList<>();
        positions = new ArrayList<>();
    }

    public MatrixDivider(Matrix m1, Matrix m2){
        this.m1 = m1;
        this.m2 = m2;
        pairs = new ArrayList<>();
        positions = new ArrayList<>();
    }

    public void addMatrices(Matrix m1, Matrix m2){
        this.m1 = m1;
        this.m2 = m2;
    }

    // to get value in 1 field you have to multiply specific row of m1 by specific column of m2
    public void divide(){
        int[] tmpM1;
        int tmpPairs[][] = new int[m1.getColumns()][2];
        int[] tmpPos = new int[2];
        for(int i = 0; i < m1.getRows(); i++){ //go through every row of m1
            tmpM1 = m1.getMatrix()[i]; //read specific row of first matrix
            for(int j = 0; j < m2.getColumns(); j++){ //go through every column of m2
                for(int k = 0; k < m1.getColumns(); k++){ //go through every value in specific column
                    tmpPairs[k][0] = tmpM1[k];
                        tmpPairs[k][1] = m2.getMatrix()[k][j];
                }
                pairs.add(cloneDoubleArray(tmpPairs));
                tmpPos[0] = i;
                tmpPos[1] = j;
                positions.add(cloneArray(tmpPos));
            }
        }
    }

    private int[][] cloneDoubleArray(int[][] array){
        int[][] tmp = new int[array.length][array[0].length];
        for(int i = 0; i < tmp.length; i++){
            for(int j = 0; j < tmp[0].length; j++){
                tmp[i][j] = array[i][j];
            }
        }
        return tmp;
    }

    private int[] cloneArray(int[] array){
        int[] tmp = new int[array.length];
        for(int i = 0; i < tmp.length; i++){
            tmp[i] = array[i];
        }
        return tmp;
    }

    public ArrayList<int[][]> getPairs() {
        return pairs;
    }

    public ArrayList<int[]> getPositions() {
        return positions;
    }
}
