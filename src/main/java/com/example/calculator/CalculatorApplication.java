
package com.example.calculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@Controller
public class CalculatorApplication {

    private String expression = "";

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

    @GetMapping("/")
    public String showCalculator(Model model) {
        model.addAttribute("expression", expression);
	return "index";
    }

    @PostMapping("/")
    public String handleButton(@RequestParam String button, Model model) {
        if (button.equals("C")) {
            expression = "";
        } else if (button.equals("=")) {
            try {
                expression = evaluateExpression(expression);
            } catch (Exception e) {
                expression = "Error";
            }
        } else {
            expression += button;
        }

        model.addAttribute("expression", expression);
        return "index";
    }

    private String evaluateExpression(String expr) {
        // Simple evaluator using JavaScript engine
        try {
            javax.script.ScriptEngine engine = new javax.script.ScriptEngineManager().getEngineByName("JavaScript");
            Object result = engine.eval(expr);
            return String.valueOf(result);
        } catch (Exception e) {
            return "Error";
        }
    }
}

