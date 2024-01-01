package main;

/*
 * Date Created: 2021/10/11
 * Name: Nihongo
 * File Name: Nihongo.java
 */

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Nihongo {
  @SuppressWarnings("all")
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
  protected static final HashMap<Character, Character> zenkakuHiraganaToKatakana = new HashMap<>() {{
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

  // HashMap of Dakuten kana
  protected static final HashMap<Character, Character> zenkakuDakutenHiraganaToKatakana = new HashMap<>() {{
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
  protected static final HashMap<Character, Character> handakutenHiraganaToKatakana = new HashMap<>() {{
    // Handakuten P
    put('ぱ', 'パ');
    put('ぴ', 'ピ');
    put('ぷ', 'プ');
    put('ぺ', 'ペ');
    put('ぽ', 'ポ');
  }};

  // HashMap of Katakana to Hiragana
  protected static final HashMap<Character, Character> zenkakuKatakanaToHiragana = new HashMap<>() {{
    for (Map.Entry<Character, Character> entry : zenkakuHiraganaToKatakana.entrySet())
      put(entry.getValue(), entry.getKey());
  }};

  // HashMap of Half-Width Katakana to Full-Width Katakana
  protected static final HashMap<Character, Character> hankakuToZenkakuKatakana = new HashMap<>() {{
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

  // HashMap of Full-Width Katakana to Half-Width Katakana
  protected static final HashMap<Character, Character> zenkakuToHankakuKatakana = new HashMap<>() {{
    for (Map.Entry<Character, Character> entry : hankakuToZenkakuKatakana.entrySet())
      put(entry.getValue(), entry.getKey());

  }};

  public static String beautify(String str) {
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

  public static String extractJapanese(String str, String type) {
    return switch (type.toLowerCase()) {
      case "punctuation" -> regexMatcher(str, regexPunctuation);
      case "hiragana" -> regexMatcher(str, regexHiragana);
      case "katakana" -> regexMatcher(str, regexKatakana);
      case "romaji" -> regexMatcher(str, regexRomaji);
      case "cjk" -> regexMatcher(str, regexCJK);
      case "extended_cjk" -> regexMatcher(str, regexExtendedCJK);
      case "all" -> regexMatcher(str, regexAll);
      default -> str;
    };
  }

  /**
   * Regex matching.
   * @param str   Provide a string.
   * @param regex Provide valid regex.
   * @return Output with the regex search.
   */
  public static String regexMatcher(String str, String regex) {
    StringBuilder stringBuilder = new StringBuilder();
    Pattern patternJP = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    Matcher matcherJP = patternJP.matcher(str);

    while (matcherJP.find()) stringBuilder.append(matcherJP.group());

    return stringBuilder.toString();
  }

  /**
   * Gets the character count for all the general types of Japanese characters.
   * @param str  Provide a string.
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
   * @param str   Provide a string.
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
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (isZenkakuKatakana(str.charAt(i)) || isHankakuKatakana(str.charAt(i))) {
        stringBuilder.append(katakanaToHiragana(str.charAt(i)));
      } else stringBuilder.append(str.charAt(i));
    }
    return stringBuilder.toString();
  }

  /**
   * Converts string with Hiragana or Hankaku Katakana to Zenkaku Katakana.
   * @param str Provide a string.
   * @return Converted string.
   */
  public static String toKatakana(String str) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (isHiragana(str.charAt(i))) stringBuilder.append(hiraganaToKatakana(str.charAt(i)));
      else stringBuilder.append(str.charAt(i));
    }

    return stringBuilder.toString();
  }

  /**
   * Converts Hiragana and Katakana to Hankaku Katakana.
   * @param str Provide a string.
   * @return Converted string.
   */
  public static String toHankakuKatakana(String str) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      char kana = str.charAt(i);
      if (isDakuten(kana) || isHandakuten(kana)) {
        String normalizedKana = Normalizer.normalize(String.valueOf(kana), Normalizer.Form.NFD);
        String regularKana = normalizedKana.replace("゙", "").replace("゚", "");
        stringBuilder.append(zenkakuKatakanaToHiragana.get(hiraganaToKatakana(regularKana.charAt(0))));
        stringBuilder.append(normalizedKana.endsWith("゙") ? 'ﾞ' : 'ﾟ');
      } else if (isHiragana(kana))
        stringBuilder.append(zenkakuToHankakuKatakana.get(hiraganaToKatakana(kana)));
      else if (isKatakana(kana))
        stringBuilder.append(zenkakuToHankakuKatakana.get(kana));
      else
        stringBuilder.append(kana);
    }
    return stringBuilder.toString();
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
   * Checks if a character is Katakana.
   * @param ch Provide a character.
   * @return Boolean.
   */
  public static boolean isKatakana(char ch) {
    return ch >= firstZenkakuKatakana && lastZenkakuKatakana >= ch;
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
    return zenkakuDakutenHiraganaToKatakana.containsKey(ch)
    || zenkakuDakutenHiraganaToKatakana.containsKey(katakanaToHiragana(ch));
  }

  /**
   * Checks if a kana character is handakuten.
   * @param ch Provide a character.
   * @return Boolean.
   */
  public static boolean isHandakuten(char ch) {
    return handakutenHiraganaToKatakana.containsKey(ch) || handakutenHiraganaToKatakana.containsKey(katakanaToHiragana(ch));
  }

  // HashMap of Katakana to Romaji
  static final HashMap<String, String> katakanaToRomaji = new HashMap<>() {{
    put("ア", "a");
    put("イ", "i");
    put("ウ", "u");
    put("エ", "e");
    put("オ", "o");
    put("カ", "ka");
    put("キ", "ki");
    put("ク", "ku");
    put("ケ", "ke");
    put("コ", "ko");
    put("サ", "sa");
    put("シ", "shi");
    put("ス", "su");
    put("セ", "se");
    put("ソ", "so");
    put("タ", "ta");
    put("チ", "chi");
    put("ツ", "tsu");
    put("テ", "te");
    put("ト", "to");
    put("ナ", "na");
    put("ニ", "ni");
    put("ヌ", "nu");
    put("ネ", "ne");
    put("ノ", "no");
    put("ハ", "ha");
    put("ヒ", "hi");
    put("フ", "fu");
    put("ヘ", "he");
    put("ホ", "ho");
    put("マ", "ma");
    put("ミ", "mi");
    put("ム", "mu");
    put("メ", "me");
    put("モ", "mo");
    put("ヤ", "ya");
    put("ユ", "yu");
    put("ヨ", "yo");
    put("ラ", "ra");
    put("リ", "ri");
    put("ル", "ru");
    put("レ", "re");
    put("ロ", "ro");
    put("ワ", "wa");
    put("ヲ", "wo");
    put("ン", "n");
    put("ガ", "ga");
    put("ギ", "gi");
    put("グ", "gu");
    put("ゲ", "ge");
    put("ゴ", "go");
    put("ザ", "za");
    put("ジ", "ji");
    put("ズ", "zu");
    put("ゼ", "ze");
    put("ゾ", "zo");
    put("ダ", "da");
    put("ヂ", "ji");
    put("ヅ", "zu");
    put("デ", "de");
    put("ド", "do");
    put("バ", "ba");
    put("ビ", "bi");
    put("ブ", "bu");
    put("ベ", "be");
    put("ボ", "bo");
    put("パ", "pa");
    put("ピ", "pi");
    put("プ", "pu");
    put("ペ", "pe");
    put("ポ", "po");
    put("キャ", "kya");
    put("キュ", "kyu");
    put("キョ", "kyo");
    put("シャ", "sha");
    put("シュ", "shu");
    put("ショ", "sho");
    put("チャ", "cha");
    put("チュ", "chu");
    put("チョ", "cho");
    put("ニャ", "nya");
    put("ニュ", "nyu");
    put("ニョ", "nyo");
    put("ヒャ", "hya");
    put("ヒュ", "hyu");
    put("ヒョ", "hyo");
    put("リャ", "rya");
    put("リュ", "ryu");
    put("リョ", "ryo");
    put("ギャ", "gya");
    put("ギュ", "gyu");
    put("ギョ", "gyo");
    put("ジャ", "ja");
    put("ジュ", "ju");
    put("ジョ", "jo");
    put("ティ", "ti");
    put("ディ", "di");
    put("ツィ", "tsi");
    put("ヂャ", "dya");
    put("ヂュ", "dyu");
    put("ヂョ", "dyo");
    put("ビャ", "bya");
    put("ビュ", "byu");
    put("ビョ", "byo");
    put("ピャ", "pya");
    put("ピュ", "pyu");
    put("ピョ", "pyo");
    put("ー", "-");
    put("チェ", "che");
    put("フィ", "fi");
    put("フェ", "fe");
    put("ウィ", "wi");
    put("ウェ", "we");
    put("ヴィ", "ⅴi");
    put("ヴェ", "ve");
    put("「", "\"");
    put("」", "\"");
    put("。", ".");
  }};

  public static String convertKatakanaToRomaji(String str) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (i <= str.length() - 2) {
        if (katakanaToRomaji.containsKey(str.substring(i, i + 2))) {
          stringBuilder.append(katakanaToRomaji.get(str.substring(i, i + 2)));
          i++;
        } else if (katakanaToRomaji.containsKey(str.substring(i, i + 1)))
          stringBuilder.append(katakanaToRomaji.get(str.substring(i, i + 1)));
        else if (str.charAt(i) == 'ッ')
          stringBuilder.append(katakanaToRomaji.get(str.substring(i + 1, i + 2)).charAt(0));
        else
          stringBuilder.append(str.charAt(i));
      } else {
        if (katakanaToRomaji.containsKey(str.substring(i, i + 1)))
          stringBuilder.append(katakanaToRomaji.get(str.substring(i, i + 1)));
        else
          stringBuilder.append(str.charAt(i));
      }
    }
    return stringBuilder.toString();
  }

  /**
   * Converts all Hiragana present in a provided string to Romaji
   * @param str The string with Hiragana text
   * @return Romaji text
   */
  public static String convertHiraganaToRomaji(String str) {
    StringBuilder katakanaString = new StringBuilder();
    for (int i = 0; i < str.length(); i++) {
      if (isKatakana(str.charAt(i)))
        katakanaString.append(str.charAt(i));
      else if (isHiragana(str.charAt(i)))
        katakanaString.append(hiraganaToKatakana(str.charAt(i)));
    }

    return convertKatakanaToRomaji(katakanaString.toString());
  }
}
