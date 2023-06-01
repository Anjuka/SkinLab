package com.fyp.vishmi.skinlab.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PredictRespones {

    /**
     * "payload": {
     * "status": "OK",
     * "resultType": "Conclusive",
     * "predClass": "not-acne",
     * "confidence": 86.0
     * }
     */

    @SerializedName("payload")
    @Expose
    private PredictPayLoad payload;

    public PredictRespones(PredictPayLoad payload) {
        this.payload = payload;
    }

    public PredictPayLoad getPayload() {
        return payload;
    }

    public void setPayload(PredictPayLoad payload) {
        this.payload = payload;
    }
}
