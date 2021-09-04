/**
 * マキヨムの並直列変換器（マキヨムのへいちょくれつへんかんき）
 */

@SuppressWarnings("NonAsciiCharacters")
/*
 * 日本語（にほんご）
 */
public class 日本語 {
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

    public static void main(String[] args) {
        System.out.println(盛れる("おいしい.本当に!!!"));
    }
}
