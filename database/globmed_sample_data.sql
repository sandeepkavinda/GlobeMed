USE `globemed`;

-- Dumping data for table globemed.patient: ~17 rows (approximately)
INSERT INTO `patient` (`nic`, `full_name`, `date_of_birth`, `address`, `gender_id`, `registered_date`) VALUES
	('12121', 'Nadeesha Fernando', '2002-12-05', 'Galle Road, Colombo, Sri Lanka', 2, '2025-09-02 09:15:30'),
	('199900123456', 'Kaveesha Perera', '2003-06-15', 'Colombo 07, Sri Lanka', 1, '2025-09-01 10:20:00'),
	('200205300222', 'Sandeepa Silva', '2002-02-22', 'Ambalangoda, Sri Lanka', 1, '2025-08-31 18:09:23'),
	('2002053333', 'Sachintha Dilshan', '2004-03-01', 'Mihinthale, Anuradhapura, Sri Lanka', 1, '2025-08-28 03:59:53'),
	('200406112643', 'Nishanthage Sachintha Dilshan Chandradasa', '2004-03-01', 'No:45, Mihinthale, Horowpathana, Anuradhapura, Sri Lanka', 1, '2025-08-29 00:00:00'),
	('20053265', 'Nishanthage Chandhradasa', '2005-08-28', 'Mathale, Sri Lanka', 1, '2025-08-28 04:03:18'),
	('200600600666', 'Roshan Silva', '2001-04-12', 'Negombo, Sri Lanka', 1, '2025-09-04 12:30:10'),
	('200700700777', 'Chamari Peris', '2005-07-08', 'Matara, Sri Lanka', 2, '2025-09-05 08:50:45'),
	('200800800888', 'Tharindu Senanayake', '2003-11-21', 'Anuradhapura, Sri Lanka', 1, '2025-09-06 14:05:00'),
	('200900900999', 'Menaka Wijesinghe', '2002-02-17', 'Gampaha, Sri Lanka', 2, '2025-09-07 15:25:30'),
	('201000100111', 'Sajith Abeywardena', '2000-05-10', 'Kurunegala, Sri Lanka', 1, '2025-09-08 10:10:10'),
	('201100100222', 'Anushka Karunathilaka', '2001-08-29', 'Jaffna, Sri Lanka', 2, '2025-09-09 09:45:00'),
	('201200200333', 'Kaveesha Perera', '2003-06-15', 'Colombo 07, Sri Lanka', 1, '2025-09-01 10:20:00'),
	('201300300444', 'Nadeesha Fernando', '2002-12-05', 'Galle Road, Colombo, Sri Lanka', 2, '2025-09-02 09:15:30'),
	('201400400555', 'Dilani Jayawardena', '2004-09-23', 'Kandy, Sri Lanka', 2, '2025-09-03 11:45:20'),
	('201500500666', 'Chamath Gunasekara', '2003-03-18', 'Matale, Sri Lanka', 1, '2025-09-04 14:10:00'),
	('223', 'Dilani Jayawardena', '2004-09-23', 'Kandy, Sri Lanka', 2, '2025-09-03 11:45:20');

-- Dumping data for table globemed.service: ~14 rows (approximately)
INSERT INTO `service` (`id`, `service_name`, `description`, `price`, `doctor_nic`, `service_type_id`, `time`) VALUES
	('SER0000001', 'ENT Consultation', 'Consultation for ear, nose and throat related issues', 1500, '200205300222', 1, '09:00:00'),
	('SER0000002', 'Cataract Surgery', 'Surgical procedure to remove cataracts and restore vision', 25000, '200205300222', 3, '14:00:00'),
	('SER0000003', 'General Consultation', 'Basic consultation for common illnesses and health check', 1200, '200205300222', 1, '11:30:00'),
	('SER0000014', 'General Consultation', 'Basic consultation with physician', 1500, '199012345678', 1, '09:00:00'),
	('SER0000015', 'Pediatric Consultation', 'Child health checkup', 2000, '199100100111', 1, '10:00:00'),
	('SER0000016', 'ENT Consultation', 'Ear, Nose, Throat consultation', 1800, '199134567890', 1, '11:00:00'),
	('SER0000017', 'Blood Test', 'Routine blood test diagnostics', 1200, '199600600666', 2, '08:30:00'),
	('SER0000018', 'X-Ray', 'X-Ray diagnostic imaging', 2500, '199845612390', 2, '13:00:00'),
	('SER0000019', 'MRI Scan', 'Advanced MRI imaging service', 18000, '200100100111', 2, '14:00:00'),
	('SER0000020', 'Appendix Surgery', 'Appendectomy surgical procedure', 40000, '200205300222', 3, '15:00:00'),
	('SER0000021', 'Cataract Surgery', 'Cataract removal surgery', 50000, '200500500555', 3, '16:00:00'),
	('SER0000022', 'Cardiac Bypass Surgery', 'Heart bypass surgical procedure', 250000, '200800800888', 3, '17:30:00'),
	('SER0000023', 'Eye Consultation', 'Consultation for vision problems', 2200, '199012345678', 1, '09:30:00'),
	('SER0000024', 'General Consultation', 'Routine check-up with general physician ', 3000, '199012345678', 1, '14:00:00');

-- Dumping data for table globemed.appointment: ~25 rows (approximately)
INSERT INTO `appointment` (`id`, `appointment_date`, `patient_nic`, `service_id`, `added_date_time`, `queue_no`) VALUES
	('APP0000001', '2025-08-29', '200406112643', 'SER0000001', '2025-08-29 04:05:31', 1),
	('APP0000002', '2025-08-30', '20053265', 'SER0000002', '2025-08-29 19:53:36', 2),
	('APP0000003', '2025-08-31', '200406112643', 'SER0000001', '2025-08-30 01:58:35', 1),
	('APP0000004', '2025-08-31', '200406112643', 'SER0000002', '2025-08-30 02:00:37', 1),
	('APP0000005', '2025-08-31', '200406112643', 'SER0000002', '2025-08-30 02:01:37', 2),
	('APP0000006', '2025-08-30', '12121', 'SER0000001', '2025-08-30 02:29:58', 1),
	('APP0000007', '2025-08-31', '12121', 'SER0000001', '2025-08-31 18:08:11', 1),
	('APP0000008', '2025-08-31', '200205300222', 'SER0000001', '2025-08-31 18:09:23', 1),
	('APP0000009', '2025-09-01', '12121', 'SER0000001', '2025-09-01 09:00:00', 1),
	('APP0000010', '2025-09-01', '199900123456', 'SER0000002', '2025-09-01 09:30:00', 2),
	('APP0000011', '2025-09-02', '200205300222', 'SER0000003', '2025-09-02 10:00:00', 1),
	('APP0000012', '2025-09-02', '2002053333', 'SER0000001', '2025-09-02 10:30:00', 2),
	('APP0000013', '2025-09-03', '200406112643', 'SER0000002', '2025-09-03 11:00:00', 1),
	('APP0000014', '2025-09-03', '20053265', 'SER0000003', '2025-09-03 11:30:00', 2),
	('APP0000015', '2025-09-04', '200600600666', 'SER0000001', '2025-09-04 08:50:00', 1),
	('APP0000016', '2025-09-04', '200700700777', 'SER0000002', '2025-09-04 09:20:00', 2),
	('APP0000017', '2025-09-05', '200800800888', 'SER0000003', '2025-09-05 10:00:00', 1),
	('APP0000018', '2025-09-05', '200900900999', 'SER0000001', '2025-09-05 10:30:00', 2),
	('APP0000019', '2025-09-06', '201000100111', 'SER0000002', '2025-09-06 09:15:00', 1),
	('APP0000020', '2025-09-06', '201100100222', 'SER0000003', '2025-09-06 09:45:00', 2),
	('APP0000021', '2025-09-07', '201200200333', 'SER0000001', '2025-09-07 08:55:00', 1),
	('APP0000022', '2025-09-07', '201300300444', 'SER0000002', '2025-09-07 09:25:00', 2),
	('APP0000023', '2025-09-08', '201400400555', 'SER0000003', '2025-09-08 10:10:00', 1),
	('APP0000024', '2025-09-08', '201500500666', 'SER0000001', '2025-09-08 10:40:00', 2),
	('APP0000025', '2025-09-09', '223', 'SER0000002', '2025-09-09 09:50:00', 1);

-- Dumping data for table globemed.bill: ~7 rows (approximately)
INSERT INTO `bill` (`id`, `date_time`, `appointment_id`, `payment_method_id`) VALUES
	('BLL0000001', '2025-08-31 01:24:37', 'APP0000001', 1),
	('BLL0000002', '2025-08-31 02:44:09', 'APP0000002', 3),
	('BLL0000003', '2025-08-31 18:39:21', 'APP0000003', 1),
	('BLL0000004', '2025-09-17 06:41:11', 'APP0000004', 1),
	('BLL0000005', '2025-09-17 06:41:36', 'APP0000005', 1),
	('BLL0000006', '2025-09-17 06:47:36', 'APP0000006', 2),
	('BLL0000007', '2025-09-17 12:03:42', 'APP0000007', 1);


-- Dumping data for table globemed.medical_history: ~24 rows (approximately)
INSERT INTO `medical_history` (`id`, `patient_nic`, `medical_note`, `date_time`, `appointment_id`) VALUES
	('MDH0000001', '200406112643', 'High Blood Pressure', '2025-08-29 03:43:13', 'APP0000001'),
	('MDH0000002', '20053265', 'Mild Fever', '2025-08-30 19:50:00', 'APP0000002'),
	('MDH0000003', '200406112643', 'Seasonal Allergies', '2025-08-30 01:55:00', 'APP0000003'),
	('MDH0000006', '12121', 'Routine Checkup', '2025-08-30 02:29:00', 'APP0000006'),
	('MDH0000007', '12121', 'Mild Cold Symptoms', '2025-08-31 18:05:00', 'APP0000007'),
	('MDH0000008', '200205300222', 'Eye Irritation', '2025-08-31 18:08:00', 'APP0000008'),
	('MDH0000009', '12121', 'Follow-up Checkup', '2025-09-01 08:55:00', 'APP0000009'),
	('MDH0000010', '199900123456', 'Back Pain', '2025-09-01 09:25:00', 'APP0000010'),
	('MDH0000011', '200205300222', 'Routine Eye Examination', '2025-09-02 09:55:00', 'APP0000011'),
	('MDH0000012', '2002053333', 'Minor Headache', '2025-09-02 10:30:00', 'APP0000012'),
	('MDH0000013', '200406112643', 'Fever and Cold', '2025-09-03 10:55:00', 'APP0000013'),
	('MDH0000014', '20053265', 'Skin Rash', '2025-09-03 11:25:00', 'APP0000014'),
	('MDH0000015', '200600600666', 'Routine Health Check', '2025-09-04 08:50:00', 'APP0000015'),
	('MDH0000016', '200700700777', 'Mild Flu Symptoms', '2025-09-04 09:20:00', 'APP0000016'),
	('MDH0000017', '200800800888', 'Stomach Pain', '2025-09-05 09:55:00', 'APP0000017'),
	('MDH0000018', '200900900999', 'Follow-up Eye Check', '2025-09-05 10:25:00', 'APP0000018'),
	('MDH0000019', '201000100111', 'Migraine', '2025-09-06 09:10:00', 'APP0000016'),
	('MDH0000020', '201100100222', 'Routine Checkup', '2025-09-06 09:40:00', 'APP0000020'),
	('MDH0000021', '201200200333', 'Mild Fever', '2025-09-07 08:50:00', 'APP0000021'),
	('MDH0000022', '201300300444', 'Headache', '2025-09-07 09:20:00', 'APP0000022'),
	('MDH0000023', '201400400555', 'Cold Symptoms', '2025-09-08 10:05:00', 'APP0000023'),
	('MDH0000024', '201500500666', 'Back Pain', '2025-09-08 10:35:00', 'APP0000024'),
	('MDH0000025', '223', 'Mild Fever', '2025-09-09 09:45:00', 'APP0000025'),
	('MDH0000026', '200406112643', 'Headache and Dizziness', '2025-09-17 12:07:23', 'APP0000004');


-- Dumping data for table globemed.report: ~4 rows (approximately)
INSERT INTO `report` (`id`, `title`, `date_time`, `patient_nic`, `report_type_id`, `special_notes`) VALUES
	('REP0000001', 'Cholesterol Test Report', '2025-08-30 17:24:52', '200205300222', 2, 'Patient fasting for 12 hours before test, results indicate borderline cholesterol levels'),
	('REP0000004', 'Semen Analysis Report', '2025-08-31 02:40:05', '2002053333', 2, 'Sperm count and motility evaluated, patient advised follow-up in 2 weeks'),
	('REP0000005', 'Semen Analysis Report', '2025-08-31 02:42:09', '200800800888', 2, 'Sperm morphology and motility checked, patient advised to maintain healthy lifestyle'),
	('REP0000006', 'Blood Glucose Test', '2025-09-17 12:05:34', '200406112643', 2, 'Patient fasting for 8 hours, results within normal range');


-- Dumping data for table globemed.report_item: ~11 rows (approximately)
INSERT INTO `report_item` (`id`, `title`, `value`, `report_id`) VALUES
	(1, 'Total Cholesterol', '210 mg/dL', 'REP0000001'),
	(2, 'HDL Cholesterol', '50 mg/dL', 'REP0000001'),
	(3, 'LDL Cholesterol', '140 mg/dL', 'REP0000001'),
	(9, 'Sperm Count', '60 million/mL', 'REP0000004'),
	(10, 'Motility', '75%', 'REP0000004'),
	(11, 'Morphology', 'Normal', 'REP0000004'),
	(12, 'Sperm Count', '58 million/mL', 'REP0000005'),
	(13, 'Motility', '70%', 'REP0000005'),
	(14, 'Morphology', 'Normal', 'REP0000005'),
	(17, 'Fasting Glucose', '95 mg/dL ', 'REP0000006'),
	(18, 'Postprandial Glucose', '110 mg/dL', 'REP0000006');

-- Dumping data for table globemed.staff: ~32 rows (approximately)
INSERT INTO `staff` (`nic`, `first_name`, `last_name`, `password`, `hospital_id`, `staff_status_id`, `staff_role_id`, `added_date_time`) VALUES
	('199100100111', 'Dr. Nadeesha', 'Wijeratne', '123', 'HOS0000001', 1, 2, '2025-09-16 11:01:11'),
	('199100100113', 'Dr. Sameera', 'Jayathilaka', '123', 'HOS0000001', 1, 2, '2025-09-17 11:58:52'),
	('199134567890', 'Dr. Harsha', 'Gunawardena', '123', 'HOS0000001', 1, 2, '2025-09-16 14:15:21'),
	('199300300333', 'Ravindu', 'Abeykoon', '123', 'HOS0000003', 1, 4, '2025-09-16 11:03:33'),
	('199500500555', 'Supun', 'Hettiarachchi', '123', 'HOS0000005', 1, 5, '2025-09-16 11:05:55'),
	('199556789012', 'Chamodi', 'Peris', '123', 'HOS0000001', 1, 3, '2025-09-16 14:16:40'),
	('199600600666', 'Dr. Dilini', 'Ranasinghe', '123', 'HOS0000006', 1, 2, '2025-09-16 11:06:06'),
	('199700700777', 'Harsha', 'Fonseka', '123', 'HOS0000007', 1, 3, '2025-09-16 11:07:07'),
	('199800800888', 'Lakmini', 'Dias', '123', 'HOS0000008', 1, 4, '2025-09-16 11:08:08'),
	('199845612390', 'Dr. Supun', 'Madushan', '123', 'HOS0000001', 1, 2, '2025-09-16 14:19:33'),
	('199845678912', 'Kasuni', 'Fernando', '123', 'HOS0000001', 1, 3, '2025-09-16 14:11:02'),
	('199900900999', 'Chamath', 'Gunasekara', '123', 'HOS0000009', 1, 5, '2025-09-16 11:09:09'),
	('199976543210', 'Tharindu', 'Silva', '123', 'HOS0000001', 1, 1, '2025-09-16 14:13:18'),
	('200045612378', 'Menaka', 'Wijesinghe', '123', 'HOS0000001', 1, 5, '2025-09-16 14:14:06'),
	('200100100111', 'Dr. Sajani', 'Weerasinghe', '123', 'HOS0000010', 1, 2, '2025-09-16 11:10:11'),
	('200198765432', 'Kavindu', 'Ranasinghe', '123', 'HOS0000001', 1, 4, '2025-09-16 14:17:09'),
	('200200200222', 'Nirmal', 'Ratnayake', '123', 'HOS0000011', 1, 1, '2025-09-16 11:11:22'),
	('200205300222', 'Sandeep', 'Kavinda', '123', 'HOS0000001', 1, 1, '2025-09-16 13:31:46'),
	('200300300333', 'Shenali', 'Edirisinghe', '123', 'HOS0000012', 1, 3, '2025-09-16 11:12:33'),
	('200400400444', 'Kavindu', 'Samarasekara', '123', 'HOS0000013', 1, 5, '2025-09-16 11:13:44'),
	('200500500555', 'Dr. Malsha', 'Goonetilleke', '123', 'HOS0000014', 1, 2, '2025-09-16 11:14:55'),
	('200600600666', 'Roshan', 'Bandaranayake', '123', 'HOS0000015', 1, 4, '2025-09-16 11:15:06'),
	('200700700777', 'Pavithra', 'Dissanayake', '123', 'HOS0000001', 1, 5, '2025-09-16 11:16:17'),
	('200800800888', 'Dr. Chamika', 'Kodikara', '123', 'HOS0000002', 1, 2, '2025-09-16 11:17:28'),
	('200900900999', 'Thilini', 'Rupasinghe', '123', 'HOS0000003', 1, 3, '2025-09-16 11:18:39'),
	('201000100111', 'Sajith', 'Abeywardena', '123', 'HOS0000004', 1, 4, '2025-09-16 11:19:50'),
	('201100100222', 'Anushka', 'Karunathilaka', '123', 'HOS0000005', 1, 1, '2025-09-16 11:20:21');

-- Dumping data for table globemed.treatment_plans: ~24 rows (approximately)
INSERT INTO `treatment_plans` (`id`, `plan`, `medical_history_id`) VALUES
	('TRP0000001', 'Prescribed Amlodipine 5mg once daily for high blood pressure, follow-up in 2 weeks', 'MDH0000001'),
	('TRP0000002', 'Paracetamol 500mg every 6 hours for mild fever, drink plenty of fluids', 'MDH0000002'),
	('TRP0000003', 'Cetirizine 10mg once daily for allergies, avoid allergens', 'MDH0000003'),
	('TRP0000006', 'No medication needed, routine checkup completed', 'MDH0000006'),
	('TRP0000007', 'Vitamin C 500mg once daily, hydration advised for mild cold', 'MDH0000007'),
	('TRP0000008', 'Prescribed artificial tears eye drops, avoid prolonged screen time', 'MDH0000008'),
	('TRP0000009', 'Routine checkup, advised multivitamins and balanced diet', 'MDH0000009'),
	('TRP0000010', 'Ibuprofen 400mg every 8 hours for back pain, physiotherapy recommended', 'MDH0000010'),
	('TRP0000011', 'Eye exercises and Lubricating eye drops prescribed', 'MDH0000011'),
	('TRP0000012', 'Paracetamol 500mg every 6 hours for mild headache, hydration advised', 'MDH0000012'),
	('TRP0000013', 'Paracetamol 500mg and Cough Syrup 10ml every 8 hours for fever and cold', 'MDH0000013'),
	('TRP0000014', 'Hydrocortisone cream twice daily for skin rash, avoid irritants', 'MDH0000014'),
	('TRP0000015', 'No medication needed, routine health check completed', 'MDH0000015'),
	('TRP0000016', 'Rest, hydration, and Vitamin C 500mg once daily for mild flu', 'MDH0000016'),
	('TRP0000017', 'Antacids (Gaviscon 10ml after meals) and diet modification for stomach pain', 'MDH0000017'),
	('TRP0000018', 'Routine eye check completed, Prescribed Lubricating eye drops', 'MDH0000018'),
	('TRP0000019', 'Sumatriptan 50mg as needed for migraine, avoid triggers', 'MDH0000019'),
	('TRP0000020', 'No medication, routine checkup, advised balanced diet', 'MDH0000020'),
	('TRP0000021', 'Paracetamol 500mg every 6 hours for mild fever, rest advised', 'MDH0000021'),
	('TRP0000022', 'Ibuprofen 400mg every 8 hours for headache, monitor symptoms', 'MDH0000022'),
	('TRP0000023', 'Cough Syrup 10ml every 8 hours, rest and fluids advised for cold', 'MDH0000023'),
	('TRP0000024', 'Ibuprofen 400mg every 8 hours for back pain, physiotherapy recommended', 'MDH0000024'),
	('TRP0000025', 'Paracetamol 500mg every 6 hours for mild fever, rest and hydration', 'MDH0000025'),
	('TRP0000026', 'Ibuprofen 400mg every 8 hours for headache, stay hydrated', 'MDH0000026');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
