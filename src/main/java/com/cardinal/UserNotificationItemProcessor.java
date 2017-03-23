package com.cardinal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class UserNotificationItemProcessor implements ItemProcessor<UserNotification, UserNotification> {

    private static final Logger log = LoggerFactory.getLogger(UserNotificationItemProcessor.class);
    
    @Override
    public UserNotification process(UserNotification userNotification) throws Exception {
        log.info("processing: " + userNotification);
        return userNotification;
    }
}
