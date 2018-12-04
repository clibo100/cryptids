import java.sql.*;
import com.github.javafaker.Faker;
import dnl.utils.text.table.TextTable;

import java.util.Random;
import java.util.Scanner;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "unused", "Duplicates"})
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
                    System.out.println("Thank you and stay Cryptid! :)");
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
                    System.out.println("Thank you and stay Cryptid! :)");
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
                searchCryptids();
                break;
            case 2:
                searchViewers();
                break;
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            default:
                break;

        }
    }

    private static void searchCryptids()
    {
        Scanner scanner = new Scanner(System.in);
        Connection conn = dbconnect.getConn();
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

    private static void searchViewers()
    {
        Scanner scanner = new Scanner(System.in);
        Connection conn = dbconnect.getConn();
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

    private static void Repopulate()
    {
        GenerateCryptids();
        GenerateHundredEvidence();
        GenerateHundredFolklore();
        GenerateHundredViewers();
        GenerateSightings();
        GeneratePublications();
        GenerateHundredMedia();
    }

    private static void GenerateCryptids() {
        System.out.println("Generating Cryptids...");

        //database connection
        Connection conn = dbconnect.getConn();
        try {
            //check how many cryptids are currently in DB
            PreparedStatement checkCryptids = conn.prepareStatement("SELECT COUNT(*) FROM cryptid");
            ResultSet checkRS = checkCryptids.executeQuery();
            checkRS.next();

            //if not enough are in there, generate cryptids
            if (checkRS.getInt(1) < 19) {
                PreparedStatement insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Bigfoot','Big, Man-shaped hairy creature common in PNW',250.00,7.50,'Land')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Loch Ness Monster','Aquatic Scottish animal with long neck',5000.00,20.00,'Water')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Bessie','Lake Eyrie Monster',5000.00,20.00,'Water')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Dewey Lake Monster','Michigan Bigfoot',250.00,7.50,'Land')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Flying Rod','Sky squid',250.00,7.50,'Sky')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Giant Anaconda','MegaConda',250.00,15.00,'Land')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Globster','Decaying Sea Animal',300.00,8.00,'Water')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Honey Island Swamp Monster','Hominoid/primate',200.00,8.00,'Land')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Isshii','Lake Monster',200.00,8.00,'Water')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Kraken','Sea Octopus',5000.00,20.00,'Water')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Megalodon','Giant Shark',5000.00,20.00,'Water')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Mermaid','Humanoid Fish',175.00,5.50,'Water')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Reptilian','Bipedal Humanoid Reptile',175.00,6.50,'Land')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Chupacabra','Bipedal Humanoid Reptile/Fish',175.00,6.50,'Land')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Jackalope','Horned Rabbit',20.00,1.50,'Land')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Wendigo','Cannibalistic Human Monster',250.00,10.00,'Land')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Yeti','Abominable Snowman',250.00,8.50,'Land')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Jersey Devil','Winged Bipedal Horse',250.00,8.50,'Sky')");
                insertCryptids.executeUpdate();
                insertCryptids = conn.prepareStatement("INSERT INTO cryptid VALUES (null,'Mothman','Winged Bipedal Monster',250.00,8.50,'Sky')");
                insertCryptids.executeUpdate();

                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Generation Complete");
    }

    private static void GenerateHundredViewers() {
        System.out.println("Generating Viewers...");
        Random rand = new Random();
        Faker faker = new Faker();
        try {
            //connect to database
            Connection conn = dbconnect.getConn();
            //prepare statement for inserting a viewer
            PreparedStatement pstmt = conn.prepareStatement("insert into viewer values (null, ?, ?, ?, ?, ?,?)");

            //make 100 randomly generated new viewers and add them to DB
            for (int i = 0; i < 100; ++i) {
                Viewer viewer = new Viewer(faker.name().fullName(), faker.address().cityName(), faker.job().position(),
                        rand.nextInt(60) + 15, rand.nextInt(30), rand.nextInt(30));
                pstmt.setString(1, viewer.getName());
                pstmt.setString(2, viewer.getLocation());
                pstmt.setInt(3, viewer.getAge());
                pstmt.setInt(4, viewer.getSightings());
                pstmt.setString(5, viewer.getCredentials());
                pstmt.setInt(6, viewer.getPublications());

                pstmt.executeUpdate();

            }

            conn.commit();

            System.out.println("Generation Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void GenerateSightings() {
        Random rand = new Random();
        Faker faker = new Faker();

        System.out.println("Generating Sightings for Viewers...");

        try {
            //connect to database
            Connection conn = dbconnect.getConn();

            //prepare statement for inserting sighting
            PreparedStatement pstmt = conn.prepareStatement("insert into sightings values (?, ?, ?, ?, ?)");

            //get the number of cryptids in the database used later to select random cryptids
            PreparedStatement cryptidPS = conn.prepareStatement("SELECT COUNT(*) FROM cryptid");
            ResultSet cryptidRS = cryptidPS.executeQuery();
            cryptidRS.next();
            int cryptidCount = cryptidRS.getInt(1);

            //get all viewers
            PreparedStatement viewerPS = conn.prepareStatement("SELECT * FROM viewer");
            ResultSet viewerRS = viewerPS.executeQuery();

            //generate the correct amount of sightings for each viewer
            while (viewerRS.next()) {
                //see how many sightings the viewer already has
                PreparedStatement check = conn.prepareStatement("SELECT COUNT(*) FROM sightings WHERE Viewer_ID = ?");
                check.setInt(1, viewerRS.getInt(1));
                ResultSet checkRS = check.executeQuery();
                checkRS.next();
                int viewerSightings = checkRS.getInt(1);

                //if the viewer doesn't have enough sightings to match the viewer table, generate the rest
                //randomly and add to the sighting table
                for (int i = 0; i < viewerRS.getInt(5) - viewerSightings; ++i) {
                    Sighting sighting = new Sighting(rand.nextInt(cryptidCount) + 1, viewerRS.getInt(1),
                            faker.date().birthday(1, viewerRS.getInt(4) - 13), Float.parseFloat(faker.address().latitude()), Float.parseFloat(faker.address().longitude()));
                    pstmt.setInt(1, sighting.getCID());
                    pstmt.setInt(5, sighting.getViewerID());
                    java.sql.Date theDate = new java.sql.Date(sighting.getDate().getTime());
                    pstmt.setDate(4, theDate);
                    pstmt.setFloat(2, sighting.getLatitude());
                    pstmt.setFloat(3, sighting.getLongitude());

                    pstmt.executeUpdate();

                }
            }


            conn.commit();
            System.out.println("Generation Complete");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void GeneratePublications() {
        try {
            System.out.println("Generating Publications for Viewers...");

            //connect to database
            Connection conn = dbconnect.getConn();

            //prepare statement for inserting new publication
            PreparedStatement pstmt = conn.prepareStatement("insert into publications values (?, ?, ?, ?)");

            //get all the viewers
            PreparedStatement viewerPS = conn.prepareStatement("SELECT * FROM viewer");
            ResultSet viewerRS = viewerPS.executeQuery();

            //generate the right amount of publications for each viewer
            while (viewerRS.next()) {
                //check to see how many publications the viewer already has
                PreparedStatement countCheck = conn.prepareStatement("SELECT COUNT(*) FROM publications WHERE Viewer_ID = ?");
                countCheck.setInt(1, viewerRS.getInt(1));
                ResultSet countCheckRS = countCheck.executeQuery();
                countCheckRS.next();
                int viewerPublications = countCheckRS.getInt(1);

                //if they don't have enough, randomly generate until they do
                for (int i = 0; i < viewerRS.getInt(7) - viewerPublications; ++i) {
                    Publication publication = new Publication(viewerRS.getInt(1));

                    //check to see if randomly generated publication matches a previous one by the same viewer
                    //so it can create a new one because my publication generator is unoriginal
                    while (true) {
                        PreparedStatement check = conn.prepareStatement("SELECT * FROM publications WHERE Viewer_ID = ? AND Publication = ?");
                        check.setInt(1, viewerRS.getInt(1));
                        check.setString(2, publication.getPublication());
                        ResultSet checkRS = check.executeQuery();
                        if (checkRS.next()) {
                            publication = new Publication(viewerRS.getInt(1));
                        } else {
                            break;
                        }
                    }


                    pstmt.setInt(1, publication.getViewerID());
                    pstmt.setString(2, publication.getPublication());
                    pstmt.setInt(3, publication.getYear());
                    pstmt.setString(4, publication.getPublisher());

                    pstmt.executeUpdate();

                }
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Generation Complete");
    }

    private static void GenerateHundredEvidence() {
        System.out.println("Generating Evidence...");

        //connect to database
        Connection conn = dbconnect.getConn();
        Random rand = new Random();
        Faker faker = new Faker();

        try {
            //get number of cryptids
            PreparedStatement cryptidPS = conn.prepareStatement("SELECT COUNT(*) FROM cryptid");
            ResultSet cryptidRS = cryptidPS.executeQuery();
            cryptidRS.next();
            int cryptidCount = cryptidRS.getInt(1);
            String[] medium = {"twig", "fuzzy picture", "footprint", "sound recording", "mud smears", "droppings", "abandoned den", "dead prey carcass"};

            //generate a hundred pieces of evidence
            PreparedStatement insertEvidence = conn.prepareStatement("INSERT INTO evidence VALUES (?, ?, ?, ?)");
            for (int i = 0; i < 100; ++i) {
                insertEvidence.setInt(1, rand.nextInt(cryptidCount) + 1);

                java.sql.Date theDate = new java.sql.Date(faker.date().birthday(1, 80).getTime());
                insertEvidence.setDate(2, theDate);

                insertEvidence.setString(3, medium[rand.nextInt(medium.length)]);

                insertEvidence.setString(4, faker.address().city());

                insertEvidence.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Generation Complete");

    }

    private static void GenerateHundredFolklore() {
        System.out.println("Generating Folklore...");

        //connect to database
        Connection conn = dbconnect.getConn();
        Faker faker = new Faker();
        Random rand = new Random();
        String[] folklore = {"legend", "myth", "folk tale", "wive's tale", "sonnet", "fairy tale", "song"};

        try {
            //get number of cryptids
            PreparedStatement cryptidPS = conn.prepareStatement("SELECT COUNT(*) FROM cryptid");
            ResultSet cryptidRS = cryptidPS.executeQuery();
            cryptidRS.next();
            int cryptidCount = cryptidRS.getInt(1);

            //generate a hundred pieces of folklore
            PreparedStatement insertFolklore = conn.prepareStatement("INSERT INTO folklore VALUES (?, ?, ?, ?)");
            for (int i = 0; i < 100; ++i) {
                insertFolklore.setInt(1, rand.nextInt(cryptidCount) + 1);

                insertFolklore.setString(2, folklore[rand.nextInt(folklore.length)]);

                insertFolklore.setInt(3, rand.nextInt(500) + 1200);
                insertFolklore.setString(4, faker.address().country());
                insertFolklore.executeUpdate();
            }


            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Generation Complete");
    }

    private static void GenerateHundredMedia() {
        System.out.println("Generating Media...");
        //make connection
        Connection conn = dbconnect.getConn();
        Faker faker = new Faker();
        Random rand = new Random();
        String[] format = {"Movie", "Book", "TV Show"};

        try {
            //get number of cryptids
            PreparedStatement cryptidPS = conn.prepareStatement("SELECT COUNT(*) FROM cryptid");
            ResultSet cryptidRS = cryptidPS.executeQuery();
            cryptidRS.next();
            int cryptidCount = cryptidRS.getInt(1);

            //prepare insert statement
            PreparedStatement insertMedia = conn.prepareStatement("INSERT INTO media VALUES (?, ?, ?, ?, ?)");

            //insert 100 random media pieces
            for (int i = 0; i < 100; ++i)
            {
                insertMedia.setInt(1, rand.nextInt(cryptidCount) + 1);
                insertMedia.setString(2, faker.book().title());
                insertMedia.setInt(3, rand.nextInt(100)+1900);
                insertMedia.setString(4, format[rand.nextInt(format.length)]);
                insertMedia.setInt(5, rand.nextInt(10));
                insertMedia.executeUpdate();
            }
            conn.commit();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        System.out.println("Generation Complete");
    }
}


