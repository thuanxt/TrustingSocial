import com.trustingsocial.PhoneAccount;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;

public class PhoneAccountTest {

    /**
     * Test Phone Account Constructor.
     * line is null
     */
    @Test
    public void testPhoneAccountConstructor01() {
        // Arrange
        String line = null;

        // Run
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Assert
        Assert.assertNull(phoneAccount.getPhoneNumber());
        Assert.assertNull(phoneAccount.getActivationDate());
        Assert.assertNull(phoneAccount.getDeactivationDate());
    }

    /**
     * Test Phone Account Constructor.
     * line is empty
     */
    @Test
    public void testPhoneAccountConstructor02() {
        // Arrange
        String line = "";

        // Run
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Assert
        Assert.assertNull(phoneAccount.getPhoneNumber());
        Assert.assertNull(phoneAccount.getActivationDate());
        Assert.assertNull(phoneAccount.getDeactivationDate());
    }

    /**
     * Test Phone Account Constructor.
     * line only has phone number
     */
    @Test
    public void testPhoneAccountConstructor03() {
        // Arrange
        String line = "123456789";

        // Run
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Assert
        Assert.assertEquals("123456789", phoneAccount.getPhoneNumber());
        Assert.assertNull(phoneAccount.getActivationDate());
        Assert.assertNull(phoneAccount.getDeactivationDate());
    }

    /**
     * Test Phone Account Constructor.
     * line only has activation date
     */
    @Test
    public void testPhoneAccountConstructor04() {
        // Arrange
        String line = ",2017-12-01";

        // Run
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Assert
        Assert.assertNull(phoneAccount.getPhoneNumber());
        Assert.assertEquals(LocalDate.parse("2017-12-01"), phoneAccount.getActivationDate());
        Assert.assertNull(phoneAccount.getDeactivationDate());
    }

    /**
     * Test Phone Account Constructor.
     * line only has deactivation date
     */
    @Test
    public void testPhoneAccountConstructor05() {
        // Arrange
        String line = ",,2017-12-01";

        // Run
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Assert
        Assert.assertNull(phoneAccount.getPhoneNumber());
        Assert.assertNull(phoneAccount.getActivationDate());
        Assert.assertEquals(LocalDate.parse("2017-12-01"), phoneAccount.getDeactivationDate());
    }

    /**
     * Test Phone Account Constructor.
     * line has phone number and activation date
     */
    @Test
    public void testPhoneAccountConstructor06() {
        // Arrange
        String line = "123456789,2017-12-01,";

        // Run
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Assert
        Assert.assertEquals("123456789", phoneAccount.getPhoneNumber());
        Assert.assertEquals(LocalDate.parse("2017-12-01"), phoneAccount.getActivationDate());
        Assert.assertNull(phoneAccount.getDeactivationDate());
    }

    /**
     * Test Phone Account Constructor.
     * line has phone number and deactivation date
     */
    @Test
    public void testPhoneAccountConstructor07() {
        // Arrange
        String line = "123456789,,2017-12-01";

        // Run
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Assert
        Assert.assertEquals("123456789", phoneAccount.getPhoneNumber());
        Assert.assertNull(phoneAccount.getActivationDate());
        Assert.assertEquals(LocalDate.parse("2017-12-01"), phoneAccount.getDeactivationDate());
    }

    /**
     * Test Phone Account Constructor.
     * line has activation date and deactivation date
     */
    @Test
    public void testPhoneAccountConstructor08() {
        // Arrange
        String line = ",2017-11-01,2017-12-01";

        // Run
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Assert
        Assert.assertNull(phoneAccount.getPhoneNumber());
        Assert.assertEquals(LocalDate.parse("2017-11-01"), phoneAccount.getActivationDate());
        Assert.assertEquals(LocalDate.parse("2017-12-01"), phoneAccount.getDeactivationDate());
    }

    /**
     * Test Phone Account Constructor.
     * line has full data
     */
    @Test
    public void testPhoneAccountConstructor09() {
        // Arrange
        String line = "123456789,2017-11-01,2017-12-01";

        // Run
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Assert
        Assert.assertEquals("123456789", phoneAccount.getPhoneNumber());
        Assert.assertEquals(LocalDate.parse("2017-11-01"), phoneAccount.getActivationDate());
        Assert.assertEquals(LocalDate.parse("2017-12-01"), phoneAccount.getDeactivationDate());
    }

    /**
     * Test toString()
     * Phone Account has no data
     */
    @Test
    public void testToString01() {
        // Arrange
        String line = null;
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Run
        String result = phoneAccount.toString();

        // Assert
        Assert.assertEquals(",", result);
    }

    /**
     * Test toString()
     * Phone Account only has phone number
     */
    @Test
    public void testToString02() {
        // Arrange
        String line = "123456789,,";
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Run
        String result = phoneAccount.toString();

        // Assert
        Assert.assertEquals("123456789,", result);
    }

    /**
     * Test toString()
     * Phone Account only has activation date
     */
    @Test
    public void testToString03() {
        // Arrange
        String line = ",2017-12-01,";
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Run
        String result = phoneAccount.toString();

        // Assert
        Assert.assertEquals(",2017-12-01", result);
    }

    /**
     * Test toString()
     * Phone Account only has deactivation date
     */
    @Test
    public void testToString04() {
        // Arrange
        String line = ",,2017-12-01";
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Run
        String result = phoneAccount.toString();

        // Assert
        Assert.assertEquals(",", result);
    }

    /**
     * Test toString()
     * Phone Account has full data
     */
    @Test
    public void testToString05() {
        // Arrange
        String line = "123456789,2017-11-01,2017-12-01";
        PhoneAccount phoneAccount = new PhoneAccount(line);

        // Run
        String result = phoneAccount.toString();

        // Assert
        Assert.assertEquals("123456789,2017-11-01", result);
    }

    /**
     * Test compareTo()
     * Phone Account 1's number < Phone Account 2's number
     */
    @Test
    public void testCompareTo01() {
        // Arrange
        String line = "123456789,2017-11-01,2017-12-01";
        PhoneAccount phoneAccount1 = new PhoneAccount(line);
        line = "123456799,2017-11-01,2017-12-01";
        PhoneAccount phoneAccount2 = new PhoneAccount(line);

        // Run
        int result = phoneAccount1.compareTo(phoneAccount2);

        // Assert
        Assert.assertEquals(-1, result);
    }

    /**
     * Test compareTo()
     * Phone Account 1's number > Phone Account 2's number
     */
    @Test
    public void testCompareTo02() {
        // Arrange
        String line = "123456799,2017-11-01,2017-12-01";
        PhoneAccount phoneAccount1 = new PhoneAccount(line);
        line = "123456789,2017-11-01,2017-12-01";
        PhoneAccount phoneAccount2 = new PhoneAccount(line);

        // Run
        int result = phoneAccount1.compareTo(phoneAccount2);

        // Assert
        Assert.assertEquals(1, result);
    }

    /**
     * Test compareTo()
     * Phone Account 1's number = Phone Account 2's number
     * Phone Account 1's activation date < Phone Account 2's activation date
     */
    @Test
    public void testCompareTo03() {
        // Arrange
        String line = "123456789,2017-11-01,2017-12-01";
        PhoneAccount phoneAccount1 = new PhoneAccount(line);
        line = "123456789,2017-12-01,2017-12-01";
        PhoneAccount phoneAccount2 = new PhoneAccount(line);

        // Run
        int result = phoneAccount1.compareTo(phoneAccount2);

        // Assert
        Assert.assertEquals(1, result);
    }

    /**
     * Test compareTo()
     * Phone Account 1's number = Phone Account 2's number
     * Phone Account 1's activation date > Phone Account 2's activation date
     */
    @Test
    public void testCompareTo04() {
        // Arrange
        String line = "123456789,2017-12-01,2017-12-01";
        PhoneAccount phoneAccount1 = new PhoneAccount(line);
        line = "123456789,2017-11-01,2017-12-01";
        PhoneAccount phoneAccount2 = new PhoneAccount(line);

        // Run
        int result = phoneAccount1.compareTo(phoneAccount2);

        // Assert
        Assert.assertEquals(-1, result);
    }

    /**
     * Test compareTo()
     * Phone Account 1's number = Phone Account 2's number
     * Phone Account 1's activation date = Phone Account 2's activation date
     */
    @Test
    public void testCompareTo05() {
        // Arrange
        String line = "123456789,2017-11-01,2017-12-01";
        PhoneAccount phoneAccount1 = new PhoneAccount(line);
        line = "123456789,2017-11-01,2017-12-01";
        PhoneAccount phoneAccount2 = new PhoneAccount(line);

        // Run
        int result = phoneAccount1.compareTo(phoneAccount2);

        // Assert
        Assert.assertEquals(0, result);
    }


}
