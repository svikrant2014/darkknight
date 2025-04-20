package org.darkknight;

import java.math.BigInteger;

public class Fibonacci {
    public static void main(String[] args) {
        BigInteger res = fibBig(75);
        System.out.println(res);
    }

    public static int fib(int n){
        int result =0;
        if(n ==1 || n ==2){
            return result = 1;
        }

        result = fib(n-1) + fib(n-2);
        System.out.println(result);
        return  result;
    }

    public static int fib2(int n){
        int arr[] =new int[n+2];
        arr[0]=1;
        arr[1]= 1;

        for(int i=2; i< n; i++){
            arr[i] = arr[i-1] + arr[i-2];
        }
        System.out.println(arr[n-2]);
        return arr[n-2];
    }

    public static int fib3(int n){
        int a1= 1;
        int a2= 1;
        int res = 0;
        for(int i=2; i< n; i++){
            res = a1+a2;
            a1 = a2;
            a2 = res;
        }
        System.out.println(res);
        return res;
    }

    public static BigInteger fibBig(int n){
        BigInteger a1= BigInteger.ZERO;
        BigInteger a2= BigInteger.ONE;
        BigInteger res = BigInteger.ZERO;

        for(int i=2; i< n; i++){
            res = a1.add(a2);
            a1 = a2;
            a2 = res;
        }
        System.out.println(res);
        return res;
    }
}
