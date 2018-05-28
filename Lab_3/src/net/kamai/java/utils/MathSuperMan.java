package net.kamai.java.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MathSuperMan {

    private MathSuperMan(){
        super();
    }

    public static List<Integer> resolveFkProblemLineSrav (int a, int b, int mod){


        a = onlyPositive(a, mod);
        b = onlyPositive(b, mod);


        if( gcd(a, mod) == 1){
            return Arrays.asList(easyFkVariant(a, b, mod));
        } else if( gcd(a, mod) > 1){
            return hardFkVariant(a, b, mod, gcd(a, mod));
        }

        return null;

    }

    public static int calcReverseElement(int a, int b){
        if(!isValid(a, b)){
            System.out.println("Error!Error!Error! Ivalid data: " + a + ", " + b);
            return 0;
        }
        int s = 0;
        int t = 1;
        int r = b;

        int old_s = 1;
        int old_t = 0;
        int old_r = a;

        while ( r != 0 ) {
            int quotient = old_r / r;

            int prov = r;
            r = old_r - quotient * prov;
            old_r = prov;

            int provS = s;
            s = old_s - quotient * provS;
            old_s = provS;

            int provT = t;
            t = old_t - quotient * provT;
            old_t = provT;
        }

//        System.out.println("Bézout coefficients:" + old_s + ", " + old_t);
//
//        System.out.println("greatest common divisor:" + old_r );
//
//        System.out.println("quotients by the gcd:" + t + ", " + s);

        return old_s;
    }

    private static int easyFkVariant(int a, int b, int mod){
        //easy!! Only one solution))
        //x=(a)^-1 * b

        int result = calcReverseElement(a, mod);
        if(result == 0){
            System.out.println("Sorry, shoto going ne tak... Find ZERO!");
            return 0;
        }

        result = (result * b) % mod;
        result = onlyPositive(result, mod);

        return result;
    }

    private static List<Integer> hardFkVariant(int a, int b, int mod, int d){
        // 1
        if (b % d != 0){
            return null;
        }

        ArrayList<Integer> result = new ArrayList<>();

        int x0 = easyFkVariant(a/d, b/d, mod/d);

        for (int i = 0; i < d; i++){
            result.add(x0 + (mod/d)*i);
        }

        return result;

    }

    private static boolean isValid(int element, int mod){

        if(element==0 || mod== 0  || gcd(element, mod) != 1){
            return false;
        }

        return true;
    }


    public static int gcd(int a, int b){
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public static int onlyPositive (int num, int mod){
        while (num < 0) {
            num += mod;
        }
        return num;
    }

}
