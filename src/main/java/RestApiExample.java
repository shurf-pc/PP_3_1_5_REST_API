
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestApiExample {

    private final RestTemplate restTemplate = new RestTemplate();

    public void execute() {
        String URL = "http://94.198.50.185:7081/api/users";
        ResponseEntity<String> response = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                null,
                String.class
        );
        System.out.println(response);
        String sessionId = response.getHeaders().getFirst("Set-Cookie");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> requestEntity = new HttpEntity<>(
                "{\"id\":3,\"name\":\"James\",\"lastName\":\"Brown\",\"age\":30}",
                headers
        );

        ResponseEntity<String> postResponse = restTemplate.exchange(
                URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        String stringResponse = postResponse.getBody();

        HttpEntity<String> putRequestEntity = new HttpEntity<>(
                "{\"id\":3,\"name\":\"Thomas\",\"lastName\":\"Shelby\",\"age\":30}",
                headers
        );

        ResponseEntity<String> putResponse = restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                putRequestEntity,
                String.class
        );

        stringResponse += putResponse.getBody();
        HttpEntity<Void> deleteRequestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> deleteResponse = restTemplate.exchange(
                URL + "/3",
                HttpMethod.DELETE,
                deleteRequestEntity,
                String.class
        );

        stringResponse += deleteResponse.getBody();

        System.out.println("Responce: " + stringResponse);
    }
}