package cn.korostudio.ms.data;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Server {
    @Id
    @GeneratedValue(generator="system_uuid")
    @GenericGenerator(name="system_uuid",strategy="uuid")
    private String id;
    protected String serverID;
    protected String name;
    protected String type;
    protected String host;
    protected String location;
    protected boolean online;
    protected String uptime;
    protected double load;
    protected long network_rx;
    protected long network_tx;
    protected long network_in;
    protected long network_out;
    protected int cpu;
    protected long memory_total;
    protected long memory_used;
    protected long swap_total;
    protected long swap_used;
    protected long hdd_total;
    protected long hdd_used;
    protected String custom;
    protected String region;
    protected long updated;




}
