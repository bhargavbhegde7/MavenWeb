package com.bhargav.test;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;

public class Test {
	public static void main(String[] args) throws IOException {
		Webcam webcam = Webcam.getDefault();
		webcam.open();
		ImageIO.write(webcam.getImage(), "PNG", new File("C:/Users/Aditya/Desktop/test1.png"));
	}
}
