# 🌐 GlobeMed – Medical Management System  

GlobeMed is a standalone Java-based medical management system that simplifies hospital operations.  
It manages doctors, nurses, pharmacists, patients, appointments, treatments, and medicines in one centralized platform.  

## Queries 

INSERT INTO `staff_role` (`id`, `role`) VALUES (1, 'ADMIN');
INSERT INTO `staff_role` (`id`, `role`) VALUES (2, 'DOCTOR');
INSERT INTO `staff_role` (`id`, `role`) VALUES (3, 'NURSE');
INSERT INTO `staff_role` (`id`, `role`) VALUES (4, 'PHARMACIST');
INSERT INTO `staff_role` (`id`, `role`) VALUES (5, 'RECEPTIONIST');

INSERT INTO `staff_status` (`id`, `status`) VALUES (1, 'Active');
INSERT INTO `staff_status` (`id`, `status`) VALUES (2, 'Deactive');

INSERT INTO `gender` (`id`, `gender`) VALUES (1, 'Male');
INSERT INTO `gender` (`id`, `gender`) VALUES (2, 'Female');

INSERT INTO `payment_method` (`id`, `method`) VALUES (1, 'Cash');
INSERT INTO `payment_method` (`id`, `method`) VALUES (2, 'Card');
INSERT INTO `payment_method` (`id`, `method`) VALUES (3, 'Insurance Claim');

INSERT INTO `report_type` (`id`, `type`) VALUES (1, 'Patient Medical Summery');
INSERT INTO `report_type` (`id`, `type`) VALUES (2, 'Diagnostic Results');
INSERT INTO `report_type` (`id`, `type`) VALUES (3, 'Financial Report');

INSERT INTO `service_type` (`id`, `type`) VALUES (1, 'Consultation');
INSERT INTO `service_type` (`id`, `type`) VALUES (2, 'Diagnostics Test');
INSERT INTO `service_type` (`id`, `type`) VALUES (3, 'Surgerie');
