package messenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"InfiniteLoopStatement", "resource"})
public class Main {
    private static List<User> users = new ArrayList<>();
    private static String token = "";
    private static BufferedReader reader;
    private static PrintWriter writer;

    public static void main(String[] args) {
        System.out.println("Hello world");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("try connect");
            try {
                Socket socket = new Socket("localhost", 8080);
                System.out.println("connected to server");
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream());
                if (true) {
                    return;
                }
                while (true) {
                    System.out.println("--------------------------------------");
                    System.out.println("Please enter command:\nEnter 'exit' for stop program\n/serverTime - current server time\n/register - register new user\n/login - authorization user\n/sendMessage - send message to user\n/readMessages - read messages\n/getUserById - get user by id\n/getUserByLogin - get user by login");
                    String command = consoleReader.readLine();

                    if (command.equals("exit")) {
                        break;
                    }

                    switch (command) {
                        case "/serverTime" -> {
                            getServerTime();
                        }

                        case "/register" -> {
                            registerUser(consoleReader.readLine(), consoleReader.readLine());
                        }

                        case "/login" -> {
                            loginUser(consoleReader.readLine(), consoleReader.readLine());

                        }

                        case "/sendMessage" -> {
                            sendMessage(consoleReader.readLine(), consoleReader.readLine());
                        }

                        case "/readMessages" -> {
                            readMessages(consoleReader.readLine());
                        }

                        case "/getUserById" -> {
                            getUserById(consoleReader.readLine());
                        }

                        case "/getUserByLogin" -> {
                            getUserByLogin(consoleReader.readLine());
                        }

                        default -> System.out.println("unknown command");
                    }

                    writer.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("disconnected from server");
        }
    }

    public static ArrayList<Message> readMessages(String date) throws IOException {
        ArrayList<Message> messages = new ArrayList<>();

        try {
            write("/readMessages");
            System.out.println("Enter since_date");
            write(date);
            write(token);

            boolean isSuccess = reader.readLine().equals("true");

            if (isSuccess) {
                int messagesCount = Integer.parseInt(reader.readLine());
                for (int i = 0; i < messagesCount; i++) {
                    Message message = new Message();
                    message.messageId = Integer.parseInt(reader.readLine());
                    message.fromUserId = Integer.parseInt(reader.readLine());
                    message.toUserId = Integer.parseInt(reader.readLine());
                    message.message = reader.readLine();
                    message.date = Long.parseLong(reader.readLine());
                    messages.add(message);
                }
                printMessages(messages);
            } else {
                String failureReason = reader.readLine();
                System.out.println("failed to read messages=" + failureReason);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return messages;
    }

    private static void write(String x) {
        writer.println(x);
        System.out.println("written to socket=" + x);
        writer.flush();
    }

    public static String sendMessage(String userIdToSendMessage, String textMessage) throws IOException {
        try {
            write("/sendMessage");
            writer.flush();
            write(userIdToSendMessage);
            write(textMessage);
            write(token);
            writer.flush();

            //answer
            String answer = reader.readLine();
            System.out.println("Server answer = " + answer);
            return answer;
        } catch (Throwable e) {
            e.printStackTrace();
            return "false";
        }
    }

    public static String getUserByLogin(String userName) throws IOException {
        try {
            write("/getUserByLogin");
            writer.flush();
            write(userName);
            write(token);
            writer.flush();

            //answer
            String answerLogin = reader.readLine();
            System.out.println("Username: " + answerLogin);
            String answerId = reader.readLine();
            System.out.println("User Id: " + answerId);
            String[] answer = {answerId, answerLogin};
            return Arrays.toString(answer);
        } catch (Throwable e) {
            e.printStackTrace();
            return "false";
        }
    }

    public static String getServerTime() throws IOException {
        try {
            write("/serverTime");
            writer.flush();

            //answer
            String answer = reader.readLine();
            System.out.println("Server answer = " + answer);
            return answer;
        } catch (Throwable e) {
            e.printStackTrace();
            return "false";
        }
    }

    public static String getUserById(String userId) {

        try {
            write("/getUserById");
            writer.flush();
            write(userId);
            writer.flush();
            write(token);
            writer.flush();

            //answer
            String answer = reader.readLine();

            System.out.println("Username: " + answer);
            return answer;
        } catch (Throwable e) {
            e.printStackTrace();
            return "false";
        }

    }

    public static String loginUser(String login, String password) throws IOException {
        try {
            write("/login");
            writer.flush();
            write(login);
            write(password);
            writer.flush();

            //answer
            String answer = reader.readLine();
            if (!answer.equals("true")) {
                System.out.println("failed to login, answer=" + answer);
                return answer;
            }

            int userId = Integer.parseInt(reader.readLine());
            System.out.println("Userid is: " + userId);
            token = reader.readLine();
            System.out.println("Your token is: " + token);
            return answer;
        } catch (Throwable e) {
            e.printStackTrace();
            return "false";
        }
    }

    private static void printMessages(ArrayList<Message> messages) {

        System.out.println("print messages, count = " + messages.size());
        for (Message message : messages) {
            System.out.println(message);
        }
    }


    public static String registerUser(String name, String password) {
        try {
            write("/register");
            writer.flush();
            write(name);
            write(password);
            writer.flush();

            String answer = reader.readLine();
            return answer;
        } catch (Throwable e) {
            e.printStackTrace();
            return "false";
        }
    }


}
