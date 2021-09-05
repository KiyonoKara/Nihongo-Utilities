/*
 * 作成日：２０２１年９月３日
 * 名前：マキヨムの変換器（マキヨムのへんかんき）
 * 氏名：マキヨムの並直列変換器（マキヨムのへいちょくれつへんかんき）
 */

// Regex
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 日本語（にほんご）
 */
@SuppressWarnings("NonAsciiCharacters")
public class 日本語 {
    String regexの約物 = "/[\\u3000-\\u303F]/g",
           regexの平仮名 = "/[\\u3040-\\u309f]/g",
           regexの片仮名 = "/[\\u30a0-\\u30ff]/g",
           regexのローマ字 = "/[\\uff00-\\uff9f]/g",
           regexのCJK = "/[\\u4e00-\\u9faf]/g",
           regexの拡張CJK = "/[\\u3400-\\u4dbf]]/g",
           regexの全部 = "/[\\u3000-\\u303F]|[\\u3040-\\u309F]|[\\u30A0-\\u30FF]|[\\uFF00-\\uFFEF]|[\\u4E00-\\u9FAF]|[\\u2605-\\u2606]|[\\u2190-\\u2195]|\\u203B|[0-9]/g";

    /**
     * 盛れる（もれる）
     * @param str ストリングを提供する
     * @return 綺麗ストリング
     */
    public static String 盛れる(String str) {
        return str
                .replace("/\\s/g", "　")
                .replace(",", "、")
                .replace(".", "。")
                .replace("~", "〜")
                .replace(":", "：")
                .replace("!", "！")
                .replace("?", "？")
                .trim();
    }

    /**
     * 日本語を抜き出す（にほんごをぬきだす）
     * @param str ストリングを提供する
     * @param type タイプ
     * @return 日本語のみのストリング
     */
    public static String 日本語を抜き出す(String str, String type) {
        switch (type.toUpperCase()) {
            case "約物": {

            }
            case "平仮名": {

            }
            case "片仮名": {

            }
            case "ローマ字": {

            }
            case "CJK": {

            }
            case "拡張CJK": {

            }
            case "全部": {

            }
        }
        return "";
    }

    public static void main(String[] args) {
        System.out.println(盛れる("おいしい.本当に!!!"));
    }
}
