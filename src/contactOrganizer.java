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

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getDob() {
        return dob;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getSalary() {
        return salary;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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

    private boolean isFull() {
        return nextIndex >= contactsArray.length;
    }

    private void extendArray() {
        Contacts[] tempContactsArray = new Contacts[contactsArray.length + loadFact];
        for (int i = 0; i < contactsArray.length; i++) {
            tempContactsArray[i] = contactsArray[i];
        }
        contactsArray = tempContactsArray;
    }

    public Contacts[] getContactsArray() {
        return contactsArray;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public boolean add(Contacts contacts) {
        if (isFull()) {
            extendArray();
        }
        contactsArray[nextIndex++] = contacts;
        return true;
    }

    public Contacts search(String nameOrPhone) {
        for (int i = 0; i < nextIndex; i++) {
            if (contactsArray[i].getName().equalsIgnoreCase(nameOrPhone) || contactsArray[i].getPhoneNumber().equalsIgnoreCase(nameOrPhone)) {
                return contactsArray[i];
            }
        }
        return null;
    }
}

public class contactOrganizer {
    public static ContactList contactList = new ContactList(10, 5);

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
                updateContacts();
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
        String lastID = contactsArray[contactList.getNextIndex() - 1].getId();
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

    public static void updateContacts() {
        Scanner input = new Scanner(System.in);
        do {
            System.out.println("------------------------------------------------------------");
            System.out.println("|                      Update Contact                      |");
            System.out.println("------------------------------------------------------------\n");
            System.out.print("Search Contact by Name or Phone Number : ");
            String nameOrPhone = input.next();
            /*int index = contactList.search(nameOrPhone);
            if (index == -1) {
                System.out.println("No Contact found for \"" + nameOrPhone + "\"");
                continue;
            }*/
            Contacts contacts = contactList.search(nameOrPhone);
            if (contacts == null) {
                System.out.println("No Contact found for \"" + nameOrPhone + "\"");
                continue;
            }
            System.out.println("Contact ID   : " + contacts.getId());
            System.out.println("Name         : " + contacts.getName());
            System.out.println("Phone Number : " + contacts.getPhoneNumber());
            System.out.println("Company Name : " + contacts.getCompanyName());
            System.out.println("Salary       : " + contacts.getSalary());
            System.out.println("Birthday     : " + contacts.getDob());
            System.out.println("\nWhat do you want to update....\n");
            System.out.println("[01] Name");
            System.out.println("[02] Phone Number");
            System.out.println("[03] Company Name");
            System.out.println("[04] Salary");
            System.out.print("Enter an option to continue : ");
            int opt = input.nextInt();
            switch (opt) {
                case 1:
                    updateName(contacts);
                    break;
                case 2:
                    updatePhoneNumber(contacts);
                    break;
                case 3:
                    updateCompanyName(contacts);
                    break;
                case 4:
                    updateSalary(contacts);
                    break;
            }
            break;
        } while (true);
    }

    public static void updatePhoneNumber(Contacts contacts) {
        Scanner input = new Scanner(System.in);
        System.out.print("\033[7A");
        System.out.print("\033[0J");
        System.out.println("Update Phone Number");
        System.out.println("====================");
        String phoneNumber;
        do {
            System.out.print("Input New Phone Number : ");
            phoneNumber = input.next();
            if (!isValidPhoneNumber(phoneNumber)) {
                System.out.println("Invalid Phone Number....Try again..");
                continue;
            }
            break;
        } while (true);
        contacts.setPhoneNumber(phoneNumber);
        System.out.println("Contact update successfully...");
        System.out.print("Do you want to update a another Contact(Y/N) : ");
        String opt = input.next();
        if (opt.equalsIgnoreCase("Y")) {
            clearConsole();
            updateContacts();
        } else if (opt.equalsIgnoreCase("N")) {
            clearConsole();
            homePage();
        }
    }

    public static void updateSalary(Contacts contacts) {
        Scanner input = new Scanner(System.in);
        System.out.print("\033[7A");
        System.out.print("\033[0J");
        System.out.println("Update Salary");
        System.out.println("==============");
        double salary;
        do {
            System.out.print("Input New Salary : ");
            salary = input.nextDouble();
            if (salary < 0) {
                System.out.println("Invalid Salary....Try again..");
                continue;
            }
            break;
        } while (true);
        contacts.setSalary(salary);
        System.out.println("Contact update successfully...");
        System.out.print("Do you want to update a another Contact(Y/N) : ");
        String opt = input.next();
        if (opt.equalsIgnoreCase("Y")) {
            clearConsole();
            updateContacts();
        } else if (opt.equalsIgnoreCase("N")) {
            clearConsole();
            homePage();
        }
    }

    public static void updateCompanyName(Contacts contacts) {
        Scanner input = new Scanner(System.in);
        System.out.print("\033[7A");
        System.out.print("\033[0J");
        System.out.println("Update Company Name");
        System.out.println("====================");
        System.out.print("Input New Company Name : ");
        String companyName = input.next();
        contacts.setCompanyName(companyName);
        System.out.println("Contact update successfully...");
        System.out.print("Do you want to update a another Contact(Y/N) : ");
        String opt = input.next();
        if (opt.equalsIgnoreCase("Y")) {
            clearConsole();
            updateContacts();
        } else if (opt.equalsIgnoreCase("N")) {
            clearConsole();
            homePage();
        }
    }

    public static void updateName(Contacts contacts) {
        Scanner input = new Scanner(System.in);
        System.out.print("\033[7A");
        System.out.print("\033[0J");
        System.out.println("Update Name");
        System.out.println("============");
        System.out.print("Input New Name : ");
        String name = input.next();
        contacts.setName(name);
        System.out.println("Contact update successfully...");
        System.out.print("Do you want to update a another Contact(Y/N) : ");
        String opt = input.next();
        if (opt.equalsIgnoreCase("Y")) {
            clearConsole();
            updateContacts();
        } else if (opt.equalsIgnoreCase("N")) {
            clearConsole();
            homePage();
        }
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
