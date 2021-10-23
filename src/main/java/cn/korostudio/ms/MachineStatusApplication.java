package cn.korostudio.ms;

import cn.korostudio.ms.setting.Setting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@SpringBootApplication
@Controller
public class MachineStatusApplication {

    public static void main(String[] args) {
        Setting.Init();
        SpringApplication.run(MachineStatusApplication.class, args);
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
