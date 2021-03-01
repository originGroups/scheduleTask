package demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 袁强
 * @data 2021/2/23 15:57
 * @Description
 */
@Mapper
public interface CronMapper {

    @Select("select cron from task_cron limit 1")
    public String getCron();
}
