import dnl.utils.text.table.TextTable;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

@SuppressWarnings({"Duplicates", "SqlDialectInspection", "SqlNoDataSourceInspection"})
class DatabaseConnection {

    private static Connection conn;

    DatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cryptiddb", "tester", "tester");
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    Connection getConn() {
        return conn;
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
        System.out.println("What would you like to search by?\n" +
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
                "1. Viewer ID\n" +
                "2. Title\n" +
                "3. Year\n" +
                "4. Publisher");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
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
            else if (response == 2)
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
            else if (response == 3)
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
            else if (response == 4)
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

            String[] names = {"Viewer ID Number", "Title", "Publisher", "Year"};
            String[][] publications = new String[count][4];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                publications[i][0] = Integer.toString(getRS.getInt(1));
                publications[i][1] = getRS.getString(2);
                publications[i][3] = Integer.toString(getRS.getInt(3));
                publications[i][2] = getRS.getString(4);
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
                "1. Cryptid ID\n" +
                "2. Date (YYYY-MM-DD)\n" +
                "3. Viewer ID");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
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
            else if (response == 2)
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
            else if (response == 3)
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

            String[] names = {"Cryptid ID", "Latitude", "Longitude", "Date Seen", "Viewer ID"};
            String[][] sightings = new String[count][5];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                sightings[i][0] = Integer.toString(getRS.getInt(1));
                sightings[i][1] = Float.toString(getRS.getFloat(2));
                sightings[i][2] = Float.toString(getRS.getFloat(3));
                sightings[i][3] = String.valueOf(getRS.getDate(4));
                sightings[i][4] = Integer.toString(getRS.getInt(5));
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
                "1. Cryptid ID\n" +
                "2. Title\n" +
                "3. Year\n" +
                "4. Format\n" +
                "5. Rating");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
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
            else if (response == 2)
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
            else if (response == 3)
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
            else if (response == 4)
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
            else if (response == 5)
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

            String[] names = {"Cryptid ID", "Title", "Year", "Format", "Rating"};
            String[][] media = new String[count][5];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                media[i][0] = Integer.toString(getRS.getInt(1));
                media[i][1] = getRS.getString(2);
                media[i][2] = Integer.toString(getRS.getInt(3));
                media[i][3] = getRS.getString(4);
                media[i][4] = Integer.toString(getRS.getInt(5));
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
                "1. Cryptid ID\n" +
                "2. Format\n" +
                "3. Year\n" +
                "4. Location");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
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
            else if (response == 2)
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
            else if (response == 3)
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
            else if (response == 4)
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

            String[] names = {"Cryptid ID", "Format", "Year", "Location"};
            String[][] folklore = new String[count][4];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                folklore[i][0] = Integer.toString(getRS.getInt(1));
                folklore[i][1] = getRS.getString(2);
                folklore[i][2] = Integer.toString(getRS.getInt(3));
                folklore[i][3] = getRS.getString(4);
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
                "1. Cryptid ID\n" +
                "2. Date (YYYY-MM-DD)\n" +
                "3. Location");
        int response = scanner.nextInt();
        System.out.print("Enter search term:");
        scanner.nextLine();
        String query = scanner.nextLine();

        try
        {
            if(response == 1)
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
            else if (response == 2)
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
            else if (response == 3)
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

            String[] names = {"Cryptid ID", "Date", "Type", "Location"};
            String[][] evidence = new String[count][4];
            int i = 0;
            assert getRS != null;
            while (getRS.next())
            {
                evidence[i][0] = Integer.toString(getRS.getInt(1));
                evidence[i][1] = String.valueOf(getRS.getDate(2));
                evidence[i][2] = getRS.getString(3);
                evidence[i][3] = getRS.getString(4);
                i++;
            }
            TextTable evidenceTable = new TextTable(names, evidence);
            evidenceTable.printTable();
        }


        catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
    }
}
