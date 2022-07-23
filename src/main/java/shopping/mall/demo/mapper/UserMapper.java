package shopping.mall.demo.mapper;

import shopping.mall.demo.models.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user(username, password, email, nickname,refreshtoken) values(#{username},#{password},#{email},#{nickname},#{refreshtoken})")
    int create(User user);

    @Select("select * from user")
    List<User> select();

    @Select("select * from user where username = #{username}")
    User findOne(User user);

    @Select("select * from user where username = #{username} and refreshtoken = #{refreshtoken}")
    User findRtoken(User user);

    @Select("select * from user where username = #{username} and password = #{password}")
    User findAuth(User user);

    @Insert("insert into user(username, password) values(#{username},#{password})")
    int create2(User user);

    @Update("update user set `refreshtoken`=#{refreshtoken} where `username`=#{username}")
    int updateToken(String refreshtoken, String username);
    // @Select("select (select count(*) from cw_user) as count, cw_user.name as
    // name, cw_user.number as number,cw_user.department as department,cw_user.grade
    // as grade,cw_site.site_name as site_name,cw_site.id as site_id from cw_user
    // inner join cw_site on cw_user.site_id = cw_site.id group by cw_user.id")
    // List<JoinModel> findAll();
}