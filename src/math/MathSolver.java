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
    private boolean matricesSplit = false;

    public MathSolver(AbstractComputer computer){
        this.computer = computer;
    }

    public void addMatrixes(Matrix m1, Matrix m2){
        this.m1 = m1;
        this.m2 = m2;
    }

    private boolean checkIfCorrect(){
        return m1.getColumns()==m2.getRows();
    }

    public void solveMatrix(){
        MatrixDivider divider = computer.getDivider();
        divider.addMatrices(m1,m2);
        divider.divide();

        int limit = computer.getProcessors().length;
        int[][] matrix = new int[m1.getColumns()][m2.getRows()];
        int[] rows = m2.getColumnNumbers();
        int[] columns = m1.getRowNumbers();


        int k = 0;
        for(int i = 0; i < divider.getPairs().size(); i++){ //go through the list of all pairs
            for(int j = 0; j < divider.getPairs().get(i).length; j++){ //go through all rows of pairs (their sum will give final value)
                if(!computer.getProcessors()[k].isBusy()) {
                    computer.getProcessors()[k].multiply(divider.getPairs().get(i)[j][0], divider.getPairs().get(i)[j][1]);
                    matrix[divider.getPositions().get(i)[0]][divider.getPositions().get(i)[1]] = computer.getProcessors()[k].getResult();
                }k++;
                if(k >= limit){
                    k = 0;
                }
            }

        }
        computer.setFinalM(new Matrix(matrix, rows, columns));
        computer.setMatrixSolved(true);
    }

    public void splitMatrix(int splitAmount){ //splitAmount == amount of computers that can receive parts of this matrix
        if (checkIfCorrect()) {
            if (!computer.isReadyToExecute() && !computer.isAlreadySplit()) {
                splitToSendFurther(splitAmount);
            }else{
                solveMatrix();
            }
        }else {
                System.out.println("Niepoprawny rozmiar macierzy.");
            }

    }

    private void splitToSendFurther(int splitAmount){
        int counter = 0;
        int connectionsLeftToHandle = splitAmount;
        int linesLeftToSplit = m1.getRows();
        for (int i = 0; i < splitAmount; i++) {
            /** EACH PART OF ARRAY WILL BE SENT TO ANOTHER COMPUTER AND WILL BE PROCESSED BY IT'S MATHSOLVER **/
//            copyArraysByRows(counter, connectionsLeftToHandle);
            copyArraysRows(counter, connectionsLeftToHandle);
//            copyArraysByColumns(counter, connectionsLeftToHandle);
            counter += splitM1.get(i).getRows(); //m1.getRows() or m2.getColumns() as they need to be the same number
            connectionsLeftToHandle--;
        }
        splitM2.add(m2); //m2 is untouched until everything is ready for calculations
        matricesSplit = true;
    }

    private void splitToSendToProcessors(){
        MatrixDivider divider = new MatrixDivider(m1, m2);

    }

    private void copyArraysByColumns(int startingPosition, int splitAmount){
        int[][] matrix = m1.getMatrix();

        int amountToSplit = (matrix[0].length-startingPosition)/splitAmount; //
        int k = 0; //we're creating new (smaller) matrix so we start from 0
        int[] columns = new int[amountToSplit];
        int[] rows = new int[matrix.length];
        int[][] tmp = new int[matrix.length][amountToSplit];
        boolean rowsRdy = false; //supportive boolean to save rowNumber which should be equal to all matrix.length
        int numberOfIterations = amountToSplit + startingPosition;

        //we have to work on 2 matrixes (old and new one) at the same time so 'i', 'j', 'k'
        //'i' might not be 0 from the original matrix
        for(int i = startingPosition; i < numberOfIterations; i++){
            for(int j = 0; j < matrix.length; j++){
                if(!rowsRdy) {
                    rows[j] = m1.getRowNumbers()[j];
                }
                tmp[j][k] = matrix[j][i];
            }
            rowsRdy = true;
            columns[k] = m1.getColumnNumbers()[i];
            k++;
        }
        splitM1.add(new Matrix(tmp, rows, columns));
    }

    private void copyArraysRows(int startingPosition, int splitAmount){
        int[][] matrix = m1.getMatrix();

        int amountToSplit = (matrix.length-startingPosition)/splitAmount; //
        int k = 0;
        int[] rows = new int[amountToSplit];
        int[] columns = new int[matrix[0].length];
        int[][] tmp = new int[amountToSplit][matrix[0].length];
        int numberOfIterations = amountToSplit + startingPosition;

        for(int i = 0; i < matrix[0].length; i++){
            columns[i] = i;
        }

        for(int i = startingPosition; i < numberOfIterations; i++){
            for(int j = 0; j < matrix[0].length; j++){
                tmp[k][j] = matrix[i][j];
            }
            rows[k] = m1.getRowNumbers()[i];
            k++;
        }
        splitM1.add(new Matrix(tmp, rows, columns));
    }

    private void copyArraysByRows(int startingPosition, int splitAmount){
        int[][] matrix = m2.getMatrix();

        int amountToSplit = (matrix.length-startingPosition)/splitAmount; //
        int k = 0;
        int[] rows = new int[amountToSplit];
        int[] columns = new int[matrix[0].length];
        boolean columnsRdy = false; //supportive boolean to save columnNumber which should be equal to all matrix[0].length

        int[][] tmp = new int[amountToSplit][matrix[0].length];
        int numberOfIterations = amountToSplit + startingPosition;

        for(int i = startingPosition; i < numberOfIterations; i++){
            for(int j = 0; j < matrix[0].length; j++){
                if(!columnsRdy) {
                    columns[j] = m2.getColumnNumbers()[j];
                }
                tmp[k][j] = matrix[i][j];
            }
            columnsRdy = true;
            rows[k] = m2.getRowNumbers()[i];
            k++;
        }
        splitM2.add(new Matrix(tmp, rows, columns));
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

    public Matrix getM1() {
        return m1;
    }

    public boolean isMatricesSplit() {
        return matricesSplit;
    }

    public ArrayList<Matrix> getSplitM1() {
        return splitM1;
    }

    public ArrayList<Matrix> getSplitM2() {
        return splitM2;
    }

    public Matrix getM2() {
        return m2;
    }
}
