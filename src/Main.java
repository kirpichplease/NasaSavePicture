import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner month = new Scanner(System.in);
        System.out.print("Enter month number in format MM:");
        String monthStr = month.nextLine();

        Scanner year = new Scanner(System.in);
        System.out.print("Enter year number in format YYYY: ");
        String yearStr = year.nextLine();

        int days = 0;
        int x = 0;
        int y = 0;

        String pageNasa;
        do {
            days++;
            pageNasa = downloadPageNasa("https://api.nasa.gov/planetary/apod?date="+yearStr+"-"+monthStr+"-" + days + "&api_key=LIQZRHXczPdFF75H3CYbVWV7KVSwa54qeqboEuWW");
            int urlBeginOne = pageNasa.lastIndexOf("url");
            int urlEndTwo = pageNasa.lastIndexOf("}");
            String url2 = pageNasa.substring(urlBeginOne + 6, urlEndTwo - 1);
            String image = downloadPageNasa(url2);
            y++;
            PrintWriter writer = new PrintWriter("picture" + y + ".jpg");
            writer.print(image+y);
            writer.close();
            try (InputStream in = new URL(url2).openStream()) {
                x++;
                Files.copy(in, Paths.get("image" + x + ".jpg"));
            }
        } while (days < 31 && y<31 && x<31) ; System.out.println("Картинки за месяц сохранены");


    }

    private static String downloadPageNasa (String url2) throws IOException {
        StringBuilder result = new StringBuilder();
        String line2;
        URLConnection urlConnection2 = new URL(url2).openConnection();
        try (InputStream is = urlConnection2.getInputStream(); BufferedReader br2 = new BufferedReader(new InputStreamReader(is))) {
            while ((line2 = br2.readLine()) != null) {
                result.append(line2);
            }
        }
        return result.toString();
    }
}