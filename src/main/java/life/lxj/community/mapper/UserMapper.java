package life.lxj.community.mapper;

import life.lxj.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * Created by asus on 2019/10/9.
 */
@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified) value (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified});")
    void insert(User user);
    @Select("select * from user where token=#{token};")
    User findByToken(String token);
}
