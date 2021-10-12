package main;

/*
 * Date Created: 2021/10/11
 * Name: Nihongo
 * File Name: Nihongo.java
 */

// Regex
import java.util.regex.Pattern;
import java.util.regex.Matcher;

// Collections
import java.util.Map;
import java.util.HashMap;

// Text
import java.text.Normalizer;

public class Nihongo {
    static String
                regexPunctuation = "[\u3000-\u303F]",
                regexHiragana = "[\u3040-\u309f]",
                regexKatakana = "[\u30a0-\u30ff]",
                regexRomaji = "[\uff00-\uff9f]",
                regexCJK = "[\u4e00-\u9faf]",
                regexExtendedCJK = "[\u3400-\u4dbf]]",
                regexAll = "[\u3000-\u303F]|[\u3040-\u309F]|[\u30A0-\u30FF]|[\uFF00-\uFFEF]|[\u4E00-\u9FAF]|[\u2605-\u2606]|[\u2190-\u2195]|\u203B|[0-9]";
}