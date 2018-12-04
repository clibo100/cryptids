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
        String[] names = {"Cryptid Name", "Latitude", "Longitude", "Date Seen", "Viewer Name"};
        try {
            PreparedStatement sightingPS = conn.prepareStatement("SELECT COUNT(*) FROM sightings");
            ResultSet sightingsRS = sightingPS.executeQuery();
            sightingsRS.next();
            int sightingCount = sightingsRS.getInt(1);

            String[][] sightings = new String[sightingCount][5];
            PreparedStatement getSightings = conn.prepareStatement("SELECT c.Cryptid_Name, s.Latitude, s.Longitude, s.Date_Seen, v.Viewer_Name\n" +
                    "FROM sightings s\n" +
                    "LEFT JOIN cryptid c\n" +
                    "ON s.CID = c.CID\n" +
                    "LEFT JOIN viewer v\n" +
                    "ON v.Viewer_ID = s.Viewer_ID;");
            ResultSet getRS = getSightings.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                sightings[i][0] = getRS.getString(1);
                sightings[i][1] = Float.toString(getRS.getFloat(2));
                sightings[i][2] = Float.toString(getRS.getFloat(3));
                sightings[i][3] = String.valueOf(getRS.getDate(4));
                sightings[i][4] = getRS.getString(5);
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
        String[] names = {"Author", "Title", "Year", "Publisher"};
        try {
            PreparedStatement publicationPS = conn.prepareStatement("SELECT COUNT(*) FROM publications");
            ResultSet publicationRS = publicationPS.executeQuery();
            publicationRS.next();
            int publicationCount = publicationRS.getInt(1);

            String[][] publications = new String[publicationCount][4];
            PreparedStatement getPublications = conn.prepareStatement("SELECT v.Viewer_Name, p.Publication, p.Year, p.Publisher\n" +
                    "FROM publications p\n" +
                    "LEFT JOIN viewer v\n" +
                    "ON v.Viewer_ID = p.Viewer_ID");
            ResultSet getRS = getPublications.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                publications[i][0] = getRS.getString(1);
                publications[i][1] = getRS.getString(2);
                publications[i][2] = Integer.toString(getRS.getInt(3));
                publications[i][3] = getRS.getString(4);
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
        String[] names = {"Cryptid", "Title", "Year", "Format", "Rating"};
        try {
            PreparedStatement mediaPS = conn.prepareStatement("SELECT COUNT(*) FROM media");
            ResultSet mediaRS = mediaPS.executeQuery();
            mediaRS.next();
            int mediaCount = mediaRS.getInt(1);

            String[][] media = new String[mediaCount][5];
            PreparedStatement getMedia = conn.prepareStatement("select c.Cryptid_Name, m.Title, m.Year, m.Format, m.Rating\n" +
                    "FROM media m, cryptid c\n" +
                    "WHERE c.CID = m.CID");
            ResultSet getRS = getMedia.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                media[i][0] = getRS.getString(1);
                media[i][1] = getRS.getString(2);
                media[i][2] = Integer.toString(getRS.getInt(3));
                media[i][3] = getRS.getString(4);
                media[i][4] = Integer.toString(getRS.getInt(5));
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
        String[] names = {"Cryptid", "Folklore", "Year", "Location"};
        try {
            PreparedStatement folklorePS = conn.prepareStatement("SELECT COUNT(*) FROM folklore");
            ResultSet folkloreRS = folklorePS.executeQuery();
            folkloreRS.next();
            int folkloreCount = folkloreRS.getInt(1);

            String[][] folklore = new String[folkloreCount][4];
            PreparedStatement getFolklore = conn.prepareStatement("select c.Cryptid_Name, f.Folklore, f.Year, f.Location\n" +
                    "FROM cryptid c, folklore f\n" +
                    "WHERE c.CID = f.CID");
            ResultSet getRS = getFolklore.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                folklore[i][0] = getRS.getString(1);
                folklore[i][1] = getRS.getString(2);
                folklore[i][2] = Integer.toString(getRS.getInt(3));
                folklore[i][3] = getRS.getString(4);
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
        String[] names = {"Cryptid", "Date", "Evidence", "Location"};
        try {
            PreparedStatement evidencePS = conn.prepareStatement("SELECT COUNT(*) FROM evidence");
            ResultSet evidenceRS = evidencePS.executeQuery();
            evidenceRS.next();
            int evidenceCount = evidenceRS.getInt(1);

            String[][] evidence = new String[evidenceCount][4];
            PreparedStatement getEvidence = conn.prepareStatement("select c.Cryptid_Name, e.Date, e.Medium, e.Location\n" +
                    "FROM cryptid c, evidence e\n" +
                    "WHERE c.CID = e.CID");
            ResultSet getRS = getEvidence.executeQuery();
            int i = 0;
            while (getRS.next())
            {
                evidence[i][0] = getRS.getString(1);
                evidence[i][1] = String.valueOf(getRS.getDate(2));
                evidence[i][2] = getRS.getString(3);
                evidence[i][3] = getRS.getString(4);
                i++;
            }
            TextTable evidenceTable = new TextTable(names, evidence);
            evidenceTable.printTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    static void updateCryptid() {
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
                String name = scanner.nextLine();

                PreparedStatement updateNamePS = conn.prepareStatement("UPDATE cryptid SET Cryptid_Name = ? WHERE CID = ?");
                updateNamePS.setString(1, name);
                updateNamePS.setInt(2, CID);
                updateNamePS.executeUpdate();
                conn.commit();

                System.out.println("Updated Cryptid ID: " + CID + " to name: " + name);

            }
            else if (response == 2)
            {

            }
            else if (response == 3)
            {

            }
            else if (response == 4)
            {

            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
