/**
 * Created by Apple on 2018/9/4.
 */
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeUtils {
    /***
     * 获取某段时间内 所有月份的最后一天   参数2018-1 2018-5  返回 2018-01-31 2018-02-28 2018-03-31 2018-04-31 2018-05-31
     * @param minDate
     * @param maxDate
     * @return
     *
     */
    private static List<String> getMonthBetween(String minDate, String maxDate) {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  //格式化为年月
        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        try {
            min.setTime(sdf.parse(minDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        try {
            max.setTime(sdf.parse(maxDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        List<String> lastDayOfMonthList = new ArrayList<>();
        for(String s : result) {
            String[] year_Month_Str = s.split("-");
            int year = Integer.parseInt(year_Month_Str[0]);
            int month = Integer.parseInt(year_Month_Str[1]);
            Calendar cal = Calendar.getInstance();
            //设置年份
            cal.set(Calendar.YEAR,year);
            //设置月份
            cal.set(Calendar.MONTH, month-1);
            //获取某月最大天数
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            //设置日历中月份的最大天数
            cal.set(Calendar.DAY_OF_MONTH, lastDay);
            //格式化日期
            SimpleDateFormat sdfFormat = new SimpleDateFormat("yyyy-MM-dd");
            String lastDayOfMonth = sdfFormat.format(cal.getTime());
            lastDayOfMonthList.add(lastDayOfMonth);
        }
        return lastDayOfMonthList;
    }

    //获取当前月的 每一天
    public static List<String> getDayListOfMonth() {
        List<String> list = new ArrayList<String>();
	  /*  Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
	    int year = aCalendar.get(Calendar.YEAR);
	    int month = aCalendar.get(Calendar.MONTH) + 1;
	    int day = aCalendar.getActualMaximum(Calendar.DATE);
	    for (int i = 1; i <= day; i++) {
	    	String aDate = String.valueOf(year)+"-"+month+"-"+ "0" + i;
	        if(TimeUtils.isPastDate(aDate)) {
	        	 list.add(aDate);
	        }
	    }*/

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        list.add(sdf.format(date));
        return list;
    }

    //将当前月的每一天传入 返回到当前天
    public static boolean isPastDate(String str){
        boolean flag = false;
        Date nowDate = new Date();
        Date pastDate = null;
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        //在日期字符串非空时执行
        try {
            //将字符串转为日期格式，如果此处字符串为非合法日期就会抛出异常。
            pastDate = sdf.parse(str);
            //调用Date里面的before方法来做判断
            flag = pastDate.before(nowDate);
            if (flag) {
                return flag;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;
    }

    //判断是不是当前月
    @SuppressWarnings("static-access")
    private static boolean isCurrenMonth(String t){
        Calendar cal = Calendar.getInstance();
        cal.add(cal.MONTH, 0);
        Date currentTime=cal.getTime();//当前时间的上个月时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
        String curTime=sdf.format(currentTime);
        if(t.equals(curTime)){
            return true;
        }
        return false;
    }

    /***
     * 获取当前时间的上个月份
     * @return
     */
    public static String getParamMap() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("yyyy-MM");
        c.add(Calendar.MONTH, -1);
        String startDate = simpleDateFormatDay.format(c.getTime());
        return startDate;
    }

    //2018-08  2018-01
    public static List<String> getDay(String minDate, String maxDate) {
        //判断输入的最小值 是不是  当前月  true 就是
        if(TimeUtils.isCurrenMonth(minDate)) {
            return getDayListOfMonth();
        }

        //如果最大值是当前月
        if(TimeUtils.isCurrenMonth(maxDate)) {
            String str = TimeUtils.getParamMap();
            List<String> strList = TimeUtils.getMonthBetween(minDate, str);
            List<String> dayList = getDayListOfMonth();
            strList.addAll(dayList);
            return strList;
        }

        List<String> strList = TimeUtils.getMonthBetween(minDate, maxDate);
        return strList;

    }

    public static List<String> getFormatDate(List<String> sformat) {
        List<String> listStr = new ArrayList<String>();
        for(String s : sformat) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            ParsePosition pos = new ParsePosition(0);
            Date strtodate = formatter.parse(s, pos);
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter1.format(strtodate);
            listStr.add(dateString);
        }
        return listStr;
    }


    public static void main(String[] args) {
        List<String> ss = TimeUtils.getFormatDate(TimeUtils.getDay("2018-07", "2018-09"));
        for(String s1 : ss) {
            System.out.println( s1);
        }
    }
}
