import java.io.*;
import java.util.*;

public class Main {
    static Map<String, Double> exchangeRates = new HashMap<>();
    static ArrayList<String> conversionHistory = new ArrayList<>();
    static final String HISTORY_FILE = "history.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        CurrencyConverter converter = new CurrencyConverter();

        exchangeRates.put("USD", 40.50);
        exchangeRates.put("EUR", 43.20);
        exchangeRates.put("GBP", 50.10);
        exchangeRates.put("JPY", 23.00);
        exchangeRates.put("PLN", 10.30);
        exchangeRates.put("CHF", 46.30);
        exchangeRates.put("HKD", 0.90);

        loadHistory();

        while (running) {
            System.out.println("_____________________________________\n" +
                    "|1. Get exchange rate               |\n" +
                    "|2. Convert currency                |\n" +
                    "|3. Update exchange rates           |\n" +
                    "|4. Show conversion history         |\n" +
                    "|5. Exit                            |\n" +
                    "|6. Clear conversion history        |\n" +
                    "|-----------------------------------|");
            System.out.print("Choose an option: ");

            String choose = sc.nextLine();
            switch (choose) {
                case "1": converter.getExchangeRate(); break;
                case "2": converter.convert(); break;
                case "3": converter.updateExchangeRate(); break;
                case "4": converter.showConversionHistory(); break;
                case "5":
                    System.out.println("Exiting program.");
                    running = false;
                    break;
                case "6":
                    clearHistory();
                    System.out.println("Conversion history cleared.");
                    break;
                default:
                    System.out.println("Invalid input. Try again.");
            }
        }
    }

    public static void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(HISTORY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                conversionHistory.add(line);
            }
        } catch (IOException e) {
            System.out.println("No saved history found.");
        }
    }

    public static void saveHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (String record : conversionHistory) {
                writer.write(record);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving history.");
        }
    }

    public static void addToHistory(String record) {
        conversionHistory.add(record);
        saveHistory();
    }

    public static void showConversionHistory() {
        if (conversionHistory.isEmpty()) {
            System.out.println("No conversion history yet.\n");
        } else {
            System.out.println("Your exchange history:");
            for (String record : conversionHistory) {
                System.out.println(record);
            }
        }
    }

    public static void clearHistory() {
        conversionHistory.clear();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println("Error clearing history.");
        }
    }
}

class CurrencyConverter {
    private Scanner sc = new Scanner(System.in);

    public void getExchangeRate() {
        System.out.print("Enter currency code (USD, EUR, GBP, PLN, JPY, CHF, HKD): ");
        String currency = sc.nextLine().toUpperCase();

        if (Main.exchangeRates.containsKey(currency)) {
            System.out.println("1 " + currency + " = " + Main.exchangeRates.get(currency) + " UAH");
        } else {
            System.out.println("Currency not found.");
        }
    }

    public void convert() {
        System.out.print("Enter currency code (USD, EUR, GBP, PLN, JPY, CHF, HKD): ");
        String currency = sc.nextLine().toUpperCase();

        if (!Main.exchangeRates.containsKey(currency)) {
            System.out.println("Currency not found.");
            return;
        }

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        double result = amount * Main.exchangeRates.get(currency);
        String conversionRecord = amount + " " + currency + " = " + result + " UAH";

        Main.addToHistory(conversionRecord);
        System.out.println(conversionRecord);
    }

    public void updateExchangeRate() {
        System.out.print("Enter currency code (USD, EUR, GBP, PLN, JPY, CHF, HKD): ");
        String currency = sc.nextLine().toUpperCase();

        if (!Main.exchangeRates.containsKey(currency)) {
            System.out.println("Currency not found.");
            return;
        }

        System.out.print("Enter new exchange rate: ");
        double newRate = sc.nextDouble();
        sc.nextLine();

        Main.exchangeRates.put(currency, newRate);
        System.out.println("Exchange rate for " + currency + " updated to " + newRate + " UAH");
    }

    public void showConversionHistory() {
        Main.showConversionHistory();
    }
}
