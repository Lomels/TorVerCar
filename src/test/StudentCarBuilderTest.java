package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.InvalidParameterException;

import org.junit.Test;
import logic.controller.StudentCarBuilder;
import logic.controller.exception.InvalidInputException;
import logic.model.*;
import logic.controller.StudentBuilder;

public class StudentCarBuilderTest {

	@Test
	public void newBuilderTest() throws InvalidInputException {
		String password = "aaaaa1@";
		String name = "Mario";
		String surname = "Rossi";
		String email = "mariorossi@blabla.com";
		String phone = "3336669990";
		Student s = StudentBuilder.newBuilder("1").password(password).fullname(name, surname).email(email).phone(phone)
				.build();

		String model = "Panda";
		String colour = "Red";
		CarInfo car = new CarInfo("AA123AA", 3, model, colour);
		StudentCar scbuilt = StudentCarBuilder.newCarBuilder(s).rating(0).carInfo(car).build();

		Student scostructor = new Student("1", password, email, name, surname, phone);
		StudentCar sccostructor = new StudentCar(scostructor, 0, car);
		assertEquals(scbuilt.toString(), sccostructor.toString());
	}

	@Test
	public void newBuilderTeststudentNull() {
		assertThrows(InvalidParameterException.class, () -> StudentCarBuilder.newCarBuilder(null));
	}
}
