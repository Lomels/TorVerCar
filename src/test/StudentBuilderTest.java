package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.security.InvalidParameterException;

import org.junit.Test;
import logic.controller.StudentBuilder;
import logic.entity.*;

public class StudentBuilderTest {
	
	@Test
	public void newBuilderTest() {
		StudentBuilder sb = StudentBuilder.newBuilder("000");
		assertNotNull(sb);
	}
	
	@Test
	public void newBuilderTestwithoutId() {
		assertThrows(InvalidParameterException.class, () -> {
			StudentBuilder.newBuilder("");
		});
	}
	
	@Test
	public void buildTest() {
		Student s_built = StudentBuilder.newBuilder("111").fullname("Mario", "Rossi").password("aaa").weeklyPreferencies(null).profile(null).lifts(null).build();
		Student s_costructor = new Student("111", "aaa", "Mario", "Rossi", null, null, null);
		assertEquals(s_built.toString(), s_costructor.toString());
	}

}
