package LiveProject;

import au.com.dius.pact.consumer.dsl.Dsl;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

@ExtendWith(PactConsumerTestExt.class)

public class ConsumerTest {
    //Headers
    Map<String,String> headers= new HashMap<>();

    //Resource path
    String resourcePath="/api/users";

    //Create the pact
    @Pact(consumer = "UserConsumer",provider = "UserProvider")

    public RequestResponsePact createPact(PactDslWithProvider builder){
        headers.put("Content-Type","application/json");

    //create the body
        DslPart requestResponseBody = new PactDslJsonBody()
                .numberType("id",1)
                .stringType("firstName",    "Bavithraa")
                .stringType("lastName","Marimuthu")
                .stringType("email","bavithraa.marimuthu@ibm.com");

        //create the contract
        return builder.given("A request to create a user")
                .uponReceiving("A request to create a user")
                .method("POST")
                .path(resourcePath)
                .headers(headers)
                .body(requestResponseBody)
                .willRespondWith()
                .status(201)
                .body(requestResponseBody)
                .toPact();
    }

    @Test
    @PactTestFor(providerName = "UserProvider",port = "8282")
    public void consumerTest() {
        //create base uri
        String baseURI="http://localhost:8282";
        //create req body
        Map<String,Object> reqBody = new HashMap<>();
        reqBody.put("id",121);
        reqBody.put("firstName","Bavithraa");
        reqBody.put("lastName","Marimuthu");
        reqBody.put("email","bavithraa.marimuthu@ibm.com");

        //generate response
        given().headers(headers).body(reqBody).log().all()
                .when().post(baseURI+resourcePath)
                .then().statusCode(201).log().all();

    }
}