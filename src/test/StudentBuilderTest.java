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
	public void newBuilderTest() throws InvalidInputException {
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
	public void buildTest() throws InvalidInputException {
		Student sbuilt = StudentBuilder.newBuilder("111").fullname("Mario", "Rossi").password("aaaaa1@").weeklyPreferencies(null).profile(null).lifts(null).build();
		Student scostructor = new Student("111", "aaaaa1@", "Mario", "Rossi", null, null, null);
		//TODO: Change to sbuilt.equals()
		assertEquals(sbuilt.toString(), scostructor.toString());
	
	}

}
