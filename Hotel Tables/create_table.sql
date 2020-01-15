DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer (CustomerID CHAR(20) NOT NULL,
					   fName CHAR(20),
					   lName CHAR(20),
					   Address CHAR(20),
					   phNo INTEGER,
					   DOB CHAR(20),
					   Gender CHAR(20),
					   PRIMARY KEY(CustomerID));


DROP TABLE IF EXISTS Staff;
CREATE TABLE Staff (SSN CHAR(20) NOT NULL,
					Hotel_ID CHAR(20) NOT NULL,
					fName CHAR(20),
					lName CHAR(20), 
					Address CHAR(20),
					Role CHAR(20),
					PRIMARY KEY(SSN, Hotel_ID)
					FOREIGN KEY(Hotel_ID) REFERENCES Hotel(Hotel_ID));

DROP TABLE IF EXISTS Hotel;
CREATE TABLE Hotel (Hotel_ID CHAR(20) NOT NULL,
					Address CHAR(20),
					SSN CHAR(20) NOT NULL,
					PRIMARY KEY(Hotel_ID, SSN)
					FOREIGN KEY(SSN) REFERENCES Staff(SSN));

ALTER TABLE Staff
ADD CONSTRAINT Staff_AT FOREIGN KEY (Hotel_ID) REFERENCES Hotel(Hotel_ID);

ALTER TABLE Hotel
ADD CONSTRAINT Hotel_AT FOREIGN KEY (SSN) REFERENCES Staff(SSN);

DROP TABLE IF EXISTS Room;
CREATE TABLE Room (RoomNo CHAR(20) NOT NULL,
				   Type CHAR(20),
				   Hotel_ID CHAR(20) NOT NULL,
				   PRIMARY KEY(RoomNo, Hotel_ID)
				   FOREIGN KEY(Hotel_ID) REFERENCES Hotel(Hotel_ID),
				   ON DELETE CASCADE);
					

DROP TABLE IF EXISTS Maintenance_Company;
CREATE TABLE Maintenance_Company (cmpID CHAR(20) NOT NULL,
								  Name CHAR(20),
								  Address CHAR(20),
								  Certified? CHAR(20),
								  PRIMARY KEY(cmpID));



DROP TABLE IF EXISTS Booking;
CREATE TABLE Booking (CustomerID CHAR(20) NOT NULL,
					  RoomNo CHAR(20) NOT NULL,
					  numPeople INTEGER,
					  dates CHAR(20), 
					  price INTEGER,
					  PRIMARY KEY(CustomerID, RoomNo),
					  FOREIGN KEY(CustomerID) REFERENCES Customer(CustomerID),
					  FOREIGN KEY(RoomNo) REFERENCES Room(RoomNo));

DROP TABLE IF EXISTS Repair;
CREATE TABLE Repair (RoomNo CHAR(20) NOT NULL,
					 cmpID CHAR(20) NOT NULL,
					 dates CHAR(20),
					 descrption CHAR(20),
					 repair_type CHAR(20),
					 PRIMARY KEY(RoomNo, cmpID),
					 FOREIGN KEY(RoomNo) REFERENCES Room(RoomNo),
					 FOREIGN KEY(cmpID) REFERENCES Maintenance_Company(cmpID));

DROP TABLE IF EXISTS Requests;
CREATE TABLE Requests (RoomNo CHAR(20) NOT NULL,
					   cmpID CHAR(20) NOT NULL,
					   SSN CHAR(20) NOT NULL,
					   dates CHAR(20),
					   description CHAR(20),
					   PRIMARY KEY(RoomNo, cmpID),
					   FOREIGN KEY(RoomNo) REFERENCES Room(RoomNo),
					   FOREIGN KEY(cmpID) REFERENCES Maintenance_Company(cmpID),
					   FOREIGN KEY(SSN) REFERENCES Staff(SSN));

DROP TABLE IF EXISTS Assigned;
CREATE TABLE Assigned (RoomNo CHAR(20) NOT NULL,
					   SSN CHAR(20) NOT NULL,
					   PRIMARY KEY(RoomNo, SSN),
					   FOREIGN KEY(RoomNo) REFERENCES Room(RoomNo),
					   FOREIGN KEY(SSN) REFERENCES Staff(SSN));

DROP TABLE IF EXISTS Manages;
CREATE TABLE Manages (SSN CHAR(20) NOT NULL,
					  Hotel_ID CHAR(20) NOT NULL,
					  PRIMARY KEY(SSN, Hotel_ID),
					  FOREIGN KEY(Hotel_ID) REFERENCES Hotel(Hotel_ID)
					  FOREIGN KEY(SSN) REFERENCES Staff(SSN));


