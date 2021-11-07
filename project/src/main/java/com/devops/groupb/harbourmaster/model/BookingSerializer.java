package com.devops.groupb.harbourmaster.model;

import com.devops.groupb.harbourmaster.dto.Booking;
import com.devops.groupb.harbourmaster.dto.BookingStatus;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvide;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.ZoneOffset;

public class BookingSerializer extends StdSerializer<Booking> {
    // Override function for when this function is called with no args 
    public BookingSerializer() {
        this(null); //call this object with null
    }
    // Overide function for when this function is called with arg of type 
    public BookingSerializer(Class<Booking> class1) {
        super(class1); //Call this object 
    }

    @Override 
    public void serialize(Booking appointment, JsonGenerator jsonGen, SerializerProvider prov) throws IOException {
        jsonGen.writeStartObject();
        jsonGen.writeNumberField("id", appointment.getId());
        gen.writeStringField("title", appointment.getWork().getName());
        gen.writeNumberField("start", appointment.getStart().toInstant(ZoneOffset.UTC).toEpochMilli());
        gen.writeNumberField("end", appointment.getEnd().toInstant(ZoneOffset.UTC).toEpochMilli());
        gen.writeStringField("url", "/bookings/" + appointment.getId());
        gen.writeStringField("color", appointment.getStatus().equals(AppointmentStatus.SCHEDULED) ? "#28a745" : "grey");
    }
}
