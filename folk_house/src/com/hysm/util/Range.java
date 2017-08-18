/**     
 * @discription 在此输入一句话描述此文件的作用
 * @author   刘正义         
 * @created 2016-1-4 下午3:18:51    
 * tags     
 * see_to_target     
 */

package com.hysm.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Range
{
    private static final double EARTH_RADIUS = 6371; // 地球半径 单位 千米
    private static final String AK = "koQjIQZqZMn7HN61ELn5jsFU";// 百度map的ak
    private static final String OUTPUT = "json";// 获取数据类型
    private static final int POIS = 1;

    /**
     * 计算地球上任意两点(经纬度)距离
     * 
     * @param long1
     *            第一点经度
     * @param lat1
     *            第一点纬度
     * @param long2
     *            第二点经度
     * @param lat2
     *            第二点纬度
     * @return 返回距离 单位：米
     */
    public static double Distance(double long1, double lat1, double long2,
            double lat2)
    {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                        * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    /**
     * 
     * 计算经纬度点对应正方形4个点的坐标
     * 
     * 
     * 
     * @param longitude
     *            纬度
     * 
     * @param latitude
     *            经度
     * 
     * @param distance
     *            距离 单位 千米
     * 
     * @return
     */

    public static Map<String, double[]> returnLLSquarePoint(double longitude,

    double latitude, double distance)
    {

        Map<String, double[]> squareMap = new HashMap<String, double[]>();

        // 计算经度弧度,从弧度转换为角度

        double dLongitude = 2 * (Math.asin(Math.sin(distance

        / (2 * EARTH_RADIUS))

        / Math.cos(Math.toRadians(latitude))));

        dLongitude = Math.toDegrees(dLongitude);

        // 计算纬度角度

        double dLatitude = distance / EARTH_RADIUS;

        dLatitude = Math.toDegrees(dLatitude);

        // 正方形

        double[] leftTopPoint =
        { latitude + dLatitude, longitude - dLongitude };

        double[] rightTopPoint =
        { latitude + dLatitude, longitude + dLongitude };

        double[] leftBottomPoint =
        { latitude - dLatitude,

        longitude - dLongitude };

        double[] rightBottomPoint =
        { latitude - dLatitude,

        longitude + dLongitude };

        squareMap.put("leftTopPoint", leftTopPoint);

        squareMap.put("rightTopPoint", rightTopPoint);

        squareMap.put("leftBottomPoint", leftBottomPoint);

        squareMap.put("rightBottomPoint", rightBottomPoint);

        return squareMap;
    }

    /**
     * @discription 根据经纬度获取地址
     * @author
     * @created 2016-1-4 下午3:42:46
     * @param location
     *            经纬度 纬度在前，经度在后
     * @return
     */
    public static String obtainAddress(String location)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("http://api.map.baidu.com/geocoder/v2/?ak=").append(AK)
                .append("&callback=renderReverse").append("&location=")
                .append(location).append("&output=").append(OUTPUT)
                .append("&pois=0");

        // System.out.println(sb.toString());
        return HttpUtil.getUrl(sb.toString());
    }

    /**
     * @param lat
     *            纬度
     * @param lon
     *            经度
     * @param raidus
     *            圆半径 单位米
     * @return
     */
    public static double[] getAround(double lat, double lon, int raidus)
    {

        Double latitude = lat;
        Double longitude = lon;

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = raidus;

        Double dpmLat = 1 / degree;
        Double radiusLat = dpmLat * raidusMile;
        Double minLat = latitude - radiusLat;
        Double maxLat = latitude + radiusLat;

        Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
        Double dpmLng = 1 / mpdLng;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = longitude - radiusLng;
        Double maxLng = longitude + radiusLng;
        return new double[]
        { minLat, minLng, maxLat, maxLng };
    }

    public static void test()
    {
        // 118.843204,32.009668 118.77807441","y":"32.05723550
        // 118.837917,32.035853
        String location = "32.014077,118.825956";
        System.out.println(Range.Distance(118.77807441, 32.05723550,
                118.837917, 32.035853));

        String json = HttpUtil
                .getUrl("http://api.map.baidu.com/location/ip?ak=koQjIQZqZMn7HN61ELn5jsFU&coor=bd09ll");

        try
        {
            json = new String(json.getBytes("ISO-8859-1"), "utf-8");
            System.out.print(json);
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        /*
         * System.out.println(30 * 1.414); Map<String, double[]> map =
         * Range.returnLLSquarePoint(118.842054, 31.98566, 0.03);
         * System.out.println(map.get("leftTopPoint")[0] + "  " +
         * map.get("leftTopPoint")[1]);
         * System.out.println(map.get("rightTopPoint")[0] + "  " +
         * map.get("rightTopPoint")[1]);
         * System.out.println(map.get("leftBottomPoint")[0] + "  " +
         * map.get("leftBottomPoint")[1]);
         * System.out.println(map.get("rightBottomPoint")[0] + "  " +
         * map.get("rightBottomPoint")[1]);
         * System.out.println(Range.Distance(118.842054, 31.98566,
         * map.get("leftTopPoint")[1], map.get("leftTopPoint")[0]));
         * 
         * System.out.println("************"); String tr =
         * Range.Distance(118.83805696184, 32.035253052149, 118.837997436523,
         * 32.0359001159668) + "";
         * 
         * System.out.println(tr);
         */
        System.out.println(obtainAddress("31.98566,118.842054"));
    }

}
