package com.demo.io;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

@Service
public class CoordinatesWriter implements ItemWriter<String>  {

	@Override
	public void write(List<? extends String> items) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
