package com.healthcare.test;

import com.healthcare.model.DoctorSchedule;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for Time Slot Generation and Scheduling Algorithms (Collections & Algorithms).
 */
public class AppointmentDAOTest {

    @Test
    public void testDoctorSlotGeneration() {
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setScheduleId(1);
        schedule.setDoctorId(1);
        schedule.setAvailableDate(Date.valueOf("2026-10-15"));
        schedule.setStartTime(Time.valueOf("09:00:00"));
        schedule.setEndTime(Time.valueOf("11:00:00"));
        schedule.setSlotDurationMins(30);

        List<String> slots = schedule.generateTimeSlots();

        assertNotNull(slots);
        assertEquals(4, slots.size(), "A 2-hour window with 30-min duration should yield exactly 4 slots");
        assertEquals("09:00", slots.get(0));
        assertEquals("09:30", slots.get(1));
        assertEquals("10:00", slots.get(2));
        assertEquals("10:30", slots.get(3));
    }

    @Test
    public void testDoctorSlotGenerationWith15MinIntervals() {
        DoctorSchedule schedule = new DoctorSchedule();
        schedule.setStartTime(Time.valueOf("14:00:00"));
        schedule.setEndTime(Time.valueOf("15:00:00"));
        schedule.setSlotDurationMins(15);

        List<String> slots = schedule.generateTimeSlots();

        assertNotNull(slots);
        assertEquals(4, slots.size());
        assertEquals("14:00", slots.get(0));
        assertEquals("14:15", slots.get(1));
        assertEquals("14:30", slots.get(2));
        assertEquals("14:45", slots.get(3));
    }
}
