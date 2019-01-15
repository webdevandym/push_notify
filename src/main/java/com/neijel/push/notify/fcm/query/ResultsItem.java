package com.neijel.push.notify.fcm.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultsItem {

    @JsonProperty("message_id")
    private String messageId;

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }

    @Override
    public String toString() {
        return
                "ResultsItem{" +
                        "message_id = '" + messageId + '\'' +
                        "}";
    }
}