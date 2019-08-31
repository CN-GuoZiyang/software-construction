package debug;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RemoveCommentsTest {

  private RemoveComments removeComments = new RemoveComments();

  @Test
  public void removeCommentsTest() {
    String[] strings = {"/*Test program */", "int main()", "{ ", "  // variable declaration ", "int a, b, c;",
            "/* This is a test", "   multiline  ", "   comment for ", "   testing */", "a = b + c;", "}"};
    String[] result = {"int main()", "{ ", "  ", "int a, b, c;", "a = b + c;", "}"};
    List<String> real = removeComments.removeComments(strings);
    assertEquals(result.length, real.size());
    for (int i = 0; i < result.length; i++) {
      assertEquals(result[i], real.get(i));
    }
  }

}
