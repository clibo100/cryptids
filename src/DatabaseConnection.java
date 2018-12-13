import dnl.utils.text.table.TextTable;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

@SuppressWarnings({"Duplicates", "SqlDialectInspection", "SqlNoDataSourceInspection", "WrapperTypeMayBePrimitive", "unused"})
class DatabaseConnection {

    private static Connection conn;
    private static PrintWriter writer;

    DatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptiddb", "tester", "tester");
            conn.setAutoCommit(false);
            FileWriter fileWriter = new FileWriter("log.txt", true);
            writer = new PrintWriter(fileWriter);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    Connection getConn() {
        return conn;
    }

    void closeWriter()
    {
        writer.close();
    }

    void exportDatabase() throws SQLException, IOException {
        String names = ("Cryptid ID, " + "Name, " + "Description, " + "Weight, " + "Height, " + "Biome,\n");

        FileWriter fileWriter = new FileWriter("cryptids.csv", false);
        PrintWriter writer = new PrintWriter(fileWriter);

        writer.print(names);

        PreparedStatement getPS = conn.prepareStatement("SELECT * FROM cryptid");
        ResultSet getRS = getPS.executeQuery();

        while (getRS.next())
        {
            writer.print(getRS.getInt(1) + ", "
                    + getRS.getString(2) + ", "
                    + getRS.getString(3) + ", "
                    + getRS.getFloat(4) + ", "
                    + getRS.getFloat(5) + ", "
                    + getRS.getString(6) + "\n");
        }
        writer.close();

        names = ("Viewer ID, " + "Name, " + "Location, " + "Age, " + "Sightings, " + "Credentials, " + "Publications,\n");

        fileWriter = new FileWriter("viewers.csv", false);
        writer = new PrintWriter(fileWriter);

        writer.print(names);

        getPS = conn.prepareStatement("SELECT * FROM viewer");
        getRS = getPS.executeQuery();

        while (getRS.next())
        {
            writer.print(getRS.getInt(1) + ", "
                    + getRS.getString(2) + ", "
                    + getRS.getString(3) + ", "
                    + getRS.getInt(4) + ", "
                    + getRS.getInt(5) + ", "
                    + getRS.getString(6) + ", "
                    + getRS.getInt(7) + "\n");
        }
        writer.close();

        names = ("Evidence ID, " + "CID, " + "Date, " + "Medium, " + "Location,\n");

        fileWriter = new FileWriter("evidence.csv", false);
        writer = new PrintWriter(fileWriter);

        writer.print(names);

        getPS = conn.prepareStatement("SELECT * FROM evidence");
        getRS = getPS.executeQuery();

        while (getRS.next())
        {
            writer.print(getRS.getInt(1) + ", "
                    + getRS.getInt(2) + ", "
                    + getRS.getDate(3) + ", "
                    + getRS.getString(4) + ", "
                    + getRS.getString(5) + "\n");
        }
        writer.close();

        names = ("Folklore ID, " + "CID, " + "Folklore, " + "Year, " + "Location,\n");

        fileWriter = new FileWriter("folklore.csv", false);
        writer = new PrintWriter(fileWriter);

        writer.print(names);

        getPS = conn.prepareStatement("SELECT * FROM folklore");
        getRS = getPS.executeQuery();

        while (getRS.next())
        {
            writer.print(getRS.getInt(1) + ", "
                    + getRS.getInt(2) + ", "
                    + getRS.getString(3) + ", "
                    + getRS.getInt(4) + ", "
                    + getRS.getString(5) + "\n");
        }
        writer.close();

        names = ("Media ID, " + "CID, " + "Title, " + "Year, " + "Format, " + "Rating,\n");

        fileWriter = new FileWriter("media.csv", false);
        writer = new PrintWriter(fileWriter);

        writer.print(names);

        getPS = conn.prepareStatement("SELECT * FROM media");
        getRS = getPS.executeQuery();

        while (getRS.next())
        {
            writer.print(getRS.getInt(1) + ", "
                    + getRS.getInt(2) + ","
                    + "\"" + getRS.getString(3) + "\"" + ", "
                    + getRS.getInt(4) + ", "
                    + getRS.getString(5) + ", "
                    + getRS.getInt(6) + "\n");
        }
        writer.close();


        names = ("Publication ID, " + "Viewer ID, " + "Publication, " + "Year, " + "Publisher,\n");

        fileWriter = new FileWriter("publications.csv", false);
        writer = new PrintWriter(fileWriter);

        writer.print(names);

        getPS = conn.prepareStatement("SELECT * FROM publications");
        getRS = getPS.executeQuery();

        while (getRS.next())
        {
            writer.print(getRS.getInt(1) + ", "
                    + getRS.getInt(2) + ", "
                    + getRS.getString(3) + ", "
                    + getRS.getInt(4) + ", "
                    + getRS.getString(5) + "\n");
        }
        writer.close();

        names = ("Sighting ID, " + "CID, " + "Latitude, " + "Longitude, " + "Date, " + "Viewer ID,\n");

        fileWriter = new FileWriter("sightings.csv", false);
        writer = new PrintWriter(fileWriter);

        writer.print(names);

        getPS = conn.prepareStatement("SELECT * FROM sightings");
        getRS = getPS.executeQuery();

        while (getRS.next())
        {
            writer.print(getRS.getInt(1) + ", "
                    + getRS.getInt(2) + ", "
                    + getRS.getFloat(3) + ", "
                    + getRS.getFloat(4) + ", "
                    + getRS.getDate(5) + ", "
                    + getRS.getInt(6) + "\n");
        }
        writer.close();

        System.out.println("Exported all tables to CSVs");
    }

    static void searchCryptids()
    {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        PreparedStatement countPS, pstmt;
        ResultSet getRS = null, countRS;
        System.out.println("What would you like to search by?\n" +
                "1. ID\n" +
                "2. Name\n" +
                "3. Biome");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM cryptid WHERE CID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM cryptid WHERE CID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 2)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM cryptid WHERE Cryptid_Name = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM cryptid WHERE Cryptid_Name = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }
            else if (response == 3)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM cryptid WHERE Biome = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM cryptid WHERE Biome = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }

            String[] names = {"ID Number", "Name", "Description", "Weight", "Height", "Biome"};
            String[][] cryptids = new String[count][6];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                cryptids[i][0] = Integer.toString(getRS.getInt(1));
                cryptids[i][1] = getRS.getString(2);
                cryptids[i][2] = getRS.getString(3);
                cryptids[i][3] = Float.toString(getRS.getFloat(4)) + " lbs";
                cryptids[i][4] = Float.toString(getRS.getFloat(5)) + " ft";
                cryptids[i][5] = getRS.getString(6);
                i++;
            }
            TextTable cryptidTable = new TextTable(names, cryptids);
            cryptidTable.printTable();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void searchViewers()
    {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        PreparedStatement countPS, pstmt;
        ResultSet getRS = null, countRS;
        System.out.println("What would you like to search viewers by?\n" +
                "1. ID\n" +
                "2. Name\n" +
                "3. Age\n" +
                "4. Credentials");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM viewer WHERE Viewer_ID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM viewer WHERE Viewer_ID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 2)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM viewer WHERE Viewer_Name = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM viewer WHERE Viewer_Name = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }
            else if (response == 3)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM viewer WHERE Age = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM viewer WHERE Age = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 4)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM viewer WHERE Credentials = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM viewer WHERE Credentials = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }

            String[] names = {"ID Number", "Name", "Location", "Age", "Number of Sightings", "Credentials", "Number of Publications"};
            String[][] viewers = new String[count][7];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                viewers[i][0] = Integer.toString(getRS.getInt(1));
                viewers[i][1] = getRS.getString(2);
                viewers[i][2] = getRS.getString(3);
                viewers[i][3] = Integer.toString(getRS.getInt(4));
                viewers[i][4] = Float.toString(getRS.getFloat(5));
                viewers[i][5] = getRS.getString(6);
                viewers[i][6] = Integer.toString(getRS.getInt(7));
                i++;
            }
            TextTable viewerTable = new TextTable(names, viewers);
            viewerTable.printTable();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void searchPublications()
    {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        PreparedStatement countPS, pstmt;
        ResultSet getRS = null, countRS;
        System.out.println("What would you like to search by?\n" +
                "1. Publication ID\n" +
                "2. Viewer ID\n" +
                "3. Title\n" +
                "4. Year\n" +
                "5. Publisher");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM publications WHERE Publication_ID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM publications WHERE Publication_ID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            if(response == 2)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM publications WHERE Viewer_ID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM publications WHERE Viewer_ID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 3)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM publications WHERE Publication = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM publications WHERE Publication = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }
            else if (response == 4)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM publications WHERE Year = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM publications WHERE Year = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 5)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM publications WHERE Publisher = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM publications WHERE Publisher = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }

            String[] names = {"Publicstion ID", "Viewer ID", "Title", "Publisher", "Year"};
            String[][] publications = new String[count][5];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                publications[i][0] = Integer.toString(getRS.getInt(1));
                publications[i][1] = Integer.toString(getRS.getInt(2));
                publications[i][2] = getRS.getString(3);
                publications[i][3] = Integer.toString(getRS.getInt(4));
                publications[i][4] = getRS.getString(5);
                i++;
            }
            TextTable publicationsTable = new TextTable(names, publications);
            publicationsTable.printTable();
        }


        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void searchSightings()
    {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        PreparedStatement countPS, pstmt;
        ResultSet getRS = null, countRS;
        System.out.println("What would you like to search by?\n" +
                "1. Sighting ID\n" +
                "2. Cryptid ID\n" +
                "3. Date (YYYY-MM-DD)\n" +
                "4. Viewer ID");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM sightings WHERE Sighting_ID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM sightings WHERE Sighting_ID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if(response == 2)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM sightings WHERE CID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM sightings WHERE CID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 3)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM sightings WHERE Date_Seen = ?");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = format.parse(query);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                countPS.setDate(1, sqlDate);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM sightings WHERE Date_Seen = ?");
                pstmt.setDate(1, sqlDate);
                getRS = pstmt.executeQuery();
            }
            else if (response == 4)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM sightings WHERE Viewer_ID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM sightings WHERE Viewer_ID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }

            String[] names = {"Sighting ID", "Cryptid ID", "Latitude", "Longitude", "Date Seen", "Viewer ID"};
            String[][] sightings = new String[count][6];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                sightings[i][0] = Integer.toString(getRS.getInt(1));
                sightings[i][1] = Integer.toString(getRS.getInt(2));
                sightings[i][2] = Float.toString(getRS.getFloat(3));
                sightings[i][3] = Float.toString(getRS.getFloat(4));
                sightings[i][4] = String.valueOf(getRS.getDate(5));
                sightings[i][5] = Integer.toString(getRS.getInt(6));
                i++;
            }
            TextTable sightingsTable = new TextTable(names, sightings);
            sightingsTable.printTable();
        }


        catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    static void searchMedia()
    {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        PreparedStatement countPS, pstmt;
        ResultSet getRS = null, countRS;
        System.out.println("What would you like to search by?\n" +
                "1. Media ID\n" +
                "2. Cryptid ID\n" +
                "3. Title\n" +
                "4. Year\n" +
                "5. Format\n" +
                "6. Rating");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM media WHERE Media_ID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM media WHERE Media_ID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if(response == 2)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM media WHERE CID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM media WHERE CID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 3)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM media WHERE Title = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM media WHERE Title = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }
            else if (response == 4)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM media WHERE Year = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM media WHERE Year = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 5)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM media WHERE Format = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM media WHERE Format = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }
            else if (response == 6)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM media WHERE Rating = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM media WHERE Rating = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }

            String[] names = {"Media ID", "Cryptid ID", "Title", "Year", "Format", "Rating"};
            String[][] media = new String[count][6];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                media[i][0] = Integer.toString(getRS.getInt(1));
                media[i][1] = Integer.toString(getRS.getInt(2));
                media[i][2] = getRS.getString(3);
                media[i][3] = Integer.toString(getRS.getInt(4));
                media[i][4] = getRS.getString(5);
                media[i][5] = Integer.toString(getRS.getInt(6));
                i++;
            }
            TextTable mediaTable = new TextTable(names, media);
            mediaTable.printTable();
        }


        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void searchFolklore()
    {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        PreparedStatement countPS, pstmt;
        ResultSet getRS = null, countRS;
        System.out.println("What would you like to search by?\n" +
                "1. Folklore ID\n" +
                "2. Cryptid ID\n" +
                "3. Format\n" +
                "4. Year\n" +
                "5. Location");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM folklore WHERE Folklore_ID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM folklore WHERE Folklore_ID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if(response == 2)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM folklore WHERE CID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM folklore WHERE CID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 3)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM folklore WHERE Folklore = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM folklore WHERE Folklore = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }
            else if (response == 4)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM folklore WHERE Year = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM folklore WHERE Year = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 5)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM folklore WHERE Location = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM folklore WHERE Location = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }

            String[] names = {"Folklore ID", "Cryptid ID", "Format", "Year", "Location"};
            String[][] folklore = new String[count][5];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                folklore[i][0] = Integer.toString(getRS.getInt(1));
                folklore[i][1] = Integer.toString(getRS.getInt(2));
                folklore[i][2] = getRS.getString(3);
                folklore[i][3] = Integer.toString(getRS.getInt(4));
                folklore[i][4] = getRS.getString(5);
                i++;
            }
            TextTable folkloreTable = new TextTable(names, folklore);
            folkloreTable.printTable();
        }


        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void searchEvidence()
    {
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        PreparedStatement countPS, pstmt;
        ResultSet getRS = null, countRS;
        System.out.println("What would you like to search by?\n" +
                "1. Evidence ID\n" +
                "2. Cryptid ID\n" +
                "3. Date (YYYY-MM-DD)\n" +
                "4. Location");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM evidence WHERE Evidence_ID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM evidence WHERE Evidence_ID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if(response == 2)
            {
                countPS = conn.prepareStatement("SELECT  COUNT(*) FROM evidence WHERE CID = ?");
                countPS.setInt(1, Integer.parseInt(query));
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM evidence WHERE CID = ?");
                pstmt.setInt(1, Integer.parseInt(query));
                getRS = pstmt.executeQuery();
            }
            else if (response == 3)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM evidence WHERE 'Date' = ?");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = format.parse(query);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                countPS.setDate(1, sqlDate);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM evidence WHERE 'Date' = ?");
                pstmt.setDate(1, sqlDate);
                getRS = pstmt.executeQuery();
            }
            else if (response == 4)
            {
                countPS = conn.prepareStatement("SELECT COUNT(*) FROM evidence WHERE Location = ?");
                countPS.setString(1, query);
                countRS = countPS.executeQuery();
                countRS.next();
                count = countRS.getInt(1);

                pstmt = conn.prepareStatement("SELECT * FROM evidence WHERE Location = ?");
                pstmt.setString(1, query);
                getRS = pstmt.executeQuery();
            }

            String[] names = {"Evidence ID", "Cryptid ID", "Date", "Type", "Location"};
            String[][] evidence = new String[count][5];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                evidence[i][0] = Integer.toString(getRS.getInt(1));
                evidence[i][1] = Integer.toString(getRS.getInt(2));
                evidence[i][2] = String.valueOf(getRS.getDate(3));
                evidence[i][3] = getRS.getString(4);
                evidence[i][4] = getRS.getString(5);
                i++;
            }
            TextTable evidenceTable = new TextTable(names, evidence);
            evidenceTable.printTable();
        }


        catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    static void printAllCryptids()
    {
        System.out.println("All Cryptids: ");
        String[] names = {"ID Number", "Name", "Description", "Weight", "Height", "Biome"};
        try {
            PreparedStatement cryptidPS = conn.prepareStatement("SELECT COUNT(*) FROM cryptid");
            ResultSet cryptidRS = cryptidPS.executeQuery();
            cryptidRS.next();
            int cryptidCount = cryptidRS.getInt(1);

            String[][] cryptids = new String[cryptidCount][6];
            PreparedStatement getCryptids = conn.prepareStatement("SELECT * FROM cryptid");
            ResultSet getRS = getCryptids.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                cryptids[i][0] = Integer.toString(getRS.getInt(1));
                cryptids[i][1] = getRS.getString(2);
                cryptids[i][2] = getRS.getString(3);
                cryptids[i][3] = Float.toString(getRS.getFloat(4)) + " lbs";
                cryptids[i][4] = Float.toString(getRS.getFloat(5)) + " ft";
                cryptids[i][5] = getRS.getString(6);
                i++;
            }
            TextTable cryptidTable = new TextTable(names, cryptids);
            cryptidTable.printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void printAllViewers()
    {
        System.out.println("All Viewers: ");
        String[] names = {"ID Number", "Name", "Location", "Age", "Number of Sightings", "Credentials", "Number of Publications"};
        try {
            PreparedStatement viewerPS = conn.prepareStatement("SELECT COUNT(*) FROM viewer");
            ResultSet viewerRS = viewerPS.executeQuery();
            viewerRS.next();
            int viewerCount = viewerRS.getInt(1);

            String[][] viewers = new String[viewerCount][7];
            PreparedStatement getViewers = conn.prepareStatement("SELECT * FROM viewer");
            ResultSet getRS = getViewers.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                viewers[i][0] = Integer.toString(getRS.getInt(1));
                viewers[i][1] = getRS.getString(2);
                viewers[i][2] = getRS.getString(3);
                viewers[i][3] = Integer.toString(getRS.getInt(4));
                viewers[i][4] = Integer.toString(getRS.getInt(5));
                viewers[i][5] = getRS.getString(6);
                viewers[i][6] = Integer.toString(getRS.getInt(7));
                i++;
            }
            TextTable viewerTable = new TextTable(names, viewers);
            viewerTable.printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void printAllSightings()
    {
        System.out.println("All Sightings: ");
        String[] names = {"Sighting ID", "Cryptid Name", "Latitude", "Longitude", "Date Seen", "Viewer Name"};
        try {
            PreparedStatement sightingPS = conn.prepareStatement("SELECT COUNT(*) FROM sightings");
            ResultSet sightingsRS = sightingPS.executeQuery();
            sightingsRS.next();
            int sightingCount = sightingsRS.getInt(1);

            String[][] sightings = new String[sightingCount][6];
            PreparedStatement getSightings = conn.prepareStatement("SELECT s.Sighting_ID, c.Cryptid_Name, s.Latitude, s.Longitude, s.Date_Seen, v.Viewer_Name\n" +
                    "FROM sightings s\n" +
                    "LEFT JOIN cryptid c\n" +
                    "ON s.CID = c.CID\n" +
                    "LEFT JOIN viewer v\n" +
                    "ON v.Viewer_ID = s.Viewer_ID;");
            ResultSet getRS = getSightings.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                sightings[i][0] = Integer.toString(getRS.getInt(1));
                sightings[i][1] = getRS.getString(2);
                sightings[i][2] = Float.toString(getRS.getFloat(3));
                sightings[i][3] = Float.toString(getRS.getFloat(4));
                sightings[i][4] = String.valueOf(getRS.getDate(5));
                sightings[i][5] = getRS.getString(6);
                i++;
            }
            TextTable sightingsTable = new TextTable(names, sightings);
            sightingsTable.printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void printAllPublications()
    {
        System.out.println("All Publications: ");
        String[] names = {"Publication ID", "Author", "Title", "Year", "Publisher"};
        try {
            PreparedStatement publicationPS = conn.prepareStatement("SELECT COUNT(*) FROM publications");
            ResultSet publicationRS = publicationPS.executeQuery();
            publicationRS.next();
            int publicationCount = publicationRS.getInt(1);

            String[][] publications = new String[publicationCount][5];
            PreparedStatement getPublications = conn.prepareStatement("SELECT p.Publication_ID, v.Viewer_Name, p.Publication, p.Year, p.Publisher " +
                    "FROM publications p " +
                    "LEFT JOIN viewer v " +
                    "ON v.Viewer_ID = p.Viewer_ID");
            ResultSet getRS = getPublications.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                publications[i][0] = Integer.toString(getRS.getInt(1));
                publications[i][1] = getRS.getString(2);
                publications[i][2] = getRS.getString(3);
                publications[i][3] = Integer.toString(getRS.getInt(4));
                publications[i][4] = getRS.getString(5);
                i++;
            }
            TextTable publicationsTable = new TextTable(names, publications);
            publicationsTable.printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void printAllMedia()
    {
        System.out.println("All Media: ");
        String[] names = {"Media ID", "Cryptid", "Title", "Year", "Format", "Rating"};
        try {
            PreparedStatement mediaPS = conn.prepareStatement("SELECT COUNT(*) FROM media");
            ResultSet mediaRS = mediaPS.executeQuery();
            mediaRS.next();
            int mediaCount = mediaRS.getInt(1);

            String[][] media = new String[mediaCount][6];
            PreparedStatement getMedia = conn.prepareStatement("select m.Media_ID, c.Cryptid_Name, m.Title, m.Year, m.Format, m.Rating " +
                    "FROM media m, cryptid c " +
                    "WHERE c.CID = m.CID");
            ResultSet getRS = getMedia.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                media[i][0] = getRS.getString(1);
                media[i][1] = getRS.getString(2);
                media[i][2] = getRS.getString(3);
                media[i][3] = Integer.toString(getRS.getInt(4));
                media[i][4] = getRS.getString(5);
                media[i][5] = Integer.toString(getRS.getInt(6));
                i++;
            }
            TextTable mediaTable = new TextTable(names, media);
            mediaTable.printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void printAllFolklore()
    {
        System.out.println("All Media: ");
        String[] names = {"Folklore ID"," Cryptid", "Folklore", "Year", "Location"};
        try {
            PreparedStatement folklorePS = conn.prepareStatement("SELECT COUNT(*) FROM folklore");
            ResultSet folkloreRS = folklorePS.executeQuery();
            folkloreRS.next();
            int folkloreCount = folkloreRS.getInt(1);

            String[][] folklore = new String[folkloreCount][5];
            PreparedStatement getFolklore = conn.prepareStatement("select f.Folklore_ID, c.Cryptid_Name, f.Folklore, f.Year, f.Location " +
                    "FROM cryptid c, folklore f " +
                    "WHERE c.CID = f.CID");
            ResultSet getRS = getFolklore.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                folklore[i][0] = Integer.toString(getRS.getInt(1));
                folklore[i][1] = getRS.getString(2);
                folklore[i][2] = getRS.getString(3);
                folklore[i][3] = Integer.toString(getRS.getInt(4));
                folklore[i][4] = getRS.getString(5);
                i++;
            }
            TextTable folkloreTable = new TextTable(names, folklore);
            folkloreTable.printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void printAllEvidence()
    {
        System.out.println("All Evidence: ");
        String[] names = {"Evidence ID", "Cryptid", "Date", "Evidence", "Location"};
        try {
            PreparedStatement evidencePS = conn.prepareStatement("SELECT COUNT(*) FROM evidence");
            ResultSet evidenceRS = evidencePS.executeQuery();
            evidenceRS.next();
            int evidenceCount = evidenceRS.getInt(1);

            String[][] evidence = new String[evidenceCount][5];
            PreparedStatement getEvidence = conn.prepareStatement("select e.Evidence_ID, c.Cryptid_Name, e.Date, e.Medium, e.Location " +
                    "FROM cryptid c, evidence e " +
                    "WHERE c.CID = e.CID");
            ResultSet getRS = getEvidence.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                evidence[i][0] = Integer.toString(getRS.getInt(1));
                evidence[i][1] = getRS.getString(2);
                evidence[i][2] = String.valueOf(getRS.getDate(3));
                evidence[i][3] = getRS.getString(4);
                evidence[i][4] = getRS.getString(5);
                i++;
            }
            TextTable evidenceTable = new TextTable(names, evidence);
            evidenceTable.printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void updateCryptid()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you know the cryptid's ID number?");
        String idresponse = scanner.nextLine();

        if ((idresponse.charAt(0) == 'n' || idresponse.charAt(0) == 'N')) {
            printAllCryptids();
        }

        System.out.print("Enter Cryptid ID number: ");
        int CID = scanner.nextInt();

        System.out.println("What would you like to update?");
        System.out.println("1. Name\n" +
                "2. Description\n" +
                "3. Weight\n" +
                "4. Height\n" +
                "5. Biome\n");
        int response = scanner.nextInt();

        try
        {
            if(response == 1)
            {
                System.out.print("Enter new name:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updatePS = conn.prepareStatement("UPDATE cryptid SET Cryptid_Name = ? WHERE CID = ?");
                updatePS.setString(1, input);
                updatePS.setInt(2, CID);
                updatePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE cryptid SET Cryptid_Name = " + input + " WHERE CID = " + CID + "\n");
                System.out.println("Updated Cryptid ID: " + CID + " to name: " + input);

            }
            else if (response == 2)
            {
                System.out.print("Enter new description:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE cryptid SET Description = ? WHERE CID = ?");
                updateNamePS.setString(1, input);
                updateNamePS.setInt(2, CID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE cryptid SET Description = " + input + " WHERE CID = " + CID);
                System.out.println("Updated Cryptid ID: " + CID + " to description: " + input);
            }
            else if (response == 3)
            {
                System.out.print("Enter new weight:");
                scanner.nextLine();
                Float input = scanner.nextFloat();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE cryptid SET Weight = ? WHERE CID = ?");
                updateNamePS.setFloat(1, input);
                updateNamePS.setInt(2, CID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE cryptid SET Weight = " + input + " WHERE CID = " + CID + "\n");
                System.out.println("Updated Cryptid ID: " + CID + " to weight: " + input);
            }
            else if (response == 4)
            {
                System.out.print("Enter new height:");
                scanner.nextLine();
                float input = scanner.nextFloat();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE cryptid SET Height = ? WHERE CID = ?");
                updateNamePS.setFloat(1, input);
                updateNamePS.setInt(2, CID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE cryptid SET Height = " + input + " WHERE CID = " + CID + "\n");
                System.out.println("Updated Cryptid ID: " + CID + " to height: " + input);
            }
            else if (response == 5)
            {
                System.out.print("Which biome would you like to update to?\n" +
                        "1. Land\n" +
                        "2. Water\n" +
                        "3. Sky\n");
                scanner.nextLine();
                int input = scanner.nextInt();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE cryptid SET Biome = ? WHERE CID = ?");
                String biome = null;
                if (input == 1)
                {
                    biome = "Land";
                }
                else if (input == 2)
                {
                    biome = "Water";
                }
                else if (input == 3)
                {
                    biome = "Sky";
                }
                updateNamePS.setString(1, biome);
                updateNamePS.setInt(2, CID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE cryptid SET Biome = " + input + " WHERE CID = " + CID + "\n");
                System.out.println("Updated Cryptid ID: " + CID + " to biome: " + biome);
                
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateViewer()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you know the viewer's ID number?");
        String idresponse = scanner.nextLine();

        if ((idresponse.charAt(0) == 'n' || idresponse.charAt(0) == 'N')) {
            searchViewers();
        }

        System.out.print("Enter Viewer ID number: ");
        int VID = scanner.nextInt();

        System.out.println("What would you like to update?");
        System.out.println("1. Name\n" +
                "2. Location\n" +
                "3. Age\n" +
                "4. Credentials\n");
        int response = scanner.nextInt();

        try
        {
            if(response == 1)
            {
                System.out.print("Enter new name:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updatePS = conn.prepareStatement("UPDATE viewer SET Viewer_Name = ? WHERE Viewer_ID = ?");
                updatePS.setString(1, input);
                updatePS.setInt(2, VID);
                updatePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE viewer SET Viewer_Name = " + input + " WHERE Viewer_ID = " + VID + "\n");
                System.out.println("Updated Viewer ID: " + VID + " to name: " + input);

            }
            else if (response == 2)
            {
                System.out.print("Enter new location:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE viewer SET Location = ? WHERE Viewer_ID = ?");
                updateNamePS.setString(1, input);
                updateNamePS.setInt(2, VID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE viewer SET Location = " + input + " WHERE Viewer_ID = " + VID + "\n");
                System.out.println("Updated Viewer ID: " + VID + " to location: " + input);
            }
            else if (response == 3)
            {
                System.out.print("Enter new age:");
                scanner.nextLine();
                int input = scanner.nextInt();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE viewer SET Age = ? WHERE Viewer_ID = ?");
                updateNamePS.setInt(1, input);
                updateNamePS.setInt(2, VID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE viewer SET Age = " + input + " WHERE Viewer_ID = " + VID + "\n");
                System.out.println("Updated Viewer ID: " + VID + " to Age: " + input);
            }
            else if (response == 4)
            {
                System.out.print("Enter new credentials:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE viewer SET Credentials = ? WHERE Viewer_ID = ?");
                updateNamePS.setString(1, input);
                updateNamePS.setInt(2, VID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE viewer SET Credentials = " + input + " WHERE Viewer_ID = " + VID + "\n");
                System.out.println("Updated Viewer ID: " + VID + " to Credentials: " + input);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateSighting()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you know the sighting's ID number?");
        String idresponse = scanner.nextLine();

        if ((idresponse.charAt(0) == 'n' || idresponse.charAt(0) == 'N')) {
            searchSightings();
        }

        System.out.print("Enter Sighting ID number: ");
        int ID = scanner.nextInt();

        System.out.println("What would you like to update?");
        System.out.println("1. Viewer ID\n" +
                "2. Cryptid ID\n");
        int response = scanner.nextInt();

        try
        {
            if(response == 1)
            {
                System.out.print("Enter new viewer ID:");
                scanner.nextLine();
                int input = scanner.nextInt();

                PreparedStatement getOldID = conn.prepareStatement("SELECT Viewer_ID from sightings where Sighting_ID = ?");
                getOldID.setInt(1, ID);
                ResultSet getIDRS = getOldID.executeQuery();
                getIDRS.next();
                int oldID = getIDRS.getInt(1);


                PreparedStatement updatePS = conn.prepareStatement("UPDATE sightings SET Viewer_ID = ? WHERE Sighting_ID = ?");
                updatePS.setInt(1, input);
                updatePS.setInt(2, ID);
                updatePS.executeUpdate();
                writer.print("UPDATE sightings SET Viewer_ID = " + input + " WHERE Sighting_ID = " + ID + "\n");

                PreparedStatement updateOld = conn.prepareStatement("Update viewer set Number_of_Sightings = Number_of_Sightings - 1 Where viewer_ID = ?");
                updateOld.setInt(1, oldID);
                updateOld.executeUpdate();
                writer.print("UPDATE viewer SET Number_of_Sightings = Number_of_Sightings - 1 WHERE Viewer_ID = " + oldID + "\n");

                PreparedStatement updateNew = conn.prepareStatement("Update viewer set Number_of_Sightings = Number_of_Sightings + 1 Where viewer_ID = ?");
                updateNew.setInt(1, input);
                updateNew.executeUpdate();
                writer.print("UPDATE viewer SET Number_of_Sightings = Number_of_Sightings + 1 WHERE Viewer_ID = " + input + "\n");

                conn.commit();

                System.out.println("Updated Sighting ID: " + ID + " to Viewer ID: " + input);

            }
            else if (response == 2)
            {
                System.out.print("Enter new Cryptid ID:");
                scanner.nextLine();
                int input = scanner.nextInt();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE sightings SET CID = ? WHERE Sighting_ID = ?");
                updateNamePS.setInt(1, input);
                updateNamePS.setInt(2, ID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE sightings SET CID = " + input + " WHERE Sighting_ID = " + ID + "\n");
                System.out.println("Updated Sighting ID: " + ID + " to Cryptid ID: " + input);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updatePublication()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you know the publication's ID number?");
        String idresponse = scanner.nextLine();

        if ((idresponse.charAt(0) == 'n' || idresponse.charAt(0) == 'N')) {
            searchPublications();
        }

        System.out.print("Enter Publication ID number: ");
        int ID = scanner.nextInt();

        System.out.println("What would you like to update?");
        System.out.println("1. Author\n" +
                "2. Title\n" +
                "3. Publisher\n");
        int response = scanner.nextInt();

        try
        {
            if(response == 1)
            {
                System.out.print("Enter new Author's viewer ID:");
                scanner.nextLine();
                int input = scanner.nextInt();

                PreparedStatement getOldID = conn.prepareStatement("SELECT Viewer_ID from publications where Publication_ID = ?");
                getOldID.setInt(1, ID);
                ResultSet getIDRS = getOldID.executeQuery();
                getIDRS.next();
                int oldID = getIDRS.getInt(1);
                conn.commit();

                PreparedStatement updatePS = conn.prepareStatement("UPDATE publications SET Viewer_ID = ? WHERE Publication_ID = ?");
                updatePS.setInt(1, input);
                updatePS.setInt(2, ID);
                updatePS.executeUpdate();
                writer.print("UPDATE publications SET Viewer_ID = " + input + " WHERE Publication_ID = " + ID + "\n");

                PreparedStatement updateOld = conn.prepareStatement("Update viewer set Number_of_Publications = Number_of_Publications-1 Where viewer_ID = ?");
                updateOld.setInt(1, oldID);
                updateOld.executeUpdate();
                writer.print("Update viewer set Number_of_Publications = Number_of_Publications-1 Where viewer_ID = " + oldID + "\n");

                PreparedStatement updateNew = conn.prepareStatement("Update viewer set Number_of_Publications = Number_of_Publications+1 Where viewer_ID = ?");
                updateNew.setInt(1, input);
                updateNew.executeUpdate();
                writer.print("Update viewer set Number_of_Publications = Number_of_Publications+1 Where viewer_ID = " + input + "\n");

                conn.commit();

                System.out.println("Updated Publication ID: " + ID + " to Viewer ID: " + input);

            }
            else if (response == 2)
            {
                System.out.print("Enter new Title:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE publications SET Publication = ? WHERE Publication_ID = ?");
                updateNamePS.setString(1, input);
                updateNamePS.setInt(2, ID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE publications SET Publication = " + input + " WHERE Publication_ID = " + ID + "\n");
                System.out.println("Updated Publication ID: " + ID + " to Title: " + input);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateMedia()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you know the media's ID number?");
        String idresponse = scanner.nextLine();

        if ((idresponse.charAt(0) == 'n' || idresponse.charAt(0) == 'N')) {
            searchMedia();
        }

        System.out.print("Enter Media ID number: ");
        int ID = scanner.nextInt();

        System.out.println("What would you like to update?");
        System.out.println("1. Title\n" +
                "2. Format\n" +
                "3. Rating\n" +
                "4. Year");
        int response = scanner.nextInt();

        try
        {
            if(response == 1)
            {
                System.out.print("Enter new Title:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updatePS = conn.prepareStatement("UPDATE media SET Title = ? WHERE Media_ID = ?");
                updatePS.setString(1, input);
                updatePS.setInt(2, ID);
                updatePS.executeUpdate();

                conn.commit();

                writer.print("UPDATE media SET Title = " + input + " WHERE Media_ID = " + ID + "\n");
                System.out.println("Updated Media ID: " + ID + " to Title: " + input);

            }
            else if (response == 2)
            {
                System.out.print("Enter new Format:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE media SET Format = ? WHERE Media_ID = ?");
                updateNamePS.setString(1, input);
                updateNamePS.setInt(2, ID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE media SET Format = " + input + " WHERE Media_ID = " + ID + "\n");
                System.out.println("Updated Media ID: " + ID + " to Format: " + input);
            }
            else if (response == 3)
            {
                System.out.print("Enter new rating:");
                scanner.nextLine();
                int input = scanner.nextInt();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE media SET Rating = ? WHERE Media_ID = ?");
                updateNamePS.setInt(1, input);
                updateNamePS.setInt(2, ID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE media SET Rating = " + input + " WHERE Media_ID = " + ID + "\n");
                System.out.println("Updated Media ID: " + ID + " to Rating: " + input);
            }
            else if (response == 4)
            {
                System.out.print("Enter new year:");
                scanner.nextLine();
                int input = scanner.nextInt();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE media SET 'Year' = ? WHERE Media_ID = ?");
                updateNamePS.setInt(1, input);
                updateNamePS.setInt(2, ID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE media SET Year = " + input + " WHERE Media_ID = " + ID + "\n");
                System.out.println("Updated Media ID: " + ID + " to Year: " + input);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateFolklore()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you know the folklore's ID number?");
        String idresponse = scanner.nextLine();

        if ((idresponse.charAt(0) == 'n' || idresponse.charAt(0) == 'N')) {
            searchFolklore();
        }

        System.out.print("Enter folklore ID number: ");
        int ID = scanner.nextInt();

        System.out.println("What would you like to update?");
        System.out.println("1. Folklore\n" +
                "2. Year\n" +
                "3. Location");
        int response = scanner.nextInt();

        try
        {
            if(response == 1)
            {
                System.out.print("Enter new Folklore:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updatePS = conn.prepareStatement("UPDATE folklore SET Folklore = ? WHERE Folklore_ID = ?");
                updatePS.setString(1, input);
                updatePS.setInt(2, ID);
                updatePS.executeUpdate();

                conn.commit();

                writer.print("UPDATE folklore SET Folklore = " + input + " WHERE Folklore_ID = " + ID + "\n");
                System.out.println("Updated Folklore ID: " + ID + " to Folklore: " + input);

            }
            else if (response == 2)
            {
                System.out.print("Enter new year:");
                scanner.nextLine();
                int input = scanner.nextInt();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE folklore SET 'Year' = ? WHERE Folklore_ID = ?");
                updateNamePS.setInt(1, input);
                updateNamePS.setInt(2, ID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE folklore SET Year = " + input + " WHERE Folklore_ID = " + ID + "\n");
                System.out.println("Updated Folklore ID: " + ID + " to Year: " + input);
            }
            else if (response == 3)
            {
                System.out.print("Enter new Location:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE folklore SET Location = ? WHERE Folklore_ID = ?");
                updateNamePS.setString(1, input);
                updateNamePS.setInt(2, ID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE folklore SET Location = " + input + " WHERE Folklore_ID = " + ID + "\n");
                System.out.println("Updated Folklore ID: " + ID + " to Location: " + input);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void updateEvidence()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you know the Evidence's ID number?");
        String idresponse = scanner.nextLine();

        if ((idresponse.charAt(0) == 'n' || idresponse.charAt(0) == 'N')) {
            searchEvidence();
        }

        System.out.print("Enter evidence ID number: ");
        int ID = scanner.nextInt();

        System.out.println("What would you like to update?");
        System.out.println("1. Date (yyyy-mm-dd)\n" +
                "2. Medium\n" +
                "3. Location");
        int response = scanner.nextInt();

        try
        {
            if(response == 1)
            {
                System.out.print("Enter new Date:");
                scanner.nextLine();
                String input = scanner.nextLine();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utilDate = format.parse(input);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                PreparedStatement updatePS = conn.prepareStatement("UPDATE evidence SET 'Date' = ? WHERE Evidence_ID = ?");
                updatePS.setDate(1, sqlDate);
                updatePS.setInt(2, ID);
                updatePS.executeUpdate();

                conn.commit();

                writer.print("UPDATE evidence SET Date = " + input + " WHERE Evidence_ID = " + ID + "\n");
                System.out.println("Updated Evidence ID: " + ID + " to Date: " + input);

            }
            else if (response == 2)
            {
                System.out.print("Enter new Medium:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE evidence SET Medium = ? WHERE Evidence_ID = ?");
                updateNamePS.setString(1, input);
                updateNamePS.setInt(2, ID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE evidence SET Medium = " + input + " WHERE Evidence_ID = " + ID + "\n");
                System.out.println("Updated Evidence ID: " + ID + " to Medium: " + input);
            }
            else if (response == 3)
            {
                System.out.print("Enter new Location:");
                scanner.nextLine();
                String input = scanner.nextLine();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE evidence SET Location = ? WHERE Evidence_ID = ?");
                updateNamePS.setString(1, input);
                updateNamePS.setInt(2, ID);
                updateNamePS.executeUpdate();
                conn.commit();

                writer.print("UPDATE evidence SET Location = " + input + " WHERE Evidence_ID = " + ID + "\n");
                System.out.println("Updated Evidence ID: " + ID + " to Location: " + input);
            }
        }

        catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }

    static void createCryptid() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter new Cryptid name: ");
        String name = scanner.nextLine();

        System.out.println("Enter new Cryptid description: ");
        String description = scanner.nextLine();

        System.out.println("Enter new Cryptid height: ");
        String height = scanner.nextLine();

        System.out.println("Enter new Cryptid weight: ");
        String weight = scanner.nextLine();

        System.out.println("Enter new Cryptid Biome (Land/Sky/Water): ");
        String biome = scanner.nextLine();

        PreparedStatement insertPS = conn.prepareStatement("INSERT INTO cryptid VALUES (null,?,?,?,?,?)");
        insertPS.setString(1, name);
        insertPS.setString(2, description);
        insertPS.setFloat(3, Float.parseFloat(weight));
        insertPS.setFloat(4, Float.parseFloat(height));
        insertPS.setString(5, biome);

        insertPS.executeUpdate();
        conn.commit();

        writer.print("INSERT INTO cryptid VALUES (null," + name + ","
                + description + "," + weight + "," + height + "," + biome + ")\n");

        PreparedStatement returnPS = conn.prepareStatement("SELECT MAX(CID) FROM cryptid");
        ResultSet returnRS = returnPS.executeQuery();
        returnRS.next();
        System.out.println("Added new Cryptid: " + name + " with Cryptid ID: " + returnRS.getInt(1));
    }

    static void createViewer() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter new Viewer name: ");
        String name = scanner.nextLine();

        System.out.println("Enter new Viewer Location: ");
        String location = scanner.nextLine();

        System.out.println("Enter new Viewer Age: ");
        String age = scanner.nextLine();

        System.out.println("Enter new Viewer Credentials: ");
        String credentials = scanner.nextLine();

        PreparedStatement insertPS = conn.prepareStatement("INSERT INTO viewer VALUES (null,?,?,?,0,?,0)");
        insertPS.setString(1, name);
        insertPS.setString(2, location);
        insertPS.setInt(3, Integer.parseInt(age));
        insertPS.setString(4, credentials);

        insertPS.executeUpdate();
        conn.commit();

        writer.print("INSERT INTO viewer VALUES (null," + name + ","
                + location + "," + age + "," + 0 + "," + credentials + "," + 0 +")\n");

        PreparedStatement returnPS = conn.prepareStatement("SELECT MAX(Viewer_ID) FROM viewer");
        ResultSet returnRS = returnPS.executeQuery();
        returnRS.next();
        System.out.println("Added new Viewer: " + name + " with ID: " + returnRS.getInt(1));
    }

    static void createEvidence() throws SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you know the cryptid ID of the cryptid who left this evidence?");
        String response1 = scanner.nextLine();

        if (response1.charAt(0) == 'n' || response1.charAt(0) == 'N')
        {
            System.out.println("Would you like to\n" +
                    "1. Show all cryptid options?\n" +
                    "2. Search cryptids?\n " +
                    "3. Create a new cryptid?");

            while (true)
            {
                int response2 = Integer.parseInt(scanner.nextLine());
                switch (response2)
                {
                    case 1: {
                        printAllCryptids();
                        break;
                    }
                    case 2: {
                        searchCryptids();
                        break;
                    }
                    case 3: {
                        createCryptid();
                        break;
                    }
                    default:{
                        System.out.println("Not a valid entry. Enter 1, 2, or 3 based on the options above");
                        break;
                    }
                }
                if (response2 == 1 || response2 == 2 || response2 == 3)
                {
                    break;
                }
            }

        }

        System.out.println("Enter Cryptid's ID: ");
        String CID = scanner.nextLine();

        System.out.println("Enter new Evidence Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = format.parse(date);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        System.out.println("Enter Evidence Medium/Type: ");
        String medium = scanner.nextLine();

        System.out.println("Enter Evidence Location: ");
        String location = scanner.nextLine();

        PreparedStatement insertPS = conn.prepareStatement("INSERT INTO evidence VALUES (null,?,?,?,?)");
        insertPS.setInt(1, Integer.parseInt(CID));
        insertPS.setDate(2, sqlDate);
        insertPS.setString(3, medium);
        insertPS.setString(4, location);

        insertPS.executeUpdate();
        conn.commit();

        writer.print("INSERT INTO evidence VALUES (null," + CID + ","
                + date + "," + medium + "," + location + ")\n");

        PreparedStatement returnPS = conn.prepareStatement("SELECT MAX(Evidence_ID) FROM evidence");
        ResultSet returnRS = returnPS.executeQuery();
        returnRS.next();
        System.out.println("Added new Evidence of type: " + medium + "with ID: " + returnRS.getInt(1));
    }

    static void createFolklore() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you know the cryptid ID of the cryptid who this folklore pertains to?");
        String response1 = scanner.nextLine();

        if (response1.charAt(0) == 'n' || response1.charAt(0) == 'N')
        {
            System.out.println("Would you like to\n" +
                    "1. Show all cryptid options?\n" +
                    "2. Search cryptids?\n" +
                    "3. Create a new cryptid?");

            while (true)
            {
                int response2 = Integer.parseInt(scanner.nextLine());
                switch (response2)
                {
                    case 1: {
                        printAllCryptids();
                        break;
                    }
                    case 2: {
                        searchCryptids();
                        break;
                    }
                    case 3: {
                        createCryptid();
                        break;
                    }
                    default:{
                        System.out.println("Not a valid entry. Enter 1, 2, or 3 based on the options above");
                        break;
                    }
                }
                if (response2 == 1 || response2 == 2 || response2 == 3)
                {
                    break;
                }
            }

        }

        System.out.println("Enter Cryptid's ID: ");
        String CID = scanner.nextLine();

        System.out.println("Enter new folklore type: ");
        String folklore = scanner.nextLine();

        System.out.println("Enter folklore year: ");
        String year = scanner.nextLine();

        System.out.println("Enter folklore Location: ");
        String location = scanner.nextLine();

        PreparedStatement insertPS = conn.prepareStatement("INSERT INTO folklore VALUES (null,?,?,?,?)");
        insertPS.setInt(1, Integer.parseInt(CID));
        insertPS.setString(2, folklore);
        insertPS.setInt(3, Integer.parseInt(year));
        insertPS.setString(4, location);

        insertPS.executeUpdate();
        conn.commit();

        writer.print("INSERT INTO folklore VALUES (null," + CID + ","
                + folklore + "," + year + "," + location + ")\n");

        PreparedStatement returnPS = conn.prepareStatement("SELECT MAX(Folklore_ID) FROM folklore");
        ResultSet returnRS = returnPS.executeQuery();
        returnRS.next();
        System.out.println("Added new folklore: " + folklore + " with ID: " + returnRS.getInt(1));
    }

    static void createMedia() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you know the cryptid ID of the cryptid who this media pertains to?");
        String response1 = scanner.nextLine();

        if (response1.charAt(0) == 'n' || response1.charAt(0) == 'N')
        {
            System.out.println("Would you like to\n" +
                    "1. Show all cryptid options?\n" +
                    "2. Search cryptids?\n" +
                    "3. Create a new cryptid?");

            while (true)
            {
                int response2 = Integer.parseInt(scanner.nextLine());
                switch (response2)
                {
                    case 1: {
                        printAllCryptids();
                        break;
                    }
                    case 2: {
                        searchCryptids();
                        break;
                    }
                    case 3: {
                        createCryptid();
                        break;
                    }
                    default:{
                        System.out.println("Not a valid entry. Enter 1, 2, or 3 based on the options above");
                        break;
                    }
                }
                if (response2 == 1 || response2 == 2 || response2 == 3)
                {
                    break;
                }
            }

        }

        System.out.println("Enter Cryptid's ID: ");
        String CID = scanner.nextLine();

        System.out.println("Enter new media title: ");
        String title = scanner.nextLine();

        System.out.println("Enter media year: ");
        String year = scanner.nextLine();

        System.out.println("Enter media format: ");
        String format = scanner.nextLine();

        System.out.println("Enter media rating (0-10): ");
        String rating = scanner.nextLine();

        PreparedStatement insertPS = conn.prepareStatement("INSERT INTO media VALUES (null,?,?,?,?,?)");
        insertPS.setInt(1, Integer.parseInt(CID));
        insertPS.setString(2, title);
        insertPS.setInt(3, Integer.parseInt(year));
        insertPS.setString(4, format);
        insertPS.setInt(3, Integer.parseInt(rating));

        insertPS.executeUpdate();
        conn.commit();

        writer.print("INSERT INTO media VALUES (null," + CID + ","
                + title + "," + year + "," + format + "," + rating + ")\n");

        PreparedStatement returnPS = conn.prepareStatement("SELECT MAX(Media_ID) FROM media");
        ResultSet returnRS = returnPS.executeQuery();
        returnRS.next();
        System.out.println("Added new media: " + title + " with ID: " + returnRS.getInt(1));
    }

    static void createPublication() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you know the viewer ID of the author?");
        String response1 = scanner.nextLine();

        if (response1.charAt(0) == 'n' || response1.charAt(0) == 'N')
        {
            System.out.println("Would you like to\n" +
                    "1. Show all viewers?\n" +
                    "2. Search viewers?\n" +
                    "3. Create a new viewer?");

            while (true)
            {
                int response2 = Integer.parseInt(scanner.nextLine());
                switch (response2)
                {
                    case 1: {
                        printAllViewers();
                        break;
                    }
                    case 2: {
                        searchViewers();
                        break;
                    }
                    case 3: {
                        createViewer();
                        break;
                    }
                    default:{
                        System.out.println("Not a valid entry. Enter 1, 2, or 3 based on the options above");
                        break;
                    }
                }
                if (response2 == 1 || response2 == 2 || response2 == 3)
                {
                    break;
                }
            }

        }

        System.out.println("Enter Viewer's ID: ");
        String VID = scanner.nextLine();

        System.out.println("Enter new Publication title: ");
        String title = scanner.nextLine();

        System.out.println("Enter publication year: ");
        String year = scanner.nextLine();

        System.out.println("Enter publisher: ");
        String publisher = scanner.nextLine();

        PreparedStatement insertPS = conn.prepareStatement("INSERT INTO publications VALUES (null,?,?,?,?)");
        insertPS.setInt(1, Integer.parseInt(VID));
        insertPS.setString(2, title);
        insertPS.setInt(3, Integer.parseInt(year));
        insertPS.setString(4, publisher);
        insertPS.executeUpdate();

        PreparedStatement updatePS = conn.prepareStatement("UPDATE viewer SET Number_of_Publications = Number_of_Publications + 1 WHERE Viewer_ID = ?");
        updatePS.setInt(1, Integer.parseInt(VID));
        updatePS.executeUpdate();
        conn.commit();

        writer.print("INSERT INTO publications VALUES (null," + VID + ","
                + title + "," + year + "," + publisher + ")\n");

        writer.print("UPDATE viewer SET Number_of_Publications = Number_of_Publications + 1 WHERE Viewer_ID = " + VID + "\n");

        PreparedStatement returnPS = conn.prepareStatement("SELECT MAX(Publication_ID) FROM publications");
        ResultSet returnRS = returnPS.executeQuery();
        returnRS.next();
        System.out.println("Added new publication: " + title + " with ID: " + returnRS.getInt(1));
    }

    static void createSighting() throws SQLException, ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you know the viewer ID of the person who made this sighting?");
        String response1 = scanner.nextLine();

        if (response1.charAt(0) == 'n' || response1.charAt(0) == 'N')
        {
            System.out.println("Would you like to\n" +
                    "1. Show all viewers?\n" +
                    "2. Search viewers?\n" +
                    "3. Create a new viewer?");

            while (true)
            {
                int response2 = Integer.parseInt(scanner.nextLine());
                switch (response2)
                {
                    case 1: {
                        printAllViewers();
                        break;
                    }
                    case 2: {
                        searchViewers();
                        break;
                    }
                    case 3: {
                        createViewer();
                        break;
                    }
                    default:{
                        System.out.println("Not a valid entry. Enter 1, 2, or 3 based on the options above");
                        break;
                    }
                }
                if (response2 == 1 || response2 == 2 || response2 == 3)
                {
                    break;
                }
            }

        }

        System.out.println("Enter Viewer's ID: ");
        String VID = scanner.nextLine();

        System.out.println("Do you know the cryptid ID of the cryptid sighted?");
        String response11 = scanner.nextLine();

        if (response11.charAt(0) == 'n' || response11.charAt(0) == 'N')
        {
            System.out.println("Would you like to\n" +
                    "1. Show all cryptid options?\n" +
                    "2. Search cryptids?\n " +
                    "3. Create a new cryptid?");

            while (true)
            {
                int response2 = Integer.parseInt(scanner.nextLine());
                switch (response2)
                {
                    case 1: {
                        printAllCryptids();
                        break;
                    }
                    case 2: {
                        searchCryptids();
                        break;
                    }
                    case 3: {
                        createCryptid();
                        break;
                    }
                    default:{
                        System.out.println("Not a valid entry. Enter 1, 2, or 3 based on the options above");
                        break;
                    }
                }
                if (response2 == 1 || response2 == 2 || response2 == 3)
                {
                    break;
                }
            }

        }
        System.out.println("Enter Cryptid ID: ");
        String CID = scanner.nextLine();

        System.out.println("Enter Latitude: ");
        String latitude = scanner.nextLine();

        System.out.println("Enter Longitude: ");
        String longitude = scanner.nextLine();

        System.out.println("Enter sighting Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = format.parse(date);
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        PreparedStatement insertPS = conn.prepareStatement("INSERT INTO sightings VALUES (null,?,?,?,?,?)");
        insertPS.setInt(1, Integer.parseInt(CID));
        insertPS.setFloat(2, Float.parseFloat(latitude));
        insertPS.setFloat(3, Float.parseFloat(longitude));
        insertPS.setDate(4, sqlDate);
        insertPS.setInt(5, Integer.parseInt(VID));

        insertPS.executeUpdate();

        PreparedStatement updatePS = conn.prepareStatement("UPDATE viewer SET Number_of_Sightings = Number_of_Sightings + 1 WHERE Viewer_ID = ?");
        updatePS.setInt(1, Integer.parseInt(VID));
        updatePS.executeUpdate();
        conn.commit();

        writer.print("INSERT INTO sightings VALUES (null," + CID + ","
                + latitude + "," + longitude + "," + date + "," + VID + ")\n");

        writer.print("UPDATE viewer SET Number_of_Sightings = Number_of_Sightings + 1 WHERE Viewer_ID = " + VID + "\n");

        PreparedStatement returnPS = conn.prepareStatement("SELECT MAX(Sighting_ID) FROM sightings");
        ResultSet returnRS = returnPS.executeQuery();
        returnRS.next();
        System.out.println("Added new sighting of Cryptid: " + CID + " From Viewer: "
                + VID + " with  Sighting ID: " + returnRS.getInt(1));
    }

    static void deleteCryptid() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you know the ID of the cryptid you would like to delete?");
        String response = scanner.nextLine();

        if (response.charAt(0) == 'y' || response.charAt(0) == 'Y')
        {
            System.out.println("Enter ID of Cryptid you would like to delete");
            String CID = scanner.nextLine();

            System.out.println("Are you sure you want to delete Cryptid " + CID + "? This is permanent and cannot be undone.\n" +
                    "This will also delete all media, folklore, evidence, and sightings of this cryptid permanently.");
            String sure = scanner.nextLine();

            if (sure.charAt(0) == 'y' || sure.charAt(0) == 'Y')
            {
                PreparedStatement deleteMediaPS = conn.prepareStatement("DELETE FROM media WHERE CID = ?");
                deleteMediaPS.setInt(1, Integer.parseInt(CID));
                deleteMediaPS.executeUpdate();
                writer.print("DELETE FROM media WHERE CID = " + CID + "\n");

                PreparedStatement deleteFolklorePS = conn.prepareStatement("DELETE FROM folklore WHERE CID = ?");
                deleteFolklorePS.setInt(1, Integer.parseInt(CID));
                deleteFolklorePS.executeUpdate();
                writer.print("DELETE FROM folklore WHERE CID = " + CID + "\n");

                PreparedStatement deleteEvidencePS = conn.prepareStatement("DELETE FROM evidence WHERE CID = ?");
                deleteEvidencePS.setInt(1, Integer.parseInt(CID));
                deleteEvidencePS.executeUpdate();
                writer.print("DELETE FROM evidence WHERE CID = " + CID + "\n");

                PreparedStatement getSightingsPS = conn.prepareStatement("SELECT Viewer_ID, COUNT(Viewer_ID)" +
                        " FROM sightings WHERE CID = ? GROUP BY Viewer_ID");
                getSightingsPS.setInt(1, Integer.parseInt(CID));
                ResultSet getSightingsRS = getSightingsPS.executeQuery();

                PreparedStatement removeSightingsPS = conn.prepareStatement("UPDATE Viewer SET Number_of_Sightings = Number_of_Sightings - ?" +
                        " WHERE Viewer_ID = ?");
                while (getSightingsRS.next())
                {
                    removeSightingsPS.setInt(1, getSightingsRS.getInt(2));
                    removeSightingsPS.setInt(2, getSightingsRS.getInt(1));
                    removeSightingsPS.executeUpdate();

                    writer.print("UPDATE Viewer SET Number_of_Sightings = Number_of_Sightings - " + getSightingsRS.getInt(2) +
                            " WHERE Viewer_ID = ?" + getSightingsRS.getInt(1) + "\n");
                }

                PreparedStatement deleteSightingsPS = conn.prepareStatement("DELETE FROM sightings WHERE CID = ?");
                deleteSightingsPS.setInt(1, Integer.parseInt(CID));
                deleteSightingsPS.executeUpdate();
                writer.print("DELETE FROM sightings WHERE CID = " + CID + "\n");

                PreparedStatement deletePS = conn.prepareStatement("DELETE FROM cryptid WHERE CID = ?");
                deletePS.setInt(1, Integer.parseInt(CID));
                deletePS.executeUpdate();
                writer.print("DELETE FROM cryptid WHERE CID = " + CID + "\n");

                conn.commit();

                System.out.println("Crypid " + CID + " DELETED");
            }
            else
            {
                System.out.println("Please continue to a different action");
            }
        }
        else
        {
            System.out.println("You must have the Cryptid ID to delete the cryptid. Please find cryptid ID and try again.");
        }
    }
}
