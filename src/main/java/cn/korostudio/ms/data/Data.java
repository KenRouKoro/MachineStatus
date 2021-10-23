package cn.korostudio.ms.data;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.korostudio.ms.sql.ServerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class Data {

    static protected Logger logger = LoggerFactory.getLogger(Data.class);

    @Autowired
    private ServerRepository serverRepository;

    @PostMapping("/update")
    public String upDate(@RequestParam Map<String, Object> params) {

        Server findServer = serverRepository.findByServerID((String) params.get("serverID"));
        Server server;
        server = BeanUtil.fillBeanWithMap(params, Objects.requireNonNullElseGet(findServer, Server::new), false);
        logger.debug("Get Server , name is:" + server.name + "  ID is:" + server.getServerID());
        serverRepository.save(server);

        return "OK";
    }

    @GetMapping("/data")
    public String data() {
        List<Server> servers = serverRepository.findAll();
        long time = System.currentTimeMillis() / 1000;
        for (Server server : servers) {
            if (time - server.getUpdated() > 30) {
                server.setOnline(false);
                server.setUptime("-");
                server.setCpu(100);
                server.setMemory_used(server.getMemory_total());
                server.setHdd_used(server.getHdd_total());
            }
        }
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = JSONUtil.parseArray(servers);
        jsonObject.set("servers", jsonArray);
        jsonObject.set("updated", System.currentTimeMillis() / 1000);


        return jsonObject.toStringPretty();
    }
}
