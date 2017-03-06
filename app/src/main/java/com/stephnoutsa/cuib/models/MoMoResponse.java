package com.stephnoutsa.cuib.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stephnoutsa on 1/18/17.
 */

public class MoMoResponse {

    // Private variables
    @SerializedName("StatusDesc")
    String statusDesc;

    @SerializedName("Amount")
    String amount;

    @SerializedName("ReceiverNumber")
    String receiverNumber;

    @SerializedName("SenderNumber")
    String senderNumber;

    @SerializedName("OperationType")
    String operationType;

    @SerializedName("StatusCode")
    String statusCode;

    @SerializedName("OpComment")
    String opComment;

    @SerializedName("TransactionID")
    String transactionID;

    @SerializedName("ProcessingNumber")
    String processingNumber;

    // Empty constructor
    public MoMoResponse() {

    }

    // Constructor
    public MoMoResponse(String statusDesc, String amount, String receiverNumber, String senderNumber, String operationType, String statusCode, String opComment, String transactionID, String processingNumber) {
        this.statusDesc = statusDesc;
        this.amount = amount;
        this.receiverNumber = receiverNumber;
        this.senderNumber = senderNumber;
        this.operationType = operationType;
        this.statusCode = statusCode;
        this.opComment = opComment;
        this.transactionID = transactionID;
        this.processingNumber = processingNumber;
    }

    // Getter and setter methods
    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReceiverNumber() {
        return receiverNumber;
    }

    public void setReceiverNumber(String receiverNumber) {
        this.receiverNumber = receiverNumber;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getOpComment() {
        return opComment;
    }

    public void setOpComment(String opComment) {
        this.opComment = opComment;
    }

    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public String getProcessingNumber() {
        return processingNumber;
    }

    public void setProcessingNumber(String processingNumber) {
        this.processingNumber = processingNumber;
    }
}
