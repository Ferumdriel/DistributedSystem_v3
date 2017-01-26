package math.tests;

import math.Matrix;

/**
 * Created by Binio on 2017-01-26.
 */
public class ExampleMatrix {

    Matrix m;

    public ExampleMatrix(){
        int a = 5;
        int b = 5;
        int[][] test = new int[a][b];
        for(int i = 0; i < a; i++){
            for(int j  = 0; j < b; j++){
                test[i][j] = i + j;
            }
        }
        m = new Matrix(test);
    }

    public Matrix getM() {
        return m;
    }
}
