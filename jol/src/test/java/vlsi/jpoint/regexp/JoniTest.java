package vlsi.jpoint.regexp;

import org.jcodings.specific.UTF8Encoding;
import org.joni.Matcher;
import org.joni.Option;
import org.joni.Regex;
import org.joni.Region;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JoniTest {

  @Test
  public void testJoni() {
    byte[] pattern = "a.*a.*a$".getBytes();
    byte[] str = "aaab".getBytes();

    Regex regex = new Regex(pattern, 0, pattern.length, Option.NONE, UTF8Encoding.INSTANCE);
    Matcher matcher = regex.matcher(str);
    int result = matcher.search(0, str.length, Option.DEFAULT);
    if (result != -1) {
      Region region = matcher.getEagerRegion();
      System.out.println("region = " + region);
    }
    Assert.assertEquals(result, 0, "match result");
  }
}
