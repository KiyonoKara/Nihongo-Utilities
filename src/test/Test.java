package test;

import main.日本語;

public class Test {
    public static void main(String[] args) {
        System.out.println(日本語.盛れる("おいしい.本当に!!!"));
        System.out.println(日本語.日本語を抜き出す("おいしい.本当に!!!", "平仮名"));
        System.out.println(日本語.日本語を抜き出す("本当に。", "約物"));
        System.out.println(日本語.文字の回数を受けます("日本語を話します", "全部"));
        System.out.println(日本語.文字の回数を受けます("日本語を話します", new String[]{"全部", "約物", "平仮名", "CJK"}));
        System.out.println(日本語.を平仮名に変換("ワカッタ！"));
        System.out.println(日本語.平仮名ですか('ひ'));
        System.out.println(日本語.全角片仮名ですか('ヒ'));
        System.out.println(日本語.平仮名を片仮名に変換('ふ'));
        System.out.println(日本語.片仮名を平仮名に変換('ハ'));
        System.out.println(日本語.片仮名を平仮名に変換('ﾊ'));
        System.out.println(日本語.片仮名を平仮名に変換('ﾇ'));
        System.out.println(日本語.を平仮名に変換("ｱﾘｶﾞﾄｳｺﾞｻﾞｲﾏｽ"));
        System.out.println(日本語.を片仮名に変換("おはようございます"));
        System.out.println(日本語.を半角片仮名に変換("何でもありません"));
    }
}
