package math.tests;

import math.Matrix;
import math.MatrixDivider;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Binio on 2017-01-25.
 */
public class MatrixDividerTest {

    @Test
    public void divide() throws Exception {

        MatrixDivider divider = prepDivider();
        divider.divide();

        int expectedPosition = 2; //positions[1][4] == get(5)
        assertEquals(expectedPosition, divider.getPositions().get(20)[0]); //get(20)[0] -> m1.row = 2 {0,1,2,3,4,5,6,7,8,9}
    }
    // SUPPORT METHOD FOR DIVIDE()
    private MatrixDivider prepDivider(){
        int a = 5;
        int b = 10;
        int[][] test = new int[a][b];
        for(int i = 0; i < a; i++){
            for(int j  = 0; j < b; j++){
                test[i][j] = i + j;
            }
        }
        int[][] test2 = new int[b][a];
        for(int i = 0; i < b; i++){
            for(int j  = 0; j < a; j++){
                test2[i][j] = i + j;
            }
        }
        return new MatrixDivider(new Matrix(test2), new Matrix(test));
    }


}