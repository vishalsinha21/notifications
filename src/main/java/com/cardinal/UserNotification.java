package com.cardinal;

import java.util.Date;

public class UserNotification {
    
    private String userId;
    private String cardId;
    private Date notificationDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    @Override
    public String toString() {
        return "UserNotification{" +
                "userId='" + userId + '\'' +
                ", cardId='" + cardId + '\'' +
                ", notificationDate=" + notificationDate +
                '}';
    }
}
