package com.example.beerFinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpRequest;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONException;
import org.json.JSONObject;

@CrossOrigin(origins = "*")
@RestController
public class BeerController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("{ \"data\" :");
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        sb.append((char) '}');

        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            // System.out.println(jsonText);

            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String readTextFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            // System.out.println(jsonText);
            return jsonText;
        } finally {
            is.close();
        }
    }

    @GetMapping("/beer")
    public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "You must be lost!";
    }

    @PostMapping("/beer")
    public ResponseEntity<String> greetingPost(@RequestParam(value = "name", defaultValue = "Buzz") String name,
            @RequestParam(value = "page", defaultValue = "1") String page)
            throws URISyntaxException, IOException, JSONException {
        String json = readTextFromUrl(
                String.format("https://api.punkapi.com/v2/beers?beer_name=%s&page=%s", name.replace(" ", "_"), page));
        return ResponseEntity.ok(json);
    }
}
