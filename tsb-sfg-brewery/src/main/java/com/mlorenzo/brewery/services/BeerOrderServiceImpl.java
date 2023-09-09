package com.mlorenzo.brewery.services;

import com.mlorenzo.brewery.domain.Beer;
import com.mlorenzo.brewery.domain.BeerOrder;
import com.mlorenzo.brewery.domain.Customer;
import com.mlorenzo.brewery.repositories.BeerOrderRepository;
import com.mlorenzo.brewery.repositories.BeerRepository;
import com.mlorenzo.brewery.repositories.CustomerRepository;
import com.mlorenzo.brewery.web.mappers.BeerOrderMapper;
import com.mlorenzo.brewery.web.model.BeerOrderDto;
import com.mlorenzo.brewery.web.model.BeerOrderPagedList;
import com.mlorenzo.brewery.domain.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class BeerOrderServiceImpl implements BeerOrderService {
    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            Page<BeerOrder> beerOrderPage =
                    beerOrderRepository.findAllByCustomer(customerOptional.get(), pageable);
            return new BeerOrderPagedList(beerOrderPage
                    .stream()
                    .map(beerOrderMapper::beerOrderToDto)
                    .collect(Collectors.toList()), PageRequest.of(
                    beerOrderPage.getPageable().getPageNumber(),
                    beerOrderPage.getPageable().getPageSize()),
                    beerOrderPage.getTotalElements());
        }
        else
            return null;
    }

    @Override
    public BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if (customerOptional.isPresent()) {
            BeerOrder beerOrder = beerOrderMapper.dtoToBeerOrder(beerOrderDto);
            beerOrder.setId(null); //should not be set by outside client
            beerOrder.setCustomer(customerOptional.get());
            beerOrder.setOrderStatus(OrderStatusEnum.NEW);
            //update beers from db, go boom if not found
            beerOrder.getBeerOrderLines().forEach(beerOrderLine -> {
                Optional<Beer> beerOptional = beerRepository.findById(beerOrderLine.getBeer().getId());
                if(beerOptional.isPresent())
                    beerOrderLine.setBeer(beerOptional.get());
                else {
                    //todo add exception type
                    throw new RuntimeException("Beer ID not found: " + beerOrderLine.getBeer().getId());
                }
            });
            BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
            log.debug("Saved Beer Order: " + beerOrder.getId());
            return beerOrderMapper.beerOrderToDto(savedBeerOrder);
        }
        //todo add exception type
        throw new RuntimeException("Customer Not Found");
    }

    @Override
    public BeerOrderDto getOrderById(UUID customerId, UUID orderId) {
        return beerOrderMapper.beerOrderToDto(getOrder(customerId, orderId));
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        BeerOrder beerOrder = getOrder(customerId, orderId);
        beerOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);
        beerOrderRepository.save(beerOrder);
    }

    private BeerOrder getOrder(UUID customerId, UUID orderId){
        Optional<Customer> customerOptional = customerRepository.findById(customerId);
        if(customerOptional.isPresent()){
            Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);
            if(beerOrderOptional.isPresent()){
                BeerOrder beerOrder = beerOrderOptional.get();
                // fall to exception if customer id's do not match - order not for customer
                if(beerOrder.getCustomer().getId().equals(customerId))
                    return beerOrder;
            }
            throw new RuntimeException("Beer Order Not Found");
        }
        throw new RuntimeException("Customer Not Found");
    }
}
