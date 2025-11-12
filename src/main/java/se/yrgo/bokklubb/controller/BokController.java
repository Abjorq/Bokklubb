package se.yrgo.bokklubb.controller;

import se.yrgo.bokklubb.domain.Bok;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BokController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("greeting", "Välkommen till Bokklubben!");
        return "home";
    }


    @GetMapping("/books")
    public String books(Model model) {
        List<Bok> books = getAllBooks();
        model.addAttribute("books", books);
        return "booklist";
    }


    @GetMapping("/genre")
    public String genre(@RequestParam(required = false) String type, Model model) {
        List<Bok> books = getAllBooks();


        if (type == null || type.isBlank()) {

            Set<String> genres = books.stream()
                    .map(Bok::getGenre)
                    .filter(Objects::nonNull)
                    .filter(s -> !s.isBlank())
                    .collect(Collectors.toCollection(TreeSet::new));
            model.addAttribute("genres", genres);
            return "genre";
        }



        List<Bok> filtered = books.stream()
                .filter(b -> type.equalsIgnoreCase(b.getGenre()))
                .toList();


        model.addAttribute("selectedGenre", type);
        model.addAttribute("books", filtered);
        return "genre";
    }



    private List<Bok> getAllBooks() {
        return List.of(
                new Bok("Javas Återkomst", "Anton B Jörquist", "Fantasy"),
                new Bok("Backend för en senior", "Björquist B Anton", "Kriminalare"),
                new Bok("Skapa ett OS", "Terry A. Davis", "Roman")
        );
    }

}
