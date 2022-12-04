package com.cafe.coffee;

import com.cafe.exception.BusinessLogicException;
import com.cafe.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public Coffee createCoffee(Coffee coffee) {
        verifyExistCoffee(coffee.getKorName());
        return coffeeRepository.save(coffee);
    }

    public Coffee updateCoffee(Coffee coffee) {
        Coffee findCoffee = findVerifiedCoffee(coffee.getCoffeeId());

        Optional.ofNullable(coffee.getKorName()).ifPresent(data -> findCoffee.setKorName(data));
        Optional.ofNullable(coffee.getEngName()).ifPresent(data -> findCoffee.setEngName(data));
        Optional.ofNullable(coffee.getPrice()).ifPresent(data -> findCoffee.setPrice(data));

        return coffeeRepository.save(findCoffee);
    }

    public Coffee findCoffee(long coffeeId) {
        return findVerifiedCoffee(coffeeId);
    }

    public Page<Coffee> findCoffees(int page, int size) {
        return coffeeRepository.findAll(PageRequest.of(page, size, Sort.by("coffeeId").descending()));
    }

    public void deleteCoffee(long coffeeId) {
        coffeeRepository.delete(findVerifiedCoffee(coffeeId));
    }

    private void verifyExistCoffee(String korName) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findByKorName(korName);
        if (optionalCoffee.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.COFFEE_EXISTS);
        }
    }

    private Coffee findVerifiedCoffee(long coffeeId) {
        Optional<Coffee> optionalCoffee = coffeeRepository.findById(coffeeId);
        Coffee findCoffee = optionalCoffee.orElseThrow(() -> new BusinessLogicException(ExceptionCode.COFFEE_NOT_FOUND));
        return findCoffee;
    }
}
