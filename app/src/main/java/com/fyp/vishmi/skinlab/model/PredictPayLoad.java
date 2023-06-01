package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PredictPayLoad {

    /**
     * "status": "OK",
     * "resultType": "Conclusive",
     * "predClass": "not-acne",
     * "confidence": 86.0
     */

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("resultType")
    @Expose
    private String resultType;

    @SerializedName("predClass")
    @Expose
    private String predClass;

    @SerializedName("confidence")
    @Expose
    private String confidence;

    @SerializedName("severity")
    @Expose
    private String severity;

    public PredictPayLoad(String status, String resultType, String predClass, String confidence, String severity) {
        this.status = status;
        this.resultType = resultType;
        this.predClass = predClass;
        this.confidence = confidence;
        this.severity = severity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getPredClass() {
        return predClass;
    }

    public void setPredClass(String predClass) {
        this.predClass = predClass;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
