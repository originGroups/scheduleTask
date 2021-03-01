package demo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 袁强
 * @data 2021/2/22 15:52
 * @Description 使用SpringBoot基于注解来创建定时任务
 */
@Component
public class ScheduleTasks {

    /**
     *
      字段　　允许值　　允许的特殊字符 
     秒     　 0-59 　　　　, - * / 
     分     　 0-59　　　　 , - * / 
     小时      0-23 　　　　, - * / 
     日期      1-31 　　　　, - * ? / L W C 
     月份      1-12 　　　　, - * / 
     星期      1-7 　　　　  , - * ? / L C # 
     年     1970-2099 　　, - * /
     */
    //2.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
