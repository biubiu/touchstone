package com.shawn.touchstone.alg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PhoneNumberMnemonics {

    private static final Map<Character, char[]> DIGITS_LETTER = new HashMap<>();

    static {
        DIGITS_LETTER.put('0', new char[]{'0'});
        DIGITS_LETTER.put('1', new char[]{'1'});
        DIGITS_LETTER.put('2', new char[]{'a', 'b', 'c'});
        DIGITS_LETTER.put('3', new char[]{'d', 'e', 'f'});
        DIGITS_LETTER.put('4', new char[]{'g', 'h', 'i'});
        DIGITS_LETTER.put('5', new char[]{'j', 'k', 'l'});
        DIGITS_LETTER.put('6', new char[]{'m', 'n', 'o'});
        DIGITS_LETTER.put('7', new char[]{'p', 'q', 'r', 's'});
        DIGITS_LETTER.put('8', new char[]{'t', 'u', 'v'});
        DIGITS_LETTER.put('9', new char[]{'w', 'x', 'y', 'z'});
    }

    public List<String> generate(String phoneNum) {
        if (phoneNum.length() == 0) return Collections.emptyList();
        List<String> result = new ArrayList<>();
        result.add("");
        //expandRecursive(0, new char[phoneNum.length()], phoneNum, result);
        for (char digit : phoneNum.toCharArray()) {
            result = expand(result, DIGITS_LETTER.get(digit));
        }
        return result;
    }

    private List<String> expand(List<String> curr, char[] letters) {
        List<String> next = new ArrayList<>();
        for (String str : curr) {
            for (char letter : letters) {
                next.add(str + letter);
            }
        }
        return next;
    }

    private void expandRecursive(int idx, char[] curr, String phone, List<String> result) {
        if (idx == curr.length) {
            result.add(new String(curr));
        } else {
            char digit = phone.charAt(idx);
            char[] letters = DIGITS_LETTER.get(digit);
            for (char letter : letters) {
                curr[idx] = letter;
                expandRecursive(idx + 1, curr, phone, result);
            }
        }
    }
}
