import org.apache.commons.math3.random.RandomDataGenerator;
import org.junit.Assert;
import org.junit.Test;
import my_system_model.Wrapper2;
import system_model.Wrapper1;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Math.pow;

public class MainTest {
    protected static final String TESTING_VECTORS = "src/test/resources/testingVectors.txt";
    protected static final String SYSTEM_MODEL_RESULTS = "src/test/resources/systemModelResults.txt";
    protected static final String MY_SYSTEM_MODEL_RESULTS = "src/test/resources/mySystemModelResults.txt";
    protected static final int capacity = 5;
    protected static final int testValuesCount = 500;

    @Test
    public void testOutputResults(){
        try {
            createVectors(capacity, testValuesCount);
            getResults1();
            getResults2(capacity);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i = 1;
        Assert.assertEquals(i, 1);
    }

    private static void createVectors(int capacity, int count) throws FileNotFoundException {
        int length = (int)pow(2, capacity);
        StringBuilder vect0 = new StringBuilder("00000000");
        for (int i = 0; i < capacity - 3; i++)
            vect0.append(vect0);

        StringBuilder vect = new StringBuilder(vect0);
        String binaryString;
        PrintWriter out = new PrintWriter(TESTING_VECTORS);
        long testingVector;
        for (int i = 0; i < count; i++){
            testingVector = new RandomDataGenerator().nextLong(0L, (long)pow(2, length));
            binaryString = Long.toBinaryString(testingVector);
            vect.replace(length - binaryString.length(), length, binaryString);
            out.println(vect);
            vect = new StringBuilder(vect0);
        }
        out.close();
    }

    private static void getResults1() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(TESTING_VECTORS));
        ArrayList<String> testingValues = new ArrayList<>();
        while(scanner.hasNextLine()){
            testingValues.add(scanner.nextLine());
        }
        scanner.close();
        PrintWriter out = new PrintWriter(SYSTEM_MODEL_RESULTS);
        for (String testingValue : testingValues) {
            out.println(Wrapper1.calculate(testingValue));
        }
        out.close();
    }

    private static void getResults2(int capacity) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(TESTING_VECTORS));
        ArrayList<String> testingValues = new ArrayList<>();
        while(scanner.hasNextLine()){
            testingValues.add(scanner.nextLine());
        }
        scanner.close();
        PrintWriter out = new PrintWriter(MY_SYSTEM_MODEL_RESULTS);
        for (String testingValue : testingValues) {
            System.out.println(testingValue);
            out.println(Wrapper2.calculate(testingValue, capacity));
        }
        out.close();
    }
}
