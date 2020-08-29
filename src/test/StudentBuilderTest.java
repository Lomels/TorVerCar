package test;

import static org.junit.Assert.assertTrue;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

import logic.controller.StudentBuilder;
import logic.controller.exception.InvalidInputException;
import logic.model.Student;
import logic.utilities.SameValuesChecker;
import test.utilities.TestUtilities;

public class StudentBuilderTest extends TestUtilities {

	@Test
	public void correct()
			throws InvalidInputException, IllegalAccessException, InvocationTargetException, IntrospectionException {
		Student fromBuilder = StudentBuilder.newBuilder(USER_ID + "0").fullname(NAME, SURNAME).email(EMAIL)
				.password(PASSWORD).phone(PHONE).build();
		Student fromCostructor = new Student(USER_ID + "0", PASSWORD, EMAIL, NAME, SURNAME, PHONE);

		assertTrue(SameValuesChecker.haveSamePropertyValues(Student.class, fromCostructor, fromBuilder));
	}

}
