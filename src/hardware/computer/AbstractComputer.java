package hardware.computer;

import hardware.Processor;
import math.MathSolver;
import math.Matrix;
import math.MatrixDivider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Binio on 2017-01-21.
 */
public abstract class AbstractComputer implements Runnable{
    public static final int PROCESSORS_AMOUNT = 2;


    protected int id;
    protected MathSolver solver = new MathSolver(this);
    protected MatrixDivider divider = new MatrixDivider();
    protected Processor[] processors = new Processor[PROCESSORS_AMOUNT];

    //process of splitting
    protected boolean alreadySplit = false;
    protected boolean readyToExecute = false;

    protected boolean matrixSolved = false;

    protected boolean running = false;
    protected Thread thread;

    protected List<MultiComputer> sonsList;
    protected AbstractComputer parent;
    protected Matrix m1;
    protected Matrix m2;
    protected Matrix finalM;
    protected List<Matrix> sonsResults = new ArrayList<>();
    protected boolean matrixSentToParent = false;

    //podejscie nr 2
//    protected boolean sentToSons = false;
    protected boolean sentToParent = false;
    protected boolean matrixReady = false; //final matrix in Master Computer
    protected boolean readyToSolve = false;

    /** PODEJSCIE NR 3 **/
    protected boolean sentToSons = false;
    protected boolean gotMatrixFromParent = false;

    public void trick(){
//        System.out.printf("%3d:%20s%3d\n",id,"rozmiar wynikow, size:",sonsResults.size());
//        System.out.printf("%3d:%20s\n",id,"dziala");
        //if didn't get matrices then do nothing
        //if you're master check conditions anyway
//        if(hasMatricesToSolve() || isMaster()) {
        if(gotMatrixFromParent || isMaster()) {
//            System.out.printf("%3d:%20s\n",id,"hasMatricesToSolve");
//            System.out.printf("%3d:%20s%5s\n",id,"sentToParent = ", sentToParent);
            //if already sent results back to parent - don't do anything, you can now chill
            if (!sentToParent) { //hasn't sent results yet to parent
                if (doesHaveSons()) {   // !sonsList.isEmpty()
//                    System.out.printf("%3d:%20s\n",id,"has sons");
                    if (doesHaveParent()) { //isn't master computer
                        if (!sentToSons && parent.sentToSons) { //hasn't sent to sons yet and already got from it's parent
                            split();
                            sendToSons();
                        }
                        if(gotAllMatricesFromSons()){
//                            System.out.printf("%3d:%20s\n",id,"Got matrices from all sons");
                            combineMatrices();
                            sendToParent();
                        }
                    }else{ //is master computer
//                        System.out.printf("%3d:%20s\n",id,"!doesHaveParent");
                        if (!sentToSons) { //hasn't sent to sons yet
                            split();
                            sendToSons();
                        }
                        if(gotAllMatricesFromSons()){
                            combineMatrices();
                            sendToParent();
                        }
                    }
                } else {
                    calculate();
                    sendToParent();
                }
//                sendToParent();
            }
        }
    }

    protected boolean jobIsDone(){
        if(doesHaveParent()){
            return sentToParent;
        }else{
            return hasFinalResult();
        }
    }


    protected boolean gotAllMatricesFromSons(){
//        System.out.printf("%3d:%20s:%3d\n",id,"matrices from x sons",sonsResults.size());
        return sonsResults.size()==sonsList.size();
    }

    protected void checkProcedure(){

    }

    protected boolean doesHaveParent(){
        return parent!=null;
    }

    protected boolean doesHaveSons(){
        return !sonsList.isEmpty();
    }

    protected boolean hasMatricesToSolve(){
        return m1!=null && m2!=null;
    }

    protected boolean isMaster(){
        return parent==null;
    }

    public boolean hasFinalResult(){
        return finalM!=null;
    }

    public List<Matrix> getSonsResults() {
        return sonsResults;
    }



    /** KONIEC PODEJSCIA NR 3 **/

    public boolean isReadyToExecute(){
        if(solver.getM1().getRows()==1 || sonsList.isEmpty()){
            return true;
        }
        else return false;
    }

    protected void generateProcessors(){
        for(int i = 0; i < processors.length; i++){
            processors[i] = new Processor();
        }
    }

    public MathSolver getSolver() {
        return solver;
    }

    public void setMatrices(Matrix m1, Matrix m2){
        this.m1 = m1;
        this.m2 = m2;
        solver.addMatrixes(m1,m2);
    }

    protected void sendMatrices(){
        int k = 0;
        for(MultiComputer m: sonsList){
            m.setMatrices(solver.getSplitM1().get(k++), solver.getM2());
            m.gotMatrixFromParent = true;
//            System.out.printf("%3d:%20s:%3d\n",id,"sentMatrixTo",m.id);
        }
        sentToSons = true;
    }

    protected void getInfoAboutSentMatrices(){
        for(AbstractComputer m: sonsList){
            System.out.println("Son: " + m.id);
            System.out.println("M1: ");
            m.m1.display();
            System.out.println("M2: ");
            m.m2.display();
        }
    }

    protected void getFinalMatrices(){
        int k = 0;
        boolean[] checkedList = new boolean[sonsList.size()]; //false by default
        while(k < sonsList.size()) {
            for (int i = 0; i < sonsList.size(); i++) {
                AbstractComputer mc = sonsList.get(i);
                if (mc.matrixSolved && !checkedList[i]) {
                    sonsResults.add(mc.finalM);
                    mc.matrixSentToParent = true;
                    checkedList[i] = true;
                    k++;
                }
            }
        }
    }

    /** PODEJSCIE NR 3 **/

    protected void combineMatrices(){
//        System.out.printf("%3d:%20s%3d\n",id,"combineMatrices, size:",sonsResults.size());
        if(!matrixSolved) {
            int rowsSum = sumRowsAmount();

            int[][] tmp = combineToFinalMatrix(rowsSum, sonsResults.get(0).getColumns());
            int[] rows = copyRowNumbers(rowsSum);
            int[] columns = createDefaultColumnsNumbers();

            finalM = new Matrix(tmp, rows, columns);
            matrixSolved = true;
//            System.out.printf("%3d:%20s\n",id,"matrixSolved = true");
        }
    }

    protected int sumRowsAmount(){
        int rowsSum = 0;
        for (Matrix m : sonsResults) {
            rowsSum += m.getRows();
        }
        return rowsSum;
    }

    protected int[] createDefaultColumnsNumbers(){
        int[] columns = new int[sonsResults.get(0).getColumns()];
        for (int i = 0; i < columns.length; i++) {
            columns[i] = i;
        }
        return columns;
    }

    protected int[] copyRowNumbers(int rowsSum){
        int[] rows = new int[rowsSum];
        int tmpRow = 0;
        for (int i = 0; i < sonsResults.size(); i++) {
            for (int j = 0; j < sonsResults.get(i).getRows(); j++) {
                rows[tmpRow++] = sonsResults.get(i).getRowNumbers()[j];
            }
        }
        rows = bubbleSort(rows);
        return rows;
    }

    protected int[][] combineToFinalMatrix(int rowsSum, int columns){
        int[][] tmp = new int[rowsSum][columns];

        int tmpRow = 0;
        for (Matrix m : sonsResults) {
            for (int i = 0; i < m.getRows(); i++) {
                for (int j = 0; j < m.getColumns(); j++) {
                    tmp[tmpRow][j] = m.getMatrix()[i][j];
                }
                tmpRow++;
            }
        }

//        for (Matrix m : sonsResults) {
//            for (int i = 0; i < m.getRows(); i++) {
//                for (int j = 0; j < m.getColumns(); j++) {
//                    tmp[m.getRowNumbers()[i]][j] = m.getMatrix()[i][j];
//                }
//            }
//        }
        return tmp;
    }



    /** KONIEC PODEJSCIA NR 3 **/

    /** PODEJSCIE NR X **/
    //solve will tick many times so we have to keep checking if conditions are
//    protected void solve(){
//        if(!sonsList.isEmpty() && !sentToSons){ //if has sons and hasn't sent it to them yet = split matrix and send
//            split();
//            sendToSons();
//        }else if(isSonsMatrixListFilled()){ //if there's no more sons and it already got results from them then it has to solve it
//            if(!sentToParent && parent!=null){
//                calculate();
//                sendToParent();
//            }
//            else if(parent==null){
//                calculate();
//                matrixReady = true;
//            }
//        }
//    }

    protected void split(){
        solver.splitMatrix(sonsList.size());
//        System.out.printf("%3d:%20s:%3d\n",id,"split dla x synow", sonsList.size());
    }
    protected void sendToSons(){
        sendMatrices();
        System.out.printf("%3d:%20s\n",id,"sendToSons");
    }

    protected void divide(){
        divider.divide();
    }
    protected void calculate(){
        solver.solveMatrix();
//        System.out.printf("%3d:%20s\n",id,"calculate");
    }
    protected void sendToParent(){
        if(doesHaveParent() && !sentToParent) {
            parent.sonsResults.add(finalM);
            sentToParent = true;
            System.out.printf("%3d:%20s:%3d\n",id,"sent to parent", parent.id);
        }
    }
    protected boolean isSonsMatrixListFilled(){
        return sonsList.size()==sonsResults.size();
    }


    /** KONIEC PODEJSCIA NR X **/

    public void tick(){
        if(m1!=null && m2!= null) {
            solver.splitMatrix(sonsList.size());

            if (!solver.isMatricesSplit()) {
                solver.splitMatrix(sonsList.size());
            }
            if (!sentToSons) {
                sendMatrices();
                getInfoAboutSentMatrices();
            }
            //from sons
            getFinalMatrices();
            //prepare to send something to parents
            combineMatrices();
        }


    }

    public List<MultiComputer> getSonsList() {
        return sonsList;
    }

    public MatrixDivider getDivider() {
        return divider;
    }

    public Processor[] getProcessors() {
        return processors;
    }

    public Matrix getFinalM() {
        return finalM;
    }

    public void setFinalM(Matrix finalM) {
        this.finalM = finalM;
    }

    public boolean isMatrixSolved() {
        return matrixSolved;
    }

    public void setMatrixSolved(boolean matrixSolved) {
        this.matrixSolved = matrixSolved;
    }

    protected int[] bubbleSort(int[] numArray) {

        int n = numArray.length;
        int temp = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < (n - i); j++) {

                if (numArray[j - 1] > numArray[j]) {
                    temp = numArray[j - 1];
                    numArray[j - 1] = numArray[j];
                    numArray[j] = temp;
                }

            }
        }
        return numArray;
    }

    public boolean isAlreadySplit() {
        return alreadySplit;
    }

    public void setAlreadySplit(boolean alreadySplit) {
        this.alreadySplit = alreadySplit;
    }

    public String toString(){
        return "Computer: [" + id + "]";
    }

    public int getId(){
        return id;
    }

    public void displayMatrices(){
        System.out.println("M1:");
        m1.display();
        System.out.println("M2:");
        m2.display();
    }

}
