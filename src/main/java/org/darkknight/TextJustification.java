package org.darkknight;

import java.util.ArrayList;
import java.util.List;

public class TextJustification {
    // https://leetcode.com/problems/text-justification/
    // greedy algo
    // if not the last line
    // spaces between words should be dist as left as possible
    // if last line - no extra space b/w words
    public static List<String> textJustification(String[] words, int maxWidth){
        List<String> res = new ArrayList<>();
        int n = words.length;
        int i = 0;

        while(i < n){
            int totalChars = words[i].length();
            int last = i+1;

            // decide how many words can be put on the same line
            while(last < n){
                if(totalChars + 1 + words[last].length() > maxWidth) break;
                totalChars += 1 + words[last].length();
                last++;
            }

            // modify the space between words (can be 2 scenarios)
            int gaps = last- i - 1;
            StringBuilder sb = new StringBuilder();

            // if last line
            if(last == n || gaps == 0){
                for(int p = i; p< last; p++){
                    sb.append(words[p]);
                    sb.append(" ");
                }

                sb.deleteCharAt(sb.length()-1);
                while(sb.length() < maxWidth){
                    sb.append(" ");
                }
            }else{ // if not last line
                int spaces = (maxWidth - totalChars) / gaps;
                int rest = (maxWidth - totalChars) % gaps;

                for(int p = i; p < last-1; p++){
                    sb.append(words[p]);
                    sb.append(" ");

                    for(int j=0; j< spaces + (p - i < rest ? 1 : 0); j++){
                        sb.append(" ");
                    }
                }

                sb.append(words[last-1]);
            }

            res.add((sb.toString()));
            i = last;
        }

        return res;
    }
}
