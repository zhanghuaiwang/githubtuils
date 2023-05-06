package utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by admin
 * Date:2017-11-24-0024
 * Time:10:02
 * Desc:字符串操作工具包
 */

public class StringUtils {

    private final static Pattern emailer = Pattern
            .compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将字符串转位日期类型,yyyy-MM-dd HH:mm:ss
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * yyyy-MM-dd
     *
     * @param sdate
     * @return
     */
    public static Date toDate2(String sdate) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {

        Date time = toDate(sdate);

        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        // 判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0) {
                ftime = Math.max(
                        (cal.getTimeInMillis() - time.getTime()) / 60000, 1)
                        + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days < 31) {
            ftime = days + "天前";
        } else if (days >= 31 && days <= 2 * 31) {
            ftime = "一个月前";
        } else if (days > 2 * 31 && days <= 3 * 31) {
            ftime = "2个月前";
        } else if (days > 3 * 31 && days <= 4 * 31) {
            ftime = "3个月前";
        } else {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 以友好的方式显示时间
     *
     * @param date
     * @return
     */
    public static String friendly_time(Date date) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return friendly_time(f.format(date));
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 返回long类型的今天的日期
     *
     * @return
     */
    public static long getToday() {
        Calendar cal = Calendar.getInstance();
        String curDate = dateFormater2.get().format(cal.getTime());
        curDate = curDate.replace("-", "");
        return Long.parseLong(curDate);
    }

    public static String SubTDataTime(String strDate) {
        return (StringUtils.isEmpty(strDate) ? "" : strDate.substring(0, strDate.lastIndexOf("T")));
    }

    public static String SubMDataTime(String strDate) {
        return (StringUtils.isEmpty(strDate) || strDate.indexOf(":") == -1 ? "" : strDate.substring(0, strDate.lastIndexOf(":")).replace("T", " "));
    }

    public static String SubMDataTime(String strDate, String Zstr) {
        return (StringUtils.isEmpty(strDate) || strDate.indexOf(":") == -1 ? "" : Zstr + strDate.substring(0, strDate.lastIndexOf(":")).replace("T", " "));
    }

    public static String SubDDataTime(String strDate) {
        return (StringUtils.isEmpty(strDate) || strDate.indexOf(".") == -1 ? "" : strDate.substring(0, strDate.lastIndexOf(".")).replace("T", " "));
    }

    public static String ConvertDataTime(String strDate) {
        // 准备第一个模板，从字符串中提取出日期数字
        String pat1 = "yyyy-MM-dd HH:mm:ss.SSS";
        // 准备第二个模板，将提取后的日期数字变为指定的格式
        SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);        // 实例化模板对象
        Date d = null;
        try {
            d = sdf1.parse(strDate);   // 将给定的字符串中的日期提取出来
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }
        return sdf1.format(d);    // 将日期变为新的格式
    }

    public static String ConvertTDataTime(String strDate) {
        // 准备第一个模板，从字符串中提取出日期数字
        String pat1 = "yyyy-MM-dd'T'HH:mm:ss.SSS";
        // 准备第二个模板，将提取后的日期数字变为指定的格式
        String pat2 = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf1 = new SimpleDateFormat(pat1);        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(pat2);        // 实例化模板对象

        Date d = null;
        try {
            d = sdf1.parse(strDate);   // 将给定的字符串中的日期提取出来
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }
        return sdf2.format(d);    // 将日期变为新的格式
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input)) {
            return true;
        }

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是一个合法的电子邮件地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0) {
            return false;
        }
        return emailer.matcher(email).matches();
    }

    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static int toInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        return toInt(obj.toString(), 0);
    }

    /**
     * 对象转整数
     *
     * @param obj
     * @return 转换异常返回 0
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 字符串转布尔值
     *
     * @param b
     * @return 转换异常返回 false
     */
    public static boolean toBool(String b) {
        try {
            return Boolean.parseBoolean(b);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 将一个InputStream流转换成字符串
     *
     * @param is
     * @return
     */
    public static String toConvertString(InputStream is) {
        StringBuffer res = new StringBuffer();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader read = new BufferedReader(isr);
        try {
            String line;
            line = read.readLine();
            while (line != null) {
                res.append(line);
                line = read.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != isr) {
                    isr.close();
                    isr.close();
                }
                if (null != read) {
                    read.close();
                    read = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
            }
        }
        return res.toString();
    }

    /**
     * 计算字符串出现的次数
     *
     * @param str
     * @param key
     * @return
     */
    public static int getKeyTime(String str, String key) {
        int index = 0; //定义变量。记录每一次找到的key的位置。
        int count = 0; //定义变量，记录出现的次数。
        //定义循环。只要索引到的位置不是-1，继续查找。
        while ((index = str.indexOf(key, index)) != -1) {
            //每循环一次，就要明确下一次查找的起始位置。
            index = index + key.length();
            //每查找一次，count自增。
            count++;
        }
        return count;
    }


//    public static ArrayList<QualityResultBean> sameMerge(ArrayList<QualityResultBean> qualityResultBeans){
//        Collections.sort(qualityResultBeans, new Comparator<QualityResultBean>() {
//            @Override
//            public int compare(QualityResultBean t1, QualityResultBean t2) {
//                return t1.getBadDictionaryGUID().compareTo(t2.getBadDictionaryGUID());
//            }
//        });
//        String tmpBad="";
//        ArrayList<QualityResultBean> NewList = new ArrayList<QualityResultBean>();
//        for (int i=0;i<qualityResultBeans.size();i++){
//            if((qualityResultBeans.get(i).getBadDictionaryGUID()+qualityResultBeans.get(i).getWorkGroupGUID()).equalsIgnoreCase(tmpBad)){
//                QualityResultBean qualityResultBean = new QualityResultBean();
//                qualityResultBean.setBadDictionaryGUID(qualityResultBeans.get(i).getBadDictionaryGUID());
//                qualityResultBean.setNote(qualityResultBeans.get(i).getNote());
//                qualityResultBean.setOperationGUID(qualityResultBeans.get(i).getOperationGUID());
//                qualityResultBean.setSQualityResultKey(qualityResultBeans.get(i).getSQualityResultKey());
//                qualityResultBean.setQualityResultQty(qualityResultBeans.get(i).getQualityResultQty()+NewList.get(NewList.size()-1).getQualityResultQty());
//
//                NewList.remove(NewList.size()-1);
//                NewList.add(qualityResultBean);
//            }else{
//                NewList.add(qualityResultBeans.get(i));
//            }
//            tmpBad = qualityResultBeans.get(i).getBadDictionaryGUID()+qualityResultBeans.get(i).getWorkGroupGUID();
//        }
//        return NewList;
//    }

    ///
    public static String ConvertDouble(String Num, int dian) {
        String pattern = "0.";
        for (int i = 0; i < dian; i++) {
            pattern += "0";
        }
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(Double.parseDouble(Num));
    }

//
//    public static ArrayList<QualityResultBean> sameMergeRepair(ArrayList<QualityResultBean> qualityResultBeans){
//        Collections.sort(qualityResultBeans, new Comparator<QualityResultBean>() {
//            @Override
//            public int compare(QualityResultBean t1, QualityResultBean t2) {
//                return (t1.getBadDictionaryGUID()+t1.getWorkGroupGUID()).compareTo(t2.getBadDictionaryGUID()+t2.getWareHouseGUID());
//            }
//        });
//        String tmpBad="";
//        ArrayList<QualityResultBean> NewList = new ArrayList<QualityResultBean>();
//        for (int i=0;i<qualityResultBeans.size();i++){
//            if((qualityResultBeans.get(i).getBadDictionaryGUID()+qualityResultBeans.get(i).getWareHouseGUID()).equalsIgnoreCase(tmpBad)){
//                QualityResultBean qualityResultBean = new QualityResultBean();
//                qualityResultBean.setBadDictionaryGUID(qualityResultBeans.get(i).getBadDictionaryGUID());
//                qualityResultBean.setNote(qualityResultBeans.get(i).getNote());
//                qualityResultBean.setOperationGUID(qualityResultBeans.get(i).getOperationGUID());
//                qualityResultBean.setSQualityResultKey(qualityResultBeans.get(i).getSQualityResultKey());
//                qualityResultBean.setQualityResultQty(qualityResultBeans.get(i).getQualityResultQty()+NewList.get(NewList.size()-1).getQualityResultQty());
//
//                NewList.remove(NewList.size()-1);
//                NewList.add(qualityResultBean);
//            }else{
//                NewList.add(qualityResultBeans.get(i));
//            }
//            tmpBad = qualityResultBeans.get(i).getBadDictionaryGUID()+qualityResultBeans.get(i).getWareHouseGUID();
//        }
//        return NewList;
//    }
}
