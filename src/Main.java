import java.sql.*;
import dnl.utils.text.table.TextTable;

import java.util.Scanner;

@SuppressWarnings({"SqlDialectInspection", "SqlNoDataSourceInspection", "unused", "Duplicates", "AccessStaticViaInstance"})
public class Main {

    private static DatabaseConnection dbconnect = new DatabaseConnection();

    public static void main(String[] args) {
        boolean cont = true;

        Scanner scanner = new Scanner(System.in);
        System.out.println("WELCOME TO THE CRYPTID DATABASE\n");

        while (cont) {
            System.out.println("What would you like to do?");
            System.out.println("1. View a Table\n" +
                    "2. Search\n" +
                    "3. Edit existing Record\n" +
                    "4. Exit");
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
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
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


