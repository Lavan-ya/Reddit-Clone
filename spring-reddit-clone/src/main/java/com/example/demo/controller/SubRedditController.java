package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.SubredditDto;
import com.example.demo.service.SubredditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subreddit/")
@AllArgsConstructor
public class SubRedditController {
	private final SubredditService subredditService;

	@PostMapping("/")
	public ResponseEntity<SubredditDto> createSubreddit(@RequestBody SubredditDto subredditDto) {
		SubredditDto save = subredditService.save(subredditDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(save);
	}

	@GetMapping("/getName")
	public ResponseEntity<List<SubredditDto>> getAllSubreddits() {
		List<SubredditDto> displayValues = subredditService.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(displayValues);
	}
}
