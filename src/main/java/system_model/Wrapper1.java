package system_model;

import java.util.Arrays;

public class Wrapper1 {

    private static int[] convert_to_minterms(String vector){
        int[] a = new int[200];
        Arrays.fill(a, -1);
        int c = 0;
        for(int i = 0; i < vector.length(); i++){
            if (vector.charAt(i) == '1') {
                a[c++] = i;
            }
        }
        System.out.println(Arrays.toString(a));
        return a;
    }

    public static String calculate(String vector) {
        return new QuineMcCluskeyAlgorithm().run(convert_to_minterms(vector));
    }

    public static void main(String[] args) {
        QuineMcCluskeyAlgorithm quineMcCluskeyAlgorithm = new QuineMcCluskeyAlgorithm();
        String vector = "1100110011001100";
        String result = quineMcCluskeyAlgorithm.run(convert_to_minterms(vector));
        System.out.println(result);
    }
}
