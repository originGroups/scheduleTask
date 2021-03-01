import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 袁强
 * @data 2021/2/22 16:07
 * @Description 使用SpringBoot基于注解来创建定时任务
 *
 * 报错:如果当前启动类没有包，则在启动时会报错：Your ApplicationContext is unlikely to start due to a @ComponentScan of the default package错误。

（注意：写在java文件夹下的Application类，是不从属于任何一个包的，因而启动类没有包）
 */
@SpringBootApplication(scanBasePackages = "demo")
@EnableScheduling // 3.开启定时任务
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }
}
