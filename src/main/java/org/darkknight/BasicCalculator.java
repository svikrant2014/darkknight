package org.darkknight;


import java.util.Stack;

// infix processing
// this is for single digit operands (like 1, 2, 3 not for 12, 34, etc ).. little modification required
public class BasicCalculator {

    public static void main(String[] args) {

        int val = calculate("(12+5)-8");
    }

    // without brackets
    // https://leetcode.com/problems/basic-calculator-ii/
    public static int calsII(String s) {
        if (s == null || s.length() == 0) return 0;
        Stack<Integer> stack = new Stack<>();
        s += '+';
        char op = '+';
        for (int i = 0, n = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                n = n * 10 + c - '0';
                continue;
            }
            if (c == ' ') continue;
            if (op == '+') stack.push(n);
            else if (op == '-') stack.push(-n);
            else if (op == '*') stack.push(stack.pop() * n);
            else if (op == '/') stack.push(stack.pop() / n);
            op = c;
            n = 0;
        }

        int total = 0;
        while (!stack.isEmpty()) total += stack.pop();
        return total;
    }

    // basic-calculator-ii (without stack)
    public int basicCalculatorWithoutStack(String s) {
        if (s == null || s.length() == 0) return 0;

        int len = s.length();
        int num = 0;
        int result = 0;
        int lastNumber = 0;
        char sign = '+'; // Assume the first sign is '+'

        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }

            if ((!Character.isDigit(c) && c != ' ') || i == len - 1) {
                if (sign == '+') {
                    result += lastNumber;
                    lastNumber = num;
                } else if (sign == '-') {
                    result += lastNumber;
                    lastNumber = -num;
                } else if (sign == '*') {
                    lastNumber = lastNumber * num;
                } else if (sign == '/') {
                    lastNumber = lastNumber / num;
                }

                sign = c;
                num = 0;
            }
        }

        result += lastNumber; // Add the last number
        return result;
    }

    // https://leetcode.com/problems/basic-calculator/
//    public static int cals(String s) {
//        Stack<Integer> stack = new Stack<>();
//        int result = 0;
//        int number = 0;
//        int sign = 1;
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            if (Character.isDigit(c)) {
//                number = 10 * number + (int) (c - '0');
//            } else if (c == '+') {
//                result += sign * number;
//                number = 0;
//                sign = 1;
//            } else if (c == '-') {
//                result += sign * number;
//                number = 0;
//                sign = -1;
//            } else if (c == '(') {
//                //we push the result first, then sign;
//                stack.push(result);
//                stack.push(sign);
//                //reset the sign and result for the value in the parenthesis
//                sign = 1;
//                result = 0;
//            } else if (c == ')') {
//                result += sign * number;
//                number = 0;
//                result *= stack.pop();    //stack.pop() is the sign before the parenthesis
//                result += stack.pop();   //stack.pop() now is the result calculated before the parenthesis
//
//            }
//        }
//        if (number != 0) result += sign * number;
//        return result;
//    }

    public static int precedence(char optr) {
        switch (optr) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
        }
        return -1;
    }

    public static int operation(int v1, int v2, char optr) {
        switch (optr) {
            case '+':
                return v1 + v2;
            case '-':
                return v1 - v2;
            case '*':
                return v1 * v2;
            case '/':
                return v1 / v2;
        }
        return -1;
    }

    public static int calculate(String expr) {

        Stack<Integer> opnds = new Stack<>();
        Stack<Character> optrs = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (ch == '(') {
                optrs.push(ch);
            } else if (Character.isDigit(ch)) {
                opnds.push(ch - '0');
            } else if (ch == ')') {
                while (optrs.peek() != '(') {
                    char optr = optrs.pop();
                    int v2 = opnds.pop();
                    int v1 = opnds.pop();

                    int res = operation(v1, v2, optr);
                    opnds.push(res);
                }
                optrs.pop();
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (optrs.size() > 0 && optrs.peek() != '(' && precedence(ch) <= precedence((optrs.peek()))) {
                    char optr = optrs.pop();
                    int v2 = opnds.pop();
                    int v1 = opnds.pop();

                    int res = operation(v1, v2, optr);
                    opnds.push(res);
                }
                optrs.push(ch);
            }
        }

        while (optrs.size() != 0) {
            char optr = optrs.pop();
            int v2 = opnds.pop();
            int v1 = opnds.pop();

            int res = operation(v1, v2, optr);
            opnds.push(res);
        }

        return opnds.peek();
    }
}
