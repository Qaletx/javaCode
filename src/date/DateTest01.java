package date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest01 {
    public static void main(String[] args) {
//        java中提供了处理时间得一个类Date,

//        注意获得的时间都是北京时间，他是经过转换的了，
//        通过俩种方法new对象，第一种，获得当前系统的时间,一旦获得，即固定
        Date date = new Date();
        System.out.println(date);

//        第二种为传毫秒，在此之前我们学习一个System的一个静态方法，该静态方法返回的是距离1970年1月1日0时0分0秒0毫秒到
//        系统现在经理的毫秒数，
        System.out.println(System.currentTimeMillis());
        Date date1 = new Date(1);
//        可见北京的Thu Jan 01 08:00:00 CST 1970 是美国1970年1月1日0时0分0秒0毫秒
        System.out.println(date1);
        Date date2 = new Date(System.currentTimeMillis());
        System.out.println(date2);


//        date中常用的方法

//        传毫秒重新设置时间,其实还有setYear等但过期了，不需要了解
        date2.setTime(2324);
        System.out.println(date2);

//        有设置，必定有获取

//        等同与System.currentTimeMillis()
        System.out.println(date.getTime());

//      以下方法过期了，但我觉得有必要了解下
//        获得
        System.out.println("---------------------------------------");
//        注意在获取的年份上要加上1900年
        System.out.println(date.getYear());
//        注意跟js一样需要加1，1如2月为11
        System.out.println(date.getMonth());
        System.out.println(date.getDate());
        System.out.println(date.getHours());
        System.out.println(date.getMinutes());
        System.out.println(date.getSeconds());
//        获取星期也要加1，如星期日为6
        System.out.println(date.getDay());


//        我们发现date的toString方法已经重写，但输出的格式却不符合我们中国人的体验
//        如何设定新的格式
//        SimpleDateFormat是一个时间文本格式化处理类，专门对时间进行格式化，在new对象使可以利用占位符合理地格式化
//        再用SimpleDateFormat对象传一个Date对象就可以返回一个格式化后的字符串

          /*
          具体常用如下，还有很多反jdk文档就可以
        yyyy 年(年是4位)
        MM 月（月是2位）（只有从0开始的）
        dd 日
        HH 时(0-23)
        KK 时(0-11)
        mm 分
        ss 秒
        a 下午/中午
        SSS 毫秒（毫秒3位，最高999。1000毫秒代表1秒）
        注意：在日期格式中，除了y M d H m s S这些字符不能随便写之外，剩下的符号格式自己随意组织。
         */

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat( "yyyy-MM-dd aK:mm");
        System.out.println(simpleDateFormat1.format(date));

//       特别1 不传参2021/10/19 下午9:01 可发现已经默认格式为"yyyy/MM/dd aK:mm"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        System.out.println(simpleDateFormat.format(date));

//        特别2 当格式化的参数不标准，但有提及，它会自动补全修正如一个年一般需要yyyy四个才精准，但YY的话也行
//        只留年份的后俩位，但只有一个y它会自动补全四个，其他同理
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat( "y-M-dd aK:mm");
        System.out.println(simpleDateFormat2.format(date));

//        如何从一个String类型得到一个Date对象？毕竟很多地方需要用到date对象
//        还是用到SimpleDateFormat类，new个对象，记得传格式于String一样的，然后调用.parse()传String即可
            String dateString = "1890/04/13";
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date parse = simpleDateFormat3.parse(dateString);
            System.out.println(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
