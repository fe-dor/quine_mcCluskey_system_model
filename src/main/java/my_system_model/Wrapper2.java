package my_system_model;

import java.util.ArrayList;
import java.util.Arrays;

public class Wrapper2 {
    static int n2 = 5; //разрядность функции
    static int[] func2 = string_to_array("00101000101100010010100010110001");//вектор
    static int n3 = 5; //разрядность функции
    static int[] func3 = string_to_array("01100111110011001100110011001101");//вектор

    public static void main(String[] args) {
        ArrayList<int[]> result = Run.quineMcCluskey(func3, n3);
        ArrayList<String> res_in_letters = convertToLettersForm(result);
        for (int i = 0; i < result.size(); i++){
            System.out.print(Arrays.toString(result.get(i)) + " ");
        }
        System.out.println();
        System.out.println(res_in_letters);
    }

    public static String calculate(String vector, int capacity){
        ArrayList<int[]> result = Run.quineMcCluskey(string_to_array(vector), capacity);
        return convertLettersFormToString(convertToLettersForm(result));
    }

    private static int[] string_to_array(String str){
        int[] arr = new int[str.length()];
        for (int i = 0; i < str.length(); i++){
            arr[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
        }
        return arr;
    }

    private static ArrayList<String> convertToLettersForm(ArrayList<int[]> bitForm){
        ArrayList<String> res = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        for (int[] ints : bitForm) {
            for (int j = 0; j < ints.length - 1; j++) {
                if (ints[j] != 3) {
                    switch (j) {
                        case 0 -> buf.append(ints[j] == 0 ? "a" : "A");
                        case 1 -> buf.append(ints[j] == 0 ? "b" : "B");
                        case 2 -> buf.append(ints[j] == 0 ? "c" : "C");
                        case 3 -> buf.append(ints[j] == 0 ? "d" : "D");
                        case 4 -> buf.append(ints[j] == 0 ? "e" : "E");
                    }
                }
            }
            res.add(buf.toString());
            buf = new StringBuilder();
        }
        return res;
    }

    private static String convertLettersFormToString(ArrayList<String> letForm){
        StringBuilder res = new StringBuilder();
        for (String str : letForm){
            res.append(str);
            res.append("+");
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }
}
