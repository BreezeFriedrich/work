/**
 * 
 */
package com.hysm.util;

/**
 * 菜单的处理
 * 
 * @author john
 * 
 */
public class Button
{

    public static void main(String[] args)
    {

        String str = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=IHM7uX3OGl2Mcej3qswgkIsWHvSYuB-WC5a6i1BbNIn4hTBzPzM0J-4DpSHRdlQKfJ1JBedkirFbHNnmvpQ-KBWuuJXOU1x-5wJQiuSTU8j_bb2mHyr0SuHTkBKvBPp1HAPeAIARKZ";
        String json = "{\"button\":[{\"type\":\"view\",\"name\":\"国内搜索\",\"url\":\"http://www.355967.com/folkHouse/jsp/folk/country_search.jsp\"},{\"type\":\"view\",\"name\":\"周边搜索\",\"url\":\"http://www.355967.com/folkHouse/jsp/shops/MyJsp.jsp\"},{\"type\":\"view\",\"name\":\"周租月租\",\"url\":\"http://www.355967.com/folkHouse/week/week!indexWeek.do\"}]}";

        String result = HttpUtil.getPostUrl(str, json);
        System.out.println(result);
    }
}
