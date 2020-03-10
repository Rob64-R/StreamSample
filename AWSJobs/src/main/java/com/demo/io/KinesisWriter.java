package com.demo.io;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.service.KinesisService;

@Service
public class KinesisWriter implements ItemWriter<String> {

	@Autowired
	KinesisService kinesisService;
	
	@Override
	public void write(List<? extends String> records) throws Exception {
		System.out.println("### KinesisWriter ###");
		System.out.println(records);
		kinesisService.produce(records);
	}
}
