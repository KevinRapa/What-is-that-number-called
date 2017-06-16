package practice;

import java.util.*;

public class NameInteger {
    private final static String[] 
        SUFFIXES = {"", "thousand ", "million ", "billion "},
            
        ONES = {"", "one ", "two ", "three ", "four ", "five ", 
            "six ", "seven ", "eight ", "nine "},
            
        TEENS = {"", "eleven ", "twelve ", "thirteen ", "fourteen", 
            "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"},
            
        TENS = {"", "ten ", "twenty ", "thirty ", "forty ", "fifty ", 
            "sixty ", "seventy ", "eighty ", "ninety "};
    
    public static void main(String[] args) {
        for (int i = 1; i < 100000; i += 3)
            System.out.println(parseNum(i));
    }
    
    private static String parseNum(int i) {
        if (i == 0) {
            System.out.println("zero");
        }
        
    	int mod = 1;
    	LinkedList<Integer> queue = new LinkedList<>();
    	Stack<String> stack = new Stack<>();
    	String result = "";

    	while (i != 0) {
            // Enqueue the individual integers
            mod *= 10;
            int ans = i % mod;
            queue.offer(ans);
            i -= ans;
    	}

    	int group = 0; // Represents thousand, million, etc.

    	while (! queue.isEmpty()) {
            // Group numbers in threes and parse each
            if (queue.size() >= 3) {
                int[] nums = new int[3];

                for (int j = 2; j >= 0; j--) {
                    nums[j] = queue.poll();
                }
                
                // Stack them up to be appended later
                stack.push(helper(nums, group));
            }
            else {
                // For the last of the numbers
                int[] rest = new int[3];
                int j = 2;
                
                while (! queue.isEmpty())
                    rest[j--] = queue.poll();
                
                stack.push(helper(rest, group));
            }

            group++;
    	}

    	while (! stack.isEmpty()) {
            // Append the final answer together.
            result += stack.pop();
    	}
    	return result;
    }

    private static String helper(int[] i, int group) {
    	String result = "";
        
        for (int j = 0; j < i.length; j++) {
            while (i[j] >= 10)
                i[j] /= 10;
        }
        
    	if (i[0] != 0) {
    	    result += ONES[i[0]] + "hundred ";
    	}

        if (i[1] > 1) {
            result += TENS[i[1]] + ONES[i[2]];
        } else if (i[1] == 1) {
            if (i[2] == 0) {
                result += "ten";
                return result + SUFFIXES[group];
            } else {
                result += TEENS[i[2]];
            }
        } else {
            result += ONES[i[2]]; 
        }
        

        return result + SUFFIXES[group];
    }
}
