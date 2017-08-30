/**
 * 
 */
package mapper;

/**
 * 微信处理类
 * 
 * @author
 * 
 */
public class WXPro
{

    // 添加关注着信息
    public String addSubscribe()
    {
        String sql = "insert into h_user (openid,createtime,nickname,IMGURL) values(#{openid},#{createtime},#{nickname},#{imgurl})";

        return sql;
    }

    // 用户取消关注
    public String UnSubscribe()
    {
        String sql = "update h_user set untime=#{untime} where openid=#{openid} ";

        return sql;
    }

    public String UnSubscribe2()
    {
        String sql = "update h_user set IMGURL=#{imgurl},nickname=#{nickname} where openid=#{openid} ";

        return sql;
    }
    
    public String findUserByopenid(){
        String sql="select * from h_user where openid=#{openid}";

        return sql;
    }

}