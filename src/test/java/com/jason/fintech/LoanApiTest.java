package com.jason.fintech;

import io.restassured.RestAssured;
// These STATIC imports fix the "given()" and "equalTo()" errors 
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoanApiTest {

    public static void main(String[] args) {
        System.out.println("Connecting to Ezra API...");
        int code = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1").statusCode();
        System.out.println("API is UP: " + (code == 200));
    }

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testLoanProcess() {
        given()
            .header("Content-Type", "application/json")
            .body("{\"amount\": 1000, \"userId\": 1}")
        .when()
            .post("/posts")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .time(lessThan(3000L)); 
    }
}
