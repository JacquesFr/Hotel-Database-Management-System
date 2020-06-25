# Hotel-Database-Management-System

In this project, you will model and build a hotel database management system. This system is to
track information about different hotels, such as the rooms they own, the maintenance of those
rooms, the managers they employ, the bookings their customers make, and information about the
customers that use the hotel services.

The project comprises three phases. In Phase 1, you will perform requirement analysis using the
ER-model, given a set of requirements for your database. Please note that you are being given only
a set of requirements, not the entities or actual set of attributes that go with each entity. In Phase 2
of this project, you will design a logical model of your database using the ER-model. In Phase 3,
you will implement the database, and exercise your design using queries.
You will work on Phases 1 and 2 alone. Phase 3 is to be completed with a partner. Submit all your
files via iLearn on the due date for each phase. Each submission should be a single zip file. Make
sure that everything is included in your submission and it can be uncompressed without any errors.

Phase 1: ER Design
From the description given, first identify the entity sets in your model, and the attributes for each
entity set, as required by the specifications and queries. Next, produce an ER-diagram that will
serve as the foundation for the rest of the design. You can create this diagram using your favorite
diagram editing software. Your model should use only the basic ER model features, and include
entities, attributes, and relationships. Ensure that you capture all participation and key constraints.
Also, make sure to include additional documentation describing the assumptions that you made
during the design process

Phase 2: Relational Schema Design
This final ER-diagram will be the starting point for the second phase, which involves the creation of the relational schema.
Your task in this phase will be to translate the provided ER design to a PostgreSQL relational
database schema. The database schema will be in a form of a single executable SQL script (*.sql
file with SQL statements).

Phase 3: Implementation
You will work in pairs on this part of the project. Your tasks in this phase will be:
● Develop a client application using the Java Database Connector (JDBC) for psql.
● Use the client application to support specific functionality and queries for your online
booking system.
In this phase, You will use this schema to test and demo your application to us. We will also give you
a collection of .csv files containing data that are compatible with the provided relational schema.
You will have to create your own .sql scripts to insert the data from the given .csv files into the
database.

Requirements Analysis
You are to design a hotel management database that serves the needs of hotel managers and
customers. Each of these types of individuals needs access to the following information:

Hotel Management:
1. Given a hotel ID, list a given room’s bookings for the week.
2. For each hotel ID, get highest price among all booked rooms for a given data range
3. Given a hotel ID and a date, get (1) the number of rooms still available and (2) number of
rooms booked
4. Given a hotel ID and date, get a list of customers who made bookings for that date
5. Given a booking ID, retrieve information about the customer (First & Last Name, Gender,
Date of birth, Address) who made the booking
6. Given a hotel ID and customer ID, get the total cost incurred by the customer for a given
data range.

Hotel Staff:
1. Given a Hotel ID, list all details pertaining to staff, including their positions/roles (Hotel
Managers, Receptionists, House cleaning, etc.) who are employed by that hotel
2. Hotel Managers may make maintenance/room repair requests, which will be handled by a
maintenance company. The maintenance company must be certified to handle that
specific type of repair. Given a manager ID list the hotel ID, room number and date of
request.
3. Given a hotel ID and House cleaning staff ID list all the rooms he/she is assigned to

Customers:
1. Given customer ID, list all the rooms previously booked by that customer in all the hotels
2. Given a price and a data range, list all the available rooms in all hotels for that date range,
and price at or below the specified price.
3. Given a customer ID give the hotel ID where the per-day cost incurred by that customer
was the highest.

Maintenance Companies:
1. Given a maintenance company ID, list the type of repair, the hotel, and the room number
for all repairs made by that company
2. For a given date range, list all the requests received by the maintenance company from a
particular hotel manager ID
___________________________________________________________________________

Project Report


Breakdown of work

   Ashley McDaniel and Jacques Fracchia began by writing each of the SQL statements found in /sql/queries.sql. This file does not compile but was merely for us to have a proper sql format to write all of our queries. We divided up the sixteen cases evenly, by doing every other one and checking one another after we finished a query. If the other teammate got stuck we would both brainstorm how to solve the problem. 
    Once we felt comfortable with our SQL queries, we began writing the java code. We coded the first case together, neither of us had written java before so we needed some pair programming help. After we were able to plug in all of our user input into the sql statement and worked through all of the bugs, we then divided up the sixteen cases once again.
    Ashley was working from the first case to case eight, while Jacques worked from case sixteen to case eight. Once we completed writing basic System.out.print and in.readLine() then plugging in the values into our sql statements we started testing all of our functions. 
    To do this, Ashley and Jacques then reversed our order. Jacques began testing cases one through eight while Ashley tested cases sixteen through eight. This was beneficial because we had a chance to double check one another’s code, and to get an understanding on how the code worked. We tested the code by plugging in useful inputs from looking at the csv files, then running the postgreSQL terminal to run a quick SQL statement to view the table. 
    For example, to see from case 1 that a customer was in fact added into the table we ran a quick SQL statement in the terminal: “SELECT * FROM customer ORDER BY customerID DESC;”. This allowed us to see the new customer be created with a new customerID at the top of the table. 
    After we felt comfortable with all of our tests we began looking at the validation code. We, once again, worked on the first case together to get an idea on how to go about the validation in order to make a better user experience and to make sure the user inputted the correct values.
    Once we had a basis down from looking at Case 1, we divided up the sixteen cases once again and began writing the validation for each case. You can read how we went about the validations in our ‘Error Checking’ paragraph below this document.
    Overall it was a fluid and productive experience being in a team with Ashley and Jacques. The tasks were all split evenly and we always made sure we were working on the same task, just different cases, before moving onto the next part of creating the code. 

Assumptions
    There are a few essential assumptions we have for this project. The most important ones are that the user does not throw in total garbage values. We check that some attributes cannot be NULL and there are some values that have character limits, these are described in Error Checking. We prevented the user from typing in garbage values for the dates and make sure it is in the correct yyyy-mm-dd format. However, we assume that the user types in a price with x.xx format.
    We only check that price was not null, but we assume the user is smart enough to not add a third decimal value or only leave one decimal value for the price. As long as if the inputs inserted are in context of a hotel business with correct room number hotel ID the user should have an excellent experience!

Explanations of Cases 1-16 

  Case 1:
    
   The first function addCustomer asks the user for their customer’s ID, first name, last name, address, phone number, date of birth, and gender. In the SQL query we inserted the information that we collected from the user into the correct place in the table. SQL Query: INSERT INTO Customer (customerID, fName, lName, Address, phNo, DOB, gender) VALUES (" + customerID + "," + "\'"  + first_name +"\', \'" + last_name + "\', \'" + address + "\', " + phone_number + ", \'" + dob + "\', \'" + gender + "\').

  Case 2:
      
   The second function addRoom asks the user for the hotel ID, room number, and room type. In the SQL query we inserted the information that we collected from the user accordingly into its correct place in the table. SQL Query: INSERT INTO Room (hotelID, roomNo, roomType) VALUES (" + hotel_ID + ", " + room_No + ", \'" + room_Type + "\').

  Case 3:
    
   The third function addMaintenanceCompany asks the user for the company ID, the company’s name, address of the Maintenance company, and if the maintenance company is certified. In the sql query we inserted the information that we collected from the user accordingly into its correct place in the table. SQL Query: INSERT INTO MaintenanceCompany (cmpID, name, address, isCertified) VALUES (" + cmpID + ", \'" + name + "\', \'" + address + "\', \'" + isCertified + "\').

  Case 4:
  
   The fourth function addRepair asks the user for the repair ID, hotel ID, room number, maintenance company ID (mCompany), repair Date, repair description, and repair type. In the sql query we inserted the information that we collected from the user accordingly into its correct place in the table. The SQL Query: INSERT INTO Repair (rID, hotelID, roomNo, mCompany, repairDate, description, repairType) VALUES (" + repair_ID + ", " + hotel_ID + ", " + room_No + ", " + m_Company + ", \'" + repair_Date + "\', \'" + repair_description + "\', \'" + repair_Type + "\').

  Case 5: 
    
   The fifth function bookRoom asks the user for the hotel ID, room number, number of people, booking date, price, first name, last name. In the first query we obtain the maximum value of booking ID and increment it by one in order to make a new booking ID for our new booking. SQL Query: (SELECT MAX(bID) FROM Booking). Then for the next query we make we are getting the customer first and last name given the customer ID.  SQL Query: (SELECT customerID FROM Customer WHERE fName = \'" + fName + "\' AND lName = \'" + lName + "\')". 

  Case 6:
    
   The sixth function assignHouseCleaningToRoom asks the user to input the staff_ssn, hotel ID, room number,  In the first SQL query we select the assigned ID and then increment the ID by one in order to obtain the new assigned ID. The first SQL query: SELECT MAX(asgID) FROM Assigned. In the second query we then insert the found assigned ID and the user’s input into the corresponding tables. The second SQL query is: INSERT INTO Assigned (asgID, staffID, hotelID, roomNo) VALUES (" + asgID_i + ", " + staff_ssn + ", " + hotel_ID + ", " + roomNumb + ").

  Case 7: 
      
   The seventh function repairRequest asks the user to input the manager ID, hotel ID, room number, repair ID, request Date, and description. In the first SQL query we find the maximum value of request ID and then increment it by one in order to make the new request ID. We also check that the repair hotel ID is equal to the request hotel ID. The first SQL query: SELECT MAX(req.reqID) FROM Repair rep, Request req WHERE rep.hotelID = " + hotelID + " ". In the second SQL query we insert the users input and found request ID into the request table. The second SQL query: "INSERT INTO Request (reqID, managerID, repairID, requestDate, description) VALUES (" + reqID + ", " + managerID + ", " + repairID + ", \'" + requestDate + "\', \'" + description + "\')".

  Case 8:
      
   The 8th function numberOfAvailableRooms asks the user to input the hotel ID.Then given the hotel ID we hotel ID of the Room and the hotel ID from the Booking are the same. Then we select the rooms that are booked and then return the rooms not in booked giving us the number of Available Rooms. The SQL Query: SELECT COUNT(*) FROM Room r, Booking b WHERE r.hotelID= " + hotel_id +" AND b.hotelID = " + hotel_id +" AND r.roomNo NOT IN (SELECT r.roomNo FROM Booking b WHERE r.roomNo = b.roomNo);. 

  Case 9:
    
   The 9th function numberOfBookedRooms asks the user for the the hotel ID. In the query we find the booking hotel ID given the users input. In the SQL query given an hotel ID we find the matching booking with the same hotel ID and select the count of booked rooms to get the number of booked rooms. The SQL query: SELECT COUNT(b.roomNo) FROM Booking b WHERE b.hotelID = " + hotel_id + "".

  Case 10:
      
   The tenth function listHotelRoomBookingsForAWeek which asks the user for the hotel ID, and the input Date. Then we get the booking dates from the input date to 1 week from the input date using between and interval. Given a hotel ID we find the booking with the same hotel ID for the given time interval and group these by the booking’s room numbers. The SQL query: SELECT b.roomNo FROM Booking b WHERE b.hotelID = " + hotel_id + " AND (b.bookingDate BETWEEN \'" + inputDate + "\' AND  DATE \'" + inputDate + "\' + INTERVAL '1 week' ) GROUP BY b.roomNo

  Case 11:
      
   The eleventh function topKHighestRoomPriceForADateRange asks the user for a limit k of the highest priced rooms they wanted to view, a start date, and end date. Then in the query we verify the room roomNumber and booking roomNumbers are the same in a given date and select the room number and its booking price limited by the top k users input. The SQL Query: SELECT r.roomNo, b.price FROM Room r, Booking b WHERE r.roomNo = b.roomNo AND b.bookingDate BETWEEN  \'" + startDate + "\' AND  \'" + endDate + "\' ORDER BY b.price DESC limit " + k + ".

  Case 12:
   
   The twelfth function topKHighestPriceBookingsForACustomer asks the user to input the first name, last name, top k limit of highest priced bookings. In the query we verify the customers first and last name and that the customers ID matches the booking ID and then we find booking price in descending order limited by the input k. The SQL Query: SELECT b.price FROM Customer c, Booking b WHERE c.fName = \'" + first_name + "\' AND c.lName = \'" + last_name + "\' AND c.customerID = b.customer ORDER BY b.price DESC limit " + k + ".

  Case 13:
   
   The thirteenth function totalCostForCustomer asks the user for the following values: hotel ID, the customers first and last name, the starting date they booked the room and the ending date they booked the room. Our SQL statement then sums up the price from booking for the given hotel id of the given date range. The SQL statement: SELECT SUM(b.price) FROM Customer c, Booking b WHERE b.hotelID = " + hotel_id + " AND c.fName = \'" + first_name + "\' AND c.lName = \'" + last_name + "\' AND c.customerID = b.customer AND b.bookingDate BETWEEN  \'" + startDate + "\' AND  \'" + endDate + "\' ".

  Case 14:
   
   The fourteenth function listRepairsMade asks the user to enter a maintenance company name. After making sure the name is not Null and less than 30 characters we execute the sql statement. The sql statement then selects the repair ID, repair type, hotel ID room number and description from the maintenance company table that holds their inputted name, and makes sure that the repair rows selected are from the requested maintenance company name. This is done by ensuring the maintenance company ID is the same as the maintenance company ID that performed the repair. The SQL statement: SELECT r.rID, r.repairType, r.hotelID, r.roomNo, r.description FROM Repair r, MaintenanceCompany m WHERE r.mCompany = m.cmpID AND m.name = \'" + name + "\'".

  Case 15:
   
   The fifthteenth function topKMaintenanceCompany simply asks the user to enter the number of top companies they want to see. After making sure they did not plug in a NULL value we run the sql statement that orders the maintenance company by the number of repairs they have done, then we limit the top rows by k and display the maintenance company names. This efficiently gives us the top k maintenance company names who have the most repairs. The SQL Query: SELECT m.name FROM MaintenanceCompany m, Repair r WHERE m.cmpID = r.mCompany GROUP BY m.name ORDER BY COUNT(r.rID) DESC limit " + k + "”.

  Case 16:
    
   The sixteenth function numberOfRepairsForEachRoomPerYear asks the user to enter a hotel ID and a room number. After checking that the variables were inputted correctly we then ran the sql query that counted all of the repairs that occurred for each year for a given hotel ID and room number. The SQL query: SELECT COUNT(*), DATE_PART('year', r.repairDate) FROM Repair r WHERE r.hotelID = " + hotel_id + " AND r.roomNo = "+ roomNumb +" GROUP BY DATE_PART('year', r.repairDate).

Challenges

  There were a few challenges we had. The first and obvious one was neither of us had coded in java, except for the small lab we had earlier in the quarter. This was not a difficult challenge, however there was a little bit of a learning curve, and a few beginner mistakes every now and then. Another challenge we had was that we originally tried to just go straight into the java code. However, once we got to inserting the SQL statements it became very difficult to write perfect SQL statements in one line in java.
  We paused the java coding and wrote a template of SQL statements that we could then copy and paste into java instead of having to write them from scratch in the java template. Another challenge we had was thinking out all of the validation checks we needed to have for the user. This honestly took about a third of the time for the entire project. Finding out how to make sure the user types in the dates properly, all of the special types such as boolean and gender type was difficult. 
  Apart from these challenges, everything was easily overcome with collaboration between the two of us and lots of trial and error. 

Error Checking

  If the customer information is not entered correctly then a RuntimeException error was thrown with a description of the error. Values that require to not be NULL will throw an exception, along with if their length was larger than the SQL table allocates.  
  For customer ID, phone number, company ID, repair ID  we checked to make sure it is not null, and the input is an integer type. For the first name, last name, maintenance company name, room type, repair type,  we checked that the string was not null and the correct amount of characters as given in the create.sql. For address, and repair description it could be null so we did not check it as it is optional for the user to enter. 
  Date of Birth, repair date, and the booking date used the function DateTimeFormatter function that is using the java.time.LocalDate library, and the java.time.format.DateTimeFormatter library which made sure that the date is in the form YYYY-MM-DD. Gender is checked to make sure that it is equal to either male, female, or other, else the user is asked to enter the gender again.
  For isCertified asking whether the maintenance company is certified we prompt the user to enter true or false and verify if the user entered true or false else a runtimeexception error is thrown. 


