import java.util.*;

class Course {
    String courseCode, title, description, schedule;
    int capacity, enrolledStudents;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
        this.enrolledStudents = 0;
    }

    public boolean enrollStudent() {
        if (enrolledStudents < capacity) {
            enrolledStudents++;
            return true;
        }
        return false;
    }

    public boolean dropStudent() {
        if (enrolledStudents > 0) {
            enrolledStudents--;
            return true;
        }
        return false;
    }

    public void displayCourse() {
        System.out.println(courseCode + ": " + title + " - " + description);
        System.out.println("Schedule: " + schedule + " | Capacity: " + enrolledStudents + "/" + capacity);
    }
}

class Student {
    String studentID, name;
    List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public void registerCourse(Course course) {
        if (course.enrollStudent()) {
            registeredCourses.add(course);
            System.out.println("Successfully registered for " + course.title);
        } else {
            System.out.println("Course is full!");
        }
    }

    public void dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent();
            System.out.println("Successfully dropped " + course.title);
        } else {
            System.out.println("You are not enrolled in this course.");
        }
    }

    public void displayRegisteredCourses() {
        System.out.println("\nCourses registered by " + name + ":");
        for (Course course : registeredCourses) {
            course.displayCourse();
        }
    }
}

public class CourseRegistrationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CS101", "Intro to Programming", "Basic programming concepts", 3, "Mon-Wed 10AM"));
        courses.add(new Course("MTH102", "Calculus I", "Fundamentals of calculus", 2, "Tue-Thu 2PM"));
        
        System.out.print("Enter Student ID: ");
        String studentID = scanner.next();
        System.out.print("Enter Student Name: ");
        String studentName = scanner.next();
        Student student = new Student(studentID, studentName);
        
        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. View Courses\n2. Register for Course\n3. Drop Course\n4. View Registered Courses\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    for (Course course : courses) {
                        course.displayCourse();
                    }
                    break;
                case 2:
                    System.out.print("Enter course code to register: ");
                    String regCode = scanner.next();
                    courses.stream().filter(c -> c.courseCode.equals(regCode)).findFirst()
                        .ifPresentOrElse(student::registerCourse, () -> System.out.println("Course not found!"));
                    break;
                case 3:
                    System.out.print("Enter course code to drop: ");
                    String dropCode = scanner.next();
                    courses.stream().filter(c -> c.courseCode.equals(dropCode)).findFirst()
                        .ifPresentOrElse(student::dropCourse, () -> System.out.println("Course not found!"));
                    break;
                case 4:
                    student.displayRegisteredCourses();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
        scanner.close();
    }
}
