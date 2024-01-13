package test;

import main.Nihongo;

public class TestEN {
    public static void main(String[] args) {
        System.out.println(Nihongo.convertKatakanaToRomaji("カタカナ・ローマジ"));
        System.out.println(Nihongo.toHiragana("てすと、テスト"));
        System.out.println(Nihongo.toKatakana("カタカナ、ひらがな"));
    }
}
