import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

@SuppressWarnings({"Duplicates", "SqlDialectInspection", "SqlNoDataSourceInspection"})
public class DatabaseRepopulator {


    private static DatabaseConnection dbconnect = new DatabaseConnection();

    public DatabaseRepopulator() {

    }

    private static void Repopulate() {
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
