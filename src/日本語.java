/*
 * 作成日：２０２１年９月３日
 * 名前：マキヨムの変換器（マキヨムのへんかんき）
 * 氏名：マキヨムの並直列変換器（マキヨムのへいちょくれつへんかんき）
 */

// Regex
import java.sql.SQLOutput;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * 日本語（にほんご）
 */
@SuppressWarnings("NonAsciiCharacters")
public class 日本語 {
    // TODO: これを直せる
    static String
           regexの約物 = "[\u3000-\u303F]",
           regexの平仮名 = "[\u3040-\u309f]",
           regexの片仮名 = "[\u30a0-\u30ff]",
           regexのローマ字 = "[\uff00-\uff9f]",
           regexのCJK = "[\u4e00-\u9faf]",
           regexの拡張CJK = "[\u3400-\u4dbf]]",
           regexの全部 = "[\u3000-\u303F]|[\u3040-\u309F]|[\u30A0-\u30FF]|[\uFF00-\uFFEF]|[\u4E00-\u9FAF]|[\u2605-\u2606]|[\u2190-\u2195]|\u203B|[0-9]";

    /**
     * 盛れる（もれる）
     * @param str ストリングを提供する
     * @return 綺麗ストリング
     */
    public static String 盛れる(String str) {
        return str
                .replace("\\s", "　")
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
        return switch (type.toUpperCase()) {
            case "約物" -> regexマッチャー(str, regexの約物);
            case "平仮名" -> regexマッチャー(str, regexの平仮名);
            case "片仮名" -> regexマッチャー(str, regexの片仮名);
            case "ローマ字" -> regexマッチャー(str, regexのローマ字);
            case "CJK" -> regexマッチャー(str, regexのCJK);
            case "拡張CJK" -> regexマッチャー(str, regexの拡張CJK);
            case "全部" -> regexマッチャー(str, regexの全部);
            default -> str;
        };
    }

    /**
     * regexマッチャー
     * @param str ストリングを提供する
     * @param regex 正規表現を提供する
     * @return 正規表現の実績データ
     */
    private static String regexマッチャー(String str, String regex) {
        StringBuilder stringBuilder = new StringBuilder();
        Pattern patternJP = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcherJP = patternJP.matcher(str);

        while (matcherJP.find()) {
            stringBuilder.append(matcherJP.group());
        }

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(盛れる("おいしい.本当に!!!"));
        System.out.println(日本語を抜き出す("おいしい.本当に!!!", "平仮名"));
        System.out.println(日本語を抜き出す("本当に。", "約物"));
    }
}
