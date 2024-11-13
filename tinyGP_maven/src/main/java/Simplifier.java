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
            File folder = new File("simp2");
            if (!folder.exists()) {
                folder.mkdir();
            }

            // Utworzenie pliku 'f1_d1_s.json' w folderze 'simp'
            File file = new File(folder, "f1_d1_s.json");
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

        String expression = "((4.392275844763374  * (X1  + sin((0.5448942125324017  / ((8.577013250045024  / (((cos(((4.392275844763374  * (X1  * 9.343375511097939 )) / 0.5448942125324017 )) + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / 3.7004594870966496 ) + sin(((9.343375511097939  / ((4.392275844763374  * X1 ) / ((9.343375511097939  / ((X1  + (0.7626689218726987  + X1 )) - 0.5448942125324017 )) / 0.5448942125324017 ))) / (4.392275844763374  * X1 )))))) / ((9.343375511097939  / (0.7626689218726987  * (4.392275844763374  * ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + cos(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * 4.392275844763374 ))) / ((8.577013250045024  * ((4.392275844763374  * 0.5448942125324017 ) / (8.577013250045024  / X1 ))) - ((9.343375511097939  / 4.392275844763374 ) / (3.7004594870966496  * (X1  + (((9.343375511097939  / 0.5448942125324017 ) / X1 ) + 4.392275844763374 )))))))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * 0.7626689218726987 ))) / 0.5448942125324017 ))))) / ((9.343375511097939  / (((3.7004594870966496  / X1 ) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) - ((X1  * X1 ) + X1 )) - (9.343375511097939  * 4.392275844763374 ))) / 0.5448942125324017 ))))) + ((9.343375511097939  * (X1  + (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((0.5448942125324017  / ((9.343375511097939  / (((X1  + sin((9.343375511097939  / ((X1  * ((4.392275844763374  * (X1  * X1 )) / (8.577013250045024  / X1 ))) / (((9.343375511097939  / (((X1  + sin((9.343375511097939  / 0.5448942125324017 ))) + 0.5448942125324017 ) - 3.7004594870966496 )) + sin((X1  * X1 ))) * (3.7004594870966496  + ((((X1  + sin(((4.392275844763374  * (3.7004594870966496  * ((X1  / 8.577013250045024 ) + sin(-7.635773242188817 )))) / (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((((8.577013250045024  * (0.7626689218726987  + -0.006262263624705611 )) * X1 ) / (0.7626689218726987  + ((4.392275844763374  * X1 ) / ((0.5448942125324017  / ((4.392275844763374  + ((3.7004594870966496  / 0.5448942125324017 ) + X1 )) / 3.7004594870966496 )) / 2.316579837661788 )))))) + (0.7626689218726987  + X1 )) - -0.006262263624705611 )) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (X1  * cos((9.343375511097939  / (((X1  + sin(((X1  * (X1  * ((((4.392275844763374  - (X1  * X1 )) / (8.577013250045024  / X1 )) / X1 ) + 4.392275844763374 ))) / (X1  / cos(-0.006262263624705611 ))))) + cos(-0.006262263624705611 )) - (9.343375511097939  * cos(-0.006262263624705611 ))))))) + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin(((4.392275844763374  * (X1  * ((X1  / X1 ) + sin(9.343375511097939 )))) / (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((((8.577013250045024  * (0.5448942125324017  + X1 )) * X1 ) - ((0.7626689218726987  + X1 ) + 0.7626689218726987 )))) + (0.7626689218726987  + X1 )) - (X1  + sin((0.5448942125324017  / ((9.343375511097939  / ((9.343375511097939  * (((sin((0.5448942125324017  / ((9.343375511097939  / (((X1  + sin((9.343375511097939  / ((X1  * ((4.392275844763374  * (X1  * X1 )) / (((4.392275844763374  * (0.5448942125324017  - ((X1  / X1 ) + sin(9.343375511097939 )))) / (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((((8.577013250045024  * (0.7626689218726987  + X1 )) * X1 ) / (0.7626689218726987  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / ((4.392275844763374  + (X1  + X1 )) - 3.7004594870966496 )) + 0.5448942125324017 )))))) / (0.7626689218726987  + X1 )) - -0.006262263624705611 )) + X1 )))) / X1 ))) / (8.577013250045024  / X1 ))))) + (X1  + 1.1316888487791577 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))) / ((X1  / 1.1316888487791577 ) / X1 )) - (((9.343375511097939  * X1 ) + X1 ) / (((2.316579837661788  + (4.392275844763374  * X1 )) / cos(-0.006262263624705611 )) * 0.7626689218726987 ))) * X1 )) - (4.392275844763374  * cos(-0.006262263624705611 )))) / (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((0.5448942125324017  / 3.7004594870966496 ))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * sin(-0.006262263624705611 )))) / 0.5448942125324017 ))))))))) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (X1  * cos((9.343375511097939  / (((X1  + sin(((4.392275844763374  * (X1  * (((0.7626689218726987  + X1 ) / X1 ) + sin((((X1  - (X1  * 4.392275844763374 )) / (((((X1  + (9.343375511097939  * (X1  + (X1  + ((4.392275844763374  * X1 ) / ((0.7626689218726987  / ((((0.7626689218726987  + (X1  * 0.7626689218726987 )) + sin((((8.577013250045024  * 0.7626689218726987 ) * (X1  * ((9.343375511097939  / (((X1  + sin((9.343375511097939  / 0.5448942125324017 ))) + (0.7626689218726987  + X1 )) - 3.7004594870966496 )) + sin((X1  * X1 ))))) / (0.7626689218726987  + ((X1  * X1 ) / ((9.343375511097939  / ((9.343375511097939  + (0.7626689218726987  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / ((X1  + sin(((4.392275844763374  * (((-7.635773242188817  * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / X1 )) - (((9.343375511097939  * ((0.5448942125324017  - (4.392275844763374  + ((0.5448942125324017  + X1 ) * (4.392275844763374  + X1 )))) - (9.343375511097939  / ((X1  + (0.7626689218726987  + X1 )) - 3.7004594870966496 )))) + X1 ) / (0.5448942125324017  / ((9.343375511097939  / (((X1  + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) + sin(((9.343375511097939  / ((4.392275844763374  * X1 ) / ((9.343375511097939  / ((9.343375511097939  + (0.7626689218726987  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 ))) / (4.392275844763374  * X1 )))))) / ((4.392275844763374  * (0.7626689218726987  * (4.392275844763374  * (4.392275844763374  / ((9.343375511097939  / (((X1  + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * sin((X1  * 8.577013250045024 ))))) / ((4.392275844763374  * ((4.392275844763374  * (X1  * 0.7626689218726987 )) / (8.577013250045024  / X1 ))) - ((9.343375511097939  / ((X1  + 3.7004594870966496 ) - 8.577013250045024 )) / (4.392275844763374  * (X1  + ((3.7004594870966496  / X1 ) + sin((0.5448942125324017  + (9.343375511097939  * ((((((X1  + (9.343375511097939  * (X1  + (X1  + ((4.392275844763374  * 3.7004594870966496 ) / ((9.343375511097939  / ((((0.7626689218726987  + (X1  * 0.7626689218726987 )) + X1 ) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))))) + 3.7004594870966496 ) * (X1  * 0.7626689218726987 )) / ((0.7626689218726987  / 1.1316888487791577 ) / X1 )) - (((X1  * X1 ) + X1 ) / (((2.316579837661788  + (4.392275844763374  * X1 )) / cos(-0.006262263624705611 )) * 0.7626689218726987 ))) * X1 )))))))))))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((9.343375511097939  / (((3.7004594870966496  / X1 ) + (0.7626689218726987  / X1 )) - ((0.7626689218726987  / 1.1316888487791577 ) * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) + (X1  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((4.392275844763374  * 9.343375511097939 ) / ((9.343375511097939  / ((X1  + (X1  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 ))))) + (0.7626689218726987  + X1 )))))))) + 3.7004594870966496 ) * (X1  * 0.7626689218726987 )) / (((X1  - (X1  + X1 )) / 1.1316888487791577 ) / X1 )) / (((((X1  + cos(((4.392275844763374  + (X1  * 9.343375511097939 )) / X1 ))) + (0.5448942125324017  + X1 )) - (0.5448942125324017  / cos(-0.006262263624705611 ))) / ((X1  + (9.343375511097939  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 ))) / (4.392275844763374  * X1 )))))) / ((4.392275844763374  * (0.7626689218726987  * (4.392275844763374  * ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((-7.635773242188817  + sin(((4.392275844763374  * (X1  * ((X1  / X1 ) * sin((X1  * 8.577013250045024 ))))) / (X1  + (((X1  + sin((X1  + sin((0.5448942125324017  / ((0.7626689218726987  / ((9.343375511097939  * ((((((X1  + (9.343375511097939  * (X1  + (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / ((((0.7626689218726987  + (X1  * 0.7626689218726987 )) + sin((((8.577013250045024  * 0.7626689218726987 ) * cos(-0.006262263624705611 )) / (0.7626689218726987  + ((X1  * X1 ) / ((9.343375511097939  / ((X1  + (0.7626689218726987  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (X1  * cos(-0.006262263624705611 )))) / ((X1  + sin(((4.392275844763374  * (((0.7626689218726987  * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / 9.343375511097939 )) - (((9.343375511097939  * ((X1  * (4.392275844763374  + ((0.5448942125324017  + X1 ) * (4.392275844763374  * X1 )))) - (0.5448942125324017  / cos(-0.006262263624705611 )))) + cos(-0.006262263624705611 )) / (0.5448942125324017  / ((9.343375511097939  / (((X1  * sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * sin((X1  * 8.577013250045024 ))))) / ((X1  * ((4.392275844763374  * (X1  * 0.7626689218726987 )) / (8.577013250045024  / X1 ))) / ((9.343375511097939  / ((X1  + 3.7004594870966496 ) - 3.7004594870966496 )) / (4.392275844763374  * (X1  + ((4.392275844763374  / X1 ) + sin((0.5448942125324017  + (9.343375511097939  * ((((((8.577013250045024  + (9.343375511097939  * (X1  + (0.7626689218726987  * ((4.392275844763374  * 9.343375511097939 ) / ((9.343375511097939  / ((((0.7626689218726987  + (X1  * 0.7626689218726987 )) + X1 ) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))))) + 3.7004594870966496 ) * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / X1 )) - (((9.343375511097939  * X1 ) + X1 ) / (((2.316579837661788  + (4.392275844763374  * X1 )) / cos(-0.006262263624705611 )) * 0.7626689218726987 ))) * X1 )))))))))))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((4.392275844763374  * 9.343375511097939 ) / ((4.392275844763374  / ((X1  + (X1  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 ))))) + (0.7626689218726987  + X1 )))))))) + 3.7004594870966496 ) * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) * X1 )) - (((9.343375511097939  * X1 ) + X1 ) / (((2.316579837661788  + (4.392275844763374  * X1 )) / cos(-0.006262263624705611 )) * 0.7626689218726987 ))) * X1 )) / (4.392275844763374  * cos(-0.006262263624705611 )))) / (X1  + ((4.392275844763374  * X1 ) / ((((3.7004594870966496  + 0.5448942125324017 ) + X1 ) / (((X1  + sin((0.5448942125324017  / ((9.343375511097939  / (((X1  + sin((0.5448942125324017  / ((X1  * ((4.392275844763374  * (X1  * X1 )) / (8.577013250045024  / X1 ))) / (9.343375511097939  * (X1  + (9.343375511097939  / 1.1316888487791577 ))))))) + (X1  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))))))) + (0.7626689218726987  + X1 )) - (X1  + sin((0.5448942125324017  / ((9.343375511097939  / ((9.343375511097939  * (((sin((0.5448942125324017  / ((9.343375511097939  / (((X1  + sin((9.343375511097939  / ((X1  - ((4.392275844763374  * (X1  * X1 )) / (-7.635773242188817  / -0.006262263624705611 ))) / (0.7626689218726987  * (3.7004594870966496  + ((((X1  + sin(((4.392275844763374  * (X1  * ((X1  / 3.7004594870966496 ) + sin(9.343375511097939 )))) / (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((((8.577013250045024  * (0.7626689218726987  + X1 )) * X1 ) / (3.7004594870966496  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / ((4.392275844763374  + ((3.7004594870966496  / 0.5448942125324017 ) + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - 0.5448942125324017 )) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (X1  * cos((X1  + 3.7004594870966496 )))) + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin(((4.392275844763374  * (X1  * ((X1  / X1 ) + sin(9.343375511097939 )))) - (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((((8.577013250045024  * (0.5448942125324017  + cos(-0.006262263624705611 ))) * X1 ) / (0.7626689218726987  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (9.343375511097939  - 9.343375511097939 )) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (X1  + sin((0.5448942125324017  / ((-0.006262263624705611  / ((9.343375511097939  * (4.392275844763374  * X1 )) - (4.392275844763374  * cos(-0.006262263624705611 )))) / (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((4.392275844763374  + sin((0.5448942125324017  / ((9.343375511097939  / (((X1  + ((8.577013250045024  * (0.7626689218726987  + -0.006262263624705611 )) * X1 )) + (X1  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) - 0.5448942125324017 )))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))))))) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (X1  * cos((9.343375511097939  / (((X1  + sin(((4.392275844763374  * (X1  * (((0.7626689218726987  + X1 ) / X1 ) + cos((((X1  - (X1  * 4.392275844763374 )) / ((4.392275844763374  * X1 ) / (((((X1  + cos(((4.392275844763374  * (X1  * 9.343375511097939 )) / X1 ))) + (0.5448942125324017  + X1 )) - (0.5448942125324017  / cos(-0.006262263624705611 ))) / ((X1  + (0.7626689218726987  + -0.006262263624705611 )) - 3.7004594870966496 )) / 0.5448942125324017 ))) / (4.392275844763374  * X1 )))))) / ((3.7004594870966496  * (0.7626689218726987  * (4.392275844763374  * ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin(((4.392275844763374  - (X1  * ((3.7004594870966496  / X1 ) * sin((X1  * 8.577013250045024 ))))) / (X1  + (0.7626689218726987  + X1 ))))) + (0.7626689218726987  + 4.392275844763374 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) + ((9.343375511097939  / (((0.5448942125324017  / X1 ) + (X1  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) + (X1  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))))))) / (8.577013250045024  / X1 )))))))))) + (X1  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))) / ((9.343375511097939  / 1.1316888487791577 ) * X1 )) - (((9.343375511097939  * X1 ) + X1 ) / (((((4.392275844763374  * X1 ) / 0.5448942125324017 ) + (4.392275844763374  * X1 )) / cos(-0.006262263624705611 )) * 0.7626689218726987 ))) * X1 )) - (4.392275844763374  * cos(-0.006262263624705611 )))) / (X1  + (((9.343375511097939  / ((X1  + (0.7626689218726987  + X1 )) - 3.7004594870966496 )) * X1 ) / ((9.343375511097939  / (((X1  + sin((0.5448942125324017  / 3.7004594870966496 ))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(0.5448942125324017 )))) / 0.5448942125324017 )))))))))))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((4.392275844763374  * X1 ) / 0.5448942125324017 ))))) + (X1  * X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))))))) / (X1  / X1 )))))))))) + (((X1  * ((4.392275844763374  * (X1  * X1 )) / (8.577013250045024  / X1 ))) / (9.343375511097939  * (X1  + (X1  + (9.343375511097939  / ((9.343375511097939  / (((X1  + (X1  + sin(((4.392275844763374  * (4.392275844763374  * ((3.7004594870966496  / X1 ) * sin((X1  * 8.577013250045024 ))))) / ((4.392275844763374  * sin((0.5448942125324017  / ((9.343375511097939  / (((X1  + sin((9.343375511097939  / ((X1  * ((4.392275844763374  * (X1  * X1 )) / (8.577013250045024  / X1 ))) / (9.343375511097939  * (X1  + (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + (X1  + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * sin((0.5448942125324017  * 8.577013250045024 ))))) / ((-0.006262263624705611  * (((((8.577013250045024  / X1 ) / ((9.343375511097939  / X1 ) / X1 )) - X1 ) * (0.5448942125324017  * (X1  + sin((X1  * X1 ))))) / (8.577013250045024  / X1 ))) / ((9.343375511097939  / ((X1  + (((X1  + sin(((4.392275844763374  * (X1  / (X1  + ((X1  * X1 ) / ((9.343375511097939  / (((X1  + cos(((4.392275844763374  * (X1  * 9.343375511097939 )) / 0.5448942125324017 ))) + (0.5448942125324017  + X1 )) - (-0.006262263624705611  / cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / (0.7626689218726987  + X1 )))) / 9.343375511097939 ) + X1 )) + 3.7004594870966496 )) / ((0.7626689218726987  + X1 ) * (X1  + ((3.7004594870966496  / X1 ) + sin((0.5448942125324017  + (9.343375511097939  * ((X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((((8.577013250045024  * (0.5448942125324017  + X1 )) * X1 ) / (0.7626689218726987  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / ((((X1  + sin((((8.577013250045024  * (0.5448942125324017  + X1 )) + X1 ) / (0.7626689218726987  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (9.343375511097939  - X1 )) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (X1  + sin((9.343375511097939  / ((9.343375511097939  / ((9.343375511097939  * (((4.392275844763374  * X1 ) / ((9.343375511097939  / ((((0.7626689218726987  + (X1  * 0.7626689218726987 )) + sin((((8.577013250045024  * 0.7626689218726987 ) * cos(-0.006262263624705611 )) / (X1  + ((X1  * X1 ) / ((9.343375511097939  / ((X1  + (0.7626689218726987  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (X1  * cos(-0.006262263624705611 )))) / ((X1  + sin(((4.392275844763374  * (((0.7626689218726987  * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / 9.343375511097939 )) - (((9.343375511097939  * ((X1  * (4.392275844763374  + ((0.5448942125324017  + X1 ) * (4.392275844763374  * X1 )))) - (0.5448942125324017  / cos(-0.006262263624705611 )))) + cos(-0.006262263624705611 )) / (0.5448942125324017  / ((9.343375511097939  / (((X1  * sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * sin((X1  * 8.577013250045024 ))))) / ((X1  * ((4.392275844763374  * (X1  * 0.7626689218726987 )) / (8.577013250045024  / X1 ))) / ((9.343375511097939  / ((X1  + 3.7004594870966496 ) - 3.7004594870966496 )) / (4.392275844763374  * (X1  + ((3.7004594870966496  / X1 ) + sin((0.5448942125324017  + (9.343375511097939  * ((((((8.577013250045024  + (9.343375511097939  * (X1  + (0.7626689218726987  * ((4.392275844763374  * 9.343375511097939 ) / ((9.343375511097939  / ((((0.7626689218726987  + (X1  * 0.7626689218726987 )) + X1 ) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))))) + 3.7004594870966496 ) * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / X1 )) - (((9.343375511097939  * X1 ) + X1 ) / (((2.316579837661788  + (4.392275844763374  * X1 )) / cos(-0.006262263624705611 )) * 0.7626689218726987 ))) * X1 )))))))))))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((4.392275844763374  * 9.343375511097939 ) / ((4.392275844763374  / ((X1  + (X1  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 ))))) + (0.7626689218726987  + X1 )))) * X1 )) - (4.392275844763374  * cos(-0.006262263624705611 )))) / (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((0.5448942125324017  / ((9.343375511097939  / (((X1  + sin((9.343375511097939  / ((X1  * 9.343375511097939 ) / (9.343375511097939  * (X1  + 3.7004594870966496 )))))) + (1.1316888487791577  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))))))) - 3.7004594870966496 )) / 0.5448942125324017 )))))) + (0.7626689218726987  + X1 )) - (X1  + sin((3.7004594870966496  / ((9.343375511097939  / ((9.343375511097939  * ((((((0.5448942125324017  + (9.343375511097939  * (X1  + (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / X1 ) / ((X1  + sin((((X1  * X1 ) * (((4.392275844763374  * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / X1 )) - (((((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * sin(X1 )))) / (X1  + (sin((((X1  * X1 ) * (((4.392275844763374  * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / X1 )) - (((((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * sin(X1 )))) / (X1  + (0.7626689218726987  + X1 ))) * ((X1  * (4.392275844763374  + ((0.5448942125324017  + X1 ) * (4.392275844763374  * X1 )))) - (9.343375511097939  / ((X1  + (0.7626689218726987  + X1 )) - 3.7004594870966496 )))) + X1 ) / (0.5448942125324017  / ((9.343375511097939  / (((X1  + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) + sin(((1.1316888487791577  / ((4.392275844763374  * X1 ) / ((9.343375511097939  / (0.7626689218726987  - 3.7004594870966496 )) / 0.5448942125324017 ))) / (4.392275844763374  * X1 )))))) / ((4.392275844763374  * (0.7626689218726987  - (4.392275844763374  * (X1  / ((((9.343375511097939  / (9.343375511097939  - 3.7004594870966496 )) / 0.5448942125324017 ) / (((X1  + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * sin((X1  * 8.577013250045024 ))))) / ((4.392275844763374  * ((4.392275844763374  * (8.577013250045024  * 0.7626689218726987 )) / (8.577013250045024  / X1 ))) / ((9.343375511097939  / ((X1  + 3.7004594870966496 ) - 3.7004594870966496 )) / 4.392275844763374 ))))) + (0.7626689218726987  + 8.577013250045024 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((9.343375511097939  / (((3.7004594870966496  / X1 ) + (0.5448942125324017  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) + (X1  + X1 )) - (9.343375511097939  - cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((4.392275844763374  * 9.343375511097939 ) / ((9.343375511097939  / ((X1  + (X1  + X1 )) / 3.7004594870966496 )) / 0.5448942125324017 )))) + X1 ))) * ((X1  * (4.392275844763374  + ((0.5448942125324017  + X1 ) * (4.392275844763374  * X1 )))) - (9.343375511097939  / ((X1  + (0.7626689218726987  + X1 )) - 3.7004594870966496 )))) + X1 ) / (0.5448942125324017  / ((9.343375511097939  / (((X1  + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) + sin(((1.1316888487791577  / ((4.392275844763374  * X1 ) / ((9.343375511097939  / (0.7626689218726987  - 3.7004594870966496 )) / 0.5448942125324017 ))) / (4.392275844763374  * X1 )))))) / ((4.392275844763374  * (0.7626689218726987  - (4.392275844763374  * (X1  / ((((9.343375511097939  / (9.343375511097939  - 3.7004594870966496 )) / 0.5448942125324017 ) / (((X1  + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * sin((X1  * 8.577013250045024 ))))) / ((4.392275844763374  * ((4.392275844763374  * (8.577013250045024  * 0.7626689218726987 )) / (8.577013250045024  / X1 ))) / ((9.343375511097939  / ((X1  + 3.7004594870966496 ) - 3.7004594870966496 )) / 4.392275844763374 ))))) + (0.7626689218726987  + 8.577013250045024 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((9.343375511097939  / (((3.7004594870966496  / X1 ) + (0.5448942125324017  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) + (X1  + X1 )) - (9.343375511097939  - cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((4.392275844763374  * 9.343375511097939 ) / ((9.343375511097939  / ((X1  + (X1  + X1 )) / 3.7004594870966496 )) / 0.5448942125324017 ))))) + (0.7626689218726987  + X1 )))))))) + 3.7004594870966496 ) * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / X1 )) - (((9.343375511097939  * X1 ) + X1 ) / (((2.316579837661788  + (4.392275844763374  * X1 )) / cos(-0.006262263624705611 )) * 0.7626689218726987 ))) * X1 )) - (4.392275844763374  * cos(-0.006262263624705611 )))) / (X1  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / (((X1  + sin((0.5448942125324017  / ((9.343375511097939  / (((X1  + sin((9.343375511097939  / ((X1  * ((-7.635773242188817  * (X1  * X1 )) / (8.577013250045024  / ((X1  + (X1  + sin(((1.1316888487791577  * (X1  * ((2.316579837661788  / X1 ) * sin((X1  * 8.577013250045024 ))))) / ((4.392275844763374  * sin((0.5448942125324017  / ((9.343375511097939  / (((X1  + sin((9.343375511097939  / ((X1  * ((-0.006262263624705611  * (X1  * X1 )) / (8.577013250045024  / X1 ))) / (9.343375511097939  * (X1  + (X1  + ((4.392275844763374  * ((9.343375511097939  / (9.343375511097939  - 3.7004594870966496 )) / 0.5448942125324017 )) / ((9.343375511097939  / (((X1  + (X1  + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / (9.343375511097939  / (9.343375511097939  - 3.7004594870966496 ))) * cos((X1  * 8.577013250045024 ))))) / ((4.392275844763374  * ((4.392275844763374  * (X1  * (X1  + sin((X1  * X1 ))))) / (8.577013250045024  / X1 ))) / ((9.343375511097939  / ((X1  + (((X1  + sin(((4.392275844763374  * (X1  + (X1  + ((X1  * X1 ) / ((9.343375511097939  / (((0.7626689218726987  + X1 ) / X1 ) + sin((((X1  - (X1  * 4.392275844763374 )) / ((4.392275844763374  * X1 ) / (((((X1  + cos(((4.392275844763374  * (X1  * 9.343375511097939 )) / X1 ))) + (0.5448942125324017  + X1 )) - (0.5448942125324017  / cos(-0.006262263624705611 ))) / ((X1  + (0.7626689218726987  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 ))) / (4.392275844763374  * X1 ))))) / 0.5448942125324017 ))))) / (0.7626689218726987  + ((X1  + cos(((4.392275844763374  * (X1  * 9.343375511097939 )) / X1 ))) + (0.5448942125324017  + X1 )))))) / -0.006262263624705611 ) + X1 )) - 3.7004594870966496 )) / (4.392275844763374  * (X1  + ((3.7004594870966496  / X1 ) + sin((0.5448942125324017  + (9.343375511097939  * ((((8.577013250045024  / X1 ) / ((9.343375511097939  / 0.7626689218726987 ) / X1 )) - X1 ) * X1 ))))))))))))) * (0.7626689218726987  + X1 )) - (X1  * cos(-0.006262263624705611 )))) / (X1  / X1 )))))))))) + (X1  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 9.343375511097939 )))) / ((9.343375511097939  / ((X1  + (((X1  + sin(((4.392275844763374  * (X1  + (X1  + ((X1  * X1 ) / ((9.343375511097939  / (((X1  + cos(((4.392275844763374  * (X1  * 4.392275844763374 )) / 0.5448942125324017 ))) + (0.5448942125324017  + X1 )) - (-0.006262263624705611  / cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / (0.7626689218726987  + 0.7626689218726987 )))) + 9.343375511097939 ) + X1 )) - 3.7004594870966496 )) / (4.392275844763374  * (X1  + ((3.7004594870966496  / X1 ) + sin((0.5448942125324017  + -0.006262263624705611 ))))))))))) + (0.7626689218726987  + X1 ))))) / (9.343375511097939  * (X1  + sin(9.343375511097939 ))))))) + (X1  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))))))) / 0.5448942125324017 ))) * 9.343375511097939 ))))))))))))) + (0.7626689218726987  + 9.343375511097939 )) - (X1  * cos(-0.006262263624705611 )))) / (X1  / 2.316579837661788 )))))))))) + (X1  / X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))) / ((9.343375511097939  / ((X1  + (((X1  + sin(((4.392275844763374  * (X1  + (X1  + ((X1  * X1 ) - ((9.343375511097939  / ((X1  + (0.5448942125324017  + X1 )) - (-0.006262263624705611  / cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / (0.7626689218726987  + X1 )))) / 9.343375511097939 ) + X1 )) - 3.7004594870966496 )) / (((X1  + sin(((2.316579837661788  * (((4.392275844763374  * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / X1 )) + (((9.343375511097939  * ((X1  * (4.392275844763374  + ((0.5448942125324017  + X1 ) - (4.392275844763374  * X1 )))) - (9.343375511097939  / ((X1  + (1.1316888487791577  + X1 )) + 3.7004594870966496 )))) + cos(-0.006262263624705611 )) / (0.5448942125324017  / ((9.343375511097939  / (((X1  + sin(((4.392275844763374  * (X1  * ((3.7004594870966496  / X1 ) * sin((X1  * 8.577013250045024 ))))) / ((4.392275844763374  * ((4.392275844763374  * (X1  * 0.7626689218726987 )) / (8.577013250045024  / X1 ))) / ((9.343375511097939  / ((X1  + 3.7004594870966496 ) - 4.392275844763374 )) / (4.392275844763374  - (X1  + ((3.7004594870966496  / X1 ) + sin((0.5448942125324017  + (9.343375511097939  * ((((((X1  + (9.343375511097939  * (X1  * (9.343375511097939  + ((4.392275844763374  * X1 ) / ((9.343375511097939  / ((((0.7626689218726987  + (X1  * 0.7626689218726987 )) + X1 ) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))))) + 3.7004594870966496 ) * (X1  * 0.7626689218726987 )) / ((9.343375511097939  / 1.1316888487791577 ) / X1 )) - (((0.7626689218726987  * X1 ) + X1 ) / (((2.316579837661788  + (4.392275844763374  * X1 )) / cos(-0.006262263624705611 )) * 0.7626689218726987 ))) * X1 )))))))))))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) / ((4.392275844763374  * 9.343375511097939 ) / ((9.343375511097939  / ((X1  + (X1  + X1 )) - 3.7004594870966496 )) / 0.5448942125324017 ))))) + (0.7626689218726987  + X1 )) * (X1  + ((3.7004594870966496  / X1 ) + sin((0.5448942125324017  + -0.006262263624705611 ))))))))))) + (0.7626689218726987  + X1 )) - 9.343375511097939 )) / (X1  / X1 ))))))) + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 )))) + (0.7626689218726987  + X1 )) - (9.343375511097939  * cos(-0.006262263624705611 )))) / 0.5448942125324017 ))))) * X1 ))";

        String optimizedExpression = simplifier.simplify(expression);

        System.out.println("Orginalne wyrażenie: " + expression);
        System.out.println("Uproszczone wyrażenie: " + optimizedExpression);

        // Zapisanie wyników do pliku JSON
        simplifier.saveToFile(expression, optimizedExpression);
    }
}
