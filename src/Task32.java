import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class Task32 {

	
	private EntryMap map;

    private TemplateEngine engine;
    
    @Before
    public void setUp() throws Exception {
        map = new EntryMap();
        engine = new TemplateEngine();
    }
    
    /**
     * NEW SPECS
     
    
    NEW TemplateEngine.evaluate specification

    1) TemplateEngine should have an additional matching mode called "optimization" (Optimization and NULL are not the same anymore).

    2) When the matching mode is "optimization", the TemplateEngine should internally try the two other matching modes ("keep-unmatched" and "delete-unmatched") and use the one which replaces the most templates.

    3) In the case that both matching modes replace the same number of templates, "keep-unmatched" should be used.
    
    */
    
    // Test1: Test To See If 'optimization' is recognized instead of defaulting to 'delete-unmatched' similar to 'null
    @Test
    public void TDDTestToSeeIfOptimizationIsValidMode(){
    	EntryMap map1 = new EntryMap();
        TemplateEngine engine1 = new TemplateEngine();
    	
        EntryMap map2 = new EntryMap();
        TemplateEngine engine2 = new TemplateEngine();
        		
    	map.store("Something", "Wrong", false);
    	map1.store("Something", "Wrong", false);
    	map2.store("Something", "Wrong", false);
    	
    	String resultOptimized = engine.evaluate("${Nothing} should match", map, "optimization");
    	String resultDeleteUnMatched = engine1.evaluate("${Nothing} should match", map1, null);
    	String resultKeepUnMatched = engine2.evaluate("${Nothing} should match", map2, "keep-unmatched");
    	
    	assertEquals(resultOptimized,resultKeepUnMatched);
    	assertEquals(" should match",resultDeleteUnMatched);
    	assertThat(resultOptimized, not(resultDeleteUnMatched));
    	
    	
    }
    
    // Test2: Test To See If Optimization Chooses KeepUnmatched Instead Of DeleteUnmatched When Zero Matches
    @Test
    public void TDDTestToSeeIfOptimizationChoosesKeepUnmatchedInsteadOfDeleteUnmatchedWhenZeroMatchesForBoth(){
    	EntryMap map1 = new EntryMap();
        TemplateEngine engine1 = new TemplateEngine();
    	
        EntryMap map2 = new EntryMap();
        TemplateEngine engine2 = new TemplateEngine();
        
   
        
    	map.store("Something", "Wrong", false);
    	map1.store("Something", "Wrong", false);
    	map2.store("Something", "Wrong", false);
    	
    	
    	
    	String resultOptimized = engine.evaluate("${Nothing should match}", map, "optimization");
    	String resultKeepUnMatched = engine1.evaluate("${Nothing should match}", map1, "keep-unmatched");
    	String resultDeleteUnMatched = engine2.evaluate("${Nothing should match}", map2, "delete-unmatched");
    	
    	assertEquals(resultOptimized,resultKeepUnMatched);
    	assertThat(resultOptimized, not(resultDeleteUnMatched));
    	assertEquals("${Nothing should match}", resultOptimized);
    }
    
    // Test3: Test To See If Optimization Chooses KeepUnmatched Instead Of DeleteUnmatched When 1 Matches
    @Test
    public void TDDTestToSeeIfOptimizationChoosesKeepUnmatchedInsteadOfDeleteUnmatchedWhenOneMatchesForBoth(){
    	EntryMap map1 = new EntryMap();
        TemplateEngine engine1 = new TemplateEngine();
    	
        EntryMap map2 = new EntryMap();
        TemplateEngine engine2 = new TemplateEngine();
        
   
        
    	map.store("Something", "Wrong", false);
    	map1.store("Something", "Wrong", false);
    	map2.store("Something", "Wrong", false);
    	map.store("Nothing", "One", false);
    	map1.store("Nothing", "One", false);
    	map2.store("Nothing", "One", false);
    	
    	
    	String resultOptimized = engine.evaluate("${Nothing} ${should match}", map, "optimization");
    	String resultKeepUnMatched = engine1.evaluate("${Nothing} ${should match}", map1, "keep-unmatched");
    	String resultDeleteUnMatched = engine2.evaluate("${Nothing} ${should match}", map2, "delete-unmatched");
    	
    	assertEquals(resultOptimized,resultKeepUnMatched);
    	assertThat(resultOptimized, not(resultDeleteUnMatched));
    	assertEquals("One ${should match}",resultOptimized);
    }
    
    // Test4: Test To See If Optimization Chooses KeepUnmatched Instead Of DeleteUnmatched When 1+ Matches
    @Test
    public void TDDTestToSeeIfOptimizationChoosesKeepUnmatchedInsteadOfDeleteUnmatchedWhenManyMatchesForBoth(){
    	EntryMap map1 = new EntryMap();
        TemplateEngine engine1 = new TemplateEngine();
    	
        EntryMap map2 = new EntryMap();
        TemplateEngine engine2 = new TemplateEngine();
        
   
        
    	map.store("Something", "Wrong", false);
    	map1.store("Something", "Wrong", false);
    	map2.store("Something", "Wrong", false);
    	map.store("Nothing", "More than", false);
    	map1.store("Nothing", "More than", false);
    	map2.store("Nothing", "More than", false);
    	map.store("should", "one", false);
    	map1.store("should", "one", false);
    	map2.store("should", "one", false);
    	
    	
    	
    	String resultOptimized = engine.evaluate("${Nothing} ${should} ${match}", map, "optimization");
    	String resultKeepUnMatched = engine1.evaluate("${Nothing} ${should} ${match}", map1, "keep-unmatched");
    	String resultDeleteUnMatched = engine2.evaluate("${Nothing} ${should} ${match}", map2, "delete-unmatched");
    	
    	assertEquals(resultOptimized,resultKeepUnMatched);
    	assertThat(resultOptimized, not(resultDeleteUnMatched));
    	assertEquals("More than one ${match}",resultOptimized);
    }
    
    // Test5: Test To See If Optimization Chooses DeleteUnMatched if DeleteUnMatched replaces more templates by 1
    @Test
    public void TDDTestToSeeIfOptimicationChoosesDeleteUmatchedWhenOneExtraMatchForDeleteUnMatched(){
    	
    	map.store("Nothing match", "It Works", false);
    
    	String resultOptimized = engine.evaluate("${Nothing ${Something} match}", map, "optimization");
    	
    	assertEquals(resultOptimized,"It Works");
    	
    }
    
    // Test6: Test To See If Optimization Chooses DeleteUnMatched if DeleteUnMatched replaces more templates by 1+
    @Test
    public void TDDTestToSeeIfOptimicationChoosesDeleteUmatchedWhenManyExtraMatchForDeleteUnMatched(){
    	
    	map.store("Something ${inner something}", "NOT works", false);
    	map.store("Nothing works match", "It Works", false);
    	map.store("Something ", "works", false);
    	
    	String resultOptimized = engine.evaluate("${Nothing ${Something ${inner something}} match}", map, "optimization");
    	
    	assertEquals(resultOptimized,"It Works");
    	
    }
    
    // Test7: Test To See If Optimization Chooses KeepUnmatched if KeepUnMatched replaces more templates by 1
    public void TDDTestToSeeIfOptimicationChoosesKeepUmatchedWhenOneExtraMatchForKeepUnMatched(){
    	
    	map.store("Nothing ${Something} match", "It Works", false);
    
    	String resultOptimized = engine.evaluate("${Nothing ${Something} match}", map, "optimization");
    	
    	assertEquals(resultOptimized,"It Works");
    	
    }
   
    // Test8: Test To See If Optimization Chooses KeepUnMatched if KeepUnMatched replaces more templates by 1+
    @Test
    public void TDDTestToSeeIfOptimicationChoosesKeepUmatchedWhenManyExtraMatchForKeepUnMatched(){
    	
    	map.store("Something ${inner something}", "works", false);
    	map.store("Nothing works match", "It Works", false);
    	map.store("Something ", "NOT works", false);
    	
    	String resultOptimized = engine.evaluate("${Nothing ${Something ${inner something}} match}", map, "optimization");
    	
    	assertEquals(resultOptimized,"It Works");
    	
    }
    
}
