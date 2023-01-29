package com.example.stock_resto.Controllers;
import com.example.stock_resto.Repositories.ProduitRefRepository;
import com.example.stock_resto.Repositories.ProduitStockRepository;
import com.example.stock_resto.Repositories.StatueRepository;
import com.example.stock_resto.entities.ProduitRef;
import com.example.stock_resto.entities.ProduitStock;
import com.example.stock_resto.entities.Statue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProduitStockController {
    @Autowired
    private ProduitStockRepository produitStockRepository;
    @Autowired
    private ProduitRefRepository produitRefRepository;

    List<ProduitRef> produitRefs = new ArrayList<>();
    List<Statue> statues = new ArrayList<>();
    @Autowired
    private StatueRepository statueRepository;

    @GetMapping("/addProduitStock")
    public String addProduitStock(Model produitStockModel) {
        statues = statueRepository.findAll();
        produitRefs = produitRefRepository.findAll();
        produitStockModel.addAttribute("produitRefs", produitRefs);
        produitStockModel.addAttribute("statues", statues);
        ProduitStock produitStock = new ProduitStock();
        produitStockModel.addAttribute("produitStock", produitStock);
        ProduitRef produitRef = new ProduitRef();
        produitStockModel.addAttribute("produitRef", produitRef);
        Statue statue = new Statue();
        produitStockModel.addAttribute("statue", statue);
        return "produitStock_form";
    }

    @PostMapping("/produitStock/saveProduitStock")
    public String saveProduitStock(@ModelAttribute("produitStock") ProduitStock produitStock,
                                   @ModelAttribute("produitRef") ProduitRef produitRef,
                                   @ModelAttribute("statue") Statue statue,
                                   @RequestParam("idProduitRef") Integer idProduitRef,
                                   @RequestParam("idStatue") Integer idStatue,
                                   Model produitStockModel) {
        produitRef = produitRefRepository.findById(idProduitRef).get();
        statue = statueRepository.findById(idStatue).get();
        produitStock.setProduitRef(produitRef);
        produitStock.setStatue(statue);
        produitStockRepository.saveAndFlush(produitStock);
        produitStockModel.addAttribute("produitRef", produitRef);
        produitStockModel.addAttribute("statue", statue);
        produitStockModel.addAttribute("produitStock", produitStock);

        return "redirect:/produitStock";
    }





    @GetMapping("/produitStock")
    public String getAllProduit(Model model, @Param("keyword") String keyword) {
        try {
            List<ProduitStock> produitStocks = new ArrayList<>();

            if (keyword == null) {
                produitStockRepository.findAll().forEach(produitStocks ::add);

            } else {
                produitStockRepository.findByProduitRefNomProduitRefLikeIgnoreCase("%"+keyword+"%").forEach(produitStocks::add);
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("produitStocks",produitStocks);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        return "produitStock";
    }
    @GetMapping("/produitStock/{id}")
    public String editProduitStock(@PathVariable("id") Integer idStock, Model model, RedirectAttributes redirectAttributes) {
        try {
            produitRefs=produitRefRepository.findAll();
            statues=statueRepository.findAll();
            ProduitStock produitStock=produitStockRepository.findById(idStock).get();
            model.addAttribute("produitStock", produitStock);
            model.addAttribute("produitRefs",produitRefs);
            model.addAttribute("pageNomProduitSock", "Edit produit (ID: " + idStock + ")");
            model.addAttribute("statues",statues);
            return "produitStock_form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());

            return "redirect:/produitStock";
        }
    }
    @GetMapping("/produitStock/delete/{id}")
    public String deleteProduitStock(@PathVariable("id") Integer id,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            produitStockRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "The product with id=" + id + " has been deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/produitStock";
    }}
