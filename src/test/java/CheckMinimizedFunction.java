import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.pow;
import static java.lang.Math.scalb;

public class CheckMinimizedFunction {
    private ArrayList<String> testingValues1;
    private ArrayList<String> testingValues2;
    private ArrayList<String> inputVectors;
    private final int capacity;

    CheckMinimizedFunction(int capacity){
        this.capacity = capacity;
    }

    public static void main(String[] args) {
        new CheckMinimizedFunction(5).run();
    }

    public void run(){
        try {
            readResultArrays();
            readInputVector();
            for (int i = 0; i < 1; i++) {
                System.out.print(inputVectors.get(i) + " " + testingValues1.get(i) + " " + testingValues2.get(i));
                System.out.print(" " + checkOneVector(i, 0) + " ");
                System.out.println(checkOneVector(i, 1));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readResultArrays() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(MainTest.SYSTEM_MODEL_RESULTS));
        testingValues1 = new ArrayList<>();
        while(scanner.hasNextLine()){
            testingValues1.add(scanner.nextLine());
        }
        scanner.close();
        scanner = new Scanner(new File(MainTest.MY_SYSTEM_MODEL_RESULTS));
        testingValues2 = new ArrayList<>();
        while(scanner.hasNextLine()){
            testingValues2.add(scanner.nextLine());
        }
        scanner.close();
    }

    private void readInputVector() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(MainTest.TESTING_VECTORS));
        inputVectors = new ArrayList<>();
        while(scanner.hasNextLine()){
            inputVectors.add(scanner.nextLine());
        }
        scanner.close();
    }

    private boolean checkOneVector(int index, int mode){
        boolean result = false;
        boolean final_result = true;

        if (index > inputVectors.size() || index < 0){
            System.out.println("Error: wrong index");
            return false;
        }

        String[] implss = mode == 0 ? testingValues1.get(index).split("\\+") : testingValues2.get(index).split("\\+");

        ArrayList<char[]> impls = new ArrayList<>();
        for (String imp : implss){
            impls.add(imp.toCharArray());
        }

        StringBuilder buf;

        for (int i = 0; i < pow(2, capacity); i++){
            result = false;
            buf = new StringBuilder(Integer.toBinaryString(i));
            buf.insert(0, "0000".toCharArray(), 0, capacity - buf.length());
            for (char[] imp : impls){
                result = checkOneImp(imp, buf.toString()) || result;
                //System.out.println(Arrays.toString(imp) + " " + result);
            }
            final_result = final_result && ((result ? '1' : '0') == inputVectors.get(index).charAt(i));
        }
        return final_result;
    }

    private boolean checkOneImp(char[] imp, String index){
        boolean res = true;
        for (char c : imp){
            switch (c) {
                case ('a') -> res = index.charAt(0) == '0' && res;
                case ('A') -> res = index.charAt(0) == '1' && res;
                case ('b') -> res = index.charAt(1) == '0' && res;
                case ('B') -> res = index.charAt(1) == '1' && res;
                case ('c') -> res = index.charAt(2) == '0' && res;
                case ('C') -> res = index.charAt(2) == '1' && res;
                case ('d') -> res = index.charAt(3) == '0' && res;
                case ('D') -> res = index.charAt(3) == '1' && res;
                case ('e') -> res = index.charAt(4) == '0' && res;
                case ('E') -> res = index.charAt(4) == '1' && res;
            }
        }
        return res;
    }

}
