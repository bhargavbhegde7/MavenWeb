package com.bhargav.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StreamController {
	
	/*
	 * This will actually load the whole video file in a byte array in memory,
	 * so it's not recommended.
	 */
	//(downloads the file)
	@RequestMapping(value = "/getvideo1", method = RequestMethod.GET)
	@ResponseBody public ResponseEntity<byte[]> getVideo1(HttpServletResponse response) {
	    ResponseEntity<byte[]> result = null;
	    try {
	        Path path = Paths.get("C:/Users/Aditya/Desktop/true_d.mp4");
	        byte[] image = Files.readAllBytes(path);

	        response.setStatus(HttpStatus.OK.value());
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.setContentLength(image.length);
	        result = new ResponseEntity<byte[]>(image, headers, HttpStatus.OK);
	    } catch (java.nio.file.NoSuchFileException e) {
	        response.setStatus(HttpStatus.NOT_FOUND.value());
	    } catch (Exception e) {
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    }
	    return result;
	}
	
	//stream copy
	//(downloads the file)
	@RequestMapping(value = "/getvideo2", method = RequestMethod.GET)
	@ResponseBody public void getVideo2(HttpServletResponse response) {
	    try {
	        File file = new File("C:/Users/Aditya/Desktop/true_d.mp4");
	        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
	        response.setHeader("Content-Disposition", "attachment; filename="+file.getName().replace(" ", "_"));
	        InputStream iStream = new FileInputStream(file);
	        IOUtils.copy(iStream, response.getOutputStream());
	        response.flushBuffer();
	    } catch (java.nio.file.NoSuchFileException e) {
	        response.setStatus(HttpStatus.NOT_FOUND.value());
	    } catch (Exception e) {
	        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
	    }
	}
	
	//FileSystemResource
	//actually streams
	@RequestMapping(value = "/getvideo3", method = RequestMethod.GET)
	@ResponseBody public FileSystemResource getVideo3(HttpServletResponse response) {
	    return new FileSystemResource("C:/Users/Aditya/Desktop/true_d.mp4");
	}
}
