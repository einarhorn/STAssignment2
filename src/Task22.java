import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class Task22 {

    private EntryMap map;

    private TemplateEngine engine;

    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }

    // Constants
    final String VALID_ARG_1 = "name";
    final String VALID_ARG_2 = "Adam";
    
    
    // Fails m_0
    // Spec 6 - Input String contains many nested templates and has one extra close braces
    @Test
    public void TemplateEngineSpec6Test_ComplexString(){
    	map.store("date", "10th", false);
    	map.store("month", "January", false);
    	map.store("10th of January", "Febuary",false);
    	map.store("${ This is due 10th of Febuary }", "It Works", false);
    	String result = engine.evaluate("${${ This is due ${date} of ${ ${date} of ${month} } }}}", map, "keep-unmatched");
    	assertEquals("It Works}",result);
    }
    
    
    
	// m_1
    @Test
    public void Part22NewTest_SpaceBetweenTemplateChar(){

    	map.store("hi", "y", false);
    	String result = engine.evaluate("$x{hi}hello", map, "keep-unmatched");
    	
    	assertEquals("$x{hi}hello",result);
    }
    
    
	
	// m_2
    @Test
    public void Part22NewTest_EmptyTemplateInEngine(){

    	String result = engine.evaluate("${}hello", map, "delete-unmatched");
    	
    	assertEquals("hello",result);
    }
    
    
	
    
    // m_3
    // Spec 5 - Map should contain both values evern thought the keys are identical. Different due to value and boolean
    @Test
    public void EntryMapSpec5_DifferentEntryByValueAndCaseSensitivity() 
    {
    	map.store("name", "Donald", true);
    	map.store("name", "trump", false);
    	String result1 = engine.evaluate("${name} Duck is so funny!",map,"keep-unmatched");
    	String resutl2 = engine.evaluate("I still have a ${NAME} card up my sleeve!", map, "keep-unmatched");
    	assertEquals("Donald Duck is so funny!",result1);
    	assertEquals("I still have a trump card up my sleeve!",resutl2);
    }
    
    
	    
    // m_4
    // Spec 5 - Map should contain only one copy of identical entries
    @Test
    public void EntryMapSpec5_RejectDuplivateEntries(){ 

    	map.store("name", "adam", false);
    	map.store("name", "adam", false);
    	int size = map.getEntries().size();
    	assertEquals(1,size);
    	
    }
    
    
    // m_5
    // Spec 3 - Template matching case insensitive
    @Test
    public void EntryMapSpec3_FalseCaseSensitive(){
    	map.store("NAME", VALID_ARG_2, false);
    	String result = engine.evaluate("Hello ${name}", map,"delete-unmatched");
    	assertEquals("Hello Adam", result);
    	
    }
    
    // m_6
    // Spec 7 - Testing the ordering when template lengths are equal. Equal length templates should be ordered left-right.

    @Test
    public void TemplateEngineSpec7_EqualLengthTest(){
    	
    	map.store("name", "Einar", true);
    	map.store("name", "Ritvik", false);
    	String result1 = engine.evaluate("Second ${name} is ${NAME}", map, "keep-unmatched");
    	String result2 = engine.evaluate("Second ${NAME} is ${name}", map, "keep-unmatched");
    	
    	
    	assertEquals("Second Einar is Ritvik", result1);
    	assertEquals("Second Ritvik is Einar", result2);
    }
    
    
    
    // m_7 - Complex string fails this one as well
    
    
    // m_8 - Complex string fails this one as well (It throws an error)
    
    
    // m_9 - Complex string fails this one as well
    




    
}