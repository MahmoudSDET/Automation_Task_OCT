

package com.web.core.utils;

import com.github.javafaker.Faker;

import java.security.SecureRandom;

public class FakerUtils {

	public static String generateName(){
		Faker faker = new Faker();
		return faker.regexify("[A-Za-z]{10}");
	}

	public static int generateNumber(){
		Faker faker = new Faker();
		return faker.number().numberBetween(11111,99999);
	}
	public static String generateDescription(){
		Faker faker = new Faker();
		return "Description " + faker.regexify("[ A-Za-z0-9_@./#&+-]{50}");
	}


	private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
	private static final SecureRandom random = new SecureRandom();

	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());
			char randomChar = CHARACTERS.charAt(randomIndex);
			sb.append(randomChar);
		}
		return sb.toString();
	}
}
