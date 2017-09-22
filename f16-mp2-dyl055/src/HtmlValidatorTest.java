import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;




public class HtmlValidatorTest {

	@Test
	public void constructorAndGetterTest(){
		HtmlValidator h1 = new HtmlValidator();
		
		
		HtmlTag t = new HtmlTag("p");
		Queue<HtmlTag> x = new LinkedList<HtmlTag>();
		x.add(t);
		HtmlValidator h2 = new HtmlValidator(x);
		
		assertEquals(t, h2.getTags().peek());
		assertFalse(h1.equals(h2));
	}
	
	@Test
	public void addTagTest(){
		HtmlValidator h1 = new HtmlValidator();
		HtmlTag t1 = new HtmlTag("p");
		
		h1.addTag(t1);
		assertEquals(t1, h1.getTags().peek());
		
		HtmlTag t2 = null;
		
		try{
			h1.addTag(t2);
			fail("Did not throw IllegalArgumentException for adding null tag");
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
		
	}
	
	@Test
	public void removeAllTest(){
		HtmlValidator h1 = new HtmlValidator();
		HtmlValidator h2 = new HtmlValidator();
		
		HtmlTag t1 = new HtmlTag("p");
		HtmlTag t2 = new HtmlTag("h");
		
		
		
		h1.addTag(t1);
		h1.addTag(t2);
		
		h2.addTag(t2);
		
		String nullString = null;
			
		try{
			h1.removeAll(nullString);
			fail("Did not throw IllegalArgumentException for trying to remove a null html tag");
		}catch(IllegalArgumentException e){
			assertTrue(true);
		}
		h1.removeAll("p");
		
		assertEquals(h1.getTags(), h2.getTags());		
		
	}
	
	@Test
	public void validatorTest1() throws IOException{
		
		String expectedFile = "testcases/expected_output_1.txt";
		String htmlFile = "testcases/test1.html";
		
		String expected = readCompleteFile(expectedFile);
		String html = readCompleteFile(htmlFile);
		
		Queue<HtmlTag> tags = HtmlTag.tokenize(html);
		
		HtmlValidator h1 = new HtmlValidator(tags);
		
		assertEquals(h1.validate(), expected);
		
	}
	@Test
	public void validatorTest2() throws IOException{
		
		String expectedFile = "testcases/expected_output_2.txt";
		String htmlFile = "testcases/test2.html";
		
		String expected = readCompleteFile(expectedFile);
		String html = readCompleteFile(htmlFile);
		
		Queue<HtmlTag> tags = HtmlTag.tokenize(html);
		
		HtmlValidator h1 = new HtmlValidator(tags);
		
		assertEquals(h1.validate(), expected);
		
	}
	@Test
	public void validatorTest3() throws IOException{
		
		String expectedFile = "testcases/expected_output_3.txt";
		String htmlFile = "testcases/test3.html";
		
		String expected = readCompleteFile(expectedFile);
		String html = readCompleteFile(htmlFile);
		
		Queue<HtmlTag> tags = HtmlTag.tokenize(html);
		
		HtmlValidator h1 = new HtmlValidator(tags);
		
		assertEquals(h1.validate(), expected);
		
	}
	@Test
	public void validatorTest4() throws IOException{
		
		String expectedFile = "testcases/expected_output_4.txt";
		String htmlFile = "testcases/test4.html";
		
		String expected = readCompleteFile(expectedFile);
		String html = readCompleteFile(htmlFile);
		
		Queue<HtmlTag> tags = HtmlTag.tokenize(html);
		
		HtmlValidator h1 = new HtmlValidator(tags);
		
		assertEquals(h1.validate(), expected);
		
	}
	@Test
	public void validatorTest5() throws IOException{
		
		String expectedFile = "testcases/expected_output_5.txt";
		String htmlFile = "testcases/test5.html";
		
		String expected = readCompleteFile(expectedFile);
		String html = readCompleteFile(htmlFile);
		
		Queue<HtmlTag> tags = HtmlTag.tokenize(html);
		
		HtmlValidator h1 = new HtmlValidator(tags);
		
		assertEquals(h1.validate(), expected);
		
	}
	@Test
	public void validatorTest6() throws IOException{
		
		String expectedFile = "testcases/expected_output_6.txt";
		String htmlFile = "testcases/test6.html";
		
		String expected = readCompleteFile(expectedFile);
		String html = readCompleteFile(htmlFile);
		
		Queue<HtmlTag> tags = HtmlTag.tokenize(html);
		
		HtmlValidator h1 = new HtmlValidator(tags);
		
		assertEquals(h1.validate(), expected);
		
	}
	@Test
	public void validatorTest7() throws IOException{
		
		String expectedFile = "testcases/expected_output_7.txt";
		String htmlFile = "testcases/test7.html";
		
		String expected = readCompleteFile(expectedFile);
		String html = readCompleteFile(htmlFile);
		
		Queue<HtmlTag> tags = HtmlTag.tokenize(html);
		
		HtmlValidator h1 = new HtmlValidator(tags);
		
		assertEquals(h1.validate(), expected);
		
	}
	@Test
	public void validatorTest8() throws IOException{
		
		String expectedFile = "testcases/expected_output_8.txt";
		String htmlFile = "testcases/test8.html";
		
		String expected = readCompleteFile(expectedFile);
		String html = readCompleteFile(htmlFile);
		
		Queue<HtmlTag> tags = HtmlTag.tokenize(html);
		
		HtmlValidator h1 = new HtmlValidator(tags);
		
		assertEquals(h1.validate(), expected);
		
	}
	
	
	@Test
	public void validateTest9(){
		HtmlValidator h1 = new HtmlValidator();
		
		HtmlTag t1 = new HtmlTag("html");
		HtmlTag t2 = new HtmlTag("b");
		HtmlTag t3 = new HtmlTag("b", false);
		HtmlTag t4 = new HtmlTag("html", false);
		
		
		h1.addTag(t1);
		h1.addTag(t2);
		h1.addTag(t3);
		h1.addTag(t4);
		
		String expected = "<html>\n    <b>\n    </b>\n</html>\n";
		assertEquals(expected, h1.validate());
	}
	@Test
	public void validateTest10(){
		HtmlValidator h1 = new HtmlValidator();
		
		HtmlTag t1 = new HtmlTag("html");
		HtmlTag t2 = new HtmlTag("br");
		HtmlTag t3 = new HtmlTag("br");
		HtmlTag t4 = new HtmlTag("html", false);
		
		
		h1.addTag(t1);
		h1.addTag(t2);
		h1.addTag(t3);
		h1.addTag(t4);
		
		String expected = "<html>\n    <br>\n    <br>\n</html>\n";
		assertEquals(expected, h1.validate());
	}
	
	@Test
	public void validateTest11(){
		HtmlValidator h1 = new HtmlValidator();
		
		HtmlTag t1 = new HtmlTag("html");
		HtmlTag t2 = new HtmlTag("b");
		HtmlTag t3 = new HtmlTag("b", false);
		
		
		h1.addTag(t1);
		h1.addTag(t2);
		h1.addTag(t3);
		
		String expected = "<html>\n    <b>\n    </b>\nERROR unclosed tag: <html>\n";
		assertEquals(expected, h1.validate());
	}
	@Test
	public void validateTest12(){
		HtmlValidator h1 = new HtmlValidator();
		
		HtmlTag t1 = new HtmlTag("html");
		HtmlTag t2 = new HtmlTag("b");
		HtmlTag t3 = new HtmlTag("b", false);
		
		
		h1.addTag(t1);
		h1.addTag(t2);
		h1.addTag(t3);
		
		String expected = "<html>\n    <b>\n    </b>\nERROR unclosed tag: <html>\n";
		assertEquals(expected, h1.validate());
	}
	
	@Test
	public void validateTest13(){
		HtmlValidator h1 = new HtmlValidator();
		
		HtmlTag t1 = new HtmlTag("html");
		HtmlTag t2 = new HtmlTag("br");
		HtmlTag t3 = new HtmlTag("br");
		HtmlTag t4 = new HtmlTag("img");
		HtmlTag t5 = new HtmlTag("html", false);
		
		
		h1.addTag(t1);
		h1.addTag(t2);
		h1.addTag(t3);
		h1.addTag(t4);
		h1.addTag(t5);
		
		String expected = "<html>\n    <br>\n    <br>\n    <img>\n</html>\n";
		assertEquals(expected, h1.validate());
	}
    @Test
	public void testDefensive(){
		Queue<HtmlTag> tags = new LinkedList<HtmlTag>();
		HtmlValidator validator = new HtmlValidator(tags);
		
		validator.addTag(new HtmlTag("div"));
		
		assertFalse(!tags.isEmpty());
	}
	
	@Test
	public void testDefensive2(){
		Queue<HtmlTag> tags = new LinkedList<HtmlTag>();
		HtmlValidator validator = new HtmlValidator(tags);
		
		tags.add( new HtmlTag("div") );
		assertFalse(!validator.getTags().isEmpty());
	}

    public static String readCompleteFile(String address) throws IOException {
        InputStream stream = new FileInputStream(address); // open file

        // read each letter into a buffer
        StringBuffer buffer = new StringBuffer();
        while (true) {
            int ch = stream.read();
            if (ch < 0) {
                break;
            }

            buffer.append((char) ch);
        }

        return buffer.toString();
    }
    

}
