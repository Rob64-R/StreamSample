package com.demo.io;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Service;

import com.demo.common.Methods;

@Service
public class CoordinatesReader implements ItemReader<String> {

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		String coordinates = Methods.generateCoordinates();
		
		System.out.println("### CoordinatesReader ###");
		System.out.println(coordinates);
		
		return coordinates;
	}
		

}
