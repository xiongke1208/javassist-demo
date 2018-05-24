package com.javassist.demo;

/**
 * Created by 1234qwer on 2018/5/24.
 */
public class Calculator {

    public void getSum(long n) {
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += i;
        }
        System.out.println("n="+n+",sum="+sum);
    }
}
