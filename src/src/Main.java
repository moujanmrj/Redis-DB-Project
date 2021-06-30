import redis.clients.jedis.Jedis;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Jedis jedis = new Jedis("localhost");
        Scanner scanner = new Scanner(new File("NYSE_20210301.csv"));

        while (scanner.hasNext()){
            String[] arr = scanner.nextLine().split(",");
            jedis.set(arr[0], arr[1]);
        }
        scanner.close();

        while (true){
            Scanner commandScanner = new Scanner(System.in);
            String command = commandScanner.nextLine();
            String[] commands = command.split("\\s+");

            switch (commands[0]) {
                case "create":
                    if (!(jedis.get(commands[1])== null))
                        System.out.println("false");

                    else {
                        jedis.set(commands[1], commands[2]);
                        System.out.println("true");
                    }
                    break;
                case "fetch":
                    if (jedis.get(commands[1])== null)
                        System.out.println("false");
                    else {
                        System.out.println(jedis.get(commands[1]));
                        System.out.println("true");
                    }
                    break;
                case "update":
                    if (jedis.get(commands[1])== null)
                        System.out.println("false");
                    else {
                        jedis.del(commands[1]);
                        jedis.set(commands[1], commands[2]);
                        System.out.println("true");
                    }
                    break;
                case "delete":
                    if (jedis.get(commands[1])== null)
                        System.out.println("false");
                    else {
                        jedis.del(commands[1]);
                        System.out.println("true");
                    }
                    break;
            }
            System.out.println();
        }
    }
}
