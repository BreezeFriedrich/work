/**     
 * @discription 生成随机字符
 * @author   刘正义         
 * @created 2015-12-23 下午4:46:19    
 * tags     
 * see_to_target     
 */

package com.hysm.util;

import java.util.Random;

public class GSNumber
{
    private static Random strGen = new Random();;
    private static Random numGen = new Random();;
    private static char[] numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
            .toCharArray();
    private static char[] numbers = ("0123456789").toCharArray();

    /**
     * @discription 产生随机字符串
     * @author 刘正义
     * @created 2015-12-23 下午4:47:35
     * @param length
     *            长度
     * @return
     */
    public static final String randomString(int length)
    {
        if (length < 1)
        {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++)
        {
            randBuffer[i] = numbersAndLetters[strGen.nextInt(61)];
        }
        return new String(randBuffer);
    }

    /**
     * @discription 产生随机数值字符串
     * @author 刘正义
     * @created 2015-12-23 下午4:47:06
     * @param length
     *            长度
     * @return
     */
    public static final String randomNumStr(int length)
    {
        if (length < 1)
        {
            return null;
        }
        char[] randBuffer = new char[length];
        for (int i = 0; i < randBuffer.length; i++)
        {
            randBuffer[i] = numbers[numGen.nextInt(9)];
        }
        return new String(randBuffer);
    }

}
