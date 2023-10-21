package system_model;

import system_model.mcquine.McQuineController;

import java.util.Arrays;
import java.util.Locale;

public class Wrapper1 {

    public static String calculate(String vector) {
        return convertToLowUpForm(McQuineController.calculate(strVectorToMinterms(vector)));
    }

    public static void main(String[] args) {
        String vector = "1100110011001100";
        String result = McQuineController.calculate(strVectorToMinterms(vector));
        System.out.println(result);
    }

    private static String strVectorToMinterms(String vect){
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < vect.length(); i++){
            if (vect.charAt(i) == '1')
                res.append(i).append(" ");
        }
        return res.deleteCharAt(res.length()-1).toString();
    }

    private static String convertToLowUpForm(String str){
        String up = str.toUpperCase();
        String low = str.toLowerCase();
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < str.length(); i++){
            if(i != str.length()-1 && str.charAt(i+1) == '\''){
                buf.append(low.charAt(i));
            }
            else if (str.charAt(i) != '\''){
                buf.append(up.charAt(i));
            }
        }
        System.out.println(buf);
        return buf.toString();
    }
}
