
import java.util.Scanner;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "unused", "Duplicates", "AccessStaticViaInstance"})
public class Main {

    private static DatabaseConnection dbconnect = new DatabaseConnection();

    public static void main(String[] args) {
        boolean cont = true;
        DatabaseRepopulator dr = new DatabaseRepopulator();

        Scanner scanner = new Scanner(System.in);
        System.out.println("WELCOME TO THE CRYPTID DATABASE\n");

        while (cont) {
            System.out.println("What would you like to do?");
            System.out.println("1. View a Table\n" +
                    "2. Search\n" +
                    "3. Edit existing Record\n" +
                    "4. Create a new Entry\n" +
                    "5. Exit");
            int response = scanner.nextInt();

            switch (response)
            {
                case 1: {
                    printTable();
                    break;
                }
                case 2: {
                    searchTable();
                    break;
                }
                case 3: {
                    updateTable();
                    break;
                }
                case 4: {
                    createNewEntry();
                    break;
                }
                case 5: {
                    cont = false;
                    System.out.println("Thank you and stay Cryptic! :)");
                    break;
                }
                default: {
                    System.out.println("Invalid Request");
                    break;
                }

            }
            if (cont) {
                System.out.println("\nWould you like to continue?");
                scanner.nextLine();
                String contin = scanner.nextLine();
                if ((contin.charAt(0) == 'n' || contin.charAt(0) == 'N')) {
                    System.out.println("Thank you and stay Cryptic! :)");
                    cont = false;
                }
            }
        }
    }

    private static void createNewEntry() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to create?");
        System.out.println("1. Cryptid\n" +
                "2. Viewer\n" +
                "3. Publication\n" +
                "4. Sighting\n" +
                "5. Media\n" +
                "6. Folklore\n" +
                "7. Evidence");
        int response = scanner.nextInt();

        switch (response) {
            case 1:
                //put method for making new cryptid here
                break;
            case 2:
                //put method for making new viewer here
                break;
            case 3:
                //put method for making new publication here
                break;
            case 4:
                //put method for making new sighting here
                break;
            case 5:
                //put method for making new media here
                break;
            case 6:
                //put method for making new folklore here
                break;
            case 7:
                //put method for making new evidence here
                break;
            default:
                break;

        }
    }

    private static void updateTable() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What would you like to update?");
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
                dbconnect.updateCryptid();
                break;
            case 2:
                dbconnect.updateViewer();
                break;
            case 3:
                dbconnect.updatePublication();
                break;
            case 4:
                dbconnect.updateSighting();
                break;
            case 5:
                dbconnect.updateMedia();
                break;
            case 6:
                dbconnect.updateFolklore();
                break;
            case 7:
                dbconnect.updateEvidence();
                break;
            default:
                break;

        }
    }

    private static void searchTable() {
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

    private static void printTable() {
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

        switch (response) {
            case 1:
                dbconnect.printAllCryptids();
                break;
            case 2:
                dbconnect.printAllViewers();
                break;
            case 3:
                dbconnect.printAllPublications();
                break;
            case 4:
                dbconnect.printAllSightings();
                break;
            case 5:
                dbconnect.printAllMedia();
                break;
            case 6:
                dbconnect.printAllFolklore();
                break;
            case 7:
                dbconnect.printAllEvidence();
                break;
            default:
                System.out.println("Invalid Input");
        }
    }
}


