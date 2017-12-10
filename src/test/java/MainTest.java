import com.trustingsocial.Main;
import com.trustingsocial.PhoneAccount;
import org.junit.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainTest {

    @BeforeClass
    public static void setUp() {
        // Create TestCsv01
        String testFileName = "TestCsv01.csv";
        String[] testData = {
                "333,2017-01-01,2017-03-01",
                "222,2017-01-01,2017-03-01",
                "111,2017-01-01,2017-03-01",
                "111,2017-05-01,2017-06-01",
                "222,2017-03-01,2017-05-01"
        };
        createTestFile(testFileName, testData);
    }

    @AfterClass
    public static void cleanUp() {
        try {
            // Delete TestCsv01
            Files.deleteIfExists(Paths.get("TestCsv01.csv"));
            // Delete TestCsvOut01
            Files.deleteIfExists(Paths.get("TestCsvOut01.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create test data
     */
    private static void createTestFile(String fileName, String[] testData) {
        final String HEADER = "PHONE_NUMBER,ACTIVATION_DATE,DEACTIVATION_DATE";

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Paths.get(fileName)))) {
            // write header
            pw.println(HEADER);
            // write all the records from the list
            Arrays.asList(testData).forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test convertFileToList()
     * file TestCsv01: test sort
     */
    @Test
    public void testConvertFileToList01() {
        // Arrange
        String filename = "TestCsv01.csv";
        String[] expectedData = {
                "111,2017-05-01,2017-06-01",
                "111,2017-01-01,2017-03-01",
                "222,2017-03-01,2017-05-01",
                "222,2017-01-01,2017-03-01",
                "333,2017-01-01,2017-03-01"
        };

        // Run
        List<PhoneAccount> result = Main.convertFileToList(filename);

        // Assert
        Assert.assertEquals(5, result.size());
        int i = 0;
        for (PhoneAccount phone : result) {
            String[] expectedResult = expectedData[i].split(",");
            // check phone number
            Assert.assertEquals(expectedResult[0], phone.getPhoneNumber());
            // check activation date
            Assert.assertEquals(LocalDate.parse(expectedResult[1]), phone.getActivationDate());
            // check deactivation date
            Assert.assertEquals(LocalDate.parse(expectedResult[2]), phone.getDeactivationDate());
            i++;
        }
    }

    /**
     * Test getActualActivationDateList()
     * empty list
     */
    @Test
    public void testGetActualActivationDateList01() {
        // Arrange
        List<PhoneAccount> phoneList = new ArrayList<>();

        // Run
        List<PhoneAccount> result = Main.getActualActivationDateList(phoneList);

        // Assert
        Assert.assertEquals(0, result.size());
    }

    /**
     * Test getActualActivationDateList()
     * data has only one phone number, output first record
     */
    @Test
    public void testGetActualActivationDateList02() {
        // Arrange
        String[] testData = {
                "111,2017-05-01,2017-06-01",
                "111,2017-01-01,2017-03-01",
                "111,2016-03-01,2016-05-01"
        };
        String[] expectedData = {
                "111,2017-05-01,2017-06-01"
        };
        // prepare expected result
        String[] expectedResult = expectedData[0].split(",");
        // prepare input list from test data
        List<PhoneAccount> phoneList = Arrays.stream(testData)
                .map(PhoneAccount::new)
                .collect(Collectors.toList());
        // Run
        List<PhoneAccount> result = Main.getActualActivationDateList(phoneList);

        // Assert
        Assert.assertEquals(1, result.size());
        // check phone number
        Assert.assertEquals(expectedResult[0], result.get(0).getPhoneNumber());
        // check activation date
        Assert.assertEquals(LocalDate.parse(expectedResult[1]), result.get(0).getActivationDate());
        // check deactivation date
        Assert.assertEquals(LocalDate.parse(expectedResult[2]), result.get(0).getDeactivationDate());
    }

    /**
     * Test getActualActivationDateList()
     * data has only one phone number, output middle record
     */
    @Test
    public void testGetActualActivationDateList03() {
        // Arrange
        String[] testData = {
                "111,2017-05-01,2017-06-01",
                "111,2017-01-01,2017-05-01",
                "111,2016-03-01,2016-05-01"
        };
        String[] expectedData = {
                "111,2017-01-01,2017-05-01"
        };
        // prepare expected result
        String[] expectedResult = expectedData[0].split(",");
        // prepare input list from test data
        List<PhoneAccount> phoneList = Arrays.stream(testData)
                .map(PhoneAccount::new)
                .collect(Collectors.toList());
        // Run
        List<PhoneAccount> result = Main.getActualActivationDateList(phoneList);

        // Assert
        Assert.assertEquals(1, result.size());
        // check phone number
        Assert.assertEquals(expectedResult[0], result.get(0).getPhoneNumber());
        // check activation date
        Assert.assertEquals(LocalDate.parse(expectedResult[1]), result.get(0).getActivationDate());
        // check deactivation date
        Assert.assertEquals(LocalDate.parse(expectedResult[2]), result.get(0).getDeactivationDate());
    }

    /**
     * Test getActualActivationDateList()
     * data has only one phone number, output last record
     */
    @Test
    public void testGetActualActivationDateList04() {
        // Arrange
        String[] testData = {
                "111,2017-05-01,2017-06-01",
                "111,2017-01-01,2017-05-01",
                "111,2016-03-01,2017-01-01"
        };
        String[] expectedData = {
                "111,2016-03-01,2017-01-01"
        };
        // prepare expected result
        String[] expectedResult = expectedData[0].split(",");
        // prepare input list from test data
        List<PhoneAccount> phoneList = Arrays.stream(testData)
                .map(PhoneAccount::new)
                .collect(Collectors.toList());
        // Run
        List<PhoneAccount> result = Main.getActualActivationDateList(phoneList);

        // Assert
        Assert.assertEquals(1, result.size());
        // check phone number
        Assert.assertEquals(expectedResult[0], result.get(0).getPhoneNumber());
        // check activation date
        Assert.assertEquals(LocalDate.parse(expectedResult[1]), result.get(0).getActivationDate());
        // check deactivation date
        Assert.assertEquals(LocalDate.parse(expectedResult[2]), result.get(0).getDeactivationDate());
    }

    /**
     * Test getActualActivationDateList()
     * data has 2 phone numbers, output last record
     */
    @Test
    public void testGetActualActivationDateList05() {
        // Arrange
        String[] testData = {
                "111,2017-05-01,2017-06-01",
                "111,2017-01-01,2017-05-01",
                "111,2016-03-01,2017-01-01",
                "222,2017-05-01,2017-06-01"
        };
        String[] expectedData = {
                "111,2016-03-01,2017-01-01",
                "222,2017-05-01,2017-06-01"
        };

        // prepare input list from test data
        List<PhoneAccount> phoneList = Arrays.stream(testData)
                .map(PhoneAccount::new)
                .collect(Collectors.toList());
        // Run
        List<PhoneAccount> result = Main.getActualActivationDateList(phoneList);

        // Assert
        Assert.assertEquals(2, result.size());
        int i = 0;
        for (PhoneAccount phone : result) {
            String[] expectedResult = expectedData[i].split(",");
            // check phone number
            Assert.assertEquals(expectedResult[0], phone.getPhoneNumber());
            // check activation date
            Assert.assertEquals(LocalDate.parse(expectedResult[1]), phone.getActivationDate());
            // check deactivation date
            Assert.assertEquals(LocalDate.parse(expectedResult[2]), phone.getDeactivationDate());
            i++;
        }
    }

    /**
     * Test getActualActivationDateList()
     * data has 3 phone numbers
     * the first number output first record
     * the second number output middle record
     * the third number output last record
     */
    @Test
    public void testGetActualActivationDateList06() {
        // Arrange
        String[] testData = {
                "111,2017-05-01,2018-01-01",
                "111,2016-01-01,2016-05-01",
                "111,2015-03-01,2016-01-01",
                "222,2017-05-01,2018-01-01",
                "222,2017-04-01,2017-05-01",
                "222,2017-01-01,2017-03-01",
                "333,2017-05-01,2017-06-01",
                "333,2016-05-01,2017-05-01",
                "333,2015-05-01,2016-05-01"
        };
        String[] expectedData = {
                "111,2017-05-01,2018-01-01",
                "222,2017-04-01,2017-05-01",
                "333,2015-05-01,2016-05-01"
        };

        // prepare input list from test data
        List<PhoneAccount> phoneList = Arrays.stream(testData)
                .map(PhoneAccount::new)
                .collect(Collectors.toList());
        // Run
        List<PhoneAccount> result = Main.getActualActivationDateList(phoneList);

        // Assert
        Assert.assertEquals(3, result.size());
        int i = 0;
        for (PhoneAccount phone : result) {
            String[] expectedResult = expectedData[i].split(",");
            // check phone number
            Assert.assertEquals(expectedResult[0], phone.getPhoneNumber());
            // check activation date
            Assert.assertEquals(LocalDate.parse(expectedResult[1]), phone.getActivationDate());
            // check deactivation date
            Assert.assertEquals(LocalDate.parse(expectedResult[2]), phone.getDeactivationDate());
            i++;
        }
    }

    /**
     * Test writeToFile()
     */
    @Test
    public void testWriteToFile01() {
        // Arrange
        final String fileName = "TestCsvOut01.csv";
        final String header = "PHONE_NUMBER,REAL_ACTIVATION_DATE";
        String[] testData = {
                "111,2017-05-01,2018-01-01",
                "222,2017-04-01,2017-05-01",
                "333,2015-05-01,2016-05-01"
        };

        // Arrange
        String[] expectedData = {
                header,
                "111,2017-05-01",
                "222,2017-04-01",
                "333,2015-05-01"
        };

        // prepare input list from test data
        List<PhoneAccount> phoneList = Arrays.stream(testData)
                .map(PhoneAccount::new)
                .collect(Collectors.toList());
        // Run
        Main.writeToFile(fileName, header, phoneList);

        // Assert
        // check file exists
        Assert.assertTrue(Files.exists(Paths.get(fileName)));
        // check file content
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            List<String> resultList = stream.collect(Collectors.toList());
            int i = 0;
            for (String result : resultList) {
                Assert.assertEquals(expectedData[i], result);
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
