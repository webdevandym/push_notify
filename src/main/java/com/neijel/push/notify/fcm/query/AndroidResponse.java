package com.neijel.push.notify.fcm.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.neijel.push.notify.platform.query.Response;

import java.util.List;

public class AndroidResponse implements Response {

    @JsonProperty("canonical_ids")
    private int canonicalIds;

    @JsonProperty("success")
    private int success;

    @JsonProperty("failure")
    private int failure;

    @JsonProperty("results")
    private List<ResultsItem> results;

    @JsonProperty("multicast_id")
    private long multicastId;

    private int statusCode;

    private String requestError;

    public void setCanonicalIds(int canonicalIds) {
        this.canonicalIds = canonicalIds;
    }

    public int getCanonicalIds() {
        return canonicalIds;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getSuccess() {
        return success;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getFailure() {
        return failure;
    }

    public void setResults(List<ResultsItem> results) {
        this.results = results;
    }

    public List<ResultsItem> getResults() {
        return results;
    }

    public void setMulticastId(long multicastId) {
        this.multicastId = multicastId;
    }

    public long getMulticastId() {
        return multicastId;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getRequestError() {
        return requestError;
    }

    public void setRequestError(String requestError) {
        this.requestError = requestError;
    }

    @Override
    public String toString() {
        return "AndroidResponse{" +
                "canonicalIds=" + canonicalIds +
                ", success=" + success +
                ", failure=" + failure +
                ", results=" + results +
                ", multicastId=" + multicastId +
                ", statusCode=" + statusCode +
                ", requestError='" + requestError + '\'' +
                '}';
    }
}