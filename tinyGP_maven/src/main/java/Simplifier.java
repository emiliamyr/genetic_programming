import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.JSONObject;

public class Simplifier {

    // Funkcja do uproszczania wyrażenia
    public String simplify(String function) {
        ExprEvaluator evaluator = new ExprEvaluator();
        IExpr result = evaluator.eval(function);

        return result.toString()
                .replace("+", " + ")
                .replace("-", " - ")
                .replace("*", " * ")
                .replace("/", " / ")
                .replace("Sin", "sin")
                .replace("Cos", "cos");
    }

    // Funkcja do zapisywania do pliku JSON
    public void saveToFile(String expression, String simplifiedExpression) {
        try {
            // Sprawdzenie, czy folder 'simp' istnieje, jeśli nie, to go utwórz
            File folder = new File("simp");
            if (!folder.exists()) {
                folder.mkdir();
            }

            // Utworzenie pliku 'f1_d1_s.json' w folderze 'simp'
            File file = new File(folder, "f6_d4_s.json");
            FileWriter writer = new FileWriter(file);

            // Tworzenie obiektu JSON
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("original", expression);
            jsonObject.put("simplified", simplifiedExpression);

            // Zapis do pliku JSON
            writer.write(jsonObject.toString(4)); // '4' formatuje JSON z wcięciami dla lepszej czytelności
            writer.close();

            System.out.println("Wyrażenie zapisane do pliku: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania pliku: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Simplifier simplifier = new Simplifier();

        String expression = "((X1  * ((5.849250748474738  + ((((2.6258744346082885  + 9.187037679177042 ) / X1 ) + (5.849250748474738  + (((2.6258744346082885  + 9.187037679177042 ) + ((((-4.65247982023302  - 2.6258744346082885 ) + ((((X2  + X1 ) * 9.187037679177042 ) / 9.187037679177042 ) + 2.6258744346082885 )) + (((X1  / (X1  + (((9.187037679177042  / 9.187037679177042 ) + (5.849250748474738  / (((9.187037679177042  / 9.187037679177042 ) + (5.849250748474738  / (((((((((X2  * X1 ) + (((((9.187037679177042  / (5.780428971853112  + X1 )) / X2 ) + X2 ) + (X1  / 7.341443615278493 )) + ((5.849250748474738  * (5.780428971853112  * 5.780428971853112 )) - (((X2  * 5.780428971853112 ) - (((((9.187037679177042  / (-4.65247982023302  + (-4.65247982023302  / 7.341443615278493 ))) + 9.187037679177042 ) / (-5.201824731714508  + -6.88678651179925 )) + 9.187037679177042 ) / -5.201824731714508 )) + 5.849250748474738 )))) / (X2  * (X2  * 5.780428971853112 ))) + (2.6258744346082885  - X1 )) * (2.6258744346082885  - -4.65247982023302 )) / 9.187037679177042 ) + 2.6258744346082885 ) / 9.187037679177042 ) + X2 ))) + X2 ))) + (((((((((((5.780428971853112  + X1 ) * (((X2  * X1 ) + (((X1  + X2 ) + (X1  / 7.341443615278493 )) + (9.187037679177042  - -4.65247982023302 ))) - -4.65247982023302 )) + (((((5.780428971853112  + X1 ) * (2.6258744346082885  - -4.65247982023302 )) / 9.187037679177042 ) + -4.65247982023302 ) + (9.187037679177042  - 5.574026146987636 ))) + (((((((X2  * X1 ) + (2.6258744346082885  + (9.187037679177042  - -4.65247982023302 ))) / (X2  * 7.341443615278493 )) + (2.6258744346082885  - X1 )) + X1 ) * (2.6258744346082885  - -4.65247982023302 )) / 9.187037679177042 )) / X2 ) / 7.341443615278493 ) / (-4.65247982023302  + (-4.65247982023302  / 7.341443615278493 ))) + 9.187037679177042 ) / 9.187037679177042 ) / 7.341443615278493 ) - 5.574026146987636 )))) / X1 ) + X2 )) * 5.780428971853112 )) + (((((((X2  - ((2.6258744346082885  * 2.6258744346082885 ) * (X2  * 5.780428971853112 ))) / (5.849250748474738  * 5.780428971853112 )) - ((9.187037679177042  + (((((((X2  * X1 ) + ((((((((X1  / (X1  + ((((5.574026146987636  * (2.6258744346082885  - -4.65247982023302 )) / 9.187037679177042 ) + ((X1  + ((9.270831512254865  / X2 ) + X2 )) * 5.574026146987636 )) + (((((((X2  - (5.849250748474738  * ((9.187037679177042  * (2.6258744346082885  - -4.65247982023302 )) * 5.780428971853112 ))) / (5.849250748474738  * 5.780428971853112 )) - (X2  + 2.6258744346082885 )) + (((X1  + (-4.65247982023302  + (((9.270831512254865  + X1 ) * 9.187037679177042 ) - 5.574026146987636 ))) + 9.270831512254865 ) / X1 )) / X1 ) / ((((7.341443615278493  / (-4.65247982023302  + (-4.65247982023302  / 7.341443615278493 ))) + 9.270831512254865 ) / X2 ) / 9.270831512254865 )) - 9.187037679177042 )))) / X2 ) + X2 ) / (((X2  * 5.780428971853112 ) - (X1  / 2.6258744346082885 )) + 5.849250748474738 )) / X2 ) + X2 ) + -5.201824731714508 ) + 9.187037679177042 )) / (X2  * 7.341443615278493 )) + (2.6258744346082885  - X1 )) * (2.6258744346082885  - -4.65247982023302 )) / 9.187037679177042 ) + 2.6258744346082885 )) + -6.88678651179925 )) + 5.849250748474738 ) / X1 ) / ((((9.187037679177042  / (-4.65247982023302  + (-4.65247982023302  / 7.341443615278493 ))) + 9.187037679177042 ) / 9.187037679177042 ) / 7.341443615278493 )) + (-5.201824731714508  + -6.88678651179925 ))))) + 9.270831512254865 )) / 5.780428971853112 )) + (X2  * (-5.201824731714508  + X1 )))";



        String optimizedExpression = simplifier.simplify(expression);

        System.out.println("Orginalne wyrażenie: " + expression);
        System.out.println("Uproszczone wyrażenie: " + optimizedExpression);

        // Zapisanie wyników do pliku JSON
        simplifier.saveToFile(expression, optimizedExpression);
    }
}
