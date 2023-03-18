package com.sanedge.myecommerce.auth.controllers;

import static com.sanedge.myecommerce.utils.Constants.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.myecommerce.dto.BasketDto;
import com.sanedge.myecommerce.dto.CommandDto;
import com.sanedge.myecommerce.entity.Command;
import com.sanedge.myecommerce.exception.ItemNotFoundException;
import com.sanedge.myecommerce.mapper.CommandMapper;
import com.sanedge.myecommerce.repository.CommandRepository;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;

@RestController
@Tag(name = "Commands")
@RequestMapping(COMMANDS_ENDPOINT)
public class CommandController {
    private final CommandRepository commandRepository;
    private final CommandMapper commandMapper;

    CommandController(
            CommandRepository commandRepository,
            CommandMapper commandMapper) {
        this.commandMapper = commandMapper;
        this.commandRepository = commandRepository;
    }

    @PutMapping("test")
    @Transactional
    public void Test() {
    }

    @GetMapping(params = { "_start", "_end", "_sort", "_order" })
    public ResponseEntity<List<CommandDto>> getAll(
            @RequestParam(name = "_end") Integer end,
            @RequestParam(name = "_sort") String sort,
            @RequestParam(name = "_order") String order,
            @RequestParam(name = "_start") Integer start,
            @RequestParam(name = "returned", required = false) Boolean returned,
            @RequestParam(name = "total_gte", required = false) String total_gte,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "date_lte", required = false) String date_lte,
            @RequestParam(name = "date_gte", required = false) String date_gte,
            @RequestParam(name = "customer_id", required = false) Long customer_id,
            @RequestParam(name = "q", required = false) String queryByText) {
        Integer take = end - start;

        List<CommandDto> commandsQueRes = commandMapper.getPaginatedcommands(
                start,
                take,
                sort,
                order,
                status,
                date_gte,
                date_lte,
                customer_id,
                total_gte,
                returned,
                queryByText);

        String commandCount = commandMapper.getCommandCount(
                status,
                date_gte,
                date_lte,
                customer_id,
                total_gte,
                returned,
                queryByText);

        return ResponseEntity
                .ok()
                .header("X-Total-Count", commandCount)
                .body(buildCommandsDtoList(commandsQueRes));
    }

    @GetMapping(params = "id")
    public ResponseEntity<List<Command>> getManyReference(
            @RequestParam("id") List<Long> ids) {
        List<Command> commands = commandMapper.getManyCommands(ids);

        return ResponseEntity.ok().body(commands);
    }

    @GetMapping("{id}")
    public ResponseEntity<Command> getById(@PathVariable("id") Long id) {
        Command command = commandRepository
                .findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Order", id));

        return ResponseEntity.ok().body(command);
    }

    @PutMapping("{id}")
    public ResponseEntity<Command> update(
            @PathVariable("id") Long id,
            @RequestBody Command command) {
        Command updatedCommand = commandMapper.updateCommand(
                command.getId(),
                command.getStatus(),
                command.getReturned());

        return ResponseEntity.ok().body(updatedCommand);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long id) {
        commandRepository.deleteById(id);
        return ResponseEntity.ok().body(true);
    }

    private List<CommandDto> buildCommandsDtoList(
            List<CommandDto> commandsQueRes) {
        return commandsQueRes
                .stream()
                .map(
                        customer -> {
                            String productId_quantity = customer.getProductId_quantity();

                            customer.setBasket(processBasketeDtoList(productId_quantity));
                            customer.setProductId_quantity(null);
                            return customer;
                        })
                .collect(Collectors.toList());
    }

    private ArrayList<BasketDto> processBasketeDtoList(
            String productId_quantity) {
        ArrayList<String> flatList = new ArrayList<String>(
                Arrays.asList(productId_quantity.split(",")));

        ArrayList<ArrayList<String>> nestedList = new ArrayList<ArrayList<String>>();

        for (int i = 0; i < flatList.size(); i += 2) {
            nestedList.add(new ArrayList<String>());
            nestedList.get(i / 2).add(flatList.get(i));
            nestedList.get(i / 2).add(flatList.get(i + 1));
        }

        ArrayList<BasketDto> basketDtoList = new ArrayList<>();

        nestedList.forEach(
                innerList -> {
                    Long productIdLong = Long.parseLong(innerList.get(0));
                    Integer quantityInt = Integer.parseInt(innerList.get(1));

                    basketDtoList.add(BasketDto.of(quantityInt, productIdLong));
                });

        return basketDtoList;
    }
}
