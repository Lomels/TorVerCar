package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import logic.controller.StudentBuilder;
import logic.controller.exception.InvalidInputException;
import logic.entity.*;

public class StudentBuilderTest {
	
	@Test
	public void newBuilderTest() {
		StudentBuilder sb = StudentBuilder.newBuilder("000");
		assertNotNull(sb);
	}
	
	@Test
	public void newBuilderTestwithoutId() {
		assertThrows(InvalidInputException.class, () -> 
			StudentBuilder.newBuilder("")
		);
	}
	
	@Test
	public void buildTest() {
		Student sbuilt = StudentBuilder.newBuilder("111").fullname("Mario", "Rossi").password("aaa").weeklyPreferencies(null).profile(null).lifts(null).build();
		Student scostructor = new Student("111", "aaa", "Mario", "Rossi", null, null, null);
		assertEquals(sbuilt.toString(), scostructor.toString());
	}

}
