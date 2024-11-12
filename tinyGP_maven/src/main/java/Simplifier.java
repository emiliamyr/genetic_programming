import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;


public class Simplifier {
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

    public static void main(String[] args) {
        Simplifier simplifier = new Simplifier();

        String expression = "((x1 + Sin((0.8191652358075636 * x1) + ((sin(x1) * 1.7791234893237649) / 3.1050906515930556)) * sin(2.002432509297936)) * sin(0.8191652358075636))";

        String optimizedExpression = simplifier.simplify(expression);

        System.out.println("Orginalne wyrażenie: " + expression);
        System.out.println("Uproszczone wyrażenie: " + optimizedExpression);
    }
}