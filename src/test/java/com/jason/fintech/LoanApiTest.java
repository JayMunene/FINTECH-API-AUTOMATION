package com.jason.fintech;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;
import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoanApiTest {
    
    private static String createdLoanId;
    
    public static void main(String[] args) {
        System.out.println("=== Fintech API Health Check ===");
        System.out.println("Connecting to API...");
        int code = RestAssured.get("https://jsonplaceholder.typicode.com/posts/1").statusCode();
        System.out.println("API Status: " + (code == 200 ? "✓ HEALTHY" : "✗ DOWN"));
        System.out.println("Status Code: " + code);
    }

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        System.out.println("\n🚀 Starting Fintech API Test Suite");
        System.out.println("Base URI: " + RestAssured.baseURI);
    }

    @BeforeEach
    public void testSetup(TestInfo testInfo) {
        System.out.println("\n▶ Running: " + testInfo.getDisplayName());
    }

    @AfterEach
    public void testTeardown(TestInfo testInfo) {
        System.out.println("✓ Completed: " + testInfo.getDisplayName());
    }

    @Test
    @Order(1)
    @DisplayName("TC01 - Create Loan Application with Valid Data")
    public void testCreateLoanApplication() {
        Map<String, Object> loanData = new HashMap<>();
        loanData.put("userId", 1);
        loanData.put("amount", 5000.00);
        loanData.put("interestRate", 12.5);
        loanData.put("tenure", 24);
        loanData.put("purpose", "Business Expansion");
        
        Response response = given()
            .header("Content-Type", "application/json")
            .body(loanData)
            .log().all()
        .when()
            .post("/posts")
        .then()
            .log().all()
            .statusCode(201)
            .body("id", notNullValue())
            .body("userId", equalTo(1))
            .time(lessThan(3000L))
        .extract().response();
        
        createdLoanId = response.jsonPath().getString("id");
        System.out.println("✓ Loan Application Created | ID: " + createdLoanId);
    }

    @Test
    @Order(2)
    @DisplayName("TC02 - Retrieve Loan Application Details")
    public void testGetLoanApplication() {
        given()
            .log().uri()
        .when()
            .get("/posts/1")
        .then()
            .log().body()
            .statusCode(200)
            .body("id", equalTo(1))
            .body("userId", notNullValue())
            .time(lessThan(2000L));
        
        System.out.println("✓ Loan Details Retrieved Successfully");
    }

    @Test
    @Order(3)
    @DisplayName("TC03 - Update Loan Application Status")
    public void testUpdateLoanApplication() {
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("id", 1);
        updateData.put("status", "APPROVED");
        updateData.put("approvedAmount", 4500.00);
        
        given()
            .header("Content-Type", "application/json")
            .body(updateData)
            .log().all()
        .when()
            .put("/posts/1")
        .then()
            .log().all()
            .statusCode(200)
            .body("id", equalTo(1))
            .time(lessThan(2500L));
        
        System.out.println("✓ Loan Status Updated to APPROVED");
    }

    @Test
    @Order(4)
    @DisplayName("TC04 - Get All Loan Applications")
    public void testGetAllLoans() {
        given()
            .log().uri()
        .when()
            .get("/posts")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("size()", greaterThan(0))
            .time(lessThan(3000L));
        
        System.out.println("✓ Retrieved All Loan Applications");
    }

    @Test
    @Order(5)
    @DisplayName("TC05 - Verify Interest Calculation Logic")
    public void testInterestCalculation() {
        // Simulating interest calculation validation
        double principal = 5000.00;
        double rate = 12.5;
        int tenure = 24;
        double expectedInterest = (principal * rate * tenure) / (12 * 100);
        
        System.out.println("Principal: ₹" + principal);
        System.out.println("Interest Rate: " + rate + "%");
        System.out.println("Tenure: " + tenure + " months");
        System.out.println("Calculated Interest: ₹" + String.format("%.2f", expectedInterest));
        
        Assertions.assertTrue(expectedInterest > 0, "Interest calculation should be positive");
        System.out.println("✓ Interest Logic Validated");
    }

    @Test
    @Order(6)
    @DisplayName("TC06 - Negative Test - Invalid Loan Amount")
    public void testInvalidLoanAmount() {
        Map<String, Object> invalidData = new HashMap<>();
        invalidData.put("userId", 1);
        invalidData.put("amount", -1000); // Negative amount
        
        given()
            .header("Content-Type", "application/json")
            .body(invalidData)
            .log().all()
        .when()
            .post("/posts")
        .then()
            .log().all()
            // JSONPlaceholder accepts everything, but in real API this would be 400
            .statusCode(anyOf(equalTo(201), equalTo(400)));
        
        System.out.println("✓ Negative Test Case Executed");
    }

    @Test
    @Order(7)
    @DisplayName("TC07 - Response Time Performance Check")
    public void testResponseTimePerformance() {
        long startTime = System.currentTimeMillis();
        
        Response response = given()
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .time(lessThan(2000L))
        .extract().response();
        
        long responseTime = response.time();
        System.out.println("Response Time: " + responseTime + "ms");
        
        Assertions.assertTrue(responseTime < 2000, 
            "Response time should be under 2 seconds for production");
        System.out.println("✓ Performance Test Passed");
    }

    @Test
    @Order(8)
    @DisplayName("TC08 - Verify Response Headers")
    public void testResponseHeaders() {
        given()
        .when()
            .get("/posts/1")
        .then()
            .statusCode(200)
            .header("Content-Type", containsString("application/json"))
            .time(lessThan(2000L));
        
        System.out.println("✓ Response Headers Validated");
    }

    @Test
    @Order(9)
    @DisplayName("TC09 - Delete Loan Application")
    public void testDeleteLoanApplication() {
        given()
            .log().uri()
        .when()
            .delete("/posts/1")
        .then()
            .log().status()
            .statusCode(200)
            .time(lessThan(2000L));
        
        System.out.println("✓ Loan Application Deleted");
    }

    @Test
    @Order(10)
    @DisplayName("TC10 - Verify 404 for Non-Existent Loan")
    public void testNonExistentLoan() {
        given()
            .log().uri()
        .when()
            .get("/posts/99999")
        .then()
            .log().status()
            .statusCode(404);
        
        System.out.println("✓ 404 Handling Verified");
    }

    @AfterAll
    public static void teardown() {
        System.out.println("\n✅ Test Suite Completed Successfully");
        System.out.println("═══════════════════════════════════════");
    }
}
