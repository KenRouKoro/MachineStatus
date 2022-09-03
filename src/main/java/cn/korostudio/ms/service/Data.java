package cn.korostudio.ms.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.korostudio.ms.data.Server;
import cn.korostudio.ms.setting.Setting;
import cn.korostudio.ms.sql.ServerRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class Data {

    private ServerRepository serverRepository;

    @Getter
    private static ServerRepository StaticServerRepository = null;


    @Autowired
    public void setServerRepository(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
        StaticServerRepository = serverRepository;
    }

    @PostMapping("/update")
    public String upDate(@RequestParam Map<String, Object> params) {

        String password = Setting.getSetting().getStr("remove-password","korostudio");
        String getPassword = (String) params.get("password");
        if(!Objects.equals(password, getPassword)){
            return "{\"status\":\"error_password\"}";
        }

        Server findServer = serverRepository.findByServerID((String) params.get("serverID"));
        Server server;
        server = BeanUtil.fillBeanWithMap(params, Objects.requireNonNullElseGet(findServer, Server::new), false);
        server.setUpdated(System.currentTimeMillis() / 1000);
        log.debug("Get Server , name is:" + server.getName() + "  ID is:" + server.getServerID());
        serverRepository.save(server);

        return "{\"status\":\"ok\"}";
    }

    @GetMapping("/data")
    public String data() {

        List<Server> servers = serverRepository.findAll();
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = JSONUtil.parseArray(servers);
        jsonObject.set("servers", jsonArray);
        jsonObject.set("updated", System.currentTimeMillis() / 1000);

        return jsonObject.toStringPretty();
    }
    @PostMapping("/remove")
    public  String remove(@RequestParam Map<String, Object> params){
        String password = Setting.getSetting().getStr("remove-password","korostudio");
        String getPassword = (String) params.get("password");
        if(!Objects.equals(password, getPassword)){
            return "{\"status\":\"error_password\"}";
        }
        String id = (String) params.get("serverID");
        if (id == null){
            return "{\"status\":\"null_id\"}";
        }
        Server findServer = serverRepository.findByServerID(id);
        if(findServer == null){
            return "{\"status\":\"no_such_server\"}";
        }

        serverRepository.delete(findServer);

        return "{\"status\":\"ok\"}";
    }
}
