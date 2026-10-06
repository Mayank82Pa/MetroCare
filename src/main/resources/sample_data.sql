-- ========================================================================
-- ONLINE HEALTHCARE MANAGEMENT SYSTEM - SAMPLE DATA
-- Passwords:
-- admin@healthcare.com    -> admin123 (SHA-256: 240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9)
-- dr.sharma@healthcare.com -> doctor123 (SHA-256: e80b5017098950fc58aad83c8c14978e262fca83a8601c82190999a5131234bf)
-- dr.patel@healthcare.com  -> doctor123
-- dr.ananya@healthcare.com -> doctor123
-- rahul@gmail.com          -> patient123 (SHA-256: 3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1)
-- priya@gmail.com          -> patient123
-- ========================================================================

-- 1. INSERT SYSTEM SETTINGS
INSERT INTO system_settings (setting_key, setting_value, description) VALUES
('hospital_name', 'MetroCare Healthcare Center', 'Name of the healthcare institution'),
('contact_email', 'support@metrocare.com', 'Primary support email'),
('emergency_hotline', '+91 98765 43210', '24/7 Emergency response helpline'),
('appointment_slot_duration', '30', 'Default appointment slot length in minutes'),
('max_advance_booking_days', '30', 'Maximum days ahead a patient can book');

-- 2. INSERT USERS (Admins, Doctors, Patients)
INSERT INTO users (user_id, name, email, password, role, phone, status) VALUES
-- Admin
(1, 'System Administrator', 'admin@healthcare.com', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN', '+91 90000 00001', 'ACTIVE'),

-- Doctors
(2, 'Dr. Rajesh Sharma', 'dr.sharma@healthcare.com', 'e80b5017098950fc58aad83c8c14978e262fca83a8601c82190999a5131234bf', 'DOCTOR', '+91 98111 22233', 'ACTIVE'),
(3, 'Dr. Sneha Patel', 'dr.patel@healthcare.com', 'e80b5017098950fc58aad83c8c14978e262fca83a8601c82190999a5131234bf', 'DOCTOR', '+91 98222 33344', 'ACTIVE'),
(4, 'Dr. Ananya Roy', 'dr.ananya@healthcare.com', 'e80b5017098950fc58aad83c8c14978e262fca83a8601c82190999a5131234bf', 'DOCTOR', '+91 98333 44455', 'ACTIVE'),
(5, 'Dr. Vikram Mehta', 'dr.mehta@healthcare.com', 'e80b5017098950fc58aad83c8c14978e262fca83a8601c82190999a5131234bf', 'DOCTOR', '+91 98444 55566', 'ACTIVE'),

-- Patients
(6, 'Rahul Verma', 'rahul@gmail.com', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1', 'PATIENT', '+91 97111 11111', 'ACTIVE'),
(7, 'Priya Nair', 'priya@gmail.com', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1', 'PATIENT', '+91 97222 22222', 'ACTIVE'),
(8, 'Amit Kumar', 'amit@gmail.com', '3c9909afec25354d551dae21590bb26e38d53f2173b8d3dc3eee4c047e7ab1c1', 'PATIENT', '+91 97333 33333', 'ACTIVE');

-- 3. INSERT DOCTOR DETAILS
INSERT INTO doctors (doctor_id, user_id, specialization, qualification, experience_years, consultation_fee, bio) VALUES
(1, 2, 'Cardiology', 'MBBS, MD, DM (Cardiology)', 14, 800.00, 'Senior Interventional Cardiologist specializing in heart rhythm and cardiac care.'),
(2, 3, 'Dermatology', 'MBBS, MD (Dermatology)', 9, 600.00, 'Expert in clinical dermatology, skin health, and aesthetic care.'),
(3, 4, 'Neurology', 'MBBS, MD, DM (Neurology)', 12, 900.00, 'Specialist in migraine, neurological disorders, and stroke management.'),
(4, 5, 'General Physician', 'MBBS, MD (Internal Medicine)', 8, 500.00, 'General health consultant focusing on preventive medicine and chronic lifestyle diseases.');

-- 4. INSERT PATIENT DETAILS
INSERT INTO patients (patient_id, user_id, date_of_birth, gender, blood_group, address, emergency_contact) VALUES
(1, 6, '1995-05-14', 'Male', 'O+', '42 Park Avenue, New Delhi', '+91 97111 00000'),
(2, 7, '1998-11-23', 'Female', 'B+', '18 Green Glen Layout, Bangalore', '+91 97222 00000'),
(3, 8, '1990-03-08', 'Male', 'A+', '74 Shivaji Road, Mumbai', '+91 97333 00000');

-- 5. INSERT DOCTOR SCHEDULES (Dates relative to current year/week)
INSERT INTO doctor_schedule (schedule_id, doctor_id, available_date, start_time, end_time, slot_duration_mins, is_available) VALUES
(1, 1, '2026-10-12', '09:00:00', '13:00:00', 30, TRUE),
(2, 1, '2026-10-13', '14:00:00', '18:00:00', 30, TRUE),
(3, 2, '2026-10-12', '10:00:00', '14:00:00', 30, TRUE),
(4, 2, '2026-10-14', '15:00:00', '19:00:00', 30, TRUE),
(5, 3, '2026-10-13', '09:30:00', '13:30:00', 30, TRUE),
(6, 4, '2026-10-12', '08:30:00', '12:30:00', 30, TRUE);

-- 6. INSERT APPOINTMENTS
INSERT INTO appointments (appointment_id, patient_id, doctor_id, schedule_id, appointment_date, appointment_time, reason, status) VALUES
(1, 1, 1, 1, '2026-10-12', '09:30:00', 'Routine cardiac checkup & ECG review', 'CONFIRMED'),
(2, 2, 2, 3, '2026-10-12', '10:30:00', 'Skin rash and allergy consultation', 'COMPLETED'),
(3, 3, 3, 5, '2026-10-13', '10:00:00', 'Frequent tension headaches', 'PENDING'),
(4, 1, 4, 6, '2026-10-12', '09:00:00', 'General health checkup & blood test review', 'COMPLETED');

-- 7. INSERT MEDICAL RECORDS (For completed appointments)
INSERT INTO medical_records (record_id, appointment_id, patient_id, doctor_id, diagnosis, prescription, clinical_notes, follow_up_date) VALUES
(1, 2, 2, 2, 'Contact Dermatitis (Mild)', '1. Tab Levocetirizine 5mg (once daily at night for 5 days)\n2. Calamine topical lotion (twice daily)\n3. Avoid fragrant soaps', 'Patient showed mild redness on forearms. Advised hypoallergenic skincare.', '2026-10-26'),
(2, 4, 1, 4, 'Mild Vitamin D3 Deficiency & Fatigue', '1. Cap Vitamin D3 60,000 IU (once weekly for 8 weeks)\n2. Tab Multivitamin (daily after breakfast)', 'Vital signs normal: BP 120/80 mmHg, Pulse 72 bpm. Advised 20 mins morning sunlight.', '2026-11-12');

-- 8. INSERT PATIENT FEEDBACK
INSERT INTO feedback (feedback_id, appointment_id, patient_id, doctor_id, rating, comments) VALUES
(1, 2, 2, 2, 5, 'Dr. Sneha was very thorough and compassionate. The rash subsided in 2 days!'),
(2, 4, 1, 4, 5, 'Dr. Vikram took time to understand all symptoms and explained the test results clearly.');
