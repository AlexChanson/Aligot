package utility;

import java.util.ArrayList;

public class TextFormatter {
    public static String[] format(String input, int lineWidth){
        String[] temp = input.split("\n");
        ArrayList<String> out = new ArrayList<>();
        for(String s : temp){
            if(s.length() <= lineWidth)
                out.add(s);
            else
                out.addAll(split(s, lineWidth));
        }
        return out.toArray(new String[out.size()]);

    }

    private static ArrayList<String> split(String s, int lineWidth) {
        ArrayList<String> out = new ArrayList<>();
        String[] words = s.split(" ");
        if(s.length() <= lineWidth){
            out.add(s);
            return out;
        }

        for(int i = words.length - 1; i != 0; --i){
            String candidate = "";
            for (int j = 0; j < i; ++j)
                candidate += words[j] + " ";
            if(candidate.length() <= lineWidth){
                //System.out.println("Candidate valid !: " + candidate);
                out.add(candidate);
                String rest = "";
                for (int k = i; k < words.length; ++k){
                    rest += words[k];
                    if(k != words.length - 1)
                        rest += " ";
                }
                out.addAll(split(rest, lineWidth));
                return out;
            }
        }

        return out;
    }
}
