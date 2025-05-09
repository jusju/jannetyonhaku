package com.seleniumtraining.seleniumapp;

import com.seleniumtraining.seleniumapp.domain.Yritys;
import com.seleniumtraining.seleniumapp.domain.YritysRepository;
import com.seleniumtraining.seleniumapp.testservices.HttpMethods;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SeleniumappApplicationTests {

	@Autowired
	HttpMethods httpMethods;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private YritysRepository yritysRepository;

	// nämä päivitetään postmanin avulla
	private String adminJWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik5nOVBnejBBcGJwc05mczdsRjBxeiJ9.eyJpc3MiOiJodHRwczovL2Rldi1qbW1iejFvczF3bG1jejNpLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2N2Y0M2I5YTg3ZTViN2JjNTZiOTNmOGMiLCJhdWQiOiJodHRwczovL2V4YW1wbGUuY29tLyIsImlhdCI6MTc0NDM1NjIwMSwiZXhwIjoxNzQ0NDQyNjAxLCJzY29wZSI6ImFkbWluOmFsbCIsImd0eSI6InBhc3N3b3JkIiwiYXpwIjoiTUVjVzYwSTJkWVk5NHBkZXg1QlBJdXp2SXREbUpMM2QifQ.QrwL8qqvHJpjmw5wvw_2bKdAhyvQqqmJJD2HAbcURMfXoqaKg42zn1s65Gdj9bNMwvL9ti7YA6T4UhIWUBXR9sBQK74aYT6nVDGPibJJD-pyht_NOmSC9wV-NUzh9zmfNpNvekB-i8garRlXUbWX_SN7g18KVTqXtoBupRgqvEZzgobplyuX1kfUWdVTEi2AYKwLYSluCQNytgTZZwLgLY1uCJ4Nffr5fh7b2ADvVKqRSLd16eiS4-_tNSJNp5lPHd1fGS-v3bKB-2sXb5SvMGQG_RgvLoxnaBeyo4TVdr89Ey7fbpBrgfMvz4VbgL7fDG179r0HqhfwXCmUoJv6XQ";
	private String testjwt = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik5nOVBnejBBcGJwc05mczdsRjBxeiJ9.eyJpc3MiOiJodHRwczovL2Rldi1qbW1iejFvczF3bG1jejNpLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2N2QwMzdlNDExYTU0ZDQ2OGJhZTYxMjMiLCJhdWQiOiJodHRwczovL2V4YW1wbGUuY29tLyIsImlhdCI6MTc0NDM1NjMxMSwiZXhwIjoxNzQ0NDQyNzExLCJndHkiOiJwYXNzd29yZCIsImF6cCI6Ik1FY1c2MEkyZFlZOTRwZGV4NUJQSXV6dkl0RG1KTDNkIn0.uDrcYUQM1Gf6t6d5U46m3zxRQG3HNeEaAwJyWuiR7J8JKIkgDySXZLVT4HPZkSRXRpAvcQY6FmRENNHtTDMPq1QJEjoN4R1cpmx6Gf6tNo5_lUCisKv2zwMlhZwHVm9z1UrtaslgtpSnAHb0-0TOhmXqsq-B4RluphA7oPIymkQNPTWDCU8_wRZUInqqwJpMXvvGe7Pln2wwxCT2JA6dFTEQl-6M4RyS36AXvV22wnnmq0UTZTeP3kk35pIg4qGdMxGZ1XXbRfuni4IhKJ24LhDsZ8NmoFANQrWC6aQ996AIpkpZRw3ahJE3xPHGgg-2wbLFU6AXXb0SD4gi8qhhnA";
	private String testReadJWT = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik5nOVBnejBBcGJwc05mczdsRjBxeiJ9.eyJpc3MiOiJodHRwczovL2Rldi1qbW1iejFvczF3bG1jejNpLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2N2Y0MDRkOTFjNTg5NTE0MzhmZTA2MzMiLCJhdWQiOiJodHRwczovL2V4YW1wbGUuY29tLyIsImlhdCI6MTc0NDM1NjM1OCwiZXhwIjoxNzQ0NDQyNzU4LCJzY29wZSI6InJlYWQ6eXJpdHlrc2V0IiwiZ3R5IjoicGFzc3dvcmQiLCJhenAiOiJNRWNXNjBJMmRZWTk0cGRleDVCUEl1enZJdERtSkwzZCJ9.Knx_ljVNtntTI3QuUXXnoRJx-1JlBPP58hjSRb35_x2Ldkj9EQxLQp6C9axddF6e8t7vtujvp_F6kjp_yRk1gDiH2VbiUp4NzcjFtiJE-nWIpmA60Vs4zyFFE9mtb9FVeGmO6ju1LodnXa9IEDDhtNq70tT_LMJ96NMxT0v5WnCYLIXVVaecrYAg5VnHDjY7hH127oxYJfhe6NJIJ76MHjBXrJJ5Etz4puKG_1SR8AKLgEj6jBy7fPexe09JuQO1bO_hB8RTt1aTKSheyn-qb8W9QE68M9NtNK0OQu-t-mQHAkBbUZmyNpiTUu_s9P8ZC2p91ZaqR2dzhI0D8bkFzw";

	@Value("${okta.oauth2.issuer}")
	private String issuer;

	@Value("${okta.oauth2.audience}")
	private String audience;

	@Value("${okta.oauth2.client-id}")
	private String clientId;

	@Value("${okta.oauth2.client-secret}")
	private String clientSecret;

	@BeforeAll
	void setUp() throws Exception {
		// Lisätään yritykset tietokantaan
		Yritys adminYritys = new Yritys();
		adminYritys.setYritysNimi("Yritys 1");
		adminYritys.setUsername("auth0|67f43b9a87e5b7bc56b93f8c"); // admin
		yritysRepository.save(adminYritys);

		Yritys adminYritys2 = new Yritys();
		adminYritys2.setYritysNimi("Yritys 2");
		adminYritys2.setUsername("auth0|67f43b9a87e5b7bc56b93f8c"); // admin
		yritysRepository.save(adminYritys2);

		Yritys readYritys = new Yritys();
		readYritys.setYritysNimi("Yritys 3");
		readYritys.setUsername("auth0|67f404d91c58951438fe0633"); // test_read
		yritysRepository.save(readYritys);

		Yritys publicYritys = new Yritys();
		publicYritys.setYritysNimi("Yritys 4");
		publicYritys.setUsername("publicTest");
		yritysRepository.save(publicYritys);

		System.out.println(yritysRepository.findAll());
	}

	@Test
	void testPrintUserDetailsOnJwt() throws Exception {
		mockMvc.perform(get("/api/userDetails")
				.header("Authorization", "Bearer " + testReadJWT))
				.andExpect(status().isOk());
	}

	@Test
	void testPrintResponseBody() throws Exception {
		String responseBody = mockMvc.perform(get("/api/userDetails")
				.header("Authorization", "Bearer " + testReadJWT))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		System.out.println("Response Body: " + responseBody);
	}

	@Test
	void testPublicEndpoint() throws Exception {
		String responsebody = mockMvc.perform(get("/api/public")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		System.out.println(responsebody);
	}

	@Test
	void testProtectedEndpoint() throws Exception {

		mockMvc.perform(get("/api/secret")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void testGetYrityksetWithoutReadAuthorization() throws Exception {
		mockMvc.perform(get("/api/yritykset")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + testjwt))
				.andExpect(status().isForbidden());
	}

	// on riippuvainen testeihin jotka muuttavat tietokannan tilaa
	@Test
	void testGetYrityksetAndGetOnlyThatHasRightUsername() throws Exception {
		mockMvc.perform(get("/api/yritykset")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + adminJWT))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].yritysNimi").value("Yritys 5"))
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void testEditYritysWithRightUsername() throws Exception {
		mockMvc.perform(patch("/api/editYritys/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + adminJWT)
				.content("{\"yritysNimi\": \"Yritys 5\"}"))
				.andExpect(status().isOk())
				.andExpect(content().string("Yritys: Yritys 5 updated successfully!"));
	}

	@Test
	void testDeleteYritysWithRightUsername() throws Exception {
		mockMvc.perform(delete("/api/deleteYritys/2")
				.header("Authorization", "Bearer " + adminJWT))
				.andExpect(status().isOk());
	}

	@Test
	void testEditYritysWithWrongUserName() throws Exception {
		mockMvc.perform(patch("/api/editYritys/1")
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + testjwt)
				.content("{\"yritysNimi\": \"Yritys 4\"}"))
				.andExpect(status().isForbidden());
	}
}
