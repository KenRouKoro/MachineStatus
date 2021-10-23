package cn.korostudio.ms.sql;

import cn.korostudio.ms.data.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    Server findById(String id);

    Server findByName(String name);

    Server findByServerID(String serverID);
}
