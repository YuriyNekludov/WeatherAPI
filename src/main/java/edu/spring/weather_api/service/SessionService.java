package edu.spring.weather_api.service;

import edu.spring.weather_api.dto.SessionDto;
import edu.spring.weather_api.mapper.SessionMapper;
import edu.spring.weather_api.model.Session;
import edu.spring.weather_api.repository.SessionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional(readOnly = true)
public class SessionService {

    SessionRepository sessionRepository;

    @Cacheable(value = "sessions", key = "#id", unless = "#result == null")
    public Session getById(UUID id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Transactional
    @CacheEvict(value = "sessions", key = "#session.id")
    public void deleteSession(Session session) {
        sessionRepository.delete(session);
    }

    @Transactional
    @Scheduled(fixedRate = 3600000)
    @CacheEvict(value = "sessions", allEntries = true)
    public void cleanupSessions() {
        log.info("Начало проверки и очистки сессий пользователей...");
        sessionRepository.deleteExpiredSessions();
        log.warn("Проверка и очистка сессий завершена.");
    }
}
