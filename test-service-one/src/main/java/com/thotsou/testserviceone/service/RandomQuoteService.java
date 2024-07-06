package com.thotsou.testserviceone.service;

import com.thotsou.testserviceone.model.Quote;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RandomQuoteService {

    private final static List<String> QUOTES_CATEGORIES = List.of(
            "age",
            "alone",
            "amazing",
            "anger",
            "architecture",
            "art",
            "attitude",
            "beauty",
            "best",
            "birthday",
            "business",
            "car",
            "change",
            "communication",
            "computers",
            "cool",
            "courage",
            "dad",
            "dating",
            "death",
            "design",
            "dreams",
            "education",
            "environmental",
            "equality",
            "experience",
            "failure",
            "faith",
            "family",
            "famous",
            "fear",
            "fitness",
            "food",
            "forgiveness",
            "freedom",
            "friendship",
            "funny",
            "future",
            "god",
            "good",
            "government",
            "graduation",
            "great",
            "happiness",
            "health",
            "history",
            "home",
            "hope",
            "humor",
            "imagination",
            "inspirational",
            "intelligence",
            "jealousy",
            "knowledge",
            "leadership",
            "learning",
            "legal",
            "life",
            "love",
            "marriage",
            "medical",
            "men",
            "mom",
            "money",
            "morning",
            "movies",
            "success"
    );

    private final RestTemplate restTemplate;

    public ResponseEntity<Quote> generateRandomQuote(String category) {
        try {
            ResponseEntity<Quote[]> quotesApiResponse = this.restTemplate
                    .getForEntity("/quotes?category={quoteCategory}", Quote[].class, category);
            Quote[] fetchedQuotesArray = quotesApiResponse.getBody();

            return (fetchedQuotesArray == null || fetchedQuotesArray.length == 0) ?
                    ResponseEntity.ok(new Quote(null, null, null))
                    : ResponseEntity.ok(fetchedQuotesArray[0]);
        } catch (Exception e) {
            return ResponseEntity.ok(new Quote(null, null, null));
        }
    }

    public ResponseEntity<List<String>> getQuotesCategories() {
        // saving categories in a static class variable is not optimal
        // categories should be saved in DB and this method would use a respective quotes repository class to retrieve all quotes.
        return ResponseEntity.ok(QUOTES_CATEGORIES);
    }

}
