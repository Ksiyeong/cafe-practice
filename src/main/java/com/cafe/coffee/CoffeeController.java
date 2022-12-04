package com.cafe.coffee;

import com.cafe.dto.MultiResponseDto;
import com.cafe.dto.SingleResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coffees")
@Validated // @Min 애너테이션 사용을 위해 필요
public class CoffeeController {
    private final CoffeeService coffeeService;
    private final CoffeeMapper mapper;

    public CoffeeController(CoffeeService coffeeService, CoffeeMapper mapper) {
        this.coffeeService = coffeeService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity postCoffee(@Valid @RequestBody CoffeeDto.Post coffeePostDto) {

        Coffee coffee = coffeeService.createCoffee(mapper.coffeePostDtoToCoffee(coffeePostDto));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.CoffeeToCoffeeResponseDto(coffee)), HttpStatus.CREATED);
    }

    @PatchMapping("/{coffee-id}")
    public ResponseEntity patchCoffee(@PathVariable("coffee-id") @Positive long coffeeId,
                                      @Valid @RequestBody CoffeeDto.Patch coffeePatchDto) {
        coffeePatchDto.setCoffeeId(coffeeId);

        Coffee coffee = coffeeService.updateCoffee(mapper.coffeePatchDtoToCoffee(coffeePatchDto));

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.CoffeeToCoffeeResponseDto(coffee)), HttpStatus.OK);
    }

    @GetMapping("/{coffee-id}")
    public ResponseEntity getCoffee(@Positive @PathVariable("coffee-id") long coffeeId) {
        Coffee coffee = coffeeService.findCoffee(coffeeId);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.CoffeeToCoffeeResponseDto(coffee)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCoffees(@Positive @RequestParam int page,
                                     @Positive @RequestParam int size) {
        Page<Coffee> pageCoffees = coffeeService.findCoffees(page - 1, size);

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.CoffeesToCoffeeResponsesDto(pageCoffees.getContent()), pageCoffees),
                HttpStatus.OK);
    }

    @DeleteMapping("/{coffee-id}")
    public ResponseEntity deleteCoffee(@Positive @PathVariable("coffee-id") long coffeeId) {
        coffeeService.deleteCoffee(coffeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
