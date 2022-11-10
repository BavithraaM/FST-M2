import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHub_RestAssured_Project {
    RequestSpecification reqSpec;
    String sshKey;
    int sshId;

    @BeforeClass
    public void setUp(){
       reqSpec= new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
               .addHeader("Authorization","token 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")
                .setBaseUri("https://api.github.com")
                .build();
	sshKey = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQCOkC+tCNHscULD4xbD2c+XGw/MMLRngv29ShsiTYzQ1FQuO8tI2U3HiQa3T3UiP1M0nD1DtGI9xLHpsV9gV+OZ7T4rmQLW8wLcQJ+vwQqFs9QsZq15lqe74CEyuBsQFDznLh6rbi5RbYOVvRQu+GCmqIOatbTXDqZ3lGlBrmvPIQ==";
    }

    @Test
    public void addSSHKey(){
        String reqBody = "{\"title\": \"TestAPIKey\", \"key\": \"" + sshKey + "\" }";


        Response res= given().spec(reqSpec).body(reqBody).when().post("/user/keys");
        System.out.println(res.body().asPrettyString());
        res.then().statusCode(201);
        res.then().body("title",equalTo("TestAPIKey"));
        sshId=res.then().extract().path("id");
    }

    @Test
    public void getSSHKey(){
        Response res=given().spec(reqSpec).when().get("/user/keys");
        System.out.println(res.getBody().asPrettyString());
        res.then().statusCode(200);
        Reporter.log("Response of GET request is: "+res.getBody().asPrettyString());
    }

    @Test
    public void deleteSSHKey(){
        Response res= given().spec(reqSpec).pathParams("keyId",sshId).when().delete("/user/keys/{keyId}");
        System.out.println(res.getBody().asPrettyString());
        res.then().statusCode(204);
        Reporter.log("Response of GET request is: "+res.getBody().asPrettyString());
    }
}