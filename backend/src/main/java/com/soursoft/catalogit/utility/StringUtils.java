package com.soursoft.catalogit.utility;

public class StringUtils {

    public static String formatToCamelCase(String input) {
        if (input == null || input.trim().length() == 0) {
            throw new IllegalArgumentException("Input String Cannot Be null or empty !!");
        }
        String in = input.toLowerCase();

        boolean isSpecial = true;
        int startIndex = 0;
        int endIndex = 0;

        StringBuilder sb = new StringBuilder(in.length());
        for(int i = 0; i < input.length(); i++) {
            if(in.substring(i, i+1).matches("[-:,\\.@!# $%^&*()_+><]|[\\_]")  || i == in.length()-1 || i == in.length()){
                    isSpecial = true;
                    endIndex = i == 0 ? 0 : i-1;
                    endIndex = i == in.length()-1 ? i+1 : i;
                    if(endIndex > in.length()-1) endIndex = in.length()-1;
                } else {
                    if(isSpecial) {
                        startIndex = i;
                        isSpecial = false;
                    }
                }

            if(isSpecial || (!isSpecial && i == in.length())) {
                String t = in.substring(startIndex, endIndex+1);
                if(!t.equalsIgnoreCase("")) {
                    if(t.matches("^[a-zA-Z].*+")) {
                        sb.append(t.replaceFirst(t.substring(0, 1), t.substring(0, 1).toUpperCase()));
                    } else {
                        sb.append(t);
                    }
                }
                startIndex = i+1;
            }
        }
        return sb.toString();
    }


    public static String formatToUrl(String input) {
        StringBuilder sb = new StringBuilder();

        for(char c : input.toCharArray()) {
            if(Character.isLetterOrDigit(c)) {
                sb.append(c);
            } else {
                sb.append("%" + (int)c);
            }
        }

        return sb.toString();
    }
}
