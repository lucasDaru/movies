package com.texoit.movie.controller;

import com.texoit.movie.exception.MovieException;
import com.texoit.movie.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    @Test
    public void testImportCsv_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "movies.csv", MediaType.MULTIPART_FORM_DATA_VALUE, "year;title;studios;producers;winner\n2000;Movie Title;Studio1,Studio2;Producer1,Producer2;Yes".getBytes());

        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();

        doNothing().when(movieService).processCSVFile(file);

        mockMvc.perform(multipart("/api/movie/import/csv")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("The CSV file processed was succeed."));

        verify(movieService, times(1)).processCSVFile(file);
    }

    @Test
    public void testImportCsv_EmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "movies.csv", MediaType.MULTIPART_FORM_DATA_VALUE, new byte[0]);

        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();

        mockMvc.perform(multipart("/api/movie/import/csv")
                        .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("The CSV file is empty."));

        verify(movieService, never()).processCSVFile(file);
    }

    @Test
    public void testImportCsv_Failure() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file", "movies.csv", MediaType.MULTIPART_FORM_DATA_VALUE, "year;title;studios;producers;winner\n2000;Movie Title;Studio1,Studio2;Producer1,Producer2;Yes".getBytes());

        mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();

        doThrow(new MovieException(MovieException.Message.ERROR_READ_FILE)).when(movieService).processCSVFile(file);

        mockMvc.perform(multipart("/api/movie/import/csv")
                        .file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error when process the CSV file: Could not read the file."));

        verify(movieService, times(1)).processCSVFile(file);
    }
}

