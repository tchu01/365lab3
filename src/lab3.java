import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by timothy on 1/30/17.
 */
public class lab3 {
    public static HashMap<Integer, ArrayList<BigInteger>> idToCreditCardNumber = new HashMap<>();

    public static void main(String[] args) {
        generateCustomers();
        generateCreditCards();
        generateVenders();
        generateOwnership();
        generateTransactions();
    }

    // Generate 1000 customers
    public static void generateCustomers() {
        try {
            PrintWriter writer = new PrintWriter("customers.sql", "UTF-8");
            long ssn = 100000000;
            long increment = 0;
            long phone = 2000000000;

            for(int i = 0; i < 1000; i++) {
                writer.println("INSERT");
                writer.println("INTO Customer (ssn, name, address, phone)");
                writer.println("VALUES (\"" + ssn + "\", \"Name" + increment + "\", \"" + increment + " Grand Ave\", \"" + phone + "\");");
                writer.println();

                ssn++;
                increment++;
                phone++;
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Generate 1000 credit cards
    public static void generateCreditCards() {
        try {
            PrintWriter writer = new PrintWriter("creditcards.sql", "UTF-8");
            BigInteger number = new BigInteger("1000000000000000");
            double creditLimit = 3000.00;

            for(int i = 0; i < 1000; i++) {
                writer.println("INSERT");
                writer.println("INTO CreditCard (number, type, creditLimit, currentBalance)");
                writer.println("VALUES (\"" + number + "\", \"AMEX\", " + creditLimit + ", 0.00);");
                writer.println();

                number = number.add(BigInteger.ONE);
                creditLimit += 100.00;
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Generate 100 venders
    public static void generateVenders() {
        try {
            PrintWriter writer = new PrintWriter("venders.sql", "UTF-8");
            int increment = 0;

            for(int i = 0; i < 100; i++) {
                writer.println("INSERT");
                writer.println("INTO Vender (name, location)");
                writer.println("VALUES (\"Vender" + increment + "\", \"" + increment + " Madonna Road\");");
                writer.println();

                increment++;
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Every user has between 1-3 credit cards and every card has at most 2 owners
    public static void generateOwnership() {
        try {
            PrintWriter writer = new PrintWriter("ownership.sql", "UTF-8");
            int id = 4;
            BigInteger number = new BigInteger("1000000000000000");

            for(int i = 0; i < 1000; i++) {
                if(!idToCreditCardNumber.containsKey(id)) {
                    idToCreditCardNumber.put(id, new ArrayList<>());
                }

                writer.println("INSERT");
                writer.println("INTO Ownership (id, number)");
                writer.println("VALUES (" + id + ", \"" + number + "\");");
                writer.println();
                idToCreditCardNumber.get(id).add(number);

                if(i % 100 == 0) {
                    BigInteger oneAhead = number;
                    oneAhead = oneAhead.add(BigInteger.ONE);
                    writer.println("INSERT");
                    writer.println("INTO Ownership (id, number)");
                    writer.println("VALUES (" + id + ", \"" + oneAhead + "\");");
                    writer.println();
                    idToCreditCardNumber.get(id).add(oneAhead);
                }

                if(i % 200 == 0) {
                    BigInteger twoAhead = number;
                    twoAhead = twoAhead.add(BigInteger.ONE);
                    twoAhead = twoAhead.add(BigInteger.ONE);
                    writer.println("INSERT");
                    writer.println("INTO Ownership (id, number)");
                    writer.println("VALUES (" + id + ", \"" + twoAhead + "\");");
                    writer.println();
                    idToCreditCardNumber.get(id).add(twoAhead);
                }

                number = number.add(BigInteger.ONE);
                id++;
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    // Generate 2000 random transactions
    public static void generateTransactions() {
        try {
            PrintWriter writer = new PrintWriter("transactions.sql", "UTF-8");
            Random r = new Random();
            int customerId = r.nextInt(1000) + 4;
            BigInteger number = idToCreditCardNumber.get(customerId).get(0);
            int venderId = r.nextInt(100) + 2;
            double amount = (r.nextDouble() * 100) + 20;

            for(int i = 0; i < 2000; i++) {
                writer.println("INSERT");
                writer.println("INTO Transaction (customerId, number, venderId, date, amount)");
                writer.println("VALUES (" + customerId + ", \"" + number + "\", " + venderId + ", NOW(), " + amount +");");
                writer.println();

                customerId = r.nextInt(1000) + 4;
                venderId = r.nextInt(100) + 2;
                amount = (r.nextDouble() * 100) + 20;
                number = idToCreditCardNumber.get(customerId).get(0);
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
