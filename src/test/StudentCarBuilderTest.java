package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.InvalidParameterException;

import org.junit.Test;
import logic.controller.StudentCarBuilder;
import logic.controller.StudentBuilder;

import logic.entity.*;

public class StudentCarBuilderTest {

	@Test
	public void newBuilderTest() {
		Student s = StudentBuilder.newBuilder("1").build();
		StudentCarBuilder scb = StudentCarBuilder.newCarBuilder(s).rating(0);
		StudentCar scbuilt = scb.build();
		Student scostructor = new Student("1", null, null, null, null, null, null);
		StudentCar sccostructor = new StudentCar(scostructor, 0, null, null);
		assertEquals(scbuilt.toString(), sccostructor.toString());
	}
	
	@Test
	public void newBuilderTestallMethod() {
		Student s = StudentBuilder.newBuilder("1").build();
		StudentCarBuilder scb = StudentCarBuilder.newCarBuilder(s).rating(0).carInfo(null).reports(null);
		StudentCar scbuilt = scb.build();
		Student scostructor = new Student("1", null, null, null, null, null, null);
		StudentCar sccostructor = new StudentCar(scostructor, 0, null, null);
		assertEquals(scbuilt.toString(), sccostructor.toString());
	}
	
	@Test 
	public void newBuilderTeststudentNull() {
		assertThrows(InvalidParameterException.class, () ->
			StudentCarBuilder.newCarBuilder(null)
		);
	}
}
