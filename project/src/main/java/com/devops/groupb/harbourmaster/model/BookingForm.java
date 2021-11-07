package com.devops.groupb.harbourmaster.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class BookingForm {
    private int workId;
    private int pilotId; //ID for pilot assigned to this booking
    private int shipId; //ID for Ship 
    private int berthId; //ID of berth to take ship to.

    private LocalDateTime start;
    private LocalDateTime end;

    public BookingForm() {

    }

    public BookingForm(int workId, int pilotId, int shipId, int berthId, LocalDateTime start, LocalDateTime end) {
        this.workId = workId;
        this.pilotId = pilotId;
        this.shipId = shipId;
        this.berthId = berthId;
        this.start = start;
        this.end = end;
    }

    public int getWorkId() {
        return this.workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public int getPilotId() {
        return this.pilotId;
    }

    public void setPilotId(int pilotId) {
        this.pilotId = pilotId;
    }

    public int getShipId() {
        return this.shipId;
    }

    public void setShipId(int shipId) {
        this.shipId = shipId;
    }
    
    public int getBerthId() {
        return this.berthId;
    }

    public void setBerthId(int berthId) {
        this.berthId = berthId;
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

}
