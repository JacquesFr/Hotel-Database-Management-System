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
