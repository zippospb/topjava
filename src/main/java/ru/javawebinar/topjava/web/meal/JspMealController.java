package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @Autowired
    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("")
    public String all(Model model){
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping("/filter")
    public String filter(
            Model model,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            @RequestParam(name = "startTime", required = false) LocalTime startTime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
            @RequestParam(name = "endTime", required = false) LocalTime endTime
    ){
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String doDelete(@PathVariable int id){
        delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String preUpdate(@PathVariable int id, Model model){
        model.addAttribute("meal", get(id));
        return "mealForm";
    }

    @PostMapping("/save")
    public String save(
            @ModelAttribute("mealForm") Meal meal
    ){
        if(meal.isNew()){
            create(meal);
        } else {
            update(meal, meal.getId());
        }
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String preCreate(Model model){
        model.addAttribute("meal", new Meal(LocalDateTime.now(), "", 10));
        model.addAttribute("action", "create");
        return "mealForm";
    }


}
