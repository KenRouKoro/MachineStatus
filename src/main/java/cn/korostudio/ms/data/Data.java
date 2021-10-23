package cn.korostudio.ms.data;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.korostudio.ms.sql.ServerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Data {

    static protected Logger logger = LoggerFactory.getLogger(Data.class);

    @Autowired
    private ServerRepository serverRepository;

    @PostMapping("/update")
    public String  upDate(@RequestParam Map<String,Object> params){

        Server findServer = serverRepository.findByServerID((String) params.get("serverID"));
        Server server;
        if (findServer!=null){
            server = BeanUtil.fillBeanWithMap(params, findServer, false);
        }else{
            server = BeanUtil.fillBeanWithMap(params, new Server(), false);
        }
        logger.info(server.toString());
        serverRepository.save(server);

        return "OK";
    }

    @GetMapping("/data")
    public String data(){
        List<Server>servers = serverRepository.findAll();
        long time = System.currentTimeMillis()/1000;
        for(Server server:servers){
            if(time - server.getUpdated() > 30){
                server.setOnline(false);
                server.setUptime("-");
            }
        }
        JSONObject jsonObject= new JSONObject();
        JSONArray jsonArray = JSONUtil.parseArray(servers);
        jsonObject.set("servers",jsonArray);
        jsonObject.set("updated",System.currentTimeMillis()/1000);


        return jsonObject.toStringPretty();
    }
}
