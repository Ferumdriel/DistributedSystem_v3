import math.Matrix;

/**
 * Created by Binio on 2017-01-21.
 */
public class Launcher {

    public static void main(String[] args){
        int a = 10, b = 5;

        int[][] test = new int[a][b];
        for(int i = 0; i < a; i++){
            for(int j  = 0; j < b; j++){
                test[i][j] = i + j;
            }
        }

        Matrix m = new Matrix(test);
    }
}
