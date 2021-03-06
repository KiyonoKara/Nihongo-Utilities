package main;

/*
 * 作成日：２０２１年９月３日
 * 名前：Nihongo Utilities
 * 氏名：Nihongo Utilities
 */

// 正規表現
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// Collections
import java.util.Map;
import java.util.HashMap;

// Text
import java.text.Normalizer;

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

    // 全角平仮名の限度
    // 最初の全角平仮名の文字、U+3041
    private static final char 最初の全角平仮名 = 'ぁ';

    // 最後の全角平仮名の文字、U+3096
    private static final char 最後の全角平仮名 = 'ゖ';

    // 全角片仮名の限度
    // 最初の全角片仮名の文字、U+30A1
    private static final char 最初の全角片仮名 = 'ァ';

    // 最後の全角片仮名の文字、U+30FA
    private static final char 最後の全角片仮名 = 'ヺ';

    // 半角片仮名の限度
    // 最初の半角片仮名の文字、U+FF66　
    private static final char 最初の半角片仮名 = 'ｦ';

    // 最後の半角片仮名の文字、U+FF9D　
    private static final char 最後の半角片仮名 = 'ﾝ';

    // HashMap of Hiragana to Katakana
    protected static final HashMap<Character, Character> 平仮名と片仮名 = new HashMap<>() {{
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
        put('ん', 'ン');

        // Dakuten G
        put('が', 'ガ');
        put('ぎ', 'ギ');
        put('ぐ', 'グ');
        put('げ', 'ゲ');
        put('ご', 'ゴ');

        // Dakuten Z
        put('ざ', 'ザ');
        put('じ', 'ジ');
        put('ず', 'ズ');
        put('ぜ', 'ゼ');
        put('ぞ', 'ゾ');

        // Dakuten D
        put('だ', 'ダ');
        put('ぢ', 'ヂ');
        put('づ', 'ヅ');
        put('で', 'デ');
        put('ど', 'ド');

        // Dakuten B
        put('ば', 'バ');
        put('び', 'ビ');
        put('ぶ', 'ブ');
        put('べ', 'ベ');
        put('ぼ', 'ボ');

        // Handakuten P
        put('ぱ', 'パ');
        put('ぴ', 'ピ');
        put('ぷ', 'プ');
        put('ぺ', 'ペ');
        put('ぽ', 'ポ');
    }};

    // HashMap of Katakana to Hiragana
    protected static final HashMap<Character, Character> 片仮名と平仮名 = new HashMap<>() {{
        for (Map.Entry<Character, Character> entry : 平仮名と片仮名.entrySet())
            put(entry.getValue(), entry.getKey());
    }};

    protected static final HashMap<Character, Character> 半角片仮名と全角片仮名 = new HashMap<>() {{
        // Punctuation
        put('｡', '。');
        put('｢', '「');
        put('｣', '」');
        put('､', '、');
        put('･', '・');

        // W-column
        put('ﾜ', 'ワ');
        put('ｦ', 'ヲ');

        // Vowels (To Half-width Hiragana)
        put('ｧ', 'ァ');
        put('ｨ', 'ィ');
        put('ｩ', 'ゥ');
        put('ｪ', 'ェ');
        put('ｫ', 'ォ');

        // Hankaku Y-column
        put('ｬ', 'ャ');
        put('ｭ', 'ュ');
        put('ｮ', 'ョ');

        // Glottal stop
        put('ｯ', 'ッ');

        // Prolonged sound mark
        put('ｰ', 'ー');

        // Vowels
        put('ｱ', 'ア');
        put('ｲ', 'イ');
        put('ｳ', 'ウ');
        put('ｴ', 'エ');
        put('ｵ', 'オ');

        // K-column
        put('ｶ', 'カ');
        put('ｷ', 'キ');
        put('ｸ', 'ク');
        put('ｹ', 'ケ');
        put('ｺ', 'コ');

        // S-column
        put('ｻ', 'サ');
        put('ｼ', 'シ');
        put('ｽ', 'ス');
        put('ｾ', 'セ');
        put('ｿ', 'ソ');

        // T-column
        put('ﾀ', 'タ');
        put('ﾁ', 'チ');
        put('ﾂ', 'ツ');
        put('ﾃ', 'テ');
        put('ﾄ', 'ト');

        // N-column
        put('ﾅ', 'ナ');
        put('ﾆ', 'ニ');
        put('ﾇ', 'ヌ');
        put('ﾈ', 'ネ');
        put('ﾉ', 'ノ');

        // H-column
        put('ﾊ', 'ハ');
        put('ﾋ', 'ヒ');
        put('ﾌ', 'フ');
        put('ﾍ', 'ヘ');
        put('ﾎ', 'ホ');

        // M-column
        put('ﾏ', 'マ');
        put('ﾐ', 'ミ');
        put('ﾑ', 'ム');
        put('ﾒ', 'メ');
        put('ﾓ', 'モ');

        // Y-column
        put('ﾔ', 'ヤ');
        put('ﾕ', 'ユ');
        put('ﾖ', 'ヨ');

        // R-column
        put('ﾗ', 'ラ');
        put('ﾘ', 'リ');
        put('ﾙ', 'ル');
        put('ﾚ', 'レ');
        put('ﾛ', 'ロ');

        // Nasal vowels
        put('ﾝ', 'ン');

        // Dakuten & Handakuten
        put('ﾞ', '゛');
        put('ﾟ', '゜');
    }};

    // HashMap of Zenkaku Katakana to Hankaku Katakana
    protected static final HashMap<Character, Character> 全角片仮名と半角平仮名 = new HashMap<>() {{
        for (Map.Entry<Character, Character> entry : 半角片仮名と全角片仮名.entrySet())
            put(entry.getValue(), entry.getKey());

    }};

    // HashMap of Dakuten kana
    protected static final HashMap<Character, Character> 濁点平仮名と片仮名 = new HashMap<>() {{
        // Dakuten G
        put('が', 'ガ');
        put('ぎ', 'ギ');
        put('ぐ', 'グ');
        put('げ', 'ゲ');
        put('ご', 'ゴ');

        // Dakuten Z
        put('ざ', 'ザ');
        put('じ', 'ジ');
        put('ず', 'ズ');
        put('ぜ', 'ゼ');
        put('ぞ', 'ゾ');

        // Dakuten D
        put('だ', 'ダ');
        put('ぢ', 'ヂ');
        put('づ', 'ヅ');
        put('で', 'デ');
        put('ど', 'ド');

        // Dakuten B
        put('ば', 'バ');
        put('び', 'ビ');
        put('ぶ', 'ブ');
        put('べ', 'ベ');
        put('ぼ', 'ボ');
    }};

    // HashMap of Handakuten kana
    protected static final HashMap<Character, Character> 半濁点平仮名と片仮名 = new HashMap<>() {{
        // Handakuten P
        put('ぱ', 'パ');
        put('ぴ', 'ピ');
        put('ぷ', 'プ');
        put('ぺ', 'ペ');
        put('ぽ', 'ポ');
    }};

    /**
     * 盛れる（もれる）
     * @param str ストリングを提供します
     * @return 綺麗文字列
     */
    public static String 盛れます(String str) {
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
     * @param str ストリングを提供します
     * @param type タイプ
     * @return 日本語のみの文字列
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
     * @param str ストリングを提供します
     * @param regex 正規表現を提供します
     * @return 正規表現の実績データ
     */
    public static String 正規表現マッチャー(String str, String regex) {
        StringBuilder stringBuilder = new StringBuilder();
        Pattern patternJP = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcherJP = patternJP.matcher(str);

        while (matcherJP.find()) stringBuilder.append(matcherJP.group());

        return stringBuilder.toString();
    }

    /**
     * 文字の回数を受けます（もじのかいすうをうけます）
     * @param str ストリングを提供します
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
     * @param str ストリングを提供します
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

    /**
     * を平仮名に変換（をひらがなにへんかん）
     * @param str ストリングを提供します
     * @return 平仮名の文字列
     */
    public static String を平仮名に変換(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (全角片仮名ですか(str.charAt(i)) || 半角片仮名ですか(str.charAt(i))) {
                stringBuilder.append(片仮名を平仮名に変換(str.charAt(i)));
            } else stringBuilder.append(str.charAt(i));
        }
        return stringBuilder.toString();
    }

    /**
     * を片仮名に変換（を片仮名にへんかん）
     * @param str ストリングを提供します
     * @return 片仮名の文字列
     */
    public static String を片仮名に変換(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (平仮名ですか(str.charAt(i))) stringBuilder.append(平仮名を片仮名に変換(str.charAt(i)));
            else stringBuilder.append(str.charAt(i));
        }

        return stringBuilder.toString();
    }

    /**
     * を半角片仮名に変換（をはんかくかたかなにへんかん）
     * @param str ストリングを提供します
     * @return 半角片仮名の文字列
     */
    public static String を半角片仮名に変換(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char kana = str.charAt(i);
            if (濁点ですか(kana) || 半濁点ですか(kana)) {
                String normalizedKana = Normalizer.normalize(String.valueOf(kana), Normalizer.Form.NFD);
                String regularKana = normalizedKana.replace("゙", "").replace("゚", "");
                stringBuilder.append(全角片仮名と半角平仮名.get(平仮名を片仮名に変換(regularKana.charAt(0))));
                stringBuilder.append(normalizedKana.endsWith("゙") ? 'ﾞ' : 'ﾟ');
            }
            else if (平仮名ですか(kana))
                stringBuilder.append(全角片仮名と半角平仮名.get(平仮名を片仮名に変換(kana)));
            else if (片仮名ですか(kana))
                stringBuilder.append(全角片仮名と半角平仮名.get(kana));
            else
                stringBuilder.append(kana);
        }
        return stringBuilder.toString();
    }

    /**
     * 平仮名ですか（ひらがなですか）
     * @param 一文字 一文字を提供します
     * @return 真否
     */
    public static boolean 平仮名ですか(char 一文字) {
        return 一文字 >= 最初の全角平仮名 && 最後の全角平仮名 >= 一文字;
    }

    /**
     * 全角片仮名ですか（ぜんかくかたかなですか）
     * @param 一文字 一文字を提供します
     * @return 真否
     */
    public static boolean 全角片仮名ですか(char 一文字) {
        return 一文字 >= 最初の全角片仮名 && 最後の全角片仮名 >= 一文字;
    }

    /**
     * 半角片仮名ですか（はんかくかたかなですか）
     * @param 一文字 一文字を提供します
     * @return 真否
     */
    public static boolean 半角片仮名ですか(char 一文字) {
        return 一文字 >= 最初の半角片仮名 && 最後の半角片仮名 >= 一文字;
    }

    /**
     * 片仮名ですか（かたかなですか）
     * @param 一文字 一文字を提供します
     * @return 真否
     */
    public static boolean 片仮名ですか(char 一文字) {
        return 全角片仮名ですか(一文字) || 半角片仮名ですか(一文字);
    }

    /**
     * 平仮名を片仮名に変換（ひらがなをかたかなにへんかん）
     * @param 一文字 一文字を提供します
     * @return 片仮名の一文字
     */
    public static char 平仮名を片仮名に変換(char 一文字) {
        return 平仮名ですか(一文字) ? (char) (一文字 + 0x60) : 一文字;
    }

    /**
     * 片仮名を平仮名に変換（かたかなをひらがなにへんかん）
     * @param 一文字 一文字を提供します
     * @return 平仮名の一文字
     */
    public static char 片仮名を平仮名に変換(char 一文字) {
        return 全角片仮名ですか(一文字) ?
                (char) (一文字 - 0x60) : 半角片仮名ですか(一文字) ?
                (char) ((半角片仮名と全角片仮名.get(一文字)) - 0x60) : 一文字;
    }

    /**
     * 濁点ですか（だくてんですか）
     * @param 一文字 一文字を提供します
     * @return 濁点の一文字
     */
    public static boolean 濁点ですか(char 一文字) {
        return 濁点平仮名と片仮名.containsKey(一文字) || 濁点平仮名と片仮名.containsKey(片仮名を平仮名に変換(一文字));
    }

    /**
     * 半濁点ですか（はんだくてんですか）
     * @param 一文字 一文字を提供します
     * @return 半濁点の一文字
     */
    public static boolean 半濁点ですか(char 一文字) {
        return 半濁点平仮名と片仮名.containsKey(一文字) || 半濁点平仮名と片仮名.containsKey(片仮名を平仮名に変換(一文字));
    }
}
