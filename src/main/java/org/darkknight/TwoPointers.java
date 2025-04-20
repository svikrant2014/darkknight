package org.darkknight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwoPointers {

    public static void main(String[] args) {

    }

    // s = "A man, a plan, a canal: Panama"
    private static Boolean isPalindrome(String str){
        char[] charArray = str.toLowerCase().toCharArray();
        int i =0, j=str.length()-1;

        while(i < j){
            while(i < j && !Character.isLetterOrDigit(charArray[i])){
                i++;
            }
            while(i<j && !Character.isLetterOrDigit(j)){
                j--;
            }

            if(charArray[i] != charArray[j]){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }
}
