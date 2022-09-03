package cn.korostudio.ms;

import cn.hutool.core.lang.Console;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.korostudio.ms.data.Server;
import cn.korostudio.ms.service.Data;
import cn.korostudio.ms.setting.Setting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@SpringBootApplication
@Controller
public class MachineStatusApplication {

    public static void main(String[] args) {
        Setting.Init();
        CronUtil.schedule("*/2 * * * * *", new Task() {
            @Override
            public void execute() {
                if(Data.getStaticServerRepository()==null){
                    return;
                }
                List<Server> servers = Data.getStaticServerRepository().findAll();

                long time = System.currentTimeMillis() / 1000;
                for (Server server : servers) {
                    if (time - server.getUpdated() > 5) {
                        server.setOnline(false);
                        server.setUptime("-");
                        server.setCpu(100);
                        server.setMemory_used(server.getMemory_total());
                        server.setHdd_used(server.getHdd_total());
                        Data.getStaticServerRepository().save(server);
                    }
                }
            }
        });
        SpringApplication.run(MachineStatusApplication.class, args);
        CronUtil.start();
    }

    @RequestMapping("/")
    public String index(Map<String, Object> map) {
        cn.hutool.setting.Setting setting = Setting.getSetting();
        map.put("title", setting.getStr("title", "MachineStatus"));
        map.put("header_title", setting.getStr("header-title", "Machine Status"));
        map.put("footer", setting.getStr("footer", ""));
        map.put("header_subtitle", setting.getStr("header-subtitle", "Servers\\' Probes Set up with MachineStatus"));
        map.put("icon", setting.getStr("icon", "https://file.korostudio.cn/alphaillust_68988937_20181210_114051_1617519897520.png@s_0,w_512,l_1,f_png,d_progressive,q_50"));

        return "index";
    }
}
