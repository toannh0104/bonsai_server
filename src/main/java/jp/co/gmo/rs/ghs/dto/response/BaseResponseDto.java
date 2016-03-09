/*
 * Copyright (C) 2015 by GMO Runsystem Company
 * Global HR Service application
 */
package jp.co.gmo.rs.ghs.dto.response;

/**
 * Abstract class for response dto
 * 
 * @author VinhNgh
 *
 */
public abstract class BaseResponseDto {

    private String status;

    private String message;

    /**
     * Get status
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Get message
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
