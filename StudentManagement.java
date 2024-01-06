import  java.util.*;
import java.io.*;
public class StudentManagement {
    private static final String FILE_PATH = "students.txt";

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int ch = 0;
        do {
            System.out.println("Menu :");
            System.out.println("1.create");
            System.out.println("2.display");
            System.out.println("3.update");
            System.out.println("4.delete");
            System.out.println("5.exit");
            System.out.println("Enter choice : ");
            ch = in.nextInt();
            switch (ch) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    display();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    System.out.println("EXIT...");
                    return;
                default:
                    System.out.println("Invalid choice .Please enter correct choice");
            }
        } while (ch != 5);
    }


    public static void addStudent() {
        try {
            FileWriter fileWriter = new FileWriter(FILE_PATH, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter);
            Scanner sc = new Scanner(System.in);

            while (true) {
                try {
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    if (!name.matches("[a-zA-Z]+")) {
                        throw new IllegalArgumentException("Invalid name. Please enter alphabetic characters only.");
                    }

                    String phoneNumber = getValidPhoneNumber(sc);

                    System.out.print("Address: ");
                    String address = sc.nextLine();

                    System.out.print("Email: ");
                    String email = getValidEmail(sc);

                    System.out.print("Date of Birth (YYYY-MM-DD): ");
                    String dob = getValidDateOfBirth(sc);

                    printWriter.println(name + "," + phoneNumber + "," + address + "," + email + "," + dob);
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }

            printWriter.close();
        } catch (IOException v) {
            System.out.println(v.getMessage());
        }
    }
    private static String getValidPhoneNumber(Scanner sc) {
        while (true) {
            try {
                System.out.print("Phone Number (10 digits): ");
                String phoneNumber = sc.nextLine();
                long phone = Long.parseLong(phoneNumber);
                if (phone < 0 || phoneNumber.length() != 10) {
                    throw new IllegalArgumentException("Invalid phone number. Please enter a 10-digit number.");
                }
                return phoneNumber;
            } catch (NumberFormatException e) {
                System.out.println("Invalid phone number. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static String getValidEmail(Scanner sc) {
        while (true) {
            // Simple email validation using a regular expression
            System.out.print("Email: ");
            String email = sc.nextLine();
            if (email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                return email;
            } else {
                System.out.println("Invalid email. Please enter a valid email address.");
            }
        }
    }

    private static String getValidDateOfBirth(Scanner sc) {
        while (true) {
            System.out.print("Date of Birth (YYYY-MM-DD): ");
            String dob = sc.nextLine();
            try {
                // Simple date format validation
                java.sql.Date.valueOf(dob);
                return dob;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD).");
            }
        }
    }

    public static void display() {
        try (FileReader fileReader = new FileReader(FILE_PATH);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] studentInfo = line.split(",");
                if (studentInfo.length == 5) {
                    System.out.println("Name: " + studentInfo[0] + ", Phone Number: " + studentInfo[1]+", Address: "+studentInfo[2]+", Email: "+studentInfo[3]+", Date of birth : "+studentInfo[4]);
                } else {
                    // Skip invalid data
                    continue;
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the exception details for debugging
        }
    }

    public static void update() {
        try {
            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<String> studentList = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                studentList.add(line);
            }
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter student name to update: ");
            String nameUpdate = sc.nextLine();
            List<Integer> matchingIndices = new ArrayList<>();

            for (int i = 0; i < studentList.size(); i++) {
                String[] studentInfo = studentList.get(i).split(",");
                if (studentInfo.length == 5 && studentInfo[0].equalsIgnoreCase(nameUpdate)) {
                    matchingIndices.add(i);
                    System.out.println(matchingIndices.size() + ". " + studentInfo[0] + ", " + studentInfo[1] + "," + studentInfo[2] + "," + studentInfo[3] + "," + studentInfo[4]);
                }
            }

            if (!matchingIndices.isEmpty()) {
                int selectedStudentIndex;

                // Ask user to select student only when there are 2 or more students with the same name
                if (matchingIndices.size() > 1) {
                    System.out.println("Enter the student number you want to update: ");
                    selectedStudentIndex = sc.nextInt();
                    while (selectedStudentIndex <= 0 || selectedStudentIndex > matchingIndices.size()) {
                        System.out.println("Invalid selection. Please try again.");
                        System.out.println("Enter the student number you want to update: ");
                        selectedStudentIndex = sc.nextInt();
                    }
                } else {
                    selectedStudentIndex = 1;
                }

                int selectedIndex = matchingIndices.get(selectedStudentIndex - 1);
                String[] selectedStudentInfo = studentList.get(selectedIndex).split(",");

                String newName;
                String newPhone;
                String newAddress;
                String newEmail;
                String newdob;

                // Loop until valid input is provided
                do {
                    System.out.println("Enter new name: ");
                    newName = sc.next();
                    if (!newName.matches("[a-zA-Z]+")) {
                        System.out.println("Invalid name. Please enter alphabets only.");
                    }
                } while (!newName.matches("[a-zA-Z]+"));

                do {
                    System.out.println("Enter new phone number: ");
                    newPhone = sc.next();
                    if (!newPhone.matches("\\d{10}")) {
                        System.out.println("Invalid. Please enter 10 digits.");
                    }
                } while (!newPhone.matches("\\d{10}"));

                do {
                    System.out.println("Enter new Address: ");
                    newAddress = sc.next();
                } while (newAddress.isEmpty());

                do {
                    System.out.println("Enter new email:");
                    newEmail = sc.next();
                    if (!newEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                        System.out.println("Invalid. Please enter correct email");
                    }
                } while (!newEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"));

                do {
                    System.out.println("Enter new date of birth(YYYY-MM-DD):");
                    newdob = sc.next();
                    try {
                        java.sql.Date.valueOf(newdob);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid date format. Please enter a valid date (YYYY-MM-DD).");
                    }
                } while (newdob.isEmpty() || !newdob.matches("\\d{4}-\\d{2}-\\d{2}"));

                studentList.set(selectedIndex, newName + "," + newPhone + "," + newEmail + "," + newAddress + "," + newdob);

                FileWriter fileWriter = new FileWriter(FILE_PATH, false); // Use 'true' for append mode
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                PrintWriter printWriter = new PrintWriter(bufferedWriter);
                for (String student : studentList) {
                    printWriter.println(student);
                }
                printWriter.close();
                System.out.println("Student updated!");
            } else {
                System.out.println("Student not found");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    public static void delete() {
        try {
            FileReader fileReader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            ArrayList<String> studentList = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                studentList.add(line);
            }
            bufferedReader.close();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter student name to delete: ");
            String nameDelete = sc.nextLine();
            boolean found = false;

            List<Integer> matchingIndices = new ArrayList<>();

            for (int i = 0; i < studentList.size(); i++) {
                String[] studentInfo = studentList.get(i).split(",");
                if (studentInfo[0].equals(nameDelete)) {
                    matchingIndices.add(i);
                    System.out.println(matchingIndices.size() + ". " + studentInfo[0] + ", " + studentInfo[1]);
                }
            }

            if (!matchingIndices.isEmpty()) {
                System.out.println("Enter the student number you want to delete: ");
                int selectedStudentIndex = sc.nextInt();
                sc.nextLine();

                if (selectedStudentIndex > 0 && selectedStudentIndex <= matchingIndices.size()) {
                    int selectedIndex = matchingIndices.get(selectedStudentIndex - 1);
                    studentList.remove(selectedIndex);
                    found = true;

                    FileWriter fileWriter = new FileWriter(FILE_PATH);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    PrintWriter printWriter = new PrintWriter(bufferedWriter);

                    for (String student : studentList) {
                        printWriter.println(student);
                    }
                    printWriter.close();
                } else {
                    System.out.println("Invalid selection. No student deleted.");
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}