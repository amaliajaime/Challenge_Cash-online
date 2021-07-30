package com.cashonline;

import com.cashonline.dto.LoanDTO;
import com.cashonline.dto.PageDTO;
import com.cashonline.dto.UserDTO;
import com.cashonline.service.LoanService;
import com.cashonline.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CashOnlineApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class CashOnlineApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private UserService userService;

	@Autowired
	private LoanService loanService;

	@Before()
	public void setup() throws Exception {

		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	public void testSuccessAddUser() throws Exception {

		UserDTO user = new UserDTO("amaliajaime3@gmail.com", "Amalia", "Jaime");
		String jsonRequest = mapper.writeValueAsString(user);

		mockMvc.perform(post("/users/add").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testAddUserWithInvalidEmail() throws Exception {

		UserDTO user = new UserDTO("ama@gmail", "Amalia", "Jaime");
		String jsonRequest = mapper.writeValueAsString(user);

		mockMvc.perform(post("/users/add")
				.content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

	}

	@Test
	public void testSuccessGetUserId() throws Exception{

		UserDTO user = new UserDTO("amaliajaime3@gmail.com", "Amalia", "Jaime");
		String jsonRequest = mapper.writeValueAsString(user);

		mockMvc.perform(post("/users/add").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String id = String.valueOf(userService.getUserByEmail("amaliajaime3@gmail.com").getId());

		mockMvc.perform(get("/users/" + id).content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testIncorrectGetUserId() throws Exception{

		String idRandom = String.valueOf(157);
		String jsonRequest = mapper.writeValueAsString(idRandom);

		mockMvc.perform(get("/users/" + idRandom).content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andReturn();

	}

	@Test
	public void testDeleteUserById() throws Exception{

		UserDTO user = new UserDTO("amaliajaime3@gmail.com", "Amalia", "Jaime");
		String jsonRequest = mapper.writeValueAsString(user);

		mockMvc.perform(post("/users/add").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		String id = String.valueOf(userService.getUserByEmail("amaliajaime3@gmail.com").getId());

		mockMvc.perform(delete("/users/delete/" + id).content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

	}

	@Test
	public void testIncorrectDeleteUserById() throws Exception{
	String idRandom = String.valueOf(157);
	String jsonRequest = mapper.writeValueAsString(idRandom);

	mockMvc.perform(delete("/users/delete/" + idRandom).content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest()).andReturn();

	}

	@Test
	public void testGetLoans() throws Exception{
		UserDTO user = new UserDTO("amaliajaime3@gmail.com", "Amalia", "Jaime");
		String jsonRequest = mapper.writeValueAsString(user);

		mockMvc.perform(post("/users/add").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		int id = (int)userService.getUserByEmail("amaliajaime3@gmail.com").getId();

		LoanDTO loanDTO = new LoanDTO(1500, id);
		String jsonRequest2 = mapper.writeValueAsString(loanDTO);

		mockMvc.perform(post("/loans/add").content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		mockMvc.perform(get("/loans?page=0&size=1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		PageDTO pageDTO = loanService.getAllBy(0, 1, 0);
		int totalLoans = pageDTO.getItems().size();

		assertEquals(1, totalLoans);
		assertEquals(0, (int)pageDTO.getPage());
		assertEquals(1, (int)pageDTO.getSize());

	}

	@Test
	public void testGetLoansById() throws Exception{
		UserDTO user = new UserDTO("amaliajaime3@gmail.com", "Amalia", "Jaime");
		String jsonRequest = mapper.writeValueAsString(user);

		mockMvc.perform(post("/users/add").content(jsonRequest).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		int id = (int)userService.getUserByEmail("amaliajaime3@gmail.com").getId();

		LoanDTO loanDTO = new LoanDTO(1500, id);
		String jsonRequest2 = mapper.writeValueAsString(loanDTO);

		mockMvc.perform(post("/loans/add").content(jsonRequest2).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		mockMvc.perform(get("/loans?page=0&size=1&user_id=" + id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		PageDTO pageDTO = loanService.getAllBy(0, 1, id);
		int totalLoans = pageDTO.getItems().size();

		assertEquals(1, totalLoans);
		assertEquals(0, (int)pageDTO.getPage());
		assertEquals(1, (int)pageDTO.getSize());
	}

}
