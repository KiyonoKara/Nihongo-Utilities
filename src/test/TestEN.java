package test;

import main.Nihongo;

public class TestEN {
    public static void main(String[] args) {
        System.out.println(Nihongo.convertKatakanaToRomaji("キョキュキャ"));
        System.out.println(Nihongo.toHiragana("はい、テスト"));
        System.out.println(Nihongo.toKatakana("カタカナ、ひらがな"));
    }
}
