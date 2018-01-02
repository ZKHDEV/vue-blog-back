package top.kmacro.blog.utils;

import org.apache.commons.lang.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static String MD_CODE_PATTERN_STR = "```[\\s\\S]*?```";

    public static String escapeMarkDownToHtml(String md){
        md = StringEscapeUtils.escapeHtml(md);

        Pattern pattern = Pattern.compile(MD_CODE_PATTERN_STR);
        Matcher matcher = pattern.matcher(md);

        StringBuffer html = new StringBuffer();
        while(matcher.find()) {
            matcher.appendReplacement(html, StringEscapeUtils.unescapeHtml(matcher.group(0)));
        }
        matcher.appendTail(html);

        return html.toString();
    }
}
