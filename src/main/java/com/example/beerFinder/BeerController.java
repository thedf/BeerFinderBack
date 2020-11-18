package com.example.beerFinder;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.JSONException;

@CrossOrigin(origins = "*")
@RestController
public class BeerController {

    @GetMapping("/beer")
    public ResponseEntity<String> greetingPost(@RequestParam(value = "name", defaultValue = "Punk") String name,
            @RequestParam(value = "page", defaultValue = "1") String page)
            throws URISyntaxException, IOException, JSONException {

        String GeneratedUrl = String.format("https://api.punkapi.com/v2/beers?beer_name=%s&page=%s",
                name.replace(" ", "_"), page);

        RestTemplate restTemplate = new RestTemplate();
        String res = restTemplate.getForObject(GeneratedUrl, String.class);
        return ResponseEntity.ok("{ \"data\" :" + res + '}');
    }
}
