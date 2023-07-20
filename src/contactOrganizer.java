import java.time.LocalDate;
import java.util.*;

class Contacts {
    private String id;
    private String name;
    private String phoneNumber;
    private String companyName;
    private String dob;
    private double salary;

    Contacts(String id, String name, String phoneNumber, String companyName, double salary, String dob) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.salary = salary;
        this.dob = dob;
    }
    public String getId() {
        return id;
    }
}

class ContactList {
    private Contacts[] contactsArray;
    private int nextIndex;
    private int initSize;
    private int loadFact;

    public ContactList(int initSize, int loadFact) {
        contactsArray = new Contacts[initSize];
        this.initSize = initSize;
        this.loadFact = loadFact;
        nextIndex = 0;
    }
    private boolean isFull(){
        return nextIndex>=contactsArray.length;
    }
    private void extendArray(){
        Contacts[] tempContactsArray = new Contacts[contactsArray.length+loadFact];
        for (int i = 0; i < contactsArray.length; i++) {
            tempContactsArray[i]=contactsArray[i];
        }
        contactsArray=tempContactsArray;
    }
    public Contacts[] getContactsArray() {
        return contactsArray;
    }
    public int getNextIndex(){
        return nextIndex;
    }
    public boolean add(Contacts contacts){
        if (isFull()){
            extendArray();
        }
        contactsArray[nextIndex++]=contacts;
        return true;
    }
}

public class contactOrganizer {
    public static ContactList contactList = new ContactList(10,5);
    public static void main(String[] args) {
        homePage();
    }
    public static void homePage() {
        Scanner input = new Scanner(System.in);
        System.out.println("------------------------------------------------------------");
        System.out.println("                 iFriend Contacts Organizer                 ");
        System.out.println("------------------------------------------------------------\n");
        System.out.println("[01] Add Contacts");
        System.out.println("[02] Update Contacts");
        System.out.println("[03] Delete Contacts");
        System.out.println("[04] Search Contacts");
        System.out.println("[05] List Contacts");
        System.out.println("[06] Exit\n");
        System.out.print("Enter an option to continue : ");
        int opt = input.nextInt();
        switch (opt) {
            case 1:
                clearConsole();
                addContacts();
                break;
            case 2:
                clearConsole();
                //updateContacts();
                break;
            case 3:
                clearConsole();
                //deleteContacts();
                break;
            case 4:
                clearConsole();
                //searchContacts();
                break;
            case 5:
                clearConsole();
                //listContacts();
                break;
            case 6:
                System.exit(0);
        }
    }

    public static String generateContactID() {
        Contacts[] contactsArray = contactList.getContactsArray();
        if (contactList.getNextIndex() == 0) {
            return "C0001";
        }
        String lastID = contactsArray[contactList.getNextIndex()-1].getId();
        int lastNum = Integer.parseInt(lastID.substring(1));
        return String.format("C%04d", (lastNum + 1));
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber.charAt(0) == '0' && phoneNumber.length() == 10) {
            return true;
        }
        return false;
    }

    public static boolean isValidDob(String dob) {
        String y = dob.substring(0, 4);
        int year = Integer.parseInt(y);
        String m = dob.substring(5, 7);
        int month = Integer.parseInt(m);
        String d = dob.substring(8);
        int day = Integer.parseInt(d);

        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();

        if (year < currentYear) {
            return true;
        }
        if (year == currentYear) {
            if (month < currentMonth) {
                return true;
            } else if (month == currentMonth) {
                if (day <= currentDay) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void addContacts() {
        Scanner input = new Scanner(System.in);
        String id;
        String name;
        String phoneNumber;
        String companyName;
        double salary;
        String dob;
        do {
            System.out.println("------------------------------------------------------------");
            System.out.println("|                      Add Contact                         |");
            System.out.println("------------------------------------------------------------\n");
            id = generateContactID();
            System.out.println(id + "\n=======\n");
            System.out.print("Name              : ");
            name = input.next();
            do {
                System.out.print("Phone Number      : ");
                phoneNumber = input.next();
                if (!isValidPhoneNumber(phoneNumber)) {
                    System.out.print("\tInvalid Phone Number...\nDo you want to add phone number again(Y/N:)");
                    String opt = input.next();
                    if (opt.equalsIgnoreCase("Y")) {
                        continue;
                    } else if (opt.equalsIgnoreCase("N")) {
                        homePage();
                    }
                }
                break;
            } while (true);
            System.out.print("Company Name      : ");
            companyName = input.next();
            do {
                System.out.print("Salary            : ");
                salary = input.nextDouble();
                if (salary < 0) {
                    System.out.println("Wrong input...Please try again..");
                    continue;
                }
                break;
            } while (true);
            do {
                System.out.print("B'Day(YYYY-MM-DD)	: ");
                dob = input.next();
                if (!isValidDob(dob)) {
                    System.out.print("\tInvalid Birthday...\nDo you want to add Birthday again(Y/N:)");
                    String opt = input.next();
                    if (opt.equalsIgnoreCase("Y")) {
                        continue;
                    } else if (opt.equalsIgnoreCase("N")) {
                        homePage();
                    }
                }
                break;
            } while (true);
            Contacts contacts = new Contacts(id, name, phoneNumber, companyName, salary, dob);
            contactList.add(contacts);
            System.out.println("Contact has been added successfully...");
            System.out.print("Do you want to add another Contact (Y/N) : ");
            String opt = input.next();
            if (opt.equalsIgnoreCase("Y")) {
                clearConsole();
                continue;
            } else if (opt.equalsIgnoreCase("N")) {
                clearConsole();
                homePage();
                break;
            }
            break;
        } while (true);
    }

    public final static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
// Handle any exceptions.
        }
    }
}
