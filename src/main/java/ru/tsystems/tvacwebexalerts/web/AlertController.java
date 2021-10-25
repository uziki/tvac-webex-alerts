package ru.tsystems.tvacwebexalerts.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsystems.tvacwebexalerts.service.AlertService;

@RestController
@RequestMapping("/alert")
public class AlertController {

    private final AlertService alertService;

    @Autowired
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * Endpoint for notifications, needed to be sent to webex chat
     *
     * @param alertMessage message to send to webex chat
     * @return HTTP status
     */
    @PostMapping
    public ResponseEntity<String> alert(@RequestBody String alertMessage) {
        return alertService.alert(alertMessage);
    }
}
