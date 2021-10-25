package ru.tsystems.tvacwebexalerts.service;

import org.springframework.http.ResponseEntity;

/**
 * Service is responsible for sending alerts to webex chat
 */
public interface AlertService {

    /**
     * Send alert to webex chat
     *
     * @param alertMessage message to be sent
     * @return HTTP status
     */
    ResponseEntity<String> alert(String alertMessage);
}
