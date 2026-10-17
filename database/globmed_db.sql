-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.42 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.11.0.7065
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for globemed
CREATE DATABASE IF NOT EXISTS `globemed` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `globemed`;

-- Dumping structure for table globemed.appointment
CREATE TABLE IF NOT EXISTS `appointment` (
  `id` varchar(10) NOT NULL,
  `appointment_date` date NOT NULL,
  `patient_nic` varchar(12) NOT NULL,
  `service_id` varchar(10) NOT NULL,
  `added_date_time` datetime NOT NULL,
  `queue_no` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_appointment_patient1_idx` (`patient_nic`),
  KEY `fk_appointment_service1_idx` (`service_id`),
  CONSTRAINT `fk_appointment_patient1` FOREIGN KEY (`patient_nic`) REFERENCES `patient` (`nic`),
  CONSTRAINT `fk_appointment_service1` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


-- Dumping structure for table globemed.bill
CREATE TABLE IF NOT EXISTS `bill` (
  `id` varchar(10) NOT NULL,
  `date_time` datetime NOT NULL,
  `appointment_id` varchar(10) NOT NULL,
  `payment_method_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_appoinement_bill_appointment1_idx` (`appointment_id`),
  KEY `fk_appointment_bill_payment_method1_idx` (`payment_method_id`),
  CONSTRAINT `fk_appoinement_bill_appointment1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`),
  CONSTRAINT `fk_appointment_bill_payment_method1` FOREIGN KEY (`payment_method_id`) REFERENCES `payment_method` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping structure for table globemed.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gender` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table globemed.gender: ~2 rows (approximately)
INSERT INTO `gender` (`id`, `gender`) VALUES
	(1, 'Male'),
	(2, 'Female');

-- Dumping structure for table globemed.hospital
CREATE TABLE IF NOT EXISTS `hospital` (
  `id` varchar(10) NOT NULL,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table globemed.hospital: ~15 rows (approximately)
INSERT INTO `hospital` (`id`, `name`) VALUES
	('HOS0000001', 'Asiri Hoapitals'),
	('HOS0000002', 'Roseth Hospitals'),
	('HOS0000003', 'Coperative Hospitals'),
	('HOS0000004', 'Lanka Hospitals'),
	('HOS0000005', 'Nawaloka Hospitals'),
	('HOS0000006', 'Durdans Hospital'),
	('HOS0000007', 'Hemas Hospitals'),
	('HOS0000008', 'Neville Fernando Teaching Hospital'),
	('HOS0000009', 'Kandy General Hospital'),
	('HOS0000010', 'Karapitiya Teaching Hospital'),
	('HOS0000011', 'National Hospital of Sri Lanka'),
	('HOS0000012', 'Teaching Hospital Jaffna'),
	('HOS0000013', 'Castle Street Hospital for Women'),
	('HOS0000014', 'Lady Ridgeway Hospital for Children'),
	('HOS0000015', 'Base Hospital Gampaha');

-- Dumping structure for table globemed.medical_history
CREATE TABLE IF NOT EXISTS `medical_history` (
  `id` varchar(10) NOT NULL,
  `patient_nic` varchar(45) NOT NULL,
  `medical_note` text NOT NULL,
  `date_time` datetime NOT NULL,
  `appointment_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_medical_history_patient1_idx` (`patient_nic`),
  KEY `fk_medical_history_appointment1_idx` (`appointment_id`),
  CONSTRAINT `fk_medical_history_appointment1` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`),
  CONSTRAINT `fk_medical_history_patient1` FOREIGN KEY (`patient_nic`) REFERENCES `patient` (`nic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


-- Dumping structure for table globemed.patient
CREATE TABLE IF NOT EXISTS `patient` (
  `nic` varchar(12) NOT NULL,
  `full_name` varchar(150) NOT NULL,
  `date_of_birth` date NOT NULL,
  `address` text NOT NULL,
  `gender_id` int NOT NULL,
  `registered_date` datetime NOT NULL,
  PRIMARY KEY (`nic`),
  KEY `fk_patient_gender1_idx` (`gender_id`),
  CONSTRAINT `fk_patient_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


-- Dumping structure for table globemed.payment_method
CREATE TABLE IF NOT EXISTS `payment_method` (
  `id` int NOT NULL AUTO_INCREMENT,
  `method` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table globemed.payment_method: ~2 rows (approximately)
INSERT INTO `payment_method` (`id`, `method`) VALUES
	(1, 'Cash'),
	(2, 'Card'),
	(3, 'Insurance Claim');

-- Dumping structure for table globemed.report
CREATE TABLE IF NOT EXISTS `report` (
  `id` varchar(10) NOT NULL,
  `title` varchar(45) NOT NULL,
  `date_time` datetime NOT NULL,
  `patient_nic` varchar(12) NOT NULL,
  `report_type_id` int NOT NULL,
  `special_notes` text,
  PRIMARY KEY (`id`),
  KEY `fk_report_patient1_idx` (`patient_nic`),
  KEY `fk_report_report_type1_idx` (`report_type_id`),
  CONSTRAINT `fk_report_patient1` FOREIGN KEY (`patient_nic`) REFERENCES `patient` (`nic`),
  CONSTRAINT `fk_report_report_type1` FOREIGN KEY (`report_type_id`) REFERENCES `report_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;


-- Dumping structure for table globemed.report_item
CREATE TABLE IF NOT EXISTS `report_item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` text NOT NULL,
  `value` text NOT NULL,
  `report_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_report_item_report1_idx` (`report_id`),
  CONSTRAINT `fk_report_item_report1` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3;

-- Dumping structure for table globemed.report_type
CREATE TABLE IF NOT EXISTS `report_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table globemed.report_type: ~2 rows (approximately)
INSERT INTO `report_type` (`id`, `type`) VALUES
	(1, 'Patient Medical Report'),
	(2, 'Diagnostic Results'),
	(3, 'Financial Report');

-- Dumping structure for table globemed.service
CREATE TABLE IF NOT EXISTS `service` (
  `id` varchar(10) NOT NULL,
  `service_name` varchar(100) NOT NULL,
  `description` text,
  `price` double NOT NULL,
  `doctor_nic` varchar(12) NOT NULL,
  `service_type_id` int NOT NULL,
  `time` time NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_services_staff1_idx` (`doctor_nic`),
  KEY `fk_services_service_type1_idx` (`service_type_id`),
  CONSTRAINT `fk_services_service_type1` FOREIGN KEY (`service_type_id`) REFERENCES `service_type` (`id`),
  CONSTRAINT `fk_services_staff1` FOREIGN KEY (`doctor_nic`) REFERENCES `staff` (`nic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping structure for table globemed.service_type
CREATE TABLE IF NOT EXISTS `service_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table globemed.service_type: ~2 rows (approximately)
INSERT INTO `service_type` (`id`, `type`) VALUES
	(1, 'Consultation'),
	(2, 'Diagnostics Test'),
	(3, 'Surgerie');

-- Dumping structure for table globemed.staff
CREATE TABLE IF NOT EXISTS `staff` (
  `nic` varchar(12) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `hospital_id` varchar(10) NOT NULL,
  `staff_status_id` int NOT NULL,
  `staff_role_id` int NOT NULL,
  `added_date_time` datetime NOT NULL,
  PRIMARY KEY (`nic`),
  KEY `fk_staff_staff_status1_idx` (`staff_status_id`),
  KEY `fk_staff_staff_role1_idx` (`staff_role_id`),
  KEY `fk_staff_hospital1_idx` (`hospital_id`),
  CONSTRAINT `fk_staff_hospital1` FOREIGN KEY (`hospital_id`) REFERENCES `hospital` (`id`),
  CONSTRAINT `fk_staff_staff_role1` FOREIGN KEY (`staff_role_id`) REFERENCES `staff_role` (`id`),
  CONSTRAINT `fk_staff_staff_status1` FOREIGN KEY (`staff_status_id`) REFERENCES `staff_status` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table globemed.staff: ~32 rows (approximately)
INSERT INTO `staff` (`nic`, `first_name`, `last_name`, `password`, `hospital_id`, `staff_status_id`, `staff_role_id`, `added_date_time`) VALUES
	('199400400444', 'Chathuri', 'Perera', 'admin123', 'HOS0000001', 1, 1, '2025-09-16 11:04:44'),
	('199012345678', 'Dr. Nuwan', 'Perera', 'doctor123', 'HOS0000001', 1, 2, '2025-09-16 14:10:25'),
	('199200200222', 'Ishara', 'Jayawardena', 'nurse123', 'HOS0000001', 1, 3, '2025-09-16 11:02:22'),
	('198712349876', 'Ruwan', 'Jayasinghe', 'pham123', 'HOS0000001', 1, 4, '2025-09-16 14:12:41'),
	('199265478901', 'Shalini', 'Edirisooriya', 'resip123', 'HOS0000001', 1, 5, '2025-09-16 14:18:47');

-- Dumping structure for table globemed.staff_role
CREATE TABLE IF NOT EXISTS `staff_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table globemed.staff_role: ~5 rows (approximately)
INSERT INTO `staff_role` (`id`, `role`) VALUES
	(1, 'ADMIN'),
	(2, 'DOCTOR'),
	(3, 'NURSE'),
	(4, 'PHARMACIST'),
	(5, 'RECEPTIONIST');

-- Dumping structure for table globemed.staff_status
CREATE TABLE IF NOT EXISTS `staff_status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table globemed.staff_status: ~2 rows (approximately)
INSERT INTO `staff_status` (`id`, `status`) VALUES
	(1, 'Active'),
	(2, 'Deactive');

-- Dumping structure for table globemed.treatment_plans
CREATE TABLE IF NOT EXISTS `treatment_plans` (
  `id` varchar(10) NOT NULL,
  `plan` text NOT NULL,
  `medical_history_id` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_treatment_plans_medical_history1_idx` (`medical_history_id`),
  CONSTRAINT `fk_treatment_plans_medical_history1` FOREIGN KEY (`medical_history_id`) REFERENCES `medical_history` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
