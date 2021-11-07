package main;

/*
 * Date Created: 2021/10/11
 * Name: Nihongo
 * File Name: Nihongo.java
 */

// Collections
import java.util.HashMap;

public class Nihongo {
    static String
                regexPunctuation = "[\u3000-\u303F]",
                regexHiragana = "[\u3040-\u309f]",
                regexKatakana = "[\u30a0-\u30ff]",
                regexRomaji = "[\uff00-\uff9f]",
                regexCJK = "[\u4e00-\u9faf]",
                regexExtendedCJK = "[\u3400-\u4dbf]]",
                regexAll = "[\u3000-\u303F]|[\u3040-\u309F]|[\u30A0-\u30FF]|[\uFF00-\uFFEF]|[\u4E00-\u9FAF]|[\u2605-\u2606]|[\u2190-\u2195]|\u203B|[0-9]";

    // Zenkaku Hiragana Bounds
    // First Hiragana character, U+3041
    private static final char firstZenkakuHiragana = 'ぁ';

    // Last Hiragana character, U+3096
    private static final char lastZenkakuHiragana = 'ゖ';

    // Zenkaku Katakana Bounds
    // First Full-Width Katakana character, U+30A1
    private static final char firstZenkakuKatakana = 'ァ';

    // Last Full-Width Katakana character, U+30FA
    private static final char lastZenkakuKatakana = 'ヺ';

    // Hankaku Katakana Bounds
    // First Half-Width Katakana character, U+FF66
    private static final char firstHankakuKatakana = 'ｦ';

    // Last Half-Width Katakana character, U+FF9D
    private static final char lastHankakuKatakana = 'ﾝ';

    // HashMap of Hiragana to Katakana
    protected static final HashMap<Character, Character> hiraganaToKatakana = 日本語.平仮名と片仮名;

    // HashMap of Katakana to Hiragana
    protected static final HashMap<Character, Character> katakanaToHiragana = 日本語.片仮名と平仮名;

    // HashMap of Half-Width Katakana to Full-Width Katakana
    protected static final HashMap<Character, Character> hankakuToZenkakuKatakana = 日本語.半角片仮名と全角片仮名;

    // HashMap of Full-Width Katakana to Half-Width Katakana
    protected static final HashMap<Character, Character> zenkakuToHankakuKatakana = 日本語.全角片仮名と半角平仮名;

    public static String beautify(String str) {
        return 日本語.盛れます(str);
    }

    public static String extractJapanese(String str, String type) {
        return switch (type.toUpperCase()) {
            case "punctuation" -> 日本語.正規表現マッチャー(str, regexPunctuation);
            case "hiragana" -> 日本語.正規表現マッチャー(str, regexHiragana);
            case "katakana" -> 日本語.正規表現マッチャー(str, regexKatakana);
            case "romaji" -> 日本語.正規表現マッチャー(str, regexRomaji);
            case "cjk" -> 日本語.正規表現マッチャー(str, regexCJK);
            case "extended_cjk" -> 日本語.正規表現マッチャー(str, regexExtendedCJK);
            case "all" -> 日本語.正規表現マッチャー(str, regexAll);
            default -> str;
        };
    }

    /**
     * Regex matching.
     * @param str Provide a string.
     * @param regex Provide valid regex.
     * @return Output with the regex search.
     */
    public static String regexMatcher(String str, String regex) {
        return 日本語.正規表現マッチャー(str, regex);
    }

    /**
     * Gets the character count for all the general types of Japanese characters.
     * @param str Provide a string.
     * @param type Type of character(s).
     * @return Japanese character statistics as a HashMap.
     */
    public static HashMap<String, Integer> getJPCharCount(String str, String type) {
        return new HashMap<>() {{
            put(type, extractJapanese(str, type).length());
        }};
    }

    /**
     * Gets the character count for all the general types of Japanese characters, multiple types allowed.
     * @param str Provide a string.
     * @param types Provide at least one type in an array.
     * @return Japanese character statistics as a HashMap.
     */
    public static HashMap<String, Integer> getJPCharCount(String str, String[] types) {
        return new HashMap<>() {{
            for (String type : types) {
                put(type, extractJapanese(str, type).length());
            }
        }};
    }

    /**
     * Converts string with Hankaku / Zenkaku Katakana to Hiragana.
     * @param str Provide a string.
     * @return Converted string.
     */
    public static String toHiragana(String str) {
        return 日本語.を平仮名に変換(str);
    }

    /**
     * Converts string with Hiragana or Hankaku Katakana to Zenkaku Katakana.
     * @param str Provide a string.
     * @return Converted string.
     */
    public static String toKatakana(String str) {
        return 日本語.を片仮名に変換(str);
    }

    /**
     * Converts Hiragana and Katakana to Hankaku Katakana.
     * @param str Provide a string.
     * @return Converted string.
     */
    public static String toHankakuKatakana(String str) {
        return 日本語.を半角片仮名に変換(str);
    }

    /**
     * Checks if a character is Hiragana.
     * @param ch Provide a character.
     * @return Boolean.
     */
    public static boolean isHiragana(char ch) {
        return ch >= firstZenkakuHiragana && lastZenkakuHiragana >= ch;
    }

    /**
     * Checks if a character is zenkaku Katakana.
     * @param ch Provide a character.
     * @return Boolean.
     */
    public static boolean isZenkakuKatakana(char ch) {
        return ch >= firstZenkakuKatakana && lastZenkakuKatakana >= ch;
    }

    /**
     * Checks if a character is hankaku Katakana.
     * @param ch Provide a character.
     * @return Boolean.
     */
    public static boolean isHankakuKatakana(char ch) {
        return ch >= firstHankakuKatakana && lastHankakuKatakana >= ch;
    }

    /**
     * Converts Hiragana to Katakana, full-width only.
     * @param ch Provide a character.
     * @return Converted character.
     */
    public static char hiraganaToKatakana(char ch) {
        return isHiragana(ch) ? (char) (ch + 0x60) : ch;
    }

    /**
     * Converts Katakana to Hiragana, both zenkaku and hankaku are supported.
     * @param ch Provide a character.
     * @return Converted character.
     */
    public static char katakanaToHiragana(char ch) {
        return isZenkakuKatakana(ch) ? (char) (ch - 0x60) :
                isHankakuKatakana(ch) ? (char) (hankakuToZenkakuKatakana.get(ch) - 0x60) :
                 ch;
    }

    /**
     * Checks if a kana character is dakuten.
     * @param ch Provide a character.
     * @return Boolean.
     */
    public static boolean isDakuten(char ch) {
        return 日本語.濁点ですか(ch);
    }

    /**
     * Checks if a kana character is handakuten.
     * @param ch Provide a character.
     * @return Boolean.
     */
    public static boolean isHandakuten(char ch) {
        return 日本語.半濁点ですか(ch);
    }
}
