package com.mlorenzo.sfgrestdocsexample.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mlorenzo.sfgrestdocsexample.domain.Beer;

public interface BeerRepository extends PagingAndSortingRepository<Beer,UUID>{

}
