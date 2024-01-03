package com.example.demo.service;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.SubredditDto;
import com.example.demo.model.Subreddit;
import com.example.demo.repository.SubredditRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {

	private final SubredditRepository subredditRepository;

	@Transactional
	public SubredditDto save(SubredditDto subredditDto) {
		Subreddit subreddit = mapSubreddit(subredditDto);
		Subreddit save = subredditRepository.save(subreddit);
		subredditDto.setId(save.getId());
		return subredditDto;
	}

	private Subreddit mapSubreddit(SubredditDto subredditDto) {
		return Subreddit.builder().name(subredditDto.getName()).description(subredditDto.getDescription()).build();
	}

	@Transactional
	public List<SubredditDto> getAll() {
		return subredditRepository.findAll().stream().map(this::mapToDto).collect(toList());
	}

	public SubredditDto mapToDto(Subreddit subreddit) {
		return SubredditDto.builder().name(subreddit.getName()).description(subreddit.getDescription())
				.numberOfPost(subreddit.getNumberOfPost()).id(subreddit.getId()).build();
	}
}
