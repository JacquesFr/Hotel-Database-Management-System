/*
 *
 * Database Management Systems
 * Department of Computer Science &amp; Engineering
 * University of California - Riverside
 *
 * Target DBMS: 'Postgres'
 * Jacques Fracchia
 */


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class defines a simple embedded SQL utility class that is designed to
 * work with PostgreSQL JDBC drivers.
 *
 */
public class DBProject {

   // reference to physical database connection.
   private Connection _connection = null;

   // handling the keyboard inputs through a BufferedReader
   // This variable can be global for convenience.
   static BufferedReader in = new BufferedReader(
                                new InputStreamReader(System.in));

   /**
    * Creates a new instance of DBProject
    *
    * @param hostname the MySQL or PostgreSQL server hostname
    * @param database the name of the database
    * @param username the user name used to login to the database
    * @param password the user login password
    * @throws java.sql.SQLException when failed to make a connection.
    */
   public DBProject (String dbname, String dbport, String user, String passwd) throws SQLException {

      System.out.print("Connecting to database...");
      try{
         // constructs the connection URL
         String url = "jdbc:postgresql://localhost:" + dbport + "/" + dbname;
         System.out.println ("Connection URL: " + url + "\n");

         // obtain a physical connection
         this._connection = DriverManager.getConnection(url, user, passwd);
         System.out.println("Done");
      }catch (Exception e){
         System.err.println("Error - Unable to Connect to Database: " + e.getMessage() );
         System.out.println("Make sure you started postgres on this machine");
         System.exit(-1);
      }//end catch
   }//end DBProject

   /**
    * Method to execute an update SQL statement.  Update SQL instructions
    * includes CREATE, INSERT, UPDATE, DELETE, and DROP.
    *
    * @param sql the input SQL string
    * @throws java.sql.SQLException when update failed
    */
   public void executeUpdate (String sql) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the update instruction
      stmt.executeUpdate (sql);

      // close the instruction
      stmt.close ();
   }//end executeUpdate

   /**
    * Method to execute an input query SQL instruction (i.e. SELECT).  This
    * method issues the query to the DBMS and outputs the results to
    * standard out.
    *
    * @param query the input query string
    * @return the number of rows returned
    * @throws java.sql.SQLException when failed to execute the query
    */
   public int executeQuery (String query) throws SQLException {
      // creates a statement object
      Statement stmt = this._connection.createStatement ();

      // issues the query instruction
      ResultSet rs = stmt.executeQuery (query);

      /*
       ** obtains the metadata object for the returned result set.  The metadata
       ** contains row and column info.
       */
      ResultSetMetaData rsmd = rs.getMetaData ();
      int numCol = rsmd.getColumnCount ();
      int rowCount = 0;

      // iterates through the result set and output them to standard out.
      boolean outputHeader = true;
      while (rs.next()){
	 if(outputHeader){
	    for(int i = 1; i <= numCol; i++){
		System.out.print(rsmd.getColumnName(i) + "\t");
	    }
	    System.out.println();
	    outputHeader = false;
	 }
         for (int i=1; i<=numCol; ++i)
            System.out.print (rs.getString (i) + "\t");
         System.out.println ();
         ++rowCount;
      }//end while
      stmt.close ();
      return rowCount;
   }//end executeQuery

   /**
    * Method to close the physical connection if it is open.
    */
   public void cleanup(){
      try{
         if (this._connection != null){
            this._connection.close ();
         }//end if
      }catch (SQLException e){
         // ignored.
      }//end try
   }//end cleanup

   /**
    * The main execution method
    *
    * @param args the command line arguments this inclues the <mysql|pgsql> <login file>
    */
   public static void main (String[] args) {
      if (args.length != 3) {
         System.err.println (
            "Usage: " +
            "java [-classpath <classpath>] " +
            DBProject.class.getName () +
            " <dbname> <port> <user>");
         return;
      }//end if
      
      Greeting();
      DBProject esql = null;
      try{
         // use postgres JDBC driver.
         Class.forName ("org.postgresql.Driver").newInstance ();
         // instantiate the DBProject object and creates a physical
         // connection.
         String dbname = args[0];
         String dbport = args[1];
         String user = args[2];
         esql = new DBProject (dbname, dbport, user, "");

         boolean keepon = true;
         while(keepon) {
            // These are sample SQL statements
				System.out.println("MAIN MENU");
				System.out.println("---------");
				System.out.println("1. Add new customer");
				System.out.println("2. Add new room");
				System.out.println("3. Add new maintenance company");
				System.out.println("4. Add new repair");
				System.out.println("5. Add new Booking"); 
				System.out.println("6. Assign house cleaning staff to a room");
				System.out.println("7. Raise a repair request");
				System.out.println("8. Get number of available rooms");
				System.out.println("9. Get number of booked rooms");
				System.out.println("10. Get hotel bookings for a week");
				System.out.println("11. Get top k rooms with highest price for a date range");
				System.out.println("12. Get top k highest booking price for a customer");
				System.out.println("13. Get customer total cost occurred for a give date range"); 
				System.out.println("14. List the repairs made by maintenance company");
				System.out.println("15. Get top k maintenance companies based on repair count");
				System.out.println("16. Get number of repairs occurred per year for a given hotel room");
				System.out.println("17. < EXIT");

            switch (readChoice()){
				   case 1: addCustomer(esql); break;
				   case 2: addRoom(esql); break;
				   case 3: addMaintenanceCompany(esql); break;
				   case 4: addRepair(esql); break;
				   case 5: bookRoom(esql); break;
				   case 6: assignHouseCleaningToRoom(esql); break;
				   case 7: repairRequest(esql); break;
				   case 8: numberOfAvailableRooms(esql); break;
				   case 9: numberOfBookedRooms(esql); break;
				   case 10: listHotelRoomBookingsForAWeek(esql); break;
				   case 11: topKHighestRoomPriceForADateRange(esql); break;
				   case 12: topKHighestPriceBookingsForACustomer(esql); break;
				   case 13: totalCostForCustomer(esql); break;
				   case 14: listRepairsMade(esql); break;
				   case 15: topKMaintenanceCompany(esql); break;
				   case 16: numberOfRepairsForEachRoomPerYear(esql); break;
				   case 17: keepon = false; break;
				   default : System.out.println("Unrecognized choice!"); break;
            }//end switch
         }//end while
      }catch(Exception e) {
         System.err.println (e.getMessage ());
      }finally{
         // make sure to cleanup the created table and close the connection.
         try{
            if(esql != null) {
               System.out.print("Disconnecting from database...");
               esql.cleanup ();
               System.out.println("Done\n\nBye !");
            }//end if
         }catch (Exception e) {
            // ignored.
         }//end try
      }//end try
   }//end main
   
   public static void Greeting(){
      System.out.println(
         "\n\n*******************************************************\n" +
         "              User Interface      	               \n" +
         "*******************************************************\n");
   }//end Greeting

   /*
    * Reads the users choice given from the keyboard
    * @int
    **/
   public static int readChoice() {
      int input;
      // returns only if a correct value is given.
      do {
         System.out.print("Please make your choice: ");
         try { // read the integer, parse it and break.
            input = Integer.parseInt(in.readLine());
            break;
         }catch (Exception e) {
            System.out.println("Your input is invalid!");
            continue;
         }//end try
      }while (true);
      return input;
   }//end readChoice

   
   public static void addCustomer(DBProject esql){
    try{
        //
      int customerID;
      do{
        System.out.print("Please enter customer ID: ");
        try{
          customerID = Integer.parseInt(in.readLine());
          break;
        }//try
        catch(Exception e){
          system.out.println("Your input is invalid! Must be Integer Type");
          continue;
        }//catch
      }while(true);//do while true
      //
      String first_name;
        do{
            System.out.print("Please enter first name: ");
            try{
                first_name = in.readLine();
                if(first_name.length() <= 0 || first_name.length() > 30){
                    throw new RuntimeException("First Name cannot be NULL or exceed 30 characters");
                }//if
                break;
            }//try
            catch(Exception e){
              System.out.println(e);
              continue;
            }//catch
        }while(true); //do while true 
            
      String last_name;
          do{
              System.out.print("Please enter last name: ");
              try{
                  last_name = in.readLine();
                  if(last_name.length() <= 0 || last_name.length() > 30){
                      throw new RuntimeException("Last Name cannot be NULL or exceed 30 characters");
                  }//if
                  break;
              }//try
              catch(Exception e){
                System.out.println(e);
                continue;
              }//catch
          }while(true); //do while true 

       String address;
          do{
              System.out.print("Please enter address: ");
              try{
                  address = in.readLine();
                  if(address.length() <= 0 ){
                      throw new RuntimeException("Address cannot be NULL ");
                  }//if
                  break;
              }//try
              catch(Exception e){
                System.out.println(e);
                continue;
              }//catch
          }while(true); //do while true 

          int phone_number;
          do{
            System.out.print("Please enter phone number: ");
            try{
              phone_number = Integer.parseInt(in.readLine());
              break;
            }//try
            catch(Exception e){
              system.out.println("Your input is invalid! Must be Integer Type");
              continue;
            }//catch
          }while(true);//do while true
/*
          int dob;
          do{
            System.out.print("Please enter date of birth: ");
            try{
              dob = Integer.parseInt(in.readLine());
              break;
            }//try
            catch(Exception e){
              system.out.println("Your input is invalid! Must be Integer Type");
              continue;
            }//catch
          }while(true);//do while true
*/
          localDate dobDate;
          String dob;
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          do{
              System.out.print("Please enter date of birth (YYYY-MM-DD): ");
              try{
                    dob = in.readLine();
                    dobDate = LocalDate.parse(dob, formatter);
                    break;
              }//try
              catch(Exception e){
                    System.out.println("Your input is invalid! Must be (YYYY-MM-DD) ")
                    continue;
              }
          }while(true);

          String gender;          //'Male', 'Female', 'Other'
          do{
              System.out.print("Please enter gender (Male, Female, Other): ");
              try{
                  gender = in.readLine();
                  if((gender != "Male" && gender != "Female" && gender != "Other") || gender.length() <= 0) {
                      throw new RuntimeException("Gender must be Male, Female, Other and Gender cannot be NULL ");
                  }
                  break;
              }//try
              catch(Exception e){
                System.out.println(e);
                continue;
              }//catch
          }while(true); //do while true 

         // try {
         //    java.time.LocalDate parsedDate = LocalDate.parse(dob, java.time.format.DateTimeFormat.ISO_DATE);
         // } catch (Exception e) {
         // }


         String query = String.format("INSERT INTO Customer (customerID, fName, lName, Address, phNo, DOB, gender) VALUES (" + customerID + "," + "\'"  + first_name +"\', \'" + last_name + "\', \'" + address + "\', " + phone_number + ", \'" + dob + "\', \'" + gender + "\')");
          esql.executeUpdate(query);

         //query = String.format("SELECT c.* FROM Customer c WHERE customerID = %d", customerID);
         //int rowCount = esql.executeQuery(query);
         //System.out.println("total row(s): " + rowCount);


      }catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end addCustomer

   public static void addRoom(DBProject esql){
	  try{
         System.out.print("Please enter hotelID: ");
         int hotel_ID = Integer.parseInt(in.readLine());

         System.out.print("Please enter the room number: ");
         int room_No = Integer.parseInt(in.readLine());
         
         System.out.print("please enter the room type: ");
         String room_Type = in.readLine();

         String query = String.format("INSERT INTO Room (hotelID, roomNo, roomType) VALUES (" + hotel_ID + ", " + room_No + ", \'" + room_Type + "\')");
         esql.executeUpdate(query);
      }//try
      catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end addRoom

   public static void addMaintenanceCompany(DBProject esql){
      // Given maintenance Company details add the maintenance company in the DB
      try{
      System.out.print("Please enter the company ID: ");
      int cmpID = Integer.parseInt(in.readLine());

      System.out.print("Please enter the name: ");
      String name = in.readLine();

      System.out.print("Please enter your address: ");
      String address = in.readLine();

      System.out.print("Is the company Certified? True or False: ");
      String isCertified = in.readLine();

      while((isCertified != "True") || (isCertified != "False")){
        System.out.print("Is the company Certified? True or False: ");
        isCertified = in.readLine();
     } 
         String query = String.format("INSERT INTO MaintenanceCompany (cmpID, name, address, isCertified) VALUES (" + cmpID + ", \'" + name + "\', \'" + address + "\', \'" + isCertified + "\')");
         esql.executeUpdate(query);
      }//try
      catch(Exception e){
         System.err.println (e.getMessage());
      }



   }//end addMaintenanceCompany

   public static void addRepair(DBProject esql){
	  try{
         System.out.print("Please enter repair ID: ");
         int repair_ID = Integer.parseInt(in.readLine()); //rID

         System.out.print("Please enter hotelID: ");
         int hotel_ID = Integer.parseInt(in.readLine());

         System.out.print("Please enter room number: ");
         int room_No = Integer.parseInt(in.readLine());
         
         System.out.print("Please enter maintenance Company (mCompany): ");
         int m_Company = Integer.parseInt(in.readLine());
         
         System.out.print("Please enter repair date: ");
         String repair_Date = in.readLine();

         System.out.print("Please enter description: ");
         String repair_description = in.readLine();

         System.out.print("Please enter repair type: ");
         String repair_Type = in.readLine();

         String query = String.format("INSERT INTO Repair (rID, hotelID, roomNo, mCompany, repairDate, description, repairType) VALUES (" + repair_ID + ", " + hotel_ID + ", " + room_No + ", " + m_Company + ", " + ", '" + repair_Date + "\', \'" + repair_description + "\', \'" + repair_Type + "\')");
         esql.executeUpdate(query);         

      }
      catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end addRepair

   public static void bookRoom(DBProject esql){
	  // Given hotelID, roomNo and customer Name create a booking in the DB 
    try{
      System.out.print("Please enter the Hotel ID: ");
      int hotelID = Integer.parseInt(in.readLine());

      System.out.print("Please enter the room number: ");
      int roomNo = Integer.parseInt(in.readLine());

      System.out.print("Please enter the Number of people: ");
      int noOfPeople = Integer.parseInt(in.readLine());

      System.out.print("Please enter the date: ");
      String bookingDate = in.readLine();

      System.out.print("Please enter the price: ");
      int price = Integer.parseInt(in.readLine());

      System.out.print("Please enter the first name: ");
      String fName = in.readLine();

      System.out.print("Please enter the last name: ");
      String lName = in.readLine();

      String query = "SELECT MAX(bID) FROM Booking";
      Statement statemnt = esql._connection.createStatement();
      ResultSet resltst = statemnt.executeQuery(query);
      resltst.next();
      int bID = resltst.getInt(1) + 1;

      query = "SELECT customerID FROM Customer WHERE fName = \'" + fName + "\' AND lName = \'" + lName + "\')";
      statemnt = esql._connection.createStatement();
      resltst = statemnt.executeQuery(query);
      int customer = resltst.getInt(1);

      query = String.format("INSERT INTO Booking (bID, customer, hotelID, roomNo, bookingDate, noOfPeople, price) VALUES (" + bID + ", " + customer + ", " + hotelID + ", " + roomNo + ", \'" + bookingDate + "\', " + ", " + noOfPeople + ", " + price + ")");
       esql.executeUpdate(query);         

      }
      catch(Exception e){
         System.err.println (e.getMessage());
      }


   }//end bookRoom

   public static void assignHouseCleaningToRoom(DBProject esql){
	  
    try{
      String query = "SELECT MAX(asgID) FROM Assigned";
      Statement statemnt = esql._connection.createStatement();
      ResultSet resltst = statemnt.executeQuery(query);
      resltst.next();
      int asgID_i = resltst.getInt(1) + 1;
      //System.out.println(asgID_i);

      System.out.print("Please enter the Staff SSN: ");
      int staff_ssn = Integer.parseInt(in.readLine());

      System.out.print("Please enter the Hotel ID: ");
      int hotel_ID = Integer.parseInt(in.readLine());

      System.out.print("Please enter the Room Number: ");
      int roomNumb = Integer.parseInt(in.readLine());

      query = String.format("INSERT INTO Assigned (asgID, staffID, hotelID, roomNo) VALUES (" + asgID_i + ", " + staff_ssn + ", " + hotel_ID + ", " + roomNumb + ")");
      esql.executeUpdate(query);         

    }
      catch(Exception e){
         System.err.println (e.getMessage());
      }

   }//end assignHouseCleaningToRoom
   
   public static void repairRequest(DBProject esql){
	  // Given a hotelID, Staff SSN, roomNo, repairID , date create a repair request in the DB
      try{
      System.out.print("Please enter the Manager ID: ");
      int managerID = Integer.parseInt(in.readLine());

      System.out.print("Please enter the Hotel ID: ");
      int hotelID = Integer.parseInt(in.readLine());

      System.out.print("Please enter the Room Number: ");
      int roomNo = Integer.parseInt(in.readLine());

      System.out.print("Please enter the repairID: ");
      int repairID = Integer.parseInt(in.readLine());

      System.out.print("Please enter a date: ");
      String requestDate = in.readLine();

      System.out.print("Please enter a description: ");
      String description = in.readLine();

      String query = String.format("SELECT MAX(req.reqID) FROM Repair rep, Request req WHERE rep.hotelID = "+ hotelID + " AND rep.rID = " + repairID + " AND req.repairID = " + repairID + " AND req.managerID = "+ managerID + ")");
      Statement statemnt = esql._connection.createStatement();
      ResultSet resltst = statemnt.executeQuery(query);
      resltst.next();
      int reqID = resltst.getInt(1) + 1;

      query = String.format("INSERT INTO Request (reqID, managerID, repairID, requestDate, description) VALUES (" + reqID + ", " + managerID + ", " + repairID + ", \'" + requestDate + "\', \'" + description + "\')");
      esql.executeUpdate(query);         

    }
      catch(Exception e){
         System.err.println (e.getMessage());
      }
   }//end repairRequest
   
   public static void numberOfAvailableRooms(DBProject esql){
	  // Given a hotelID, get the count of rooms available 
    try{
      System.out.print("Please enter the Hotel ID: ");
      int hotel_id = Integer.parseInt(in.readLine());

      String query = String.format("SELECT COUNT(*) FROM Room r, Booking b WHERE r.hotelID= " + hotel_id +" AND b.hotelID = " + hotel_id +" AND r.roomNo NOT IN (SELECT r.roomNo FROM Booking b WHERE r.roomNo = b.roomNo); ");
      esql.executeUpdate(query);         

    }
      catch(Exception e){
         System.err.println (e.getMessage());
      }


   }//end numberOfAvailableRooms
   
   public static void numberOfBookedRooms(DBProject esql){
    // Given a hotelID, get the count of rooms booked
      // Your code goes here.
      // ...
      try{
        System.out.print("Please enter the Hotel ID: ");
        int hotel_id = Integer.parseInt(in.readLine());

        String query = String.format("SELECT COUNT(b.roomNo) FROM Booking b WHERE b.hotelID = " + hotel_id + "");
        esql.executeUpdate(query);     

      }
     catch(Exception e){
         System.err.println (e.getMessage());
      } 
   }//end numberOfBookedRooms
   
   public static void listHotelRoomBookingsForAWeek(DBProject esql){
    // Given a hotelID, date - list all the rooms available for a week(including the input date) 
      // Your code goes here.
      // ...
      // ...
    try{
      System.out.print("Please enter the Hotel ID: ");
      int hotel_id = Integer.parseInt(in.readLine());

      System.out.print("Please enter a Booking Date: ");
      String inputDate = in.readLine();

      String query = String.format("SELECT b.roomNo FROM Booking b WHERE b.hotelID = " + hotel_id + " AND (b.bookingDate BETWEEN \'" + inputDate + "\' AND  DATE \'" + inputDate + "\' + INTERVAL '1 week' ) GROUP BY b.roomNo");
      esql.executeUpdate(query);     

    }
    catch(Exception e){
         System.err.println (e.getMessage());
      } 
   }//end listHotelRoomBookingsForAWeek
   
   public static void topKHighestRoomPriceForADateRange(DBProject esql){
    // List Top K Rooms with the highest price for a given date range
      // Your code goes here.
      // ...
    try{

      System.out.print("Please enter the Number of highest priced rooms: ");
      int k = Integer.parseInt(in.readLine());

      System.out.print("Please enter a Start Date: ");
      String startDate = in.readLine();

      System.out.print("Please enter a end Date: ");
      String endDate = in.readLine();

      String query = String.format("SELECT r.roomNo, b.price FROM Room r, Booking b WHERE r.roomNo = b.roomNo AND b.bookingDate BETWEEN  \'" + startDate + "\' AND  \'" + endDate + "\' ORDER BY b.price DESC limit " + k + " ");
      esql.executeUpdate(query);     

      }
      catch(Exception e){
         System.err.println (e.getMessage());
      } 
   }//end topKHighestRoomPriceForADateRange
   
   public static void topKHighestPriceBookingsForACustomer(DBProject esql){
    // Given a customer Name, List Top K highest booking price for a customer 
      // Your code goes here.
      // ...
      // ...
    try{
      System.out.print("Please enter first name: ");
      String first_name = in.readLine();

      System.out.print("Please enter last name: ");
      String last_name = in.readLine();
      
      System.out.print("Please enter the Number of highest booking prices you want to see for a customer: ");
      int k = Integer.parseInt(in.readLine());

      String query = String.format("SELECT b.price FROM Customer c, Booking b WHERE c.fName = \'" + first_name + "\' AND c.lName = \'" + last_name + "\' AND c.customerID = b.customer ORDER BY b.price DESC limit " + k + " ");
      esql.executeUpdate(query);     

    }
    catch(Exception e){
      System.err.println (e.getMessage());
    }
   }//end topKHighestPriceBookingsForACustomer
   
   public static void totalCostForCustomer(DBProject esql){
    // Given a hotelID, customer Name and date range get the total cost incurred by the customer
      // Your code goes here.
      // ...
    try{
      System.out.print("Please enter the Hotel ID: ");
      int hotel_id = Integer.parseInt(in.readLine());

      System.out.print("Please enter first name: ");
      String first_name = in.readLine();

      System.out.print("Please enter last name: ");
      String last_name = in.readLine();

      System.out.print("Please enter a Start Date: ");
      String startDate = in.readLine();

      System.out.print("Please enter a end Date: ");
      String endDate = in.readLine();

      String query = String.format("SELECT SUM(b.price) FROM Customer c, Booking b WHERE b.hotelID = " + hotel_id + " AND c.fName = \'" + first_name + "\' AND c.lName = \'" + last_name + "\' AND c.customerID = b.customer AND b.bookingDate BETWEEN  \'" + startDate + "\' AND  \'" + endDate + "\' ");
      esql.executeUpdate(query);     

    }

    catch(Exception e){
         System.err.println (e.getMessage());
    }
   }//end totalCostForCustomer
   
   public static void listRepairsMade(DBProject esql){
    // Given a Maintenance company name list all the repairs along with repairType, hotelID and roomNo
      // Your code goes here.
      // ...
    try{
      System.out.print("Please enter a Maintenance Company Name: ");
      String name = in.readLine();
      String query = String.format("SELECT r.rID, r.repairType, r.hotelID, r.roomNo, r.description FROM Repair r, MaintenanceCompany m WHERE r.mCompany = m.cmpID AND m.name = \'" + name + "\'");
      esql.executeUpdate(query);     
    }//end listRepairsMade
    catch(Exception e){
      System.err.println (e.getMessage());
    }
}

   public static void topKMaintenanceCompany(DBProject esql){
    // List Top K Maintenance Company Names based on total repair count (descending order)
      // Your code goes here.
      // ...
      try{
        System.out.print("Please enter the Number of top companies you want to see: ");
        int k = Integer.parseInt(in.readLine());

        String query = String.format("SELECT m.name FROM MaintenanceCompany m, Repair r WHERE m.cmpID = r.mCompany ORDER BY COUNT(r.rID) DESC limit " + k + "  ");
        esql.executeUpdate(query);     

      }
      catch(Exception e){
        System.err.println (e.getMessage());
    }
   }//end topKMaintenanceCompany
   
   public static void numberOfRepairsForEachRoomPerYear(DBProject esql){
    // Given a hotelID, roomNo, get the count of repairs per year
      // Your code goes here.
      // ...
    try{
      System.out.print("Please enter the Hotel ID: ");
      int hotel_id = Integer.parseInt(in.readLine());

      System.out.print("Please enter the Room Number: ");
      int roomNumb = Integer.parseInt(in.readLine());

      String query = String.format("SELECT COUNT(*), DATE_PART('year', r.repairDate) FROM Repair r WHERE r.hotelID = " + hotel_id + " AND r.roomNo = "+ roomNumb +" GROUP BY DATE_PART('year', r.repairDate)");
      esql.executeUpdate(query);     

    }
    catch(Exception e){
      System.err.println (e.getMessage());
    }

   }//end listRepairsMade

}//end DBProject
