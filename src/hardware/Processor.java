package hardware;

/**
 * Created by Binio on 2017-01-21.
 */
public class Processor {
//    public static final int NUMBER_OF_CORES = 4;


    private int x;
    private int y;
    private int result;
    private int[] finalPosition;
    private boolean busy = false;


    public void multiply(int x, int y){
        result = x*y;
    }

    public int getResult() {
        return result;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[] getFinalPosition() {
        return finalPosition;
    }

    public void setFinalPosition(int[] finalPosition) {
        this.finalPosition = finalPosition;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }
}
