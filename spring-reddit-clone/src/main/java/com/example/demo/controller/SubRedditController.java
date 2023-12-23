package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subreddit/")
@AllArgsConstructor
public class SubRedditController {
	@GetMapping("/get")
	public String getValue() {
		return "This is working 1";
	}

	@GetMapping("/getName")
	public String getName() {
		return "This is working lavanya-2";
	}
}
