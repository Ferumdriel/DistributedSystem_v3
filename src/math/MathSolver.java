package math;

import hardware.AbstractComputer;
import hardware.Processor;
import org.junit.runner.Computer;

import java.util.ArrayList;

/**
 * MathSolver is supposed to get matrixes and split them into smaller parts so Computer will be able to send them further
 */

public class MathSolver {

    private AbstractComputer computer; //so it'll be able to send results to computer on which it's working
    private Matrix m1; //1st matrix split by rows
    private Matrix m2; //2nd matrix split by column
    private ArrayList<Matrix> splitM1 = new ArrayList<>(); //1st matrix split to smaller parts
    private ArrayList<Matrix> splitM2 = new ArrayList<>(); //2nd matrix split to smaller parts

    public MathSolver(AbstractComputer computer){
        this.computer = computer;
    }

    public void addMatrixes(Matrix m1, Matrix m2){
        this.m1 = m1;
        this.m2 = m2;
    }

    private boolean checkIfCorrect(){
        return m1.getRows()==m2.getColumns() && m1.getColumns()==m2.getRows();
    }

    public void splitMatrix(int splitAmount){ //splitAmount == amount of computers that can receive parts of this matrix
        if (checkIfCorrect()) {
            int counter = 0;
            for (int i = 0; i < splitAmount; i++) {
                /** EACH PART OF ARRAY WILL BE SENT TO ANOTHER COMPUTER AND WILL BE PROCESSED BY IT'S MATHSOLVER **/
                copyArraysByRows(counter, splitAmount);
                copyArraysByColumns(m1.getMatrix(),counter, splitAmount);
                counter+=splitAmount;
            }
        }else{
            System.out.println("Niepoprawny rozmiar macierzy.");
        }
    }

    private void copyArraysByColumns(int[][] matrix, int startingPosition, int splitAmount){
        if(startingPosition + splitAmount >= matrix[0].length){ //to avoid issues when we're at the end of matrix and we have less rows/columns to split than splitAmount
            splitAmount = matrix[0].length - startingPosition;
        }
        int endOfCurrentSplit = startingPosition + splitAmount; //
        int k = 0; //we're creating new (smaller) matrix so we start from 0
        int[] positions = new int[splitAmount];
        int[][] tmp = new int[matrix.length][splitAmount];
        //we have to work on 2 matrixes (old and new one) at the same time so 'i', 'j', 'k'
        //'i' might not be 0 from the original matrix
        for(int i = startingPosition; i < endOfCurrentSplit; i++){
            for(int j = 0; j < matrix.length; j++){
                tmp[j][k] = matrix[j][i];
            }
            positions[k] = i;
            k++;
        }
        splitM1.add(new Matrix(tmp, positions));
    }

    private void copyArraysByRows(int startingPosition, int splitAmount){
        int[][] matrix = m2.getMatrix();
        if(startingPosition + splitAmount >= matrix.length){
            splitAmount = matrix.length - startingPosition;
        }
        int current = startingPosition;
        int k = 0;
        int[] positions = new int[splitAmount];
        int[][] tmp = new int[splitAmount][matrix[0].length];
        for(int i = current; i < startingPosition + splitAmount; i++){
            for(int j = 0; j < matrix[0].length; j++){
                tmp[k][j] = matrix[i][j];
            }
            positions[k] = i;
            k++;
        }
        splitM2.add(new Matrix(tmp, positions));
    }

    public void displaySplitLists(){
        String s = "M1: \n\n";
        for(Matrix m: splitM1){
            s+= m + "\n";
        }
        s+= "M2: \n\n";
        for(Matrix m: splitM2){
            s+= m + "\n";
        }
        System.out.println(s);
    }
}
