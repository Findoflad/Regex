// незакрытые html tags, если есть повторения - сколько раз тег с таким именем не закрыт

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<>();
        Stack<String> stack = new Stack<>();

        String htmlText = "<i><h1>lorem<i>ing</i><h1>tttt</i><img src=\"path\" alt=\"txt\"/>";

        Pattern anyTagPattern = Pattern.compile("</?[a-z]+\\d?[^>]*>");
        Matcher anyTagMatcher = anyTagPattern.matcher(htmlText);
        Pattern closedTagPattern = Pattern.compile("</[a-z]+\\d?>");
        Pattern tagNamePattern = Pattern.compile("[a-z]+\\d?");

        while (anyTagMatcher.find()) {
            String tag = anyTagMatcher.group();
            Matcher tmp = tagNamePattern.matcher(tag);
            tmp.find();
            String tagName = tmp.group();

            if (closedTagPattern.matcher(tag).matches()) {
                String fromStack;

                while (!(fromStack = stack.pop()).equals(tagName)) {
                    if (map.containsKey(fromStack)) {
                        map.put(fromStack, map.get(fromStack) + 1);
                    } else {
                        map.put(fromStack, 1);
                    }
                }
            } else {
                stack.push(tagName);
            }
        }

        for (String tagName: stack) {
            if (map.containsKey(tagName)) {
                map.put(tagName, map.get(tagName) + 1);
            } else {
                map.put(tagName, 1);
            }
        }

        for (var tag : map.entrySet()) {
            System.out.println(tag.getKey() + " : " + tag.getValue());
        }
    }
}