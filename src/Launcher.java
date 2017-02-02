import math.Matrix;
import system.MultiComputerSystem;
import system.MultiProcessorSystem;
import system.SystemRequirements;

import java.util.Scanner;

/**
 * Created by Binio on 2017-01-21.
 */
public class Launcher {


    public static void main(String[] args){
        System.out.println("Po wykonaniu obliczen prosze wcisnac enter, aby przejsc do czesci wynikowej.");
        System.out.println("W celu zapewnienia niezaleznosci wynikow zastosowana jest przerwa pomiedzy rozpoczeciem pracy kolejnego systemu.");
        try{
            Thread.sleep(10000);
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        MultiComputerSystem mcs = new MultiComputerSystem();
        mcs.start();

        try{
            Thread.sleep(SystemRequirements.a*100);
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }

        MultiProcessorSystem mps = new MultiProcessorSystem();
        mps.start();

        try{
            Thread.sleep(10000);
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }

        results();
        ifDisplayFinalMatrix(mps);
        ifDisplayOriginalMatrices(mps);
        ifDisplayTimersAgain(mps, mcs);
        //result is of course the same
//        mps.displayFinalMatrix();
//        mps.displayTimeSpentOnExecution();
//        mcs.displayTimeSpentOnExecution();

    }

    public static void results(){
        System.out.println("Czesc wynikowa, wcisnij dowolny klawisz:");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
    }

    public static void ifDisplayFinalMatrix(MultiProcessorSystem mps){
        System.out.println("Czy wyswietlic iloczyn macierzy? (tak/nie)");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        if(s.equals("tak")){
            mps.getMaster().getFinalM().display();
        }
    }

    public static void ifDisplayOriginalMatrices(MultiProcessorSystem mps){
        System.out.println("Czy wyswietlic mnozniki macierzy? (tak/nie)");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        if(s.equals("tak")){
            mps.getMaster().displayMatrices();
        }
    }

    public static void ifDisplayTimersAgain(MultiProcessorSystem mps, MultiComputerSystem mcs){
        System.out.println("Czy wyswietlic ponownie czas wykonywania? (tak/nie)");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        if(s.equals("tak")){
            mps.displayTimeSpentOnExecution();
            mcs.displayTimeSpentOnExecution();
        }
    }
}
