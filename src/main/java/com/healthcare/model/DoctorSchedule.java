package com.healthcare.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Doctor Schedule Model Class representing availability blocks and time slot generation.
 */
public class DoctorSchedule implements Serializable {
    private static final long serialVersionUID = 1L;

    private int scheduleId;
    private int doctorId;
    private Date availableDate;
    private Time startTime;
    private Time endTime;
    private int slotDurationMins;
    private boolean isAvailable;

    // Joined Doctor Info
    private String doctorName;
    private String specialization;

    public DoctorSchedule() {
        this.slotDurationMins = 30;
        this.isAvailable = true;
    }

    public DoctorSchedule(int scheduleId, int doctorId, Date availableDate,
                          Time startTime, Time endTime, int slotDurationMins, boolean isAvailable) {
        this.scheduleId = scheduleId;
        this.doctorId = doctorId;
        this.availableDate = availableDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.slotDurationMins = slotDurationMins > 0 ? slotDurationMins : 30;
        this.isAvailable = isAvailable;
    }

    /**
     * Generates a list of discrete time slots based on startTime, endTime, and slotDurationMins.
     * Demonstrates use of Java Collections Framework (ArrayList).
     */
    public List<String> generateTimeSlots() {
        List<String> slots = new ArrayList<>();
        if (startTime == null || endTime == null) {
            return slots;
        }

        long startMillis = startTime.getTime();
        long endMillis = endTime.getTime();
        long stepMillis = (long) slotDurationMins * 60 * 1000;

        for (long time = startMillis; time < endMillis; time += stepMillis) {
            Time slotTime = new Time(time);
            slots.add(slotTime.toString().substring(0, 5)); // "HH:mm"
        }
        return slots;
    }

    // Getters and Setters
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getAvailableDate() {
        return availableDate;
    }

    public void setAvailableDate(Date availableDate) {
        this.availableDate = availableDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getSlotDurationMins() {
        return slotDurationMins;
    }

    public void setSlotDurationMins(int slotDurationMins) {
        this.slotDurationMins = slotDurationMins;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
