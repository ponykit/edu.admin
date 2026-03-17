package com.edu.admin.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class DateUtil {

    private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd"; // 기본 날짜 형식
    private static final String DEFAULT_DATE_NO_FORMAT = "yyyyMMdd"; // 형식없는
    private static final String DEFAULT_YEAR_MONTH_NO_FORMAT = "yyyyMM"; // 형식없는
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss:SS"; // 기본
    private static final Locale DEFAULT_TIME_ZONE_LOCALE = Locale.KOREA; // 기본 Time Zone Locale

    public static final  int YEAR      = 1;
    public static final  int MONTH      = 2;
    public static final  int DATE      = 3;
    public static final  int MONTHFIRST = 4;
    public static final  int MONTHEND  = 5;

    public static String getToday() {

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());

        return date;

    }

    public static String getTodayDot() {

        String pattern = "yyyy.MM.dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());

        return date;

    }

    public static String getTodayKorean() {

        String pattern = "yyyy 년 MM 월 dd 일";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(new Date());

        return date;

    }

    ////////////////////////////////////////////////////////////////////
    /**
     * 현재 날자를 format 서식 형태의 문자열을 생성하여 반환한다.<br>
     * default yyyyMMdd
     *
     * @param format String 서식 문자열
     * @return String
     */
    public static String toFormatString(String format) {
        return toFormatString(new Date(), format);
    }

    /**
     * Date에서 format 서식 형태의 문자열을 생성하여 반환한다. <br>
     * default Locale.KOREA
     *
     * @param date Date
     * @param format String 서식 문자열, 미지정 시 yyyyMMdd
     * @return String
     */
    public static String toFormatString(Date date, String format) {
        String dateFormat = format;
        if (format == null || format.length() == 0) {
            dateFormat = "yyyyMMdd";
        }
        return toFormatString(date, dateFormat, Locale.KOREA);
    }

    /**
     * Date에서 format 서식 형태의 문자열을 생성하여 반환한다.
     *
     * @param date Date
     * @param format String 서식 문자열, 미지정 시 yyyyMMdd
     * @param locale Locale, 미지정 시 Locale.KOREA
     * @return String
     */
    public static String toFormatString(Date date, String format, Locale locale) {
        String dateFormat = format;
        if (format == null || format.length() == 0) {
            dateFormat = "yyyyMMdd";
        }
        if (locale == null) {
            locale = Locale.KOREA;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat, locale);
        return formatter.format(date);
    }

    /**
     * 문자열 일자에서 Date를 생성하여 반환한다.<br>
     * default Locale.KOREA
     *
     * @param dateString String, 서식이 적용된 문자열
     * @param format String, 서식 문자열
     * @return Date
     */
    public static Date formatParse(String dateString, String format) {
        return formatParse(dateString, format, Locale.KOREA);
    }

    /**
     * 문자열 일자에서 Date를 생성하여 반환한다.
     * @param dateString String, 서식이 적용된 문자열
     * @param format String, 서식 문자열
     * @param locale Locale
     * @return Date
     */
    public static Date formatParse(String dateString, String format, Locale locale) {
        /* 문자열 일자를 데이트타입으로 변경할 변수 */
        Date parseDate = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format, locale);
            ParsePosition pos = new ParsePosition(0);
            parseDate = formatter.parse(dateString, pos);
        } catch (IllegalArgumentException e) {
            parseDate = null;
        }

        return parseDate;
    }

    ////////////////////////////////////////////////////////////////////

    /**
     *  현재년도를  yyyy형태로 반환한다.
     *  @return String
     */
    public static String getYyyy() {
        Calendar calendar = Calendar.getInstance();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(calendar.getTime());
    }

    /**
     *  현재 날짜와 시각을  yyyyMM 형태로 반환한다.
     *  @return String
     */
    public static String getYyyymm() {
        Calendar calendar = Calendar.getInstance();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMM";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(calendar.getTime());
    }

    /**
     *  현재 날짜와 시각을  yyyyMMdd 형태로 반환한다.
     *  @return String
     */
    public static String getYyyymmdd() {
        Calendar calendar = Calendar.getInstance();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMdd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(calendar.getTime());
    }

    /**
     * 현재 날짜와 시각을 Yyyymmddhhmmss 형태로 반환한다.
     *
     * @return
     */
    public static String getYyyymmddhhmmss() {
        Calendar calendar = Calendar.getInstance();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(calendar.getTime());
    }

    /**
     * 현재 시각을 hhmm 형태로 반환한다.
     *
     * @return
     */
    public static String getHhmm() {
        Calendar calendar = Calendar.getInstance();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "HHmm";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(calendar.getTime());
    }

    /**
     * 현재 날짜와 시각을 Yyyy-mm-dd 형태로 반환한다.
     *
     * @param cal
     * @return
     */
    public static String getYyyymmddWithDash(Calendar cal) {
        if (cal == null)
            return "";
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(cal.getTime());
    }

    /**
     * 현재 날짜와 시각을 인자의 pattern으로 변환한다.
     *
     * @param pattern
     * @return
     */
    public static String getNowDate(String pattern) {
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(Calendar.getInstance().getTime());
    }

    /**
     *  오라클DB의 DATE형식 객체 생성(현재 년월일시간분초)
     *  @return Timestamp
     */
    public static Timestamp getCurrentTimestamp() {
        Calendar calendar = Calendar.getInstance();

        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * getGregorianCalendar
     *
     * @param yyyymmdd
     * @return GregorianCalendar
     */
    public static GregorianCalendar getGregorianCalendar(String yyyymmdd) {
        if (yyyymmdd == null)
            return null;
        int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int dd = Integer.parseInt(yyyymmdd.substring(6));

        GregorianCalendar calendar = new GregorianCalendar(yyyy, mm - 1, dd, 0, 0, 0);

        return calendar;

    }

    /**
     * 지정된 플래그에 따라 연도 , 월 , 일자를 연산하여 결과를 얻는다.
     * <pre>
     *  - 사용 예 : String date = DateUtil.getAddDate(java.util.Calendar.DATE , "20080101", 1)
     * </pre>
     *
     * @param field Calendar.YEAR, Calendar.MONTH, Calendar.DATE
     * @param date 계산할 원본 Date
     * @param amount Add 크기
     * @return String
     */
    public static String getAddDate(int field, String date, int amount) {

        GregorianCalendar calDate = getGregorianCalendar(date);

        if (field == Calendar.YEAR) {
            if (calDate != null) {
                calDate.add(GregorianCalendar.YEAR, amount);
            }
        } else if (field == Calendar.MONTH) {
            if (calDate != null) {
                calDate.add(GregorianCalendar.MONTH, amount);
            }
        } else {
            if (calDate != null) {
                calDate.add(GregorianCalendar.DATE, amount);
            }
        }

        return getYyyymmdd(calDate);

    }

    /**
     * 2009-03-10 String 날짜변수를  2009-03-10 00:00:00 Timestamp 형식으로 반환한다.
     *
     * @param dateStr
     * @return
     */
    public static Timestamp replaceTimestamp(String dateStr) {
        if (dateStr == null || dateStr.length() != 10)
            return null;

        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(5, 7);
        String day = dateStr.substring(8, 10);
        // String hh = dateStr.substring(11, 13);
        // String mm = dateStr.substring(14, 16);
        // String ss = dateStr.substring(17, 18);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), 0, 0, 0);

        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * 20101013112322 String날짜변수를  2009-03-10 11:23:22 Timestamp 형식으로 반환한다.
     *
     * @param dateStr
     * @return
     */
    public static Timestamp replaceTimestampType1(String dateStr) {
        if (dateStr == null || dateStr.length() != 14)
            return null;

        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(4, 6);
        String day = dateStr.substring(6, 8);
        String hh = dateStr.substring(8, 10);
        String mm = dateStr.substring(10, 12);
        String ss = dateStr.substring(12, 14);

        Calendar calendar = Calendar.getInstance();

        calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hh),
                Integer.parseInt(mm), Integer.parseInt(ss));

        return new Timestamp(calendar.getTime().getTime());
    }

    /**
     * 지난 요일 일자 가져오기
     *
     * @param pYoil - 가져올 요일( 1:일 2:월 ~ 6:금 7:토 )
     * @return 해당요일의 일자 yyyyMMdd
     */
    public static String getBeforeYoilDate(int pYoil) {
        String strDate = "";
        Calendar cal = Calendar.getInstance();
        int nDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int nDayOfYoil = 0;

        for (int i = 1; i <= 7; i++) {
            if (nDayOfWeek == i) {
                nDayOfYoil = pYoil - i;
                break;
            }
        }

        if (nDayOfYoil > 0)
            nDayOfYoil -= 7;

        cal.add(Calendar.DATE, nDayOfYoil);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        strDate = sdf.format(cal.getTime());

        return strDate;
    }

    /**
     * getConvertYyyymmdd
     *
     * @param tmp
     * @return
     */
    public static String getConvertYyyymmdd(Timestamp tmp, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.KOREA);

        return sdf.format(tmp);
    }

    /**
     * 데이터 형식이 맞는지 여부 확인(param : yyyy-MM-dd)
     *
     * @param dateString
     * @return
     */
    public static boolean isDateFormat(String dateString) {
        if (dateString == null || dateString.length() != 10) {
            return false;
        }

        String year = dateString.substring(0, 4);
        String month = dateString.substring(5, 7);
        String day = dateString.substring(8, 10);

        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        int dayInt = Integer.parseInt(day);

        Calendar calendar = Calendar.getInstance();
        calendar.set(yearInt, monthInt - 1, dayInt);

        if (yearInt < 0 || monthInt < 0 || dayInt < 0) {
            return false;
        }

        if (monthInt > 12 || dayInt > 31) {
            return false;
        }

        int endDay = getLastDayOfMon(yearInt, monthInt);

        if (Integer.parseInt(day) > endDay) {
            return false;
        }

        return true;
    }

    /**
     * 입력된 년월의 마지막 날
     *
     * @param year
     * @param month
     * @return
     * @throws Exception
     */
    public static int getLastDayOfMon(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);

        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 입력된 해당월의 마지막 날을 반환
     *
     * @param YYYYMM
     * @return YYYYMMDD
     */
    public static String getLastDayOfMonToYYYYMMDD(String yyyyMM) throws Exception {
        String lastDay = String.valueOf(
                getLastDayOfMon(Integer.valueOf(yyyyMM.substring(0, 4)), Integer.valueOf(yyyyMM.substring(4, 6))));

        return yyyyMM + lastDay;
    }

    /**
     * 2개 날짜(Date)사이의 날짜들 리턴
     * ex) getDatesOfRange("2010-11-30", "2010-12-02", "yyyy-MM-dd")
     * getDatesOfRange
     *
     * @param beginDate
     * @param endDate
     * @param formatStr
     * @return
     * @throws Exception
     */
    public static List<Date> getDatesOfRange(String beginDate, String endDate, String formatStr) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return getDatesOfRange(format.parse(beginDate), format.parse(endDate));
    }

    /**
     * 2개 날짜(Date)사이의 날짜들 리턴
     * getDatesOfRange
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<Date> getDatesOfRange(Date beginDate, Date endDate) {
        List<Date> dates = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(beginDate);

        Calendar endDateCalendar = new GregorianCalendar();
        endDateCalendar.setTime(endDate);
        endDateCalendar.add(Calendar.DATE, 1); // 마지막 날짜를 포함하기 위해 하루를 더함

        while (calendar.before(endDateCalendar)) {
            Date tempDate = calendar.getTime();
            dates.add(tempDate);
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    /**
     * 특정 년도 범위의 목록을 리스트로 얻는다.
     * <pre>
     * 사용예 : getYyyyList (2010, 5) --> 2010 ~ 2018 까지 연도 목록을 반환
     * </pre>
     *
     * @param startYear 시작년도
     * @param addYear 현재년도로부터 더해지는 연도 수 (더해지는 마지막 년도는 포함하지 않음)
     * @return
     */
    public static List<String> getYyyyList(int startYear, int addYear) {
        List<String> yearList = new ArrayList<String>();
        for (int i = 0; i < (Integer.parseInt(getYyyy()) + addYear - startYear); i++) {
            yearList.add(i, String.valueOf(startYear + i));
        }

        return yearList;
    }

    /**
     * 날자를 계산하여 가져온다.
     * 날짜 덧셈뺄셈
     * @return yyyyMMdd
     */
    public static String getDate(int year, int month, int day, int cc) {
        DecimalFormat df = new DecimalFormat("00");
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);

        calendar.add(Calendar.DATE, cc);

        return String.format("%d%s%s", calendar.get(Calendar.YEAR), df.format(calendar.get(Calendar.MONTH) + 1),
                df.format(calendar.get(Calendar.DATE)));
    }

    /**
     * toDateFormat
     * 날짜 형식 yyyy-mm-dd
     * @return
     */
    public static String toDateFormat(String dt) {
        if (dt == null)
            return "";
        if (dt.length() == 8) {
            return String.format("%s-%s-%s", dt.substring(0, 4), dt.substring(4, 6), dt.substring(6, 8));
        } else {
            return dt;
        }
    }

    /**
     * 현재일자를 기본 날짜형식(DEFAULT_DATE_FORMAT)에 맞춰 가져온다.
     * <pre>
     * ex> nowDate() = "2013/06/10"
     * </pre>
     * @return 현재일자
     */
    public static String nowDate() {
        return getDate(null, null, DEFAULT_DATE_FORMAT);
    }

    /**
     * 현재일시를 기본 일시형식(DEFAULT_DATETIME_FORMAT)에 맞춰 가져온다.
     * <pre>
     * ex> nowDateTime() = "2013/06/10 14:23:16:484"
     * </pre>
     * @return 현재일시
     */
    public static String nowDateTime() {
        return getDate(null, null, DEFAULT_DATETIME_FORMAT);
    }

    /**
     * 현재일자를 날짜형식에 맞춰 가져온다.
     * <pre>
     * ex> getDate("yyyy/MM/dd") = 2013/06/10
     *     getDate("yyyy-MM-dd") = 2013-06-10
     * </pre>
     * @param format - 적용할 날짜형식
     * @return 현재일자
     */
    public static String getDate(String format) {
        return getDate(null, null, format);
    }

    /**
     * yyyyMMdd형식의 문자열일자를 적용할 날짜형식으로 변경 반환한다.
     * <pre>
     * ex> getDate("20130611", "yyyy/MM/dd") = 2013/06/11
     *     getDate("20130612", "yyyy-MM-dd") = 2013-06-12
     * </pre>
     * @param date - 날짜형식을 적용할 일자(문자열 일자)
     * @param format - 적용할 날짜형식
     * @return 일자
     */
    public static String getDate(String date, String format) {
        return getDate(date, null, format);
    }

    /**
     * 문자열 일자를 원하는 날짜형식으로 변환하여 반환한다.
     *
     * <pre>
     * getDate("문자열일자","문자연일자의 현 날짜형식","반환할 날짜형식")
     * ex> getDate("20130610","yyyyMMdd","yyyy-MM-dd") = "2013-06-10"
     *     getDate("20130610",null,"yyyy-MM-dd") = "2013-06-10"
     *     getDate("2013-06-10","yyyy-MM-dd","yyyy-MM-dd") = "2013-06-10"
     * </pre>
     * @param date - 날짜형식을 적용할 일자(문자열 일자)
     * @param befFormat - 날짜형식을 적용할 일자의 현재 날짜 형식
     * @param aftFormat - 적용할 날짜형식
     * @return 일자
     */
    public static String getDate(String date, String befFormat, String aftFormat) {
        Locale currentLocale = new Locale("KOREAN", "KOREA");

        String befFormatTmp = null;
        // 시큐어코딩 수정

        if (StringUtils.isNotEmpty(befFormat)) {
            /* 현재날짜형식이 없는 경우는 yyyyMMdd으로만 받는다 */
            befFormatTmp = DEFAULT_DATE_NO_FORMAT;
        } else {
            befFormatTmp = befFormat;
        }

        /* 날짜 형식 */
        SimpleDateFormat befFormatter = new SimpleDateFormat(befFormatTmp, currentLocale); // date(매개변수)
        // 날짜형식
        SimpleDateFormat aftFormatter = new SimpleDateFormat(aftFormat, currentLocale); // 반환할
        // 날짜형식

        /* 문자열 일자가 존재하지 않을 경우 현재일을 반환한다. */
        if (StringUtils.isEmpty(date)) {
            Calendar now = Calendar.getInstance(DEFAULT_TIME_ZONE_LOCALE);
            return aftFormatter.format(now.getTime());
        }

        /* 문자열일자와 문자열일자 날짜형식의 길이가 같지 않는 경우 null반환한다. */
        if (date.length() != befFormatTmp.length())
            return null;

        /* 문자열 일자를 데이트타입으로 변경할 변수 */
        Date parseDate = null;

        /* 문자열일자 Date Type으로 변경 */
        try {
            parseDate = befFormatter.parse(date);
        } catch (ParseException e) {// 시큐어코딩 수정
            /* 타입변경 에러는 null을 반환한다 */
            // log.error("타입변경 시, 에러가 발생했습니다.");
            return null;
        }

        /* 적용할 날짜형식으로 반환 */
        return aftFormatter.format(parseDate);
    }

    /**
     * yyyyMM날짜타입에 대한 월 가감 반환
     * <pre>
     * ex> addMonths("201212", 1) = "201301"
     *     addMonths("201301",-1) = "201212"
     *     addMonths("201301", 3) = "201304"
     * </pre>
     * @param yyyyMM - 문자열 년월
     * @param months - 월 증감
     * @return 년월
     */
    public static String addMonths(String yyyyMM, int months) {
        Locale currentLocale = new Locale("KOREAN", "KOREA");

        /* 날짜 형식 */
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_YEAR_MONTH_NO_FORMAT, currentLocale); // yyyyMM
        // 타입만을
        // 허용한다.

        /* 문자열 일자를 데이트타입으로 변경할 변수 */
        Date parseDate = null;

        try {
            parseDate = DateUtils.addMonths(formatter.parse(yyyyMM), months);
            return formatter.format(parseDate);
        } catch (ParseException e) {// 시큐어코딩 수정
            /* 타입변경 에러는 null을 반환한다 */
            return null;
        }
    }

    /**
     * 문자열 기반 월 가감 처리
     * @param date
     * @param months
     * @param format
     * @return
     */
    public static String addMonths(String date, int months, String format) {

        Locale currentLocale = new Locale("KOREAN", "KOREA");

        SimpleDateFormat formatter = new SimpleDateFormat(format, currentLocale);

        Date parseDate = null;

        try {

            parseDate = DateUtils.addMonths(formatter.parse(date), months);

            return formatter.format(parseDate);

        } catch (ParseException e) {

            return null;

        }

    }


    /**
     * yyyyMM날짜타입에 대한  전월을 반환한다.
     * <pre>
     * ex> lastMonth("201212") = "201211"
     *     lastMonth("201306") = "201305"
     * </pre>
     * @param yyyyMM - 문자열 년월
     * @return 전년월
     */
    public static String lastMonth(String yyyyMM) {
        return addMonths(yyyyMM, -1);
    }

    /**
     * yyyyMMdd날짜타입에 대한 일 가감 반환
     * <pre>
     * ex> addDays("20121201", 1) = "20121202"
     *     addDays("20130101",-1) = "20121231"
     *     addDays("20130610", 30) = "20130710"
     * </pre>
     * @param yyyyMMdd - 문자열 년월일
     * @param days - 일 증감
     * @return 년월일
     */
    public static String addDays(String yyyyMMdd, int days) {
        Locale currentLocale = new Locale("KOREAN", "KOREA");

        /* 날짜 형식 */
        SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_NO_FORMAT, currentLocale); // yyyyMMdd
        // 타입만을
        // 허용한다.

        /* 문자열 일자를 데이트타입으로 변경할 변수 */
        Date parseDate = null;

        try {
            parseDate = DateUtils.addDays(formatter.parse(yyyyMMdd), days);
            return formatter.format(parseDate);
        } catch (ParseException e) {// 시큐어코딩 수정
            /* 타입변경 에러는 null을 반환한다 */
            // log.error("타입변경 시, 에러가 발생했습니다.");
            return null;
        }
    }

    /**
     * 현재일에 대한 일 가감 반환
     * <pre>
     * ex> addDays(1) = "20130612"
     *     addDays(0) = "20130611"
     *     addDays(-1) = "20130610"
     *     addDays(10) = "20130701"
     * </pre>
     * @param days - 일 증감
     * @return 일자
     */
    public static String addDays(int days) {
        return addDays(getDate(null, null, DEFAULT_DATE_NO_FORMAT), days);
    }

    /**
     * 현재일에 대한 일 가감 반환
     * <pre>
     * ex> beforeDay("20130612") = "20130611"
     *     beforeDay("20120101") = "20111231"
     * </pre>
     * @param date - 년월일
     * @return 전일
     */
    public static String beforeDay(String date) {
        return addDays(getDate(date, null, DEFAULT_DATE_NO_FORMAT), -1);
    }

    /**
     * 비교월의 차이를 구한다.
     * <pre>
     * ex> beforeDay("201301","201204") = 3
     *     beforeDay("201306","201306") = 0
     * </pre>
     * @param sDate - 비교시작일자
     * @param eDate - 비교종료일자
     * @return 월 차
     */
    public static int monthsBetween(String sDate, String eDate) {

        Locale currentLocale = new Locale("KOREAN", "KOREA");

        /* 날짜 형식 */
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", currentLocale); // date(매개변수)
        // 날짜형식

        /* 문자열 일자를 데이트타입으로 변경할 변수 */
        Date sDt = null;
        Date eDt = null;

        /* 파싱 에러 시 */
        try {
            sDt = formatter.parse(sDate + "01");
            eDt = formatter.parse(eDate + "02");
        } catch (ParseException e) {// 시큐어코딩 수정
            return 0;
        }

        /* 대상년월일 */
        Calendar c1 = Calendar.getInstance();
        c1.setTime(sDt);

        /* 비교년월일 */
        Calendar c2 = Calendar.getInstance();
        c2.setTime(eDt);

        /* 결과값 */
        int diff = 0;

        if (c2.after(c1))
            while (c2.after(c1)) {
                c1.add(Calendar.MONTH, 1);
                if (c2.after(c1))
                    diff++;
            }

        else if (c2.before(c1))
            while (c2.before(c1)) {
                c1.add(Calendar.MONTH, -1);
                if (c1.before(c2))
                    diff--;
            }

        return diff;
    }

    /**
     * 개월수 구하기
     * <pre>
     * Oracle의 MONTHS_BETWEEN(TO_DATE(SUBSTR(?,1,6),'YYYYMM'), TO_DATE(SUBSTR(?,1,6),'YYYYMM')) 과 동일
     *
     * ex> DateUtil.monthsBetweenAsOracle("20131015", "20130725") => 3
     *     DateUtil.monthsBetweenAsOracle("201310"  , "201307"  ) => 3 (일자부분 무시됨)
     *     DateUtil.monthsBetweenAsOracle("20130515", "20130725") => -2
     * </pre>
     * @param paramToMonth 종료일자(월) YYYYMMDD or YYYMM
     * @param paramFromMonth 시작일자(월) YYYYMMDD or YYYMM
     * @return 개월수
     */
    public static int monthsBetweenAsOracle(String paramToMonth, String paramFromMonth) {
        int bitFlg = 1;
        int iYear = 0; // 계산된 년도
        int iMonth = 0; // 계산된 개월수
        int rMonth = 0; // 반환할 개월수
        String frMonth = paramFromMonth;
        String toMonth = paramToMonth;

        if (Integer.parseInt(paramFromMonth) > Integer.parseInt(paramToMonth)) {
            frMonth = paramToMonth;
            toMonth = paramFromMonth;
            bitFlg = -1;
        }

        iYear = Integer.parseInt(toMonth.substring(0, 4)) - Integer.parseInt(frMonth.substring(0, 4));
        iMonth = Integer.parseInt(toMonth.substring(4, 6)) - Integer.parseInt(frMonth.substring(4, 6));

        rMonth = (12 * iYear) + iMonth;
        return rMonth * bitFlg;
    }

    /**
     * 현재(한국기준) 시간정보를 얻는다.
     * <pre>
     * (예) 입력파리미터인 format string에 "yyyyMMddhh"를 셋팅하면 1998121011과 같이 Return
     * (예) format string에 "yyyyMMddHHmmss"를 셋팅하면 19990114232121과 같이 0~23시간 타입으로 Return
     *      String CurrentDate = CmUtil.getKST("yyyyMMddHH");
     * </pre>
     * @param    format 얻고자하는 현재시간의 Type
     * @return   String 현재 한국 시간
     */

    public static String getKST(String format) {
        // 1hour(ms) = 60s * 60m * 1000ms
        int millisPerHour = 60 * 60 * 1000;
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        SimpleDateFormat fmt = new SimpleDateFormat(format, currentLocale);

        SimpleTimeZone timeZone = new SimpleTimeZone(9 * millisPerHour, "KST");
        fmt.setTimeZone(timeZone);

        long time = System.currentTimeMillis();
        String str = fmt.format(new Date(time));
        return str;
    }

    /**
     * diffOfDate
     * @param begin
     * @param end
     * @return
     * @throws Exception
     */
    public static long diffOfDate(String begin, String end, String format) {

        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.KOREA);

        long diffDays = 0;

        try {

            Date beginDate;
            beginDate = formatter.parse(begin);
            Date endDate = formatter.parse(end);

            long diff = endDate.getTime() - beginDate.getTime();
            diffDays = diff / (((24 * 60) * 60) * 1000);

        } catch (ParseException e) {
            return diffDays;
        }
        return diffDays;
    }

    public static String getDateConv(String PlayDate, String convType) {
        if (convType == "1") {
            PlayDate = PlayDate.toString().substring(4, 6) + "월 " + PlayDate.toString().substring(6, 8) + "일";
            return PlayDate;
        } else if (convType == "2") {
            PlayDate = PlayDate.toString().substring(4, 6) + "/" + PlayDate.toString().substring(6, 8) + "일("
                    + getDateTimeFormat(PlayDate).toString() + ")";
            return PlayDate;
        } else if (convType == "3") {
            PlayDate = PlayDate.toString().substring(0, 4) + "년 " + PlayDate.toString().substring(4, 6) + "월 "
                    + PlayDate.toString().substring(6, 8) + "일";
            return PlayDate;
        } else if (convType == "4") {
            PlayDate = PlayDate.toString().substring(0, 4) + "-" + PlayDate.toString().substring(4, 6) + "-"
                    + PlayDate.toString().substring(6, 8);
            return PlayDate;
        } else {
            return PlayDate;
        }
    }

    public static String getDateTimeFormat(String strDate) {
        String strReturn = "";
        try {
            // 변환하려는 날짜가 8자리 이상이어야 함.
            if (strDate.length() >= 8) {
                strDate = strDate.replaceAll("-", "");
                strReturn = strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8);
            }
            return strReturn;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static String getCurrentyyyyMMddHHmmss() {
        Calendar nowTime = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.KOREA);
        return simpledateformat.format(nowTime.getTime());
    }

    public static String getCurrentyyyyMMdd() {
        Calendar nowTime = Calendar.getInstance();
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        return simpledateformat.format(nowTime.getTime());
    }

    /**
     * <p>현재 날짜와 시각을  yyyyMMdd 형태로 변환 후 return.
     * @param cal
     * @return
     */
    public static String getYyyymmdd(Calendar cal) {
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMdd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(cal.getTime());
    }

    /**
     * <p>현재 날짜와 시각을  yyyyMMddhhmmss 형태로 변환 후 return.
     * @return
     */
    public static String getCurrentDateTime() {
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(today);
    }

    /**
     * <p>현재  시각을  hhmmss 형태로 변환 후 return.
     *
     * @return hhmmss
     * @see Date
     * @see Locale
     * <p><pre>
     *  - 사용 예
     *  String date = DateUtil.getCurrentDateTime()
     * </pre>
     */
    public static String getCurrentTime() {
        Date today = new Date();
        Locale currentLocale = new Locale("KOREAN", "KOREA");
        String pattern = "HHmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, currentLocale);
        return formatter.format(today);

    }

    /**
     * <p>현재 날짜를 yyyyMMdd 형태로 변환 후 return.
     *
     * @return yyyyMMdd *
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.getCurrentYyyymmdd()
     * </pre>
     */
    public static String getCurrentYyyymmdd() {
        return getCurrentDateTime().substring(0, 8);
    }

    /**
     * <p>주로 일자를 구하는 메소드.
     *
     * @param yyyymm 년월
     * @param week 몇번째 주
     * @param pattern 리턴되는 날짜패턴 (ex:yyyyMMdd)
     * @return 연산된 날짜
     * @see Calendar
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.getWeekToDay("200801" , 1, "yyyyMMdd")
     * </pre>
     */
    @SuppressWarnings("static-access")
    public static String getWeekToDay(String yyyymm, int week, String pattern) {

        Calendar cal = Calendar.getInstance(Locale.FRANCE);

        int new_yy = Integer.parseInt(yyyymm.substring(0, 4));
        int new_mm = Integer.parseInt(yyyymm.substring(4, 6));
        int new_dd = 1;

        cal.set(new_yy, new_mm - 1, new_dd);

        // 임시 코드
        if (cal.get(cal.DAY_OF_WEEK) == cal.SUNDAY) {
            week = week - 1;
        }

        cal.add(Calendar.DATE, (week - 1) * 7 + (cal.getFirstDayOfWeek() - cal.get(Calendar.DAY_OF_WEEK)));

        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.FRANCE);

        return formatter.format(cal.getTime());

    }

    /**
     * <p>지정된 플래그에 따라 연도 , 월 , 일자를 연산한다.
     *
     * @param field 연산 필드
     * @param amount 더할 수
     * @param date 연산 대상 날짜
     * @return 연산된 날짜
     * @see Calendar
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.getOpDate(java.util.Calendar.DATE , 1, "20080101")
     * </pre>
     */
    public static String getOpDate(int field, int amount, String date) {

        GregorianCalendar calDate = getGregorianCalendar(date);

        if (field == Calendar.YEAR) {
            calDate.add(GregorianCalendar.YEAR, amount);
        } else if (field == Calendar.MONTH) {
            calDate.add(GregorianCalendar.MONTH, amount);
        } else {
            calDate.add(GregorianCalendar.DATE, amount);
        }

        return getYyyymmdd(calDate);

    }

    /**
     *  <p>입력된 일자를 더한 주를 구하여 return한다
     *
     * @param yyyymmdd 년도별
     * @param addDay 추가일
     * @return 연산된 주
     * @see Calendar
     * <p><pre>
     *  - 사용 예
     * int date = DateUtil.getWeek(DateUtil.getCurrentYyyymmdd() , 0)
     * </pre>
     */
    public static int getWeek(String yyyymmdd, int addDay) {
        Calendar cal = Calendar.getInstance(Locale.FRANCE);
        int new_yy = Integer.parseInt(yyyymmdd.substring(0, 4));
        int new_mm = Integer.parseInt(yyyymmdd.substring(4, 6));
        int new_dd = Integer.parseInt(yyyymmdd.substring(6, 8));

        cal.set(new_yy, new_mm - 1, new_dd);
        cal.add(Calendar.DATE, addDay);

        int week = cal.get(Calendar.DAY_OF_WEEK);
        return week;
    }

    /**
     * <p>입력된 년월의 마지막 일수를 return한다
     *
     * @param yyyymm
     * @return 마지막 일수
     * <p><pre>
     *  - 사용 예
     * int date = DateUtil.getLastDayOfMon("2008")
     * </pre>
     */
    public static int getLastDayOfMon(String yyyymm) {

        Calendar cal = Calendar.getInstance();
        int yyyy = Integer.parseInt(yyyymm.substring(0, 4));
        int mm = Integer.parseInt(yyyymm.substring(4)) - 1;

        cal.set(yyyy, mm, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * <p>입력된 날자가 올바른지 확인합니다.
     *
     * @param yyyymmdd
     * @return boolean
     * <p><pre>
     *  - 사용 예
     * boolean b = DateUtil.isCorrect("20080101")
     * </pre>
     */
    public static boolean isCorrect(String yyyymmdd) {
        boolean flag = false;
        if (yyyymmdd.length() < 8)
            return false;
        try {
            int yyyy = Integer.parseInt(yyyymmdd.substring(0, 4));
            int mm = Integer.parseInt(yyyymmdd.substring(4, 6));
            int dd = Integer.parseInt(yyyymmdd.substring(6));
            flag = isCorrect(yyyy, mm, dd);
        } catch (Exception ex) {
            return false;
        }
        return flag;
    }// :

    /**
     * <p>입력된 날자가 올바른 날자인지 확인합니다.
     *
     * @param yyyy
     * @param mm
     * @param dd
     * @return boolean
     * <p><pre>
     *  - 사용 예
     * boolean b = DateUtil.isCorrect(2008,1,1)
     * </pre>
     */
    public static boolean isCorrect(int yyyy, int mm, int dd) {
        if (yyyy < 0 || mm < 0 || dd < 0)
            return false;
        if (mm > 12 || dd > 31)
            return false;

        String year = "" + yyyy;
        String month = "00" + mm;
        String year_str = year + month.substring(month.length() - 2);
        int endday = getLastDayOfMon(year_str);

        if (dd > endday)
            return false;

        return true;

    }// :

    /**
     * <p>현재 일자를 입력된 type의 날짜로 반환합니다.
     *
     * @param type
     * @return String
     * @see java.text.DateFormat
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.getThisDay("yyyymmddhhmmss")
     * </pre>
     */
    public static String getThisDay(String type) {
        Date date = new Date();
        SimpleDateFormat sdf = null;

        try {
            if (type.toLowerCase().equals("yyyymmdd")) {
                sdf = new SimpleDateFormat("yyyyMMdd");
                return sdf.format(date);
            }
            if (type.toLowerCase().equals("yyyymmddhh")) {
                sdf = new SimpleDateFormat("yyyyMMddHH");
                return sdf.format(date);
            }
            if (type.toLowerCase().equals("yyyymmddhhmm")) {
                sdf = new SimpleDateFormat("yyyyMMddHHmm");
                return sdf.format(date);
            }
            if (type.toLowerCase().equals("yyyymmddhhmmss")) {
                sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                return sdf.format(date);
            }
            if (type.toLowerCase().equals("yyyymmddhhmmssms")) {
                sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                return sdf.format(date);
            } else {
                sdf = new SimpleDateFormat(type);
                return sdf.format(date);
            }
        } catch (Exception e) {
            return "[ ERROR ]: parameter must be 'YYYYMMDD', 'YYYYMMDDHH', 'YYYYMMDDHHSS'or 'YYYYMMDDHHSSMS'";
        }
    }

    /**
     * <p>입력된 일자를 '9999년 99월 99일' 형태로 변환하여 반환한다.
     *
     * @param yyyymmdd
     * @return String
     * <p><pre>
     *  - 사용 예
     * String date = DateUtil.changeDateFormat("20080101")
     * </pre>
     */
    public static String changeDateFormat(String yyyymmdd) {
        String rtnDate = null;

        String yyyy = yyyymmdd.substring(0, 4);
        String mm = yyyymmdd.substring(4, 6);
        String dd = yyyymmdd.substring(6, 8);
        rtnDate = yyyy + " 년 " + mm + " 월 " + dd + " 일";

        return rtnDate;

    }

    /**
     * <p>두 날짜간의 날짜수를 반환(윤년을 감안함)
     *
     * @param startDate 시작 날짜
     * @param endDate 끝 날짜
     * @return 날수
     * @see GregorianCalendar
     * <p><pre>
     *  - 사용 예
     * long date = DateUtil.getDifferDays("20080101","20080202")
     * </pre>
     */
    public static long getDifferDays(String startDate, String endDate) {

        GregorianCalendar StartDate = getGregorianCalendar(startDate);
        GregorianCalendar EndDate = getGregorianCalendar(endDate);
        long difer = (EndDate.getTime().getTime() - StartDate.getTime().getTime()) / 86400000;
        return difer;

    }

    /**
     * <p>현재의 요일을 구한다.
     *
     * @param
     * @return 요일
     * @see Calendar
     * <p><pre>
     *  - 사용 예
     * int day = DateUtil.getDayOfWeek()
     *  SUNDAY    = 1
     *  MONDAY    = 2
     *  TUESDAY  = 3
     *  WEDNESDAY = 4
     *  THURSDAY  = 5
     *  FRIDAY    = 6
     * </pre>
     */
    public static int getDayOfWeek() {
        Calendar rightNow = Calendar.getInstance();
        int day_of_week = rightNow.get(Calendar.DAY_OF_WEEK);
        return day_of_week;
    }

    /**
     * <p>현재주가 올해 전체의 몇째주에 해당되는지 계산한다.
     *
     * @param
     * @return 요일
     * @see Calendar
     * <p><pre>
     *  - 사용 예
     * int day = DateUtil.getWeekOfYear()
     * </pre>
     */
    public static int getWeekOfYear() {
        Locale LOCALE_COUNTRY = Locale.KOREA;
        Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
        int week_of_year = rightNow.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }

    /**
     * <p>현재주가 현재월에 몇째주에 해당되는지 계산한다.
     *
     * @param
     * @return 요일
     * @see Calendar
     * <p><pre>
     *  - 사용 예
     * int day = DateUtil.getWeekOfMonth()
     * </pre>
     */
    public static int getWeekOfMonth() {
        Locale LOCALE_COUNTRY = Locale.KOREA;
        Calendar rightNow = Calendar.getInstance(LOCALE_COUNTRY);
        int week_of_month = rightNow.get(Calendar.WEEK_OF_MONTH);
        return week_of_month;
    }

    /**
     * <p>해당 p_date날짜에 Calendar 객체를 반환함.
     *
     * @param p_date
     * @return Calendar
     * @see Calendar
     * <p><pre>
     *  - 사용 예
     * Calendar cal = DateUtil.getCalendarInstance(DateUtil.getCurrentYyyymmdd())
     * </pre>
     */
    public static Calendar getCalendarInstance(String p_date) {
        // Locale LOCALE_COUNTRY = Locale.KOREA;
        Locale LOCALE_COUNTRY = Locale.FRANCE;
        Calendar retCal = Calendar.getInstance(LOCALE_COUNTRY);

        if (p_date != null && p_date.length() == 8) {
            int year = Integer.parseInt(p_date.substring(0, 4));
            int month = Integer.parseInt(p_date.substring(4, 6)) - 1;
            int date = Integer.parseInt(p_date.substring(6));

            retCal.set(year, month, date);
        }
        return retCal;
    }

}
