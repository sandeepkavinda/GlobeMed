# GlobeMed – Hospital Management System

GlobeMed is a **Java-based standalone Hospital Management System** developed as part of an academic project. Built using Java Swing, JDBC, and MySQL, it provides a centralized platform for managing hospital staff, patients, appointments, medical records, treatment plans, billing, and diagnostic reports. The system implements role-based access control for five user roles.

The system provides role-based access control to ensure that each user can access only the features relevant to their responsibilities.

## Technologies Used

- **Programming Language:** Java
- **Java Version:** JDK 17
- **IDE:** Apache NetBeans IDE 25
- **Application Type:** Standalone Desktop Application
- **UI Framework:** Java Swing
- **Database Connectivity:** JDBC
- **Database:** MySQL

## Database

The application uses MySQL to store and manage hospital data, including:

- Staff and user information
- Patients
- Hospital services
- Appointments and queue numbers
- Medical records
- Treatment plans
- Diagnostic reports
- Billing and payment information

## User Roles and Access Control

GlobeMed implements a permission-based access control system. Each user role is assigned specific permissions that determine which features and operations they can access.

### Admin

- Access the dashboard.
- Display staff members, add new staff members, and change staff member status.
- Display and add hospital services.
- View patient information, register patients, and access detailed patient profiles.
- Display and create appointments.
- Display and create bills.
- Access report management.
- Generate diagnostic reports, patient medical reports, and financial reports.

### Doctor

- View patient information and detailed patient profiles.
- Add medical records.
- Create diagnostic reports.
- Generate diagnostic reports and patient medical reports.
- Access the report section without financial reporting permissions.

### Nurse

- View patient information and detailed patient profiles.
- Has permission to create new diagnostic reports according to the configured access handler.

### Pharmacist

- View patient information and detailed patient profiles.
- Access appointment creation.
- Display and create bills.
- Generate diagnostic reports and patient medical reports.

### Receptionist

- View patient information.
- Register new patients.
- Display and create appointments.
- Display and create bills.
- Generate diagnostic reports and patient medical reports.
- Does not have permissions for staff management, service management, or financial reports.

### Permission Summary

| Permission                       | Admin | Doctor | Nurse | Pharmacist | Receptionist |
| -------------------------------- | :---: | :----: | :---: | :--------: | :----------: |
| Display Dashboard                |  Yes  |   —    |   —   |     —      |      —       |
| Display Staff                    |  Yes  |   —    |   —   |     —      |      —       |
| Add Staff Member                 |  Yes  |   —    |   —   |     —      |      —       |
| Change Staff Status              |  Yes  |   —    |   —   |     —      |      —       |
| Display Services                 |  Yes  |   —    |   —   |     —      |      —       |
| Add Services                     |  Yes  |   —    |   —   |     —      |      —       |
| Display Patients                 |  Yes  |  Yes   |  Yes  |    Yes     |     Yes      |
| Register Patients                |  Yes  |   —    |   —   |     —      |     Yes      |
| View Patient Details             |  Yes  |  Yes   |  Yes  |    Yes     |      —       |
| Display Appointments             |  Yes  |   —    |   —   |     —      |     Yes      |
| Add Appointments                 |  Yes  |   —    |   —   |    Yes     |     Yes      |
| Display Billing                  |  Yes  |   —    |   —   |    Yes     |     Yes      |
| Add Bills                        |  Yes  |   —    |   —   |    Yes     |     Yes      |
| Add Medical Records              |   —   |  Yes   |   —   |     —      |      —       |
| Add Diagnostic Reports           |   —   |  Yes   |  Yes  |     —      |      —       |
| Generate Diagnostic Reports      |  Yes  |  Yes   |   —   |    Yes     |     Yes      |
| Generate Patient Medical Reports |  Yes  |  Yes   |   —   |    Yes     |     Yes      |
| Generate Financial Reports       |  Yes  |   —    |   —   |     —      |      —       |

## Project Structure

```text
GlobeMed/
├── build/
├── config/
├── database/
├── DB Model/
├── lib/
├── nbproject/
├── src/
├── .gitignore
├── build.xml
├── LICENSE
├── manifest.mf
└── README.md
```

## Installation and Setup

Follow these steps to set up the project locally.

### Prerequisites

Make sure you have installed:

- Java Development Kit (JDK) 17
- Apache NetBeans IDE
- MySQL Server
- MySQL Workbench or another MySQL database management tool

### Setup Instructions

1. **Clone the repository**

   ```bash
   git clone https://github.com/sandeepkavinda/GlobeMed.git
   ```

2. **Set up the database**
   - Open your preferred MySQL database management tool.
   - Import and execute the SQL file located at `database/globmed_db.sql` to create the database tables and insert the required initial data, including five user accounts for each staff role.

   - **(Optional)** Import and execute `database/globemed_sample_data.sql` to insert additional sample data for testing and demonstration. If you prefer a clean database without extra sample records, skip this step.

3. **Database Configuration**
   - Navigate to the `config/` directory.
   - Create a file named `db.properties`.
   - Add the following configuration and update it with your MySQL credentials.

   ```text
   db.url=jdbc:mysql://localhost:3306/globemed
   db.username=root
   db.password=YOUR_PASSWORD_HERE
   ```

   Replace YOUR_PASSWORD_HERE with your actual MySQL password.

4. **Open the project**
   - Open Apache NetBeans IDE and import or open the cloned GlobeMed project.

5. **Run the application**
   - In the **Projects** panel, right-click the **GlobeMed** project and select **Run**.

## Sample Login Credentials

Use the following credentials to log in to the GlobeMed Hospital Management System.

| Staff Role   | NIC            | Password    |
| ------------ | -------------- | ----------- |
| Admin        | `199400400444` | `admin123`  |
| Doctor       | `199012345678` | `doctor123` |
| Nurse        | `199200200222` | `nurse123`  |
| Pharmacist   | `198712349876` | `pham123`   |
| Receptionist | `199265478901` | `resip123`  |

## Project Objectives

- Centralize hospital operations in one application.
- Improve patient registration and appointment management.
- Maintain organized patient medical records and treatment plans.
- Simplify hospital billing and payment tracking.
- Support diagnostic reporting and patient summaries.
- Provide role-based access to protect sensitive hospital information.

## Future Improvements

Potential enhancements include:

- Medicine inventory and stock management.
- Prescription management and medicine dispensing records.
- Appointment reminders and notifications.
- Enhanced financial and operational analytics.
- Database backup and recovery features.

## Author

**Sandeep Kavinda**

- LinkedIn: [@sandeepkavinda](https://www.linkedin.com/in/sandeepkavinda/)
- GitHub: [@sandeepkavinda](https://github.com/sandeepkavinda)

## License

This project is licensed under the Boost Software [License](LICENSE) 1.0. See the LICENSE file for details.
