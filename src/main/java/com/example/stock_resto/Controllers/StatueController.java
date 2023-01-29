package com.example.stock_resto.Controllers;
import com.example.stock_resto.Repositories.StatueRepository;
import com.example.stock_resto.entities.Statue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
@Controller
public class StatueController {
        @Autowired
        private StatueRepository statueRepository;
        @GetMapping("/statues")
        public String getAllStatues(Model model, @Param("keyword") String keyword) {
            try {
                List<Statue>statues= new ArrayList<>();

                if (keyword == null) {
                    statueRepository.findAll().forEach(statues::add);

                } else {
                    statueRepository.findByNomStatueContainingIgnoreCase(keyword).forEach(statues::add);
                    model.addAttribute("keyword", keyword);
                }
                model.addAttribute("statues", statues);
            } catch (Exception e) {
                model.addAttribute("message", e.getMessage());
            }

            return "statues";
        }

        @GetMapping("/statues/new")
        public String addStatue(Model model) {
            Statue  statue   = new Statue();

            model.addAttribute("statue", statue);
            model.addAttribute("pageTitle", "Create new statue");

            return "statues_form";
        }

        @PostMapping("/statues/save")
        public String saveStatues(Statue statue , RedirectAttributes redirectAttributes) {
            try {
                statueRepository.save(statue);

                redirectAttributes.addFlashAttribute("message", "The statue has been saved successfully!");
            } catch (Exception e) {
                redirectAttributes.addAttribute("message", e.getMessage());
            }

            return "redirect:/statues";
        }

        /**
         * {id} id c'est un parametre de l'URL
         * @param id
         * @param model
         * @param redirectAttributes
         * @return
         */
        @GetMapping("/statues/{id}")
        public String editStatues(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
            try {
                Statue  statue  = statueRepository.findById(id).get();

                model.addAttribute("statue", statue);
                model.addAttribute("pageNomStatue", "Edit Statue (ID: " + id + ")");

                return "statues_form";
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());

                return "redirect:/statues";
            }}

        @GetMapping("/statues/delete/{id}")
        public String deleteCategorie(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
            try {
                statueRepository.deleteById(id);
                redirectAttributes.addFlashAttribute("message", "The Statue with id=" + id + " has been deleted successfully!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", e.getMessage());
            }
            return "redirect:/statues";
        }



    }

