package my_system_model;

import java.util.ArrayList;
import java.util.Arrays;

public class Run {
    //Ввод данных с платы:
    static byte n0 = 5; //разрядность функции
    static byte[] func = {0,1,1,0,0,1,1,1,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0};//вектор

    static byte n1 = 5; //разрядность функции
    static byte[] func1 = {1,1,1,0,0,1,1,1,1,1,0,1,1,1,0,0,0,1,0,0,1,1,0,0,0,1,0,0,1,1,0,0};//вектор

    static byte n2 = 5; //разрядность функции
    static byte[] func2 = string_to_array("00101000101100010010100010110001");//вектор

    static byte n3 = 5; //разрядность функции
    static byte[] func3 = string_to_array("11111111111111111111111111111111");//вектор

    static byte n4 = 2; //разрядность функции
    static byte[] func4 = string_to_array("11");//вектор

    public static void main(String[] args) {
        //quineMcCluskey(func, n);
        ArrayList<byte[]> quineMcCluskey = quineMcCluskey(func3, n3);
        System.out.println(Arrays.toString(func3));
        for (byte[] arr : quineMcCluskey){
            System.out.println(Arrays.toString(arr));
        }
    }

    private static byte[] string_to_array(String str){
        byte[] arr = new byte[str.length()];
        for (int i = 0; i < str.length(); i++){
            arr[i] = (byte) Integer.parseInt(String.valueOf(str.charAt(i)));
        }
        return arr;
    }

    static ArrayList<byte[]> quineMcCluskey(byte[] func, int n){
        byte k = (byte) Math.pow(2, n);
        //Массив регистров со всеми импилкантами, размер одного регситра - n, размер массива 2**n
        byte[][] implicants = new byte[k][n];
        byte ci; //Кол-во импликант

        //Получаем список импликант
        ci = 0;
        String binary;
        int bs;
        for(int i = 0; i < k; i++){
            if(func[i] == 1) {
                binary = Integer.toBinaryString(i);
                bs = binary.length();
                for(int j = n - bs; j < n; j++){
                    implicants[ci][j] = (byte) Integer.parseInt(binary.substring(j - (n - bs), j+1 - (n - bs)));
                }
                ci++;
            }
        }


        //Группы
        byte[][][][] groups = new byte[6][k][k][n+1];


        //Распределение по группам
        //Группа с 5 переменными
        int c;
        byte[][] cn = new byte[6][n+1];

        for(int i = 0; i < ci; i++){
            c = count_of_1(implicants[i], n);
            for(int j = 0; j < n; j++){ //Это ок, потому что системная модель пишется с учетом будущей реализации на verilog
                groups[0][c][cn[0][c]][j] = implicants[i][j];
            }
            cn[0][c]++;
        }

        //Переменные для склеивания
        byte cmp_out; //result of compare two numbers
        byte[] local; //new number got by merging
        byte cmp_d; //compare down
        byte cmp_u; //compare up
        boolean wf; //write flag
        byte ml = 0; //merging level
        boolean cf = true; //comparing flag used to show that it was comparing on this comparing level

        //Склеивание подгрупп в цикле

        while(cf) {
            cf = false;

            for (cmp_u = 0; cmp_u < n; cmp_u++) {
                cmp_d = (byte) (cmp_u + 1);
                for (int i = 0; i < cn[ml][cmp_u]; i++) {
                    for (int j = 0; j < cn[ml][cmp_d]; j++) {
                        //Функции сравнения вынести в виде task'ов
                        cmp_out = compare_for_merging(groups[ml][cmp_u][i], groups[ml][cmp_d][j], n); //больше 0 если можно склеить
                        if (cmp_out >= 0) {
                            cf = true;
                            local = groups[ml][cmp_u][i].clone();
                            local[cmp_out] = 3;
                            local[n] = 0;

                            groups[ml][cmp_u][i][n] = 4;
                            groups[ml][cmp_d][j][n] = 4;

                            c = count_of_1(local, n);

                            wf = true;
                            for (int p = 0; p < cn[ml + 1][c]; p++) {
                                if (compare_implicants(local, groups[ml + 1][c][p], n)) {
                                    wf = false;
                                    break;
                                }
                            }

                            if (wf) {
                                groups[ml + 1][c][cn[ml + 1][c]] = local;
                                cn[ml + 1][c]++;
                            }
                        }
                    }
                }
            }

            ml++;
        }


        //output_cmd(groups, cn);

        //Таблица Квайна
        int[][] quine_table = new int[32][32];

        //Массив простых импликант
        byte[][] pi = new byte[32][6]; //prime implicants

        //Поиск всех простых импликант
        int cpi = 0; //count of prime implicants

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                for (int b = 0; b < cn[i][j]; b++){
                    if (groups[i][j][b][n] == 0){
                        pi[cpi] = groups[i][j][b].clone();
                        cpi++;
                    }
                }
            }
        }

        //Массив 1-точек функции
        byte[][] p1 = new byte[32][6];

        int cp1 = 0; //count of point 1

        for (int i = 0; i < n+1; i++){
            for (int j = 0; j < cn[0][i]; j++) {
                p1[cp1] = groups[0][i][j].clone();
                p1[cp1][n] = 0;
                cp1++;
            }
        }

        //Заполнение таблицы Квайна
        for(int i = 0; i < ci; i++){
            for(int j = 0; j < cpi; j++){
                if (compare_for_q_table(p1[i], pi[j], n))
                    quine_table[i][j] = 1;
            }
        }

        //Поиск ядерных импликант
        int cicr; //core_implicant_check result
        //int cci = 0;
        for(int i = 0; i < ci; i++){
            cicr = core_implicant_check(quine_table[i], cpi);
            if(cicr >= 0) {
                pi[cicr][n] = 4;
                //cci++;
            }
        }

        //Отмечаем строки, перекрытые ядерными импликантами
        for (int i = 0; i < cpi; i++){
            if(pi[i][n] == 4){
                for (int j = 0; j < cp1; j++){
                    if (quine_table[j][i] == 1){
                        p1[j][n] = 4;
                    }
                }
            }
        }
        //Создадим отдельную табличку для Петрика из оставшихся простых импликант
        int[] c1pt = new int[32];
        int[][][] petrick_table = new int[32][32][32];
        int l = 0; //petrick table level
        for (int i = 0; i < cp1; i++){
            if(p1[i][n] == 0) {
                petrick_table[0][c1pt[0]] = quine_table[i].clone();
                c1pt[0]++;
            }
        }
        //Далее будем использовать одну из вариаций метода петрика.
        int pei = 0;//position of extra implicant
        //int cei = 0; //count of extra implicants
        int c1c = 0; //count of 1 in column
        int max_c1c = 0;

        if(c1pt[0] > 0) {
            while (true) {
                for (int i = 0; i < cpi; i++) {
                    for (int j = 0; j < c1pt[l]; j++) {
                        if (petrick_table[l][j][i] == 1) {
                            c1c++;
                        }
                    }
                    if (c1c > max_c1c) {
                        max_c1c = c1c;
                        pei = i;
                    }
                    c1c = 0;
                }

                pi[pei][n] = 4;
                for (int i = 0; i < c1pt[l]; i++) {
                    if (petrick_table[l][i][pei] != 1) {
                        petrick_table[l + 1][c1pt[l + 1]] = petrick_table[l][i].clone();
                        c1pt[l + 1]++;
                    }
                }
                if (c1pt[l + 1] == 0) {
                    break;
                }
                l++;
                max_c1c = 0;

            }
        }

        ArrayList<byte[]> res = new ArrayList<>();
        for (int i = 0; i < cpi; i++){
            if(pi[i][n] == 4) {
                res.add(pi[i]);
            }
        }
        return res;
    }



    static int count_of_1(byte[] bin_num, int n){
        int c = 0;
        for (int j = 0; j < n; j++){
            if (bin_num[j] == 1)
                c++;
        }
        return c;
    }

    static int core_implicant_check(int[] a, int csi){
        int pos = 0;
        int c = 0;
        for (int i = 0; i < csi; i++){
            if(a[i] == 1) {
                c++;
                pos = i;
            }
        }
        if (c == 1)
            return pos;
        else
            return -1;
    }


    static byte compare_for_merging(byte[] f1, byte[] f2, int n){
        int local_count = 0;
        byte t_pos = 0;
        for(byte t = 0; t < n; t++){
            if (f1[t] != f2[t]){
                local_count++;
                t_pos = t;
            }
        }
        if (local_count == 1)
            return t_pos;
        else
            return -1;
    }

    static boolean compare_implicants(byte[] f1, byte[] f2, int n){
        for (int i = 0; i < n; i++){
            if (f1[i] != f2[i]){
                return false;
            }
        }
        return true;
    }

    static boolean compare_for_q_table(byte[] p1, byte[] si, int n) {
        for (int i = 0; i < n; i++){
            if (!(p1[i] == si[i] || si[i] == 3)){
                return false;
            }
        }
        return true;
    }
}
