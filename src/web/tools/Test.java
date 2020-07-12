package web.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args){
        List<Integer> childComment = new ArrayList<>();
        childComment.add(1);
        childComment.add(3);
        childComment.add(9);
        childComment.add(15);
        String str = childComment.toString();
        String str1 = str.substring(1,str.length()-1);
        System.out.println(str);
        System.out.println(str1);
        List<String> returnComment = Arrays.asList(str1.split(", "));
        System.out.println(returnComment);
        List<Integer> list = new ArrayList<>();
        for(String str2:returnComment){
            list.add(Integer.valueOf(str2));
        }
        System.out.println(list);
    }
}
