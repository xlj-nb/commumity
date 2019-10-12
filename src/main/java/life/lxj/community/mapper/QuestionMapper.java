package life.lxj.community.mapper;

import life.lxj.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by asus on 2019/10/11.
 */
@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) value(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag});")
    void create(Question question);
}
