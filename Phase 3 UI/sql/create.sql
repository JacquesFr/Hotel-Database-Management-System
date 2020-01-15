-- The below sql statements drops of the tables if they exist
DROP TABLE IF EXISTS Hotel CASCADE;
DROP TABLE IF EXISTS Staff CASCADE;
DROP TABLE IF EXISTS Room  CASCADE;
DROP TABLE IF EXISTS Customer CASCADE;
DROP TABLE IF EXISTS MaintenanceCompany CASCADE;

DROP TABLE IF EXISTS Booking CASCADE;
DROP TABLE IF EXISTS Repair CASCADE;
DROP TABLE IF EXISTS Request CASCADE;
DROP TABLE IF EXISTS Assigned CASCADE;

-- The below sql statements drops of the enum types if they exits
DROP TYPE IF EXISTS StaffRole CASCADE;
DROP TYPE IF EXISTS GenderType CASCADE;
-- The below sql statements creates enum types
CREATE TYPE StaffRole AS ENUM('Receptionist','HouseCleaning','Manager'); -- enum for staffRoles
CREATE TYPE GenderType AS ENUM('Male', 'Female', 'Other'); -- enum for gender

-- The below statements create necessary tables 
CREATE TABLE Hotel( hotelID Numeric NOT NULL, 
                    address Text, 
					manager Numeric DEFAULT 0,
					PRIMARY KEY(hotelID));

CREATE TABLE Staff( SSN Numeric NOT NULL, 
					fName CHAR(30) NOT NULL, 
					lName CHAR(30) NOT NULL, 
					address Text,
					role StaffRole NOT NULL,
					employerID Numeric NOT NULL DEFAULT 0,
					PRIMARY KEY(SSN));

CREATE TABLE Room( hotelID Numeric NOT NULL,
				   roomNo  Numeric NOT NULL,
				   roomType CHAR(10) NOT NULL,
				   PRIMARY KEY(hotelID, roomNo));

CREATE TABLE Customer( customerID Numeric NOT NULL,
					   fName CHAR(30) NOT NULL,
					   lName CHAR(30) NOT NULL,
					   Address TEXT,
					   phNo Numeric,
					   DOB Date,
					   gender GenderType,
					   PRIMARY KEY(customerID));

CREATE TABLE MaintenanceCompany( cmpID Numeric NOT NULL,
								 name CHAR(30) NOT NULL,
								 address TEXT,
								 isCertified Boolean NOT NULL,
								 PRIMARY KEY(cmpID));
CREATE TABLE Booking( bID Numeric NOT NULL,
					  customer Numeric NOT NULL DEFAULT 0,
					  hotelID Numeric NOT NULL DEFAULT 0,
					  roomNo Numeric NOT NULL DEFAULT 0,
					  bookingDate Date NOT NULL,
					  noOfPeople Numeric,
					  price Numeric(6,2) NOT NULL,
					  PRIMARY KEY(bID));
					  
CREATE TABLE Repair( rID Numeric NOT NULL,
					 hotelID Numeric NOT NULL DEFAULT 0,
					 roomNo Numeric NOT NULL DEFAULT 0,
					 mCompany Numeric NOT NULL DEFAULT 0,
					 repairDate Date NOT NULL,
					 description TEXT,
					 repairType CHAR(10),
					 PRIMARY KEY(rID));
					 
CREATE TABLE Request( reqID Numeric NOT NULL,
					  managerID Numeric NOT NULL DEFAULT 0,
					  repairID Numeric NOT NULL DEFAULT 0,
					  requestDate Date NOT NULL,
					  description TEXT,
					  PRIMARY KEY(reqID));
					  
CREATE TABLE Assigned( asgID Numeric NOT NULL,
					   staffID Numeric NOT NULL DEFAULT 0,
					   hotelID Numeric NOT NULL DEFAULT 0,
					   roomNo Numeric NOT NULL DEFAULT 0,
					   PRIMARY KEY(asgID));

-- The below sql statments creates necessary Foreign Key Constraints on the above created tables
ALTER TABLE Hotel
ADD CONSTRAINT managerConstraint
FOREIGN KEY(manager)
REFERENCES Staff(SSN)
ON DELETE SET DEFAULT;
 					
ALTER TABLE Staff
ADD CONSTRAINT employerConstraint
FOREIGN KEY(employerID)
REFERENCES Hotel(hotelID)
ON DELETE CASCADE;

					   
ALTER TABLE Room
ADD CONSTRAINT weakEntityConstraint
FOREIGN KEY(hotelID)
REFERENCES Hotel(hotelID)
ON DELETE CASCADE;

ALTER TABLE Booking
ADD CONSTRAINT bookingCustomerConstraint
FOREIGN KEY(customer)
REFERENCES Customer(customerID)
ON DELETE SET DEFAULT;

ALTER TABLE Booking
ADD CONSTRAINT bookingRoomConstraint
FOREIGN KEY(hotelID, roomNo)
REFERENCES Room(hotelID, roomNo)
ON DELETE SET DEFAULT;	

ALTER TABLE Repair
ADD CONSTRAINT repairMcmpConstraint
FOREIGN KEY(mCompany)
REFERENCES MaintenanceCompany(cmpID)
ON DELETE SET DEFAULT;

ALTER TABLE Repair
ADD CONSTRAINT repairRoomConstraint
FOREIGN KEY(hotelID, roomNo)
REFERENCES Room(hotelID, roomNo)
ON DELETE SET DEFAULT;   

ALTER TABLE Request
ADD CONSTRAINT requestMngrConstraint
FOREIGN KEY(managerID)
REFERENCES Staff(SSN)
ON DELETE SET DEFAULT;

ALTER TABLE Request
ADD CONSTRAINT requestRepairConstraint
FOREIGN KEY(repairID)
REFERENCES Repair(rID)
ON DELETE SET DEFAULT;

ALTER TABLE Assigned
ADD CONSTRAINT assgndStaffConstraint
FOREIGN KEY(staffID)
REFERENCES Staff(SSN)
ON DELETE SET DEFAULT;

ALTER TABLE Assigned
ADD CONSTRAINT assgndRoomConstraint
FOREIGN KEY(hotelID, roomNo)
REFERENCES Room(hotelID, roomNo)
ON DELETE SET DEFAULT; 
-- data copy				 
COPY Hotel(	hotelID,			 
			address,	   
            manager)
FROM 'hotel.csv'
WITH DELIMITER ',';

COPY Staff(SSN, 
			fName, 
			lName, 
			address,
			role,
			employerID)
FROM 'staff.csv'
WITH DELIMITER ',';	

UPDATE hotel 
SET manager = Staff.ssn
FROM Staff
WHERE hotel.hotelID = Staff.employerID AND Staff.role = 'Manager'; 


COPY Room(	hotelID,
		    roomNo,
			roomType)
FROM 'room.csv'
WITH DELIMITER ',';	

COPY Customer(	customerID,
			    fName,
			    lName,
			    Address,
				phNo,
				DOB,
			    gender)
FROM 'customer.csv'
WITH DELIMITER ',';	


COPY MaintenanceCompany(cmpID,
						name,
						address,
						isCertified)
FROM 'maintenanceCompany.csv'
WITH DELIMITER ',';	

COPY Booking( bID,
			  customer,
			  hotelID,
			  roomNo,
			  bookingDate,
			  noOfPeople,
			  price)
FROM 'booking.csv'
WITH DELIMITER ',';	

COPY Repair( rID,
			 hotelID,
			 roomNo,
			 mCompany,
			 repairDate,
			 description,
			 repairType)
FROM 'repair.csv'
WITH DELIMITER ',';	

COPY Request(reqID,
			 managerID,
			 repairID,
			 requestDate,
			 description)
FROM 'request.csv'
WITH DELIMITER ',';		

COPY Assigned(asgID,
			  staffID,
			  hotelID,
			  roomNo)
FROM 'assigned.csv'
WITH DELIMITER ',';		
	