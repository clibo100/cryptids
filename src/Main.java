import java.sql.*;
import com.github.javafaker.Faker;
import dnl.utils.text.table.TextTable;

import java.util.Random;
import java.util.Scanner;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "unused", "Duplicates", "AccessStaticViaInstance"})
public class Main {

    private static DatabaseConnection dbconnect = new DatabaseConnection();

    public static void main(String[] args)
    {
        boolean cont = true;

        Scanner scanner = new Scanner(System.in);
        System.out.println("WELCOME TO THE CRYPTID DATABASE\n");

        while (cont)
        {
            System.out.println("What would you like to do?");
            System.out.println("1. View a Table\n" +
                    "2. Search\n" +
                    "3. Exit");
            int response = scanner.nextInt();

            switch (response)
            {
                case 1:
                {
                    printTable();
                    break;
                }
                case 2:
                {
                    searchTable();
                    break;
                }
                case 3:
                {
                    cont = false;
                    System.out.println("Thank you and stay Cryptic! :)");
                    break;
                }
                default:
                {
                    System.out.println("Invalid Request");
                    break;
                }

            }
            if (cont)
            {
                System.out.println("\nWould you like to continue?");
                scanner.nextLine();
                String contin = scanner.nextLine();
                if ((contin.charAt(0) == 'n' || contin.charAt(0) == 'N'))
                {
                    System.out.println("Thank you and stay Cryptic! :)");
                    cont = false;
                }
            }
        }
    }

    private static void searchTable()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to search?");
        System.out.println("1. Cryptids\n" +
                "2. Viewers\n" +
                "3. Publications\n" +
                "4. Sightings\n" +
                "5. Media\n" +
                "6. Folklore\n" +
                "7. Evidence");
        int response = scanner.nextInt();

        switch (response) {
            case 1:
                dbconnect.searchCryptids();
                break;
            case 2:
                dbconnect.searchViewers();
                break;
            case 3:
                dbconnect.searchPublications();
                break;
            case 4:
                dbconnect.searchSightings();
                break;
            case 5:
                dbconnect.searchMedia();
                break;
            case 6:
                dbconnect.searchFolklore();
                break;
            case 7:
                dbconnect.searchEvidence();
                break;
            default:
                break;

        }
    }

    private static void printTable()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWhich table would you like to see?");
        System.out.println("1. Cryptids\n" +
                "2. Viewers\n" +
                "3. Publications\n" +
                "4. Sightings\n" +
                "5. Media\n" +
                "6. Folklore\n" +
                "7. Evidence");
        int response = scanner.nextInt();

        switch (response)
        {
            case 1:
                printAllCryptids();
                break;
            case 2:
                printAllViewers();
                break;
            case 3:
                printAllPublications();
                break;
            case 4:
                printAllSightings();
                break;
            case 5:
                printAllMedia();
                break;
            case 6:
                printAllFolklore();
                break;
            case 7:
                printAllEvidence();
                break;
            default:
                System.out.println("Invalid Input");
        }
    }

    private static void printAllCryptids()
    {
        System.out.println("All Cryptids: ");
        Connection conn = dbconnect.getConn();
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

    private static void printAllViewers()
    {
        System.out.println("All Viewers: ");
        Connection conn = dbconnect.getConn();
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

    private static void printAllSightings()
    {
        System.out.println("All Sightings: ");
        Connection conn = dbconnect.getConn();
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

    private static void printAllPublications()
    {
        System.out.println("All Publications: ");
        Connection conn = dbconnect.getConn();
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

    private static void printAllMedia()
    {
        System.out.println("All Media: ");
        Connection conn = dbconnect.getConn();
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

    private static void printAllFolklore()
    {
        System.out.println("All Media: ");
        Connection conn = dbconnect.getConn();
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

    private static void printAllEvidence()
    {
        System.out.println("All Evidence: ");
        Connection conn = dbconnect.getConn();
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

}


