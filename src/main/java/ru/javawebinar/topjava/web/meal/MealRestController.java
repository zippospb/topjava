package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(MealRestController.REST_URL)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @GetMapping
    public List<MealWithExceed> getAll(){
        return super.getAll();
    }

    @GetMapping(value = "/{id}"/*,produces = MediaType.APPLICATION_JSON_VALUE*/)
    public Meal get(@PathVariable int id){
        return super.get(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id){
        super.delete(id);
    }

    @PutMapping(value = "/{id}")
    public void update(@RequestBody Meal meal, @PathVariable int id){
        super.update(meal, id);
    }

    @PostMapping
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal){
        Meal created = super.create(meal);

        URI uriOfNewResources = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(uriOfNewResources).body(created);
    }

    @GetMapping("/getBetween")
    public List<MealWithExceed> getBetween(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime start,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam LocalDateTime end
            ){
        return super.getBetween(start.toLocalDate(), start.toLocalTime(), end.toLocalDate(), end.toLocalTime());
    }
}