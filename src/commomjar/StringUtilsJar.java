package commomjar;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Spliterator;

public class StringUtilsJar {
//    无插入，很多是传化和查询
    public static void main(String[] args) {
//    以下是截取字符串


//        String substring(String str,int start)：避免异常地从指定的String获取一个子字符串。
//        String substring(String str,int start, int end)：可避免异常地从指定的String获取一个子字符串
//        与string.substring最大的区别就是安全性提高，""和null进行截取都返回""和null原值
//        同时还支持了负值的截取


        // ""       null和""截取后都返回null和""
        System.out.println(StringUtils.substring("", 2));
        // null
        System.out.println(StringUtils.substring(null, 2));
        // fighter 指定的起始截取位置为0，则从第一位开始截取，也就是不截取
        System.out.println(StringUtils.substring("fighter", 0));
        // ghter 指定的截取位置为2，则从左往右第三位开始截取
        System.out.println(StringUtils.substring("fighter", 2));
        // er 指定的截取位置为-2，则从右往左第二位开始截取
        System.out.println(StringUtils.substring("fighter", -2));


//        String substringAfter(String str,String separator)：在第一次出现分隔符后获取子字符串。
//        String substringAfterLast(String str,String separator)：在最后一次出现分隔符之后获取子字符串。
//        顾名思义，但要知道都是从左到右进行查找，而且不包含分隔符，所以取得的字符串可以进行分隔符拼接
//        被找字符串为""返回"" null返回null  分隔符为""返回原字符串，null返回"",找不到返回""，分隔符在尾部也返回""

        // na 从第一次出现"i"的位置向后截取，不包含第一次出现的"i"
        System.out.println(StringUtils.substringAfter("china", "i"));
        // inachina 是第一次的i
        System.out.println(StringUtils.substringAfter("chinachina", "h"));
        // "" 当分隔符是最后时
        System.out.println(StringUtils.substringAfter("china", "a"));
        // "" 分隔符在要截取的字符串中不存在，则返回""
        System.out.println(StringUtils.substringAfter("china", "d"));
        // china 分隔符为""，则返回原字符串
        System.out.println(StringUtils.substringAfter("china", ""));
        // "" 分隔符为null，则返回""
        System.out.println(StringUtils.substringAfter("china", null));
        // na "i"最后出现的位置向后截取
        System.out.println(StringUtils.substringAfterLast("chinachina", "i"));

//        有以分隔符向后切，自然有分隔符像前切的
//        String substringBefore(String str,String separator)：在第一次出现分隔符之前获取子字符串。
//        String substringBeforeLast(String str,String separator)：在最后一次出现分隔符之前获取子字符串。
//        跟上面基本一样，只不过是自左向右找到后，切前面的

//        String substringBetween(String str,String tag)：获取嵌套在同一String的两个实例之间的String。
//        String substringBetween(String str, String open, String close)：获取嵌套在两个字符串之间的字符串。
//        顾名思义，也没包含俩端，非常注意的是标志字符串正常时，被切字符为""和null都返回null
//        当被切字符串正常时，标志字符串为""或null也是""和null返回
//        都为""返回"" 都为null返回null
//        "" null 和null "" 都返回null

        //null
        System.out.println(StringUtils.substringBetween(null, "nice"));
        //null
        System.out.println(StringUtils.substringBetween("", "nice"));
        //""
        System.out.println(StringUtils.substringBetween("nice", ""));
        //null
        System.out.println(StringUtils.substringBetween("nice", null));
        //null
        System.out.println(StringUtils.substringBetween(null, null));
        //""
        System.out.println(StringUtils.substringBetween("", ""));
        //null
        System.out.println(StringUtils.substringBetween("", null));
        //null
        System.out.println(StringUtils.substringBetween(null, ""));
        //abc
        System.out.println(StringUtils.substringBetween("tagabctag", "tag"));
        //ght
        System.out.println(StringUtils.substringBetween("fighter", "fi", "er"));

//        判空
//        boolean isBlank(CharSequence cs) :检查CharSequence是否为"" null 或一切UNICODE空白字符
//        boolean isEmpty(CharSequence cs)：检查CharSequence是否为"" 或null。
//        boolean isWhitespace(CharSequence cs)：判""和或一切UNICODE空白字符
//        boolean isNotBlank(CharSequence cs)：Blank的取反
//        boolean isNotEmpty(CharSequence cs)：Empty的取反
//        boolean isAnyBlank(CharSequence… css)：Blank中变成可变长数组，且一个不满足，返回false
//        boolean isAnyEmpty(CharSequence… css)：Empty中变成可变长数组，且一个不满足，返回false
//        boolean isNoneBlank(CharSequence… css)：如上取反
//        boolean isNoneEmpty(CharSequence… css)：如上取反

//        去除空白
//
//        String trim(String str)：从此String的两端移除空白字符，但有些UNICODE空白字符不能去除，如中文的全角空格，null返回null(优点) ""返回""
//        String trimToEmpty(String str)：同上只不过null和"" 返回的是""
//        String trimToNull(String str)： 同上只不过null和"" 返回的是null
//        String strip(String str)：同trim但能去除所有UNOICODE的空白字符
//        String stripToEmpty(String str)：同上只不过null和"" 返回的是""
//        String stripToNull(String str)：同上只不过null和"" 返回的是null
//        String deleteWhitespace(String str):跟strip一致，但去除整个字符串中全部UNICODE空白字符

        //""
        System.out.println(StringUtils.trim(""));
        //null
        System.out.println(StringUtils.trim(null));
        //""
        System.out.println(StringUtils.trimToEmpty(""));
        //""
        System.out.println(StringUtils.trimToEmpty(null));
        //null
        System.out.println(StringUtils.trimToNull(""));
        //null
        System.out.println(StringUtils.trimToNull(null));
        //　a
        System.out.println(StringUtils.trim("\u3000a"));
        //a
        System.out.println(StringUtils.strip("\u3000a"));
        //sdad
        System.out.println(StringUtils.deleteWhitespace("   s \u3000da d  "));

//        String capitalize(String str)：根据Character.toTitleCase（int）将首字母大写更改为首字母大写(不是每个单词)，""返回"" null返回null
//        String uncapitalize(String str)：将一个字符串取消大写(不是每个单词)，将每个字符按Character.toLowerCase（int）更改为小写，""返回"" null返回null
//        String upperCase(String str)：根据String.toUpperCase（）将String转换为大写，""返回"" null返回null
//        String lowerCase(String str)：根据String.toLowerCase（）将String转换为小写，""返回"" null返回null
//        String swapCase(String str)：交换字符串的大小写，将大写更改为小写，将小写更改为大写，""返回"" null返回null
//        boolean isAllUpperCase(CharSequence cs)：检查CharSequence是否仅包含大写字符，""和null都返回false
//        boolean isAllLowerCase(CharSequence cs)：检查CharSequence是否仅包含小写字符，""和null都返回false

        System.out.println(StringUtils.capitalize(""));
        System.out.println(StringUtils.capitalize(null));
        //Hello every one
        System.out.println(StringUtils.capitalize("hello every one"));
        System.out.println(StringUtils.uncapitalize(""));
        System.out.println(StringUtils.capitalize(null));
        //Hello Every one
        System.out.println(StringUtils.uncapitalize("Hello Every one"));
        System.out.println(StringUtils.upperCase(""));
        System.out.println(StringUtils.upperCase(null));
        System.out.println(StringUtils.lowerCase(""));
        System.out.println(StringUtils.lowerCase(null));
        System.out.println(StringUtils.swapCase(""));
        System.out.println(StringUtils.swapCase(null));
        //hHIsdAfFasAFSjJ
        System.out.println(StringUtils.swapCase("HhiSDaFfASafsJj"));
        //false
        System.out.println(StringUtils.isAllLowerCase(""));
        //false
        System.out.println(StringUtils.isAllLowerCase(null));
        //false
        System.out.println(StringUtils.isAllUpperCase(""));
        //false
        System.out.println(StringUtils.isAllUpperCase(null));

        //替换
        //以下三个方法为replace的安全版，且把char单独分出来了，当被替换字符正常而当第二个或第三个参数为null时返回原字符串，第二个为"",不管第三个是什么都返回原字符串
//        当第三个为""时，自然是去除字符，第一个null则为null,第一个""则为""，后面的方法也是如此
//        String replace(String text, String searchString, String replacement)：将字符串中的子串替换成另一个字符串
//        String replaceChars(String str, char searchChar, char replaceChar)：将一个字符串中所有出现的字符替换为另一个。
//        String replaceChars(String str, String searchChars, String replaceChars)：一次性替换字符串中的多个字符。
//        可以再加个数字指定替换次数，0为不替换，负值为全部替换
//        String replace(String text, String searchString, String replacement, int max)：
//        String replaceOnce(String text, String searchString, String replacement)：一次将第一个字符串替换成另一个字符串。
//        String replaceEach(String text, String[] searchList, String[] replacementList)：替换另一个字符串中所有出现的字符串，数组个数
//        要匹配且数组b中1替换数组a中1，不对对碰
//        String replaceEachRepeatedly(String text, String[] searchList, String[]replacementList)：同上，但会对对碰

//        String overlay(String str,String overlay,int start,int end)：用另一个字符串覆盖一个字符串的一部分,为左闭右开覆盖，且当第一参数为null
//        时返回null，第二个为null或""，皆是返回""  ，第二个参数覆盖第一个


        //dad
        System.out.println(StringUtils.replace("dad",null,null));
        //dad
        System.out.println(StringUtils.replace("dad",null,""));
        //da
        System.out.println(StringUtils.replace("da","","sda"));
        //""
        System.out.println(StringUtils.replace("","","sda"));
        //null
        System.out.println(StringUtils.replace(null,"das","das"));
        //bbbb
        System.out.println(StringUtils.replace("aaaa","a","b",-2));
        //""
        System.out.println(StringUtils.overlay("",null,0,5));
        //null
        System.out.println(StringUtils.overlay(null,"asdas",0,5));
        //replaceEach方法,可以同时替换多个字符序列，但被替换和替换的字符序列的个数应该对应，否则会报IllegalArgumentException
        //xinz
        System.out.println(StringUtils.replaceEach("china", new String[]{"ch", "a"}, new String[]{"x", "z"}));
        System.out.println(StringUtils.replaceEach("china", null, new String[]{"x", "z"}));
        //抛出异常
//        System.out.println(StringUtils.replaceEach("china", new String[]{"ch", "a"}, new String[]{"x", "z", "y"}));
        //replaceEachRepeatedly方法,可以循环进行替换
        //zhina
        System.out.println(StringUtils.replaceEachRepeatedly("china", new String[]{"c", "x"}, new String[]{"x", "z"}));

//  包含
//        以下俩个参数出现至少一个null时为false，第二个为""永为true

//        boolean contains(CharSequence seq, int searchChar)：检查CharSequence是否包含搜索的CharSequence，处理null的情况。
//        boolean contains(CharSequence seq, CharSequence searchSeq)：检查CharSequence是否包含搜索的CharSequence，处理null的情况。
//        弥补了string.contain区分大小写的缺点
//        boolean containsIgnoreCase(CharSequence str, CharSequence searchStr)：检查CharSequence是否包含搜索CharSequence（不区分大小写），处理null。
//         以集合判断只要有一个就为ture
//        boolean containsAny(CharSequence cs, char… searchChars)：检查CharSequence是否在给定的字符集中包含任何字符。
//        boolean containsAny(CharSequence cs, CharSequence searchChars)：检查CharSequence是否在给定的字符集中包含任何字符。
//        出现其他就为false
//        boolean containsOnly(CharSequence cs,char… valid)：检查CharSequence是否仅包含某些字符。
//        boolean containsOnly(CharSequence cs, String validChars)：检查CharSequence是否仅包含某些字符。
//        出现一个为false
//        boolean containsNone(CharSequence cs,char… searchChars)：检查CharSequence是否不包含某些字符。
//        boolean containsNone(CharSequence cs, String invalidChars)：检查CharSequence是否不包含某些字符。
//        弥补了string中的区分大小写
//        boolean startsWith(CharSequence str,CharSequence prefix)：检查CharSequence是否以指定的前缀开头。
//        boolean startsWithIgnoreCase(CharSequence str,CharSequence prefix)：不区分大小写，检查CharSequence是否以指定的前缀开头。
//        只要满足一个就为ture
//        boolean startsWithAny(CharSequence string,CharSequence… searchStrings)：检查CharSequence是否以任何提供的区分大小写的前缀开头。
//        同理start换成end
        //true
        System.out.println(StringUtils.contains("sda",97));
        //false
        System.out.println(StringUtils.contains("",null));
        //false
        System.out.println(StringUtils.contains(null,""));
        //true
        System.out.println(StringUtils.contains("asd",""));
        //true
        System.out.println(StringUtils.contains("",""));
        //true
        System.out.println(StringUtils.containsAny("asdas","sa"));

//        反转 null为null ""为""
//        String reverse(String str)：根据StringBuilder.reverse（）反转字符串。
//        String reverseDelimited(String str, char separatorChar)：以分隔符为中心，反转字符串，如果多个，则外则为一组反转,奇数的中心一个不变

//        null
        System.out.println(StringUtils.reverse(null));
//        ""
        System.out.println(StringUtils.reverse(""));
//        56X34X12
        System.out.println(StringUtils.reverseDelimited("12X34X56",'X'));
//        78X56X34X12
        System.out.println(StringUtils.reverseDelimited("12X34X56X78",'X'));

//        移除 第一个值为null则返回null 第二个值为null返回原值
//        String remove(String str, char remove)：从源字符串中删除所有出现的字符。
//        String remove(String str, String remove)：从源字符串中删除所有出现的子字符串。

        //null
        System.out.println(StringUtils.remove(null,""));
        //asd
        System.out.println(StringUtils.remove("asd",null));
        //asd
        System.out.println(StringUtils.remove("asd",""));

//        查找索引 第一个值为null返回-1，表示没找到,当第一个参数不是null 而第二个参数为""时返回0
//        int indexOf(CharSequence seq, int searchChar)：返回指定字符首次出现的序列中的索引。
//        int indexOf(CharSequence seq,CharSequence searchSeq)：在CharSequence中查找第一个索引。
//        int indexOf(CharSequence seq,CharSequence searchSeq,int startPos)：从指定位置在CharSequence中发现第一个索引的不区分大小写。
//        弥补了string中的区分大小写
//        int indexOfIgnoreCase(CharSequence str, CharSequence searchStr)：CharSequence中第一个索引的大小写不区分大小写。
//          以上四个lastIndexOf也有


//      拼接（第一参数不能为null，数组可以不初始化，为默认值）
//        为split反过来,但split只能返回String数组，join能传任何数组，再加个连接符，返回一个String
//
        String join = StringUtils.join(new short[2], '-');
//        0-0
        System.out.println(join);

        String join1 = StringUtils.join(new short[]{2,4}, '-');
//        2-4
        System.out.println(join1);


//        省略(，第一个参数为null时返回null,""返回"",参数maxWidth小于等于3时(因为要3个点，加上第二个参数，类推)，抛出IllegalArgumentException异常)
//           对于一些文本字符串很长，需要取前m个而后面加三个点，总共m+3个字符可以用此
//        第二个参数可有可无，默认为三个点
//       StringUtils.abbreviate(String text,int maxWidth)
//       StringUtils.abbreviate(String text,String abbrevMarker,int maxWidth)

//        异常
//        System.out.println(StringUtils.abbreviate("sdalkjkljlkjilkdas", 3));
//        sd...
        System.out.println(StringUtils.abbreviate("sdalkjkljlkjilkdas", 5));
//       异常
//        System.out.println(StringUtils.abbreviate("sdalkjkljlkjilkdas", "oooo",4));
//        sdoooo
        System.out.println(StringUtils.abbreviate("sdalkjkljlkjilkdas", "oooo",6));

//        左截取，右截取 截取满足的个数，null返回null ""返回""
        System.out.println(StringUtils.left("    dasd", 5));
        System.out.println(StringUtils.right("dasdasdasd", 3));
        System.out.println(StringUtils.right(null, 3));

//        填充(第一参数为null返回null，填充的字符串""和null都按""处理，则返回原字符串,当size小于原字符串返回原串)
//        StringUtils.leftPad(String str,int size)  (默认是空格)
//        StringUtils.leftPad(String str,int size,char/String)  (填充在具体位置然后截取)
//        StringUtils.rightPad(String str,int size)
//        StringUtils.rightPad(String str,int size,char/String)
//        StringUtils.center(String str,int size)（优先补的右）
//        StringUtils.center(String str,int size,char/String) (当超过size返回原串)

//        dasdd
        System.out.println(StringUtils.leftPad("sda", 5,"dasdd"));
//        sdada
        System.out.println(StringUtils.rightPad("sda", 5,"dasdd"));
//        null
        System.out.println(StringUtils.rightPad(null, 5,"dasdd"));
//        dad
        System.out.println(StringUtils.rightPad("dad", 5,null));
//        dad
        System.out.println(StringUtils.rightPad("dad", 5,""));
//        dad
        System.out.println(StringUtils.rightPad("dad", 1));

//        优先补右  4
        System.out.println(StringUtils.center("sda", 4).length());
//        sda
        System.out.println(StringUtils.center("sda", 4,"dasdd"));
//        *sda**
        System.out.println(StringUtils.center("sda", 6,'*'));

//        比较(非常安全的比较，俩个null也为相等0，而还可以通过-1，和1，去判断null在嘛)
//        StringUtils.compare(String str1,String str2)
//        StringUtils.compare(String str1,String str2,boolean nulllsLess)第三个参数默认为true，即第一个为null返回-1，第二个为null返回1,false
//        则相反

//        0
        System.out.println(StringUtils.compare(null, null));
//        -1
        System.out.println(StringUtils.compare(null, "2"));
//        1
        System.out.println(StringUtils.compare("s", null));

//        切割(第一个参数为null则返回null 第一个为"" 则返回"",当原数组<=size返回原串，size为负异常)
//        String StringUtils.truncate(String string,int size) (size为要的长度，切为从左到右切)

//        null
        System.out.println(StringUtils.truncate(null,2));
//        ""
        System.out.println(StringUtils.truncate("",2));
//        as
        System.out.println(StringUtils.truncate("asdf",2));
//        asdf
        System.out.println(StringUtils.truncate("asdf",6));
//        ""
        System.out.println(StringUtils.truncate("asdf",0));
//        异常
//        System.out.println(StringUtils.truncate("asdf",-1));

//     包装(即左右包装一个char或string,第一个参数为null返回null，为""返回"" 第二个字符串为null或""都返回原值)
//        String StringUtils.wrap(String str1, char char1)
//        String StringUtils.wrap(String str1, String str2)
//        去包装(对null和""的处理同上面)
//        String StringUtils.unwrap(String str1, char char1)  （注意俩侧都得有，不然返回原串）
//        String StringUtils.unwrap(String str1, String str2)  （注意俩侧都得有，不然返回原串）

//        XdsfX
        System.out.println(StringUtils.wrap("dsf", 'X'));
//        null
        System.out.println(StringUtils.wrap(null, 'X'));
//        ""
        System.out.println(StringUtils.wrap("", 'X'));
//        da
        System.out.println(StringUtils.wrap("da", null));
//        da
        System.out.println(StringUtils.wrap("da", ""));
//        das  俩侧都得要有
        System.out.println(StringUtils.unwrap("das", "d"));


//        其他
//        如果字符串为null，返回""空字符串，可以指定默认字符串，否者返回原字符串
        System.out.println(StringUtils.defaultString(null));
        System.out.println(StringUtils.defaultString(null,"我是null，返回一个空字符\"\""));
        System.out.println(StringUtils.defaultString("原字符串","我是null，返回一个空字符\"\""));

//        如果字符串为null "" 空白字符 返回成一个字符串(必须指明),否者返回原字符串
        System.out.println(StringUtils.defaultIfBlank("  ", "我是来判断的"));
        System.out.println(StringUtils.defaultIfBlank("", "我是来判断的"));
        System.out.println(StringUtils.defaultIfBlank("d", "我是来判断的"));

//         如果字符串为null ""  返回成一个字符串(必须指明),否者返回原字符串
        System.out.println(StringUtils.defaultIfEmpty("", "我是来判断的"));
        System.out.println(StringUtils.defaultIfEmpty(null, "我是来判断的"));
        System.out.println(StringUtils.defaultIfEmpty("  ", "我是来判断的"));
        System.out.println(StringUtils.defaultIfEmpty("d", "我是来判断的"));

//      切掉尾巴最后一个，非常好用,""返回"" null返回null
        System.out.println(StringUtils.chop("dsj,"));
        System.out.println(StringUtils.chop(""));
        System.out.println(StringUtils.chop(null));



//      切掉尾巴指定的一段，如果没有则返回原串，因为是过期的，所以不用了解过多
//        dasda
        System.out.println(StringUtils.chomp("dasda","sd"));
//        das
        System.out.println(StringUtils.chomp("dasda","da"));


    }
}
