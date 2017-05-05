package org.progresssoft.forex.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * Tests for Controller {@link ForexController}
 * 
 * @author hafeeztsd
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ForexControllerTest {
	// http://www.mkyong.com/spring-boot/spring-boot-file-upload-example/
	@Autowired
	private ForexController controller;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void shouldUpload() throws Exception {
		String name = "small_data.csv";
		File file = new File(ClassLoader.getSystemResource(name).getPath());
		MockMultipartFile mockFile = new MockMultipartFile("file", FileUtils.readFileToByteArray(file));
		mockMvc.perform(MockMvcRequestBuilders.fileUpload("/upload").file(mockFile))
				.andExpect(status().isOk());
	}

}
