package com.bisaibang.monojwt.repository;

import com.bisaibang.monojwt.domain.chat.ChatLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ChatLog entity.
 */
@SuppressWarnings("unused")
public interface ChatLogRepository extends JpaRepository<ChatLog,Long> {

    @Query("select chatLog from ChatLog chatLog where chatLog.toWhom.login = ?#{principal.username}")
    List<ChatLog> findByToWhomIsCurrentUser();

    @Query("select chatLog from ChatLog chatLog where chatLog.fromWhom.login = ?#{principal.username}")
    List<ChatLog> findByFromWhomIsCurrentUser();

}
