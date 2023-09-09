package com.mlorenzo.brewery.web.controllers;

import com.mlorenzo.brewery.services.BeerService;
import com.mlorenzo.brewery.web.model.BeerDto;
import com.mlorenzo.brewery.web.model.BeerPagedList;
import com.mlorenzo.brewery.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping(produces = { "application/json" })
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle){
        if (pageNumber == null || pageNumber < 0)
            pageNumber = DEFAULT_PAGE_NUMBER;
        if (pageSize == null || pageSize < 1)
            pageSize = DEFAULT_PAGE_SIZE;
        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize));
        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }

    @GetMapping(path = {"/{beerId}"},produces = { "application/json" })
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity<>(beerService.findBeerById(beerId), HttpStatus.OK);
    }
}
