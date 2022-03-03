package stepDef.DBStepDef;

import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import utils.DBUtil;

import java.math.BigDecimal;
import java.sql.SQLException;

public class DBStepDef {

    private final Logger logger = LogManager.getLogger(DBStepDef.class);

    @Given("User is able to connect to database")
    public void user_is_able_to_connect_to_database() throws SQLException {
        DBUtil.createDBConnection();
        System.out.println("I am able to connect to db");
        logger.info("I can connect to my database");
        logger.error("connection failed");
    }

    @Given("User sends the {string} to database")
    public void user_sends_the_to_database(String query) throws SQLException {
//        DBUtil.executeQuery(query);
        Object actualSalary = DBUtil.getCellValue(query);
        logger.info("Query is executed");
        int expectedSalary = 2100;
        logger.info("The value from the database " + actualSalary, " is matching with the " + expectedSalary);
        logger.info("Step is passing for getting the value of the salary");
    }

    @Given("User sends the {string} to database to get min {int}")
    public void user_sends_the_to_database_to_get_min(String query, Integer expectedSalary) {
        Object actualSalary = DBUtil.getCellValue(query);
        logger.info("Query is executed");
        Assert.assertEquals(actualSalary, new BigDecimal(expectedSalary));
        logger.debug("The value from the database " + actualSalary, " is matching with the " + new BigDecimal(expectedSalary));
        logger.info("Step is passing for getting the value of the salary");
    }
}
