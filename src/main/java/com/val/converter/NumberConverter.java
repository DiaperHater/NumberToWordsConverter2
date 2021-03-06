package com.val.converter;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;


/*
* Класс преобразующий любое целое число в его словесное представление
* */

public class NumberConverter {

    private final HighestExponentOfThousandConverter highestExponentOfThousandConverter = new HighestExponentOfThousandConverter();
    private final BigInteger ONE_THOUSAND = new BigInteger("1000");

    //Перегруженый asWords(BigInteger)
    public String asWords(long num) {
        return asWords(BigInteger.valueOf(num));
    }

    //Перегруженый asWords(BigInteger)
    public String asWords(String num) {
        return asWords(new BigInteger(num));
    }

    //Метод преобразующий входной параметр num
    //в его словесное представление
    public String asWords(BigInteger number){

        if (number.signum() == 0){
            return "ноль";
        }

        String sign = "";
        if (number.signum() == -1){
            sign = "минус";
            number = number.negate();
        }


        ArrayList<BigInteger> list = new ArrayList();
        int exponent = 0;
        BigInteger poweredThousand = ONE_THOUSAND.pow(exponent);
        StringBuilder sb = new StringBuilder();

        while (poweredThousand.compareTo(number) <= 0){

            BigInteger divisionResult = number.divide(poweredThousand);
            BigInteger remainderResult = divisionResult.remainder(ONE_THOUSAND);
            BigInteger nThExponentOfThousnd = remainderResult.multiply(poweredThousand);

            if( nThExponentOfThousnd.equals(BigInteger.ZERO) ){
                ++exponent;
                poweredThousand = ONE_THOUSAND.pow(exponent);
                continue;
            }
            sb.insert(0, " " + highestExponentOfThousandConverter.asWords(nThExponentOfThousnd) );

            ++exponent;
            poweredThousand = ONE_THOUSAND.pow(exponent);
        }

        String result = sb.toString().substring(1);

        if (sign.length() != 0){
            result = sign +" "+ result;
        }

        return result;
    }


    public static void main(String[] args) {

        String numToConvert = "";
        Scanner scanner = new Scanner(System.in);
        NumberConverter nc = new NumberConverter();

        System.out.println("++++Number to words converter++++");

        while ( true ){
            System.out.print("Enter the number (or 'q' for exit): ");
            numToConvert = scanner.nextLine();
            if (numToConvert.equals("q")){
                break;
            }
            System.out.println(numToConvert +" = "+ nc.asWords(numToConvert));
        }

        System.out.println("Quit.");

    }


}
