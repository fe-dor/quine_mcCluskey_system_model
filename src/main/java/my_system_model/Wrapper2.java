package my_system_model;

import java.util.ArrayList;
import java.util.Arrays;

public class Wrapper2 {
    static int n2 = 5; //разрядность функции
    static int[] func2 = string_to_array("00101000101100010010100010110001");//вектор
    static int n3 = 5; //разрядность функции
    static int[] func3 = string_to_array("11111111111111110000000000000000");//вектор

    public static void main(String[] args) {
        ArrayList<byte[]> result = Run.quineMcCluskey(func3, n3);
        ArrayList<String> res_in_letters = convertToLettersForm(result, n3);
        for (int i = 0; i < result.size(); i++){
            System.out.print(Arrays.toString(result.get(i)) + " ");
        }
        System.out.println();
        System.out.println(res_in_letters);
    }

    public static String calculate(String vector, int capacity){
        ArrayList<byte[]> result = Run.quineMcCluskey(string_to_array(vector), capacity);
        return convertLettersFormToString(convertToLettersForm(result, capacity));
    }

    private static int[] string_to_array(String str){
        int[] arr = new int[str.length()];
        for (int i = 0; i < str.length(); i++){
            arr[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
        }
        return arr;
    }

    private static ArrayList<String> convertToLettersForm(ArrayList<byte[]> bitForm, int n){
        ArrayList<String> res = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        for (byte[] ints : bitForm) {
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
        if (bitForm.size() == 1) {
            boolean flag = true;
            for (int i = 0; i < n; i++){
                flag = flag && bitForm.get(0)[i] == 3;
            }
            if (flag){
                res = new ArrayList<>(){};
                res.add("1");
            }
        }
        return res;
    }

    private static String convertLettersFormToString(ArrayList<String> letForm){
        StringBuilder res = new StringBuilder();
        for (String str : letForm){
            res.append(str);
            res.append("+");
        }
        if(res.length() != 0) {
            res.deleteCharAt(res.length() - 1);
            return res.toString();
        }
        else
            return "";
    }
}
