package games.stendhal.server.entity.npc.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;

/**
 * Test the NPC conversation WordList class.
 * 
 * @author Martin Fuchs
 */
public class WordListTest {

	@Test
	public final void testNouns() {
		WordList wl = WordList.getInstance();

		WordEntry w = wl.find("house");
		assertNotNull(w);
		assertTrue(w.getType().isObject());
		assertEquals(ExpressionType.OBJECT, w.getTypeString());
		assertEquals("houses", w.getPlurSing());

		w = wl.find("man");
		assertNotNull(w);
		assertTrue(w.getType().isSubject());
		assertEquals(ExpressionType.SUBJECT, w.getTypeString());
		assertEquals("men", w.getPlurSing());

		w = wl.find("carrot");
		assertNotNull(w);
		assertTrue(w.getType().isObject());
		assertEquals(ExpressionType.OBJECT + ExpressionType.SUFFIX_FOOD, w.getTypeString());
		assertEquals("carrots", w.getPlurSing());

		w = wl.find("carrots");
		assertNotNull(w);
		assertTrue(w.getType().isObject());
		assertEquals(ExpressionType.OBJECT + ExpressionType.SUFFIX_FOOD + ExpressionType.SUFFIX_PLURAL, w.getTypeString());
		assertEquals("carrot", w.getPlurSing());

		w = wl.find("water");
		assertNotNull(w);
		assertTrue(w.getType().isObject());
		assertEquals(ExpressionType.OBJECT + ExpressionType.SUFFIX_FLUID, w.getTypeString());
		assertEquals("waters", w.getPlurSing());

		w = wl.find("she");
		assertNotNull(w);
		assertTrue(w.getType().isSubject());
		assertEquals(ExpressionType.SUBJECT + ExpressionType.SUFFIX_PRONOUN, w.getTypeString());
		assertEquals("they", w.getPlurSing());
	}

	@Test
	public final void testVerbs() {
		WordList wl = WordList.getInstance();

		WordEntry w = wl.find("say");
		assertNotNull(w);
		assertTrue(w.getType().isVerb());
		assertEquals(ExpressionType.VERB, w.getTypeString());

		w = wl.find("open");
		assertNotNull(w);
		assertTrue(w.getType().isVerb());
		assertEquals(ExpressionType.VERB, w.getTypeString());

		w = wl.find("are");
		assertNotNull(w);
		assertTrue(w.getType().isVerb());
		assertEquals(ExpressionType.VERB + ExpressionType.SUFFIX_PLURAL, w.getTypeString());
	}

	@Test
	public final void testAdjectives() {
		WordList wl = WordList.getInstance();

		WordEntry w = wl.find("white");
		assertNotNull(w);
		assertTrue(w.getType().isAdjective());
		assertEquals(ExpressionType.ADJECTIVE + ExpressionType.SUFFIX_COLOR, w.getTypeString());

		w = wl.find("silvery");
		assertNotNull(w);
		assertTrue(w.getType().isAdjective());
		assertEquals(ExpressionType.ADJECTIVE + ExpressionType.SUFFIX_COLOR, w.getTypeString());

		w = wl.find("nomadic");
		assertNotNull(w);
		assertTrue(w.getType().isAdjective());
		assertEquals(ExpressionType.ADJECTIVE, w.getTypeString());
	}

	@Test
	public final void testPrepositions() {
		WordList wl = WordList.getInstance();

		WordEntry w = wl.find("with");
		assertNotNull(w);
		assertTrue(w.getType().isPreposition());
		assertEquals(ExpressionType.PREPOSITION, w.getTypeString());

		w = wl.find("on");
		assertNotNull(w);
		assertTrue(w.getType().isPreposition());
		assertEquals(ExpressionType.PREPOSITION, w.getTypeString());
	}

	@Test
	public final void testPlural() {
		WordList wl = WordList.getInstance();

		assertEquals("houses", wl.plural("house"));
		assertEquals("cookies", wl.plural("cookie"));
		assertEquals("cookies", wl.plural("cooky"));
	}

	@Test
	public final void testSingular() {
		WordList wl = WordList.getInstance();

		assertEquals("house", wl.singular("houses"));
		assertEquals("cookie", wl.singular("cookies"));
	}
	
	@Test
	public void testTrimWords() {
		String word = "BLABLA";
		assertThat(WordList.trimWord(word), is("blabla"));
		assertThat(word, is("BLABLA"));
		word = "";
		assertThat(WordList.trimWord(word), is(""));
		
		word = "\'";
		assertThat(WordList.trimWord(word), is(""));
	
		
		word = "\'\'";
		assertThat(WordList.trimWord(word), is(""));
		
		
		word = "b\'\'";
		assertThat(WordList.trimWord(word), is("b"));
	
		
		word = "\'\'B\'L\'A\'B\'L\'A\'\'";
		assertThat(WordList.trimWord(word), is("b\'l\'a\'b\'l\'a"));
	}
	

}
