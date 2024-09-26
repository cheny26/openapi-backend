package com.cheny.openapi.interface1.controller;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
/**
 * @author chen_y
 * @date 2024-09-22 12:51
 */



@RestController
@RequestMapping("/string")
public class StringController {

    @PostMapping("/reverse")
    public Map<String, String> reverseString(@RequestBody Map<String, String> request) {
        String original = request.get("text");
        String reversed = new StringBuilder(original).reverse().toString();

        Map<String, String> response = new HashMap<>();
        response.put("original", original);
        response.put("reversed", reversed);
        return response;
    }

    @PostMapping("/count")
    public Map<String, Object> countCharacters(@RequestBody Map<String, String> request) {
        String text = request.get("text");

        Map<String, Object> response = new HashMap<>();
        response.put("text", text);
        response.put("length", text.length());
        response.put("wordCount", text.split("\\s+").length);
        response.put("letterCount", text.replaceAll("[^a-zA-Z]", "").length());
        response.put("digitCount", text.replaceAll("\\D", "").length());
        return response;
    }

    @PostMapping("/case")
    public Map<String, String> changeCase(@RequestBody Map<String, String> request) {
        String text = request.get("text");
        String operation = request.get("operation");

        Map<String, String> response = new HashMap<>();
        response.put("original", text);

        switch (operation.toLowerCase()) {
            case "upper":
                response.put("result", text.toUpperCase());
                break;
            case "lower":
                response.put("result", text.toLowerCase());
                break;
            default:
                response.put("error", "Invalid operation. Use 'upper' or 'lower'.");
        }

        return response;
    }

    @PostMapping("/trim")
    public Map<String, String> trimString(@RequestBody Map<String, String> request) {
        String text = request.get("text");

        Map<String, String> response = new HashMap<>();
        response.put("original", text);
        response.put("trimmed", text.trim());
        return response;
    }
}