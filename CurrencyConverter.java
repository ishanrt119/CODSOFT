import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_KEY = "793fc6eae4b3cc62cd88a080";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Currency selection
        System.out.print("Enter the base currency (e.g., USD): ");
        String baseCurrency = scanner.next().toUpperCase();

        System.out.print("Enter the target currency (e.g., EUR): ");
        String targetCurrency = scanner.next().toUpperCase();

        // Step 2: Amount input
        System.out.print("Enter the amount to convert: ");
        double amount = scanner.nextDouble();

        // Step 3: Perform currency conversion
        try {
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);
            if (exchangeRate != -1) {
                double convertedAmount = amount * exchangeRate;
                // Step 4: Display the result
                System.out.printf("Converted Amount: %.2f %s\n", convertedAmount, targetCurrency);
            } else {
                System.out.println("Unable to retrieve exchange rate. Please try again later.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        scanner.close();
    }

    // Method to fetch the exchange rate from the API
    private static double fetchExchangeRate(String baseCurrency, String targetCurrency) throws Exception {
        String requestUrl = API_URL + "/" + baseCurrency + "/" + targetCurrency;
        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Manually parse JSON to find "conversion_rate"
            String jsonResponse = response.toString();
            String conversionRateString = extractConversionRate(jsonResponse);

            return conversionRateString != null ? Double.parseDouble(conversionRateString) : -1;
        } else {
            System.out.println("HTTP Error: " + responseCode);
            return -1;
        }
    }

    // Method to extract "conversion_rate" from JSON response
    private static String extractConversionRate(String jsonResponse) {
        // Locate the "conversion_rate" key and extract the value
        String searchKey = "\"conversion_rate\":";
        int startIndex = jsonResponse.indexOf(searchKey);
        if (startIndex != -1) {
            startIndex += searchKey.length();
            int endIndex = jsonResponse.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = jsonResponse.indexOf("}", startIndex);
            }
            return jsonResponse.substring(startIndex, endIndex).trim();
        }
        return null;
    }
}
