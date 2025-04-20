package org.darkknight.Meta;

public class AddDecimalStrings {

    //
    public static String addDecimalStrings(String num1, String num2) {
        // Split integer and decimal parts
        String[] parts1 = num1.split("\\.");
        String[] parts2 = num2.split("\\.");

        String intPart1 = parts1[0];
        String intPart2 = parts2[0];

        String decPart1 = (parts1.length > 1) ? parts1[1] : "0";
        String decPart2 = (parts2.length > 1) ? parts2[1] : "0";

        // Normalize decimal lengths
        int maxDecLen = Math.max(decPart1.length(), decPart2.length());
        decPart1 = padRight(decPart1, maxDecLen);
        decPart2 = padRight(decPart2, maxDecLen);

        // Add decimal part
        StringBuilder decSum = new StringBuilder();
        int carry = addStrings(decPart1, decPart2, decSum, 0);

        // Add integer part with carry
        StringBuilder intSum = new StringBuilder();
        carry = addStrings(intPart1, intPart2, intSum, carry);

        // Construct final result
        String result = intSum.toString();
        if (maxDecLen > 0) {
            result += "." + decSum.toString();
        }

        return result;
    }

    // Function to add two numeric strings and return carry
    private static int addStrings(String num1, String num2, StringBuilder result, int carry) {
        int i = num1.length() - 1, j = num2.length() - 1;

        while (i >= 0 || j >= 0 || carry > 0) {
            int digit1 = (i >= 0) ? num1.charAt(i--) - '0' : 0;
            int digit2 = (j >= 0) ? num2.charAt(j--) - '0' : 0;

            int sum = digit1 + digit2 + carry;
            result.insert(0, sum % 10);
            carry = sum / 10;
        }

        return carry;
    }

    // Pad the decimal part with zeros to the right
    private static String padRight(String s, int length) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < length) {
            sb.append("0");
        }
        return sb.toString();
    }

    // Example usage
    public static void main(String[] args) {
        System.out.println(addDecimalStrings("11.11", "123.5"));   // "134.61"
        System.out.println(addDecimalStrings("9.", "9.4"));       // "18.4"
        System.out.println(addDecimalStrings(".15", "612"));      // "612.15"
    }

}
