package org.darkknight;

import java.util.*;

public class StringManipulation {
    public static void main(String[] args) {
        StringManipulation sm = new StringManipulation();
        sm.removeDuplicatesString("abbbacxdd");
        sm.minStepsToMakeAnagram("anagram", "mangaar");
        sm.validIPAddress("1.1.1.1.");
        // sm.reverseWords(" a    b ");

        sm.compress(new char[]{'a', 'a', 'a', 'b', 'c'});

        int ans = sqrt(25);
        System.out.println(ans);

//        int val = pow(2,4);
//        List<String> res = fizzBuzz(5);
//        System.out.println(res.toString());
    }

    // https://leetcode.com/problems/partition-labels
    //
    public List<Integer> partitionLabels(String s) {
        Map<Character, Integer> map = new HashMap<>();
        // filling last impact of character's
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), i);
        }

        List<Integer> res = new ArrayList<>();
        int prev = -1;
        int max = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            max = Math.max(max, map.get(ch));
            if (max == i) {
                // partition time
                res.add(max - prev);
                prev = max;
            }
        }
        return res;
    }

    public String nei = "Neither";

    public String validIPAddress(String str) {
        if (str.length() == 0) return nei;

        if (str.indexOf('.') > 0) return checkIP4(str);
        else if (str.indexOf(':') > 0) return checkIP6(str);
        else return nei;
    }

    private String checkIP4(String str) {
        if (str.indexOf('.') == 0 || str.indexOf('.') == str.length() - 1 || str.charAt(str.length() - 1) == '.')
            return nei;
        String[] splits = str.split("\\.");
        if (splits.length != 4) return nei;

        for (String part : splits) {
            if (part.length() > 1 && part.charAt(0) == '0') return nei;
            for (char c : part.toCharArray()) {
                if (!Character.isDigit(c)) return nei;
            }

            try {
                int val = Integer.parseInt(part);
                if (val >= 0 && val <= 255) {
                    continue;
                } else {
                    return nei;
                }
            } catch (Exception ex) {
                return nei;
            }
        }
        return "IPv4";
    }

    private String checkIP6(String str) {
        if (str.indexOf(':') == 0 || str.indexOf(':') == str.length() - 1) return nei;
        String[] splits = str.split(":");
        if (splits.length != 8) return nei;

        for (String part : splits) {
            if (part.length() > 4 || part.length() < 1) return nei;

            for (char c : part.toCharArray()) {
                c = Character.toLowerCase(c);
                if (Character.isDigit(c) || c == 'a' || c == 'b' || c == 'c' || c == 'd' || c == 'e' || c == 'f')
                    continue;
                else return nei;
            }
        }
        return "IPv6";
    }

    // https://leetcode.com/problems/remove-comments/description/
    public List<String> removeComments(String[] source) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean mode = false;
        for (String str : source) {
            for (int i = 0; i < str.length(); i++) {
                if (mode) {
                    if (str.charAt(i) == '*' && i < str.length() - 1 && str.charAt(i + 1) == '/') {
                        mode = false;
                        i++;
                    }
                } else {
                    if (str.charAt(i) == '/' && i < str.length() - 1 && str.charAt(i + 1) == '/') {
                        break;
                    } else if (str.charAt(i) == '/' && i < str.length() - 1 && str.charAt(i + 1) == '*') {
                        mode = true;
                        i++;
                    } else {
                        sb.append(str.charAt(i));
                    }
                }
            }
            if (!mode && sb.length() > 0) {
                res.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        return res;
    }

    // https://leetcode.com/problems/string-compression/
    public int compress(char[] chars) {
        if (chars.length <= 1) return 1;

        int count = 0;
        int k = 0;
        for (int i = 0; i < chars.length; i++) {
            int cnt = 1;
            char c = chars[i];
            while (i + 1 < chars.length && chars[i] == chars[i + 1]) {
                cnt++;
                i++;
            }
            chars[k++] = c;
            if (cnt > 1) {
                String countStr = cnt + "";
                for (char val : countStr.toCharArray()) {
                    chars[k++] = val;
                }
            }

        }
        return k;
    }

    // https://leetcode.com/problems/number-of-ways-to-split-a-string/
    public static int numWays(String s) {
        long n = s.length();
        long MOD = 1_000_000_007;
        int ones = 0;
        for (char c : s.toCharArray()) {
            if (c == '1') ones++;
        }
        if (ones == 0) return (int) ((((n - 1) * (n - 2)) / 2) % MOD);
        if (ones % 3 != 0) return 0;

        long blocks = ones / 3;
        long ways1 = 0;
        long ways2 = 0;
        ones = 0;
        for (char c : s.toCharArray()) {
            ones += c - '0';
            if (ones == blocks) ways1++;
            else if (ones == 2 * blocks) ways2++;
        }

        return (int) ((ways1 * ways2) % MOD);
    }

    // first reverse the string, then each word and then clean up extra spaces in the end
    // https://leetcode.com/problems/reverse-words-in-a-string/
    public String reverseWords(String s) {
        char[] str = s.toCharArray();
        if (str.length == 0) return "";

        int n = s.length();
        swap(str, s.length() - 1, 0);
        int i = 0;
        int j = 0;
        boolean left = false;

        while (i < n) {
            left = false;
            while (i < n && str[i] == ' ') {
                i++;
                j++;
            }
            while (i < n && str[i] != ' ') {
                left = true;
                i++;
            }
            if (!left) break;

            swap(str, i - 1, j);
            j = i;
        }

        String res = cleanSpaces(str, n);
        return res;
    }

    String cleanSpaces(char[] a, int n) {
        int i = 0, j = 0;

        while (j < n) {
            while (j < n && a[j] == ' ') j++;             // skip spaces
            while (j < n && a[j] != ' ') a[i++] = a[j++]; // keep non spaces
            while (j < n && a[j] == ' ') j++;             // skip spaces
            if (j < n) a[i++] = ' ';                      // keep only one space
        }

        return new String(a).substring(0, i);
    }

    private void swap(char[] arr, int i, int j) {
        while (j < i) {
            char tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
            i--;
            j++;
        }
    }

    // https://leetcode.com/problems/reverse-words-in-a-string-iii
    public String reverseWordsIII(String s) {
        int i = 0, j = 0;
        int len = s.length();
        char[] res = s.toCharArray();

        while (i < len) {
            while (j < len && res[j] != ' ') {
                j++;
            }

            reverse(res, i, j - 1);
            i = j + 1;
            j++;
        }

        return String.valueOf(res);
    }

    private void reverse(char[] arr, int start, int end) {
        while (start < end) {
            char tmp = arr[start];
            arr[start++] = arr[end];
            arr[end--] = tmp;
        }
    }

    // https://leetcode.com/problems/can-place-flowers
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int len = flowerbed.length;
        if (n == 0) return true;
        if (len <= 0 || n > len) return false;

        int i = 0;
        while (i < len) {
            if (flowerbed[i] == 0) {
                if ((i - 1 < 0 || flowerbed[i - 1] == 0) && (i + 1 >= len || flowerbed[i + 1] == 0)) {
                    if (--n == 0) {
                        return true;
                    }
                    flowerbed[i] = 1;
                }
            }
            i++;
        }

        return false;
    }

    // https://leetcode.com/problems/find-words-that-can-be-formed-by-characters
    // A string is good if it can be formed by characters from chars
    // words = ["cat","bt","hat","tree"], chars = "atach" -- "cat" & "hat" so ans is 3+3=6
    public int countCharacters(String[] words, String chars) {
        int ans = 0;
        char[] arr = new char[26];
        int i = 0;
        for (char c : chars.toCharArray()) {
            arr[c - 'a']++;
        }

        for (String str : words) {
            char[] local = new char[26];
            i = 0;
            for (char c : str.toCharArray()) {
                local[c - 'a']++;
            }

            boolean worked = true;
            for (int val = 0; val < 26; val++) {
                if (local[val] <= arr[val]) {
                    continue;
                } else {
                    worked = false;
                    break;
                }
            }
            if (worked) ans += str.length();
        }
        return ans;
    }

    // https://leetcode.com/problems/replace-all-?-to-avoid-consecutive-repeating-characters/
    public String modifyString(String s) {
        char[] str = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            if (str[i] == '?') {
                for (char c = 'a'; c <= 'c'; c++) {
                    if (i > 0 && str[i - 1] == c) continue;
                    if (i + 1 < s.length() && str[i + 1] == c) continue;

                    str[i] = c;
                    break;
                }
            }
        }
        return String.valueOf(str);
    }

    public boolean rotateString(String s, String goal) {
        if (s.length() != goal.length()) return false;
        String newString = s + reverse(s);
        if (newString.contains(goal)) return true;
        return false;
    }

    private String reverse(String str) {
        int i = 0, j = str.length() - 1;
        char[] arr = str.toCharArray();
        while (i < j) {
            char tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }
        return arr.toString();
    }

    // https://leetcode.com/problems/reverse-words-in-a-string-ii/
    public void reverseWords(char[] str) {
        int n = str.length;
        swap(str, str.length - 1, 0);
        int i = 0;
        int j = 0;
        boolean left = false;

        while (i < n) {
            left = false;
            while (i < n && str[i] == ' ') {
                i++;
                j++;
            }
            while (i < n && str[i] != ' ') {
                left = true;
                i++;
            }
            if (!left) break;

            swap(str, i - 1, j);
            j = i;
        }
    }

    // https://leetcode.com/problems/compare-version-numbers/description/
    public int compareVersion(String ver1, String ver2) {
        String[] s1 = ver1.split("\\.");
        String[] s2 = ver2.split("\\.");

        int maxLength = Math.max(s1.length, s2.length);

        for (int i = 0; i < maxLength; i++) {
            int val1 = i < s1.length ? Integer.parseInt(s1[i]) : 0;
            int val2 = i < s2.length ? Integer.parseInt(s2[i]) : 0;

            if (val1 > val2) return 1;
            else if (val1 < val2) return -1;
        }
        return 0;
    }

    // https://leetcode.com/problems/add-strings/
    // addition of 2 strings
    public String addStrings(String num1, String num2) {
        if (num1.length() == 0) return num2;
        if (num2.length() == 0) return num1;

        int len1 = num1.length() - 1;
        int len2 = num2.length() - 1;

        int carry = 0;
        StringBuilder sb = new StringBuilder();
        while (len1 >= 0 || len2 >= 0) {
            int val1 = len1 >= 0 ? num1.charAt(len1--) - '0' : 0;
            int val2 = len2 >= 0 ? num2.charAt(len2--) - '0' : 0;

            int sum = val1 + val2 + carry;
            carry = sum / 10;
            sb.insert(0, sum % 10);
        }
        if (carry != 0) {
            sb.insert(0, carry);
        }
        return sb.toString();
    }

    // https://leetcode.com/problems/valid-palindrome/
    public boolean validPalindrome(String s) {
        int left =0, right= s.length()-1;

        while(left <= right){
            while(left < right && !Character.isLetterOrDigit(s.charAt(left))){
                left++;
            }
            while(left < right && !Character.isLetterOrDigit(s.charAt(right))){
                right--;
            }

            if(Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) return false;
            left++;
            right--;
        }
        return true;
    }

    // https://leetcode.com/problems/valid-palindrome-ii/
    public boolean validPalindromeII(String s) {
        int i =0; int j = s.length()-1;

        while(i < j){
            if(s.charAt(i) != s.charAt(j)){
                return isPalindrome(s, i+1, j) || isPalindrome(s, i, j-1);
            }

            i++;
            j--;
        }
        return true;
    }
    private boolean isPalindrome(String s, int i, int j){
        while(i < j){
            if(s.charAt(i) != s.charAt(j)){
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    // https://leetcode.com/problems/consecutive-characters/description/
    public int maxPower(String s) {
        if (s.length() == 0) return 0;

        int maxPower = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int power = 1;
            while (i + 1 < s.length() && s.charAt(i + 1) == c) {
                power++;
                i++;
            }
            maxPower = Math.max(maxPower, power);
        }
        return maxPower;
    }

    // https://leetcode.com/problems/string-to-integer-atoi/
    // everyone hates this Q coz stupid edge cases
    public int myAtoi(String str) {
        int len = str.length();
        if (len == 0) return 0;

        int curr = 0;
        while (curr < len && str.charAt(curr) == ' ') {
            curr++;
        }
        if (curr == len) return 0;

        char ch;
        boolean negative = str.charAt(curr) == '-' ? true : false;

        if (negative || str.charAt(curr) == '+') curr++;

        int maxLimit = Integer.MAX_VALUE / 10;
        int res = 0;

        while (curr < len && Character.isDigit(str.charAt(curr))) {
            ch = str.charAt(curr);
            int digit = ch - '0';

            if (res > maxLimit || (res == maxLimit && digit > 7)) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            res = (res * 10) + digit;
            ++curr;
        }
        return negative ? -res : res;
    }

    // https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/description/
    public int minStepsToMakeAnagram(String s, String t) {
        Map<Character, Integer> map = new HashMap<>();
        for (Character c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        int res = 0;
        for (Character ch : t.toCharArray()) {
            if (map.containsKey(ch)) {
                map.put(ch, map.get(ch) - 1);
                if (map.get(ch) == 0) map.remove(ch);
            } else {
                res++;
            }
        }
        return res;
    }

    // https://leetcode.com/problems/roman-to-integer/description/
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i < s.length() - 1 && map.get(s.charAt(i)) < map.get(s.charAt(i + 1))) {
                ans -= map.get(s.charAt(i));
            } else {
                ans += map.get(s.charAt(i));
            }
        }
        return ans;
    }

    // https://leetcode.com/problems/integer-to-roman
    // integer to roman
    public String intToRoman(int num) {
        int[] n = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] s = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int i =0;
        String str = new String();
        while (num>0){
            if (num>=n[i]){
                str=str+s[i];
                num-=n[i];
            } else{
                i++;
            }
        }
        return str;
    }

    private static int sqrt(int n) {
        if (n < 2) return n;

        int min = 2;
        int max = n / 2;
        int mid;

        while (min <= max) {
            mid = min + (max - min) / 2;
            long val = (long) mid * mid;
            if (val == n) return mid;
            if (val > n) max--;
            else min++;
        }
        return max;
    }

    // https://leetcode.com/problems/fizz-buzz/description/
    public List<String> fizzBuzz2(int n) {
        List ans = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            ans.add(
                    i % 15 == 0 ? "FizzBuzz" :
                            i % 5 == 0 ? "Buzz" :
                                    i % 3 == 0 ? "Fizz" :
                                            String.valueOf(i)
            );
        }
        return ans;
    }

    public String breakPalindrome(String palindrome) {
        int len = palindrome.length();
        if (len == 1) return "";

        char[] res = palindrome.toCharArray();
        for (int i = 0; i < len / 2; i++) {
            if (res[i] != 'a') {
                res[i] = 'a';
                return new String(res);
            }
        }

        res[len - 1] = 'b';
        return new String(res);
    }

    // https://leetcode.com/problems/fizz-buzz
    private static List<String> fizzBuzz(int n) {
        List<String> res = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0)
                res.add("FizzBuzz");
            else if (i % 5 == 0)
                res.add("Buzz");
            else if (i % 3 == 0)
                res.add("Fizz");
            else
                res.add(String.valueOf(i));
        }

        return res;
    }

    // https://leetcode.com/problems/valid-parentheses
    private static boolean validateParentheses(String s) {
        if (s == null || s.length() == 0) return true;

        Map<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');

        Stack<Character> stack = new Stack<>();

        for (Character c : s.toCharArray()) {
            if (map.containsKey(c)) {
                stack.push(map.get(c));
            } else {
                if (stack.isEmpty()) return false;

                if (stack.pop() != c) return false;
                else continue;
            }
        }
        return stack.isEmpty();
    }

    // based on KMP table
    // https://leetcode.com/problems/shortest-palindrome
    public static String shortestPalindrome(String s) {
        String rev = new StringBuilder(s).reverse().toString();
        // reverse and append to the end, use # to avoid overlap
        String str = s + "#" + rev;

        int[] p = new int[str.length()];
        // build KMP table
        // i -> suffix boundary
        // j -> prefix boundary

        for (int i = 1; i < str.length(); i++) {
            // update prefix boundary
            int j = p[i - 1];

            // move to the last pref boundary match
            while (j > 0 && str.charAt(i) != str.charAt(j))
                j = p[j - 1];

            // if prefix boundary matches suffix boundary, incr prefix len
            if (str.charAt(i) == str.charAt(j)) {
                p[i] = j + 1;
            }
        }

        return rev.substring(0, s.length() - p[str.length() - 1]) + s;

    }

    // https://leetcode.com/problems/validate-ip-address/
    public static String validateIP(String IP) {
        if (IP == null || IP.length() == 0) return "Neither";

        if (IP.indexOf(".") >= 0) {
            return isIPV4(IP);
        } else if (IP.indexOf(":") >= 0) {
            return isIPV6(IP);
        }

        return "Neither";
    }

    private static String isIPV4(String str) {
        if (str.charAt(0) == '.' || str.charAt(str.length() - 1) == '.') {
            return "Neither";
        }

        String[] arr = str.split("\\.");
        if (arr.length != 4) return "Neither";

        for (String comp : arr) {
            if (comp.isEmpty() || comp.length() > 3 || (comp.charAt(0) == '0' && comp.length() > 1)) {
                return "Neither";
            }

            for (char c : comp.toCharArray()) {
                if (c < '0' || c > '9') return "Neither";

                try {
                    int num = Integer.parseInt(comp);
                    if (num < 0 || num > 255) return "Neither";
                } catch (NumberFormatException ne) {
                    return "Neither";
                }
            }
        }

        return "IPv4";
    }

    private static String isIPV6(String str) {
        if (str.charAt(0) == ':' || str.charAt(str.length() - 1) == ':') {
            return "Neither";
        }

        String[] arr = str.split(":");
        if (arr.length != 8) return "Neither";

        for (String comp : arr) {
            if (comp.isEmpty() || comp.length() > 4) return "Neither";

            for (char c : comp.toLowerCase().toCharArray()) {
                if ((c < '0' || c > '9') && c != 'a' && c != 'b' && c != 'c' && c != 'd' && c != 'e' && c != 'f')
                    return "Neither";
            }
        }

        return "IPv6";
    }

    // https://leetcode.com/problems/simplify-path/
    public String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        String[] p = path.split("/");

        for (String str : p) {
            if (!stack.isEmpty() && str.equals("..")) {
                stack.pop();
            } else if (!str.equals(".") && !str.equals("") && !str.equals("..")) {
                stack.push(str);
            }
        }

        List<String> res = new ArrayList<>(stack);
        return "/" + String.join("/", res);
    }

    // #meta
    // https://leetcode.com/problems/longest-common-prefix/
    // O(nXm)
    public static String longestCommonPrefix(String[] strs) {
        String res = "";

        if (strs == null || strs.length == 0) return res;

        int idx = 0;
        for (char c : strs[0].toCharArray()) {
            for (int i = 1; i < strs.length; i++) {
                if (idx >= strs[i].length() || c != strs[i].charAt(idx)) {
                    return res;
                }
            }

            res += c;
            idx++;
        }
        return res;
    }

    // don't know why but this seems to be one more likable
    // should ask interviewer
    public String longestCommonPrefixUsingSort(String[] v) {
        StringBuilder ans = new StringBuilder();
        Arrays.sort(v);
        String first = v[0];
        String last = v[v.length-1];
        for (int i=0; i<Math.min(first.length(), last.length()); i++) {
            if (first.charAt(i) != last.charAt(i)) {
                return ans.toString();
            }
            ans.append(first.charAt(i));
        }
        return ans.toString();
    }

    // "flower", "flow", "flush"
    public static String longestCommonPrefixII(String[] strs) {
        if (strs.length == 0) return "";
        String pre = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(pre)) {
                pre = pre.substring(0, pre.length() - 1);
            }
        }
        return pre;
    }

    // https://leetcode.com/problems/add-binary/
    public static String addBinary(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length();
        int j = b.length();
        int carry = 0;
        while (i >= 0 || j >= 0) {
            int sum = carry;
            if (i >= 0) sum += a.charAt(i--) - '0';
            if (j >= 0) sum += b.charAt(j--) - '0';

            sb.insert(0, sum % 2);
            carry = sum / 2;
        }
        if (carry > 0) {
            sb.insert(0, 1);
        }

        return String.valueOf(sb);
    }

    // https://leetcode.com/problems/merge-strings-alternately
    public String mergeAlternately(String word1, String word2) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < word1.length() || i < word2.length()) {
            if (i < word1.length()) {
                result.append(word1.charAt(i));
            }
            if (i < word2.length()) {
                result.append(word2.charAt(i));
            }
            i++;
        }
        return result.toString();
    }

    // https://leetcode.com/problems/minimum-number-of-operations-to-make-x-and-y-equal
    public int minimumOperationsToMakeEqual(int x, int y) {
        dp = new int[10011];
        Arrays.fill(dp, -1);
        return solve(x, y);
    }
    private int[] dp;
    public int solve(int x, int y) {
        if (x <= y) return y - x;
        if (dp[x] != -1) return dp[x];
        int res = Math.abs(x - y);
        res = Math.min(res, 1 + x % 5 + solve(x / 5, y));
        res = Math.min(res, 1 + (5 - x % 5) + solve(x / 5 + 1, y));
        res = Math.min(res, 1 + x % 11 + solve(x / 11, y));
        res = Math.min(res, 1 + (11 - x % 11) + solve(x / 11 + 1, y));
        return dp[x] = res;
    }

    // https://leetcode.com/problems/interleaving-string/
    // using sliding pointer approach with 3 pointers
    // and then do some memoization
    public boolean interleavingStrings(String s1, String s2, String s3){
        int len1 = s1.length();
        int len2 = s2.length();
        int len3 = s3.length();

        if(len3 != len1 + len2) return false;

        return recInterleave(s1, s2, s3, len1, len2, len3, 0, 0, 0);
    }

    static HashMap<String, Boolean> cache = new HashMap<>();

    private boolean recInterleave(String s1, String s2, String s3, int len1, int len2, int len3 , int p1, int p2, int p3){
        if(p3 == len3){
            return (p1 == len1 && p2 == len2) ? true : false; // means if s3 is completely traversed then s1 & s2 are also completely traversed
        }

        String key = p1 + "*" + p2 + "*" + p3;
        if(cache.containsKey(key)) return cache.get(key);

        // now there are 3 cases
        boolean res = false;
        // case:1 p1 is at end
        if(p1 == len1){
            res = (s2.charAt(p2) == s3.charAt(p3)) ? recInterleave(s1, s2, s3, len1, len2, len3, p1, p2+1, p3+1) : false;
            cache.put(key, res);
            return res;
        }
        if(p2 == len2){ // case:2 p2 reached end
            res = (s1.charAt(p1) == s3.charAt(p3)) ? recInterleave(s1, s2, s3, len1, len2, len3, p1+1, p2, p3+1) : false;
            cache.put(key, res);
            return res;
        }

        // case:3 still processing
        boolean one = false;
        boolean two = false;
        if(s1.charAt(p1) == s3.charAt(p3))
            one = recInterleave(s1, s2, s3, len1, len2, len3, p1+1, p2, p3+1);
        if(s2.charAt(p2) == s3.charAt(p3))
            two = recInterleave(s1, s2, s3, len1, len2, len3, p1, p2+1, p3+1);

        cache.put(key, one || two);
        return cache.get(key);
    }

    // https://leetcode.com/problems/custom-sort-string
    // meta can challenge freq map can suffer from collisions and slow down overall thing - check below
    // use 26 char array
    public String customSortString(String order, String s) {
        Map<Character, Integer> freq = new HashMap<>();
        int len = s.length();

        for(int i =0; i<len; i++){
            char ch = s.charAt(i);
            freq.put(ch, freq.getOrDefault(ch, 0)+1);
        }

        StringBuilder res = new StringBuilder();
        for(int i= 0; i<order.length(); i++){
            char ch = order.charAt(i);
            while(freq.getOrDefault(ch,0) > 0){
                res.append(ch);
                freq.put(ch, freq.get(ch)-1);
            }
        }

        for(char letter : freq.keySet()){
            int count = freq.get(letter);
            while (count > 0){
                res.append(letter);
                count--;
            }
        }

        return res.toString();
    }

    // meta can challenge freq map can suffer from collisions and slow down overall thing - check below
    public String customSortStringMeta(String order, String s) {
        int[] freq = new int[26]; // Assuming ASCII characters

        // Count frequency of characters in s
        for (int i = 0; i < s.length(); i++) {
            freq[s.charAt(i)]++;
        }

        StringBuilder res = new StringBuilder();

        // First, add characters as per the custom 'order'
        for (int i = 0; i < order.length(); i++) {
            char ch = order.charAt(i);
            while (freq[ch] > 0) {
                res.append(ch);
                freq[ch]--;
            }
        }

        // Then, add remaining characters not in 'order'
        for (char ch = 0; ch < freq.length; ch++) {
            while (freq[ch] > 0) {
                res.append(ch);
                freq[ch]--;
            }
        }

        return res.toString();
    }

    // counting sort
    // https://leetcode.com/problems/relative-sort-array/
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] cnt = new int[1001];
        for(int n : arr1) cnt[n]++;
        int i = 0;
        for(int n : arr2) {
            while(cnt[n]-- > 0) {
                arr1[i++] = n;
            }
        }
        for(int n = 0; n < cnt.length; n++) {
            while(cnt[n]-- > 0) {
                arr1[i++] = n;
            }
        }
        return arr1;
    }

    // https://leetcode.com/problems/is-subsequence
    public boolean isSubsequence(String s, String t) {
        if(s.length() > t.length()){
            return false;
        }
        if(s.length() == 0)
            return true;
        int i = 0, j = 0;
        while(i < t.length() && j < s.length()){
            if(t.charAt(i) == s.charAt(j))
                j++;
            i++;
        }
        if(j == s.length())
            return true;
        return false;
    }

    // https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/
    public String removeDuplicatesString(String s) {
        StringBuilder sb = new StringBuilder();
        int sbLength = 0;
        for(char character : s.toCharArray()) {
            if (sbLength != 0 && character == sb.charAt(sbLength - 1))
                sb.deleteCharAt(sbLength-- - 1);
            else {
                sb.append(character);
                sbLength++;
            }
        }
        return sb.toString();
    }

    // #meta - make sure to remove all instances of char which was duplicated
    public static String removeAllDuplicatesMeta(String s) {
        int[] freq = new int[26]; // For 'a' to 'z'

        // First pass: count frequency of each character
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        StringBuilder result = new StringBuilder();

        // Second pass: build result using only characters with frequency == 1
        for (char ch : s.toCharArray()) {
            if (freq[ch - 'a'] == 1) {
                result.append(ch);
            }
        }

        return result.toString();
    }

    // #meta ... or candy-crush-1D problem
    // https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/
    public String removeDuplicates(String s, int k) {
        Stack<Integer> count = new Stack<>();
        char[] res = s.toCharArray();

        int left = 0;

        for (int i =0; i<s.length(); i++, left++){
            res[left] = res[i];

            if (left == 0 || res[left] != res[left-1]){
                count.push(1);
            } else {
                int currCount = count.pop() +1;
                if(currCount == k){
                    left = left-k;
                }else{
                    count.push(currCount);
                }
            }
        }
        return new String(res, 0, left);
    }
}
