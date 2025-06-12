import java.io.*;
import java.util.*;

public class ChatBot {
    private static String apiUrl;
    private static String botName;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        loadConfig();
        System.out.println("🤖 Welcome! I am " + botName);

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Create a blog post");
            System.out.println("2. View all blog posts");
            System.out.println("3. View statistics");
            System.out.println("4. Exit");
            System.out.print("> ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": createBlogPost(); break;
                case "2": viewAllPosts(); break;
                case "3": viewStatistics(); break;
                case "4": System.out.println("Bye!"); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    private static void loadConfig() {
        try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
            Properties props = new Properties();
            props.load(reader);
            apiUrl = props.getProperty("url");
            botName = props.getProperty("name");
        } catch (IOException e) {
            System.out.println("Error loading configuration: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void createBlogPost() {
        System.out.print("Enter blog title: ");
        String title = scanner.nextLine();

        System.out.print("Enter blog content: ");
        String content = scanner.nextLine();

        System.out.print("Enter author name: ");
        String author = scanner.nextLine();

        // Construct URL and JSON body for POST
        String endpoint = apiUrl + "?api=blogs";
        String json = String.format("{\"title\":\"%s\", \"content\":\"%s\", \"author\":\"%s\"}", title, content, author);

        String response = HttpClientHelper.sendPost(endpoint, json);
        System.out.println("Server response: " + response);
    }

    private static void viewAllPosts() {
        String endpoint = apiUrl + "?api=blogs";
        String response = HttpClientHelper.sendGet(endpoint);
        System.out.println("Posts:\n" + response);
    }

    private static void viewStatistics() {
        String endpoint = apiUrl + "?api=stats";
        String response = HttpClientHelper.sendGet(endpoint);
        System.out.println("Statistics:\n" + response);
    }
}
