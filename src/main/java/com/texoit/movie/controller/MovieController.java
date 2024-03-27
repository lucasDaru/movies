package com.texoit.movie.controller;

import com.texoit.movie.service.MovieService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    @Lazy
    private MovieService movieService;

    @ApiOperation(value = "Import CSV file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(
            value = {
                @ApiResponse(code = 200, message = "File uploaded successfully"),
                @ApiResponse(code = 400, message = "Bad request")
            }
    )
    @RequestMapping(
            path = "/import/csv",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PostMapping("/import/csv")
    public ResponseEntity<String> importCsv(
            @ApiParam(name = "file", required = true)
            @RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("The CSV file is empty.");
        }

        try {
            movieService.processCSVFile(file);
            return ResponseEntity.ok("The CSV file processed was succeed.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error when process the CSV file: " + e.getMessage());
        }
    }
}
