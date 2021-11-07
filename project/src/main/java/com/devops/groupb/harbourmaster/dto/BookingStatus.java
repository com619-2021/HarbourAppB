package com.devops.groupb.harbourmaster.dto;

public enum BookingStatus {
    SCHEDULED,
    FINISHED,
    CONFIRMED,
    INVOICED,
    CANCELED,
    DENIED,
    REJECTION_REQUESTED,
    REJECTED,
    MISSED, //Same as canceled but without prior contact 
    EXCHANGE_REQUESTED
}
