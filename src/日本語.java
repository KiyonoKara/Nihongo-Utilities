/*
 * 作成日：２０２１年９月３日
 * 名前：マキヨムの変換器（マキヨムのへんかんき）
 * 氏名：マキヨムの並直列変換器（マキヨムのへいちょくれつへんかんき）
 */

// 正規表現
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// Collections
import java.util.HashMap;

/**
 * 日本語（にほんご）
 */
@SuppressWarnings("NonAsciiCharacters")
public class 日本語 {
    static String
           正規表現の約物 = "[\u3000-\u303F]",
           正規表現の平仮名 = "[\u3040-\u309f]",
           正規表現の片仮名 = "[\u30a0-\u30ff]",
           正規表現のローマ字 = "[\uff00-\uff9f]",
           正規表現のCJK = "[\u4e00-\u9faf]",
           正規表現の拡張CJK = "[\u3400-\u4dbf]]",
           正規表現の全部 = "[\u3000-\u303F]|[\u3040-\u309F]|[\u30A0-\u30FF]|[\uFF00-\uFFEF]|[\u4E00-\u9FAF]|[\u2605-\u2606]|[\u2190-\u2195]|\u203B|[0-9]";

    private static final HashMap<Character, Character> 平仮名と片仮名 = new HashMap<>() {{
        // Vowels
        put('あ', 'ア');
        put('い', 'イ');
        put('う', 'ウ');
        put('え', 'エ');
        put('お', 'オ');

        // K-column
        put('か', 'カ');
        put('き', 'キ');
        put('く', 'ク');
        put('け', 'ケ');
        put('こ', 'コ');

        // S-column
        put('さ', 'サ');
        put('し', 'シ');
        put('す', 'ス');
        put('せ', 'セ');
        put('そ', 'ソ');

        // T-column
        put('た', 'タ');
        put('ち', 'チ');
        put('つ', 'ツ');
        put('て', 'テ');
        put('と', 'ト');

        // N-column
        put('な', 'ナ');
        put('に', 'ニ');
        put('ぬ', 'ヌ');
        put('ね', 'ネ');
        put('の', 'ノ');

        // H-column
        put('は', 'ハ');
        put('ひ', 'ヒ');
        put('ふ', 'フ');
        put('へ', 'ヘ');
        put('ほ', 'ホ');

        // M-column
        put('ま', 'マ');
        put('み', 'ミ');
        put('む', 'ム');
        put('め', 'メ');
        put('も', 'モ');

        // Y-column
        put('や', 'ヤ');
        put('ゆ', 'ユ');
        put('よ', 'ヨ');

        // R-column
        put('ら', 'ラ');
        put('り', 'リ');
        put('る', 'ル');
        put('れ', 'レ');
        put('ろ', 'ロ');

        // W-column
        put('わ', 'ワ');
        put('ゐ', 'ヰ');
        put('ゑ', 'ヱ');
        put('を', 'ヲ');

        // Nasal vowels
        put('ん', 'ん');
    }};

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
            case "約物" -> 正規表現マッチャー(str, 正規表現の約物);
            case "平仮名" -> 正規表現マッチャー(str, 正規表現の平仮名);
            case "片仮名" -> 正規表現マッチャー(str, 正規表現の片仮名);
            case "ローマ字" -> 正規表現マッチャー(str, 正規表現のローマ字);
            case "CJK" -> 正規表現マッチャー(str, 正規表現のCJK);
            case "拡張CJK" -> 正規表現マッチャー(str, 正規表現の拡張CJK);
            case "全部" -> 正規表現マッチャー(str, 正規表現の全部);
            default -> str;
        };
    }

    /**
     * 正規表現マッチャー（せいきひょうげんマッチャー）
     * @param str ストリングを提供する
     * @param regex 正規表現を提供する
     * @return 正規表現の実績データ
     */
    public static String 正規表現マッチャー(String str, String regex) {
        StringBuilder stringBuilder = new StringBuilder();
        Pattern patternJP = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcherJP = patternJP.matcher(str);

        while (matcherJP.find()) {
            stringBuilder.append(matcherJP.group());
        }

        return stringBuilder.toString();
    }

    /**
     * 文字の回数を受けます（もじのかいすうをうけます）
     * @param str ストリングを提供する
     * @param type タイプ
     * @return 日本語の統計のHashMapです
     */
    public static HashMap<String, Integer> 文字の回数を受けます(String str, String type) {
        return new HashMap<>() {{
            put(type, 日本語を抜き出す(str, type).length());
        }};
    }


    /**
     * 文字の回数を受けます（もじのかいすうをうけます）
     * @param str ストリングを提供する
     * @param types アレイタイプ
     * @return 日本語の統計のHashMapです
     */
    public static HashMap<String, Integer> 文字の回数を受けます(String str, String[] types) {
        return new HashMap<>() {{
            for (String type : types) {
                put(type, 日本語を抜き出す(str, type).length());
            }
        }};
    }

    public static void main(String[] args) {
        System.out.println(盛れる("おいしい.本当に!!!"));
        System.out.println(日本語を抜き出す("おいしい.本当に!!!", "平仮名"));
        System.out.println(日本語を抜き出す("本当に。", "約物"));
        System.out.println(文字の回数を受けます("日本語を話します", "全部"));
        System.out.println(文字の回数を受けます("日本語を話します", new String[]{"全部", "約物", "平仮名", "CJK"}));
    }
}
