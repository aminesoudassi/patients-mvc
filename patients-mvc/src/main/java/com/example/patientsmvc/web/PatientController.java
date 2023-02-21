package com.example.patientsmvc.web;

import com.example.patientsmvc.entities.Patient;
import com.example.patientsmvc.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;

@Controller
public class PatientController {
    // click on the varibale and click alt + enter it will generate a constructor
    private PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

/*    @GetMapping("/index")
    public String patients(Model model,
        @RequestParam(name="page",defaultValue ="0") int page,
        @RequestParam(name="size",defaultValue ="5") int size) {

        Page<Patient> pagePatients = patientRepository.findAll(PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        return "patients";
    }*/
    @GetMapping("/index")
    public String patients(Model model,
                           @RequestParam(name="page",defaultValue ="0") int page,
                           @RequestParam(name="size",defaultValue ="5") int size,
                           @RequestParam(name="keyword",defaultValue ="") String keyword) {

        Page<Patient> pagePatients = patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Long id, String keyword,int page) {
        patientRepository.deleteById(id);
        return "redirect:/index?keyword="+keyword+"&page="+page;
    }
    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }
    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listPatients() {
        return patientRepository.findAll();
    }

    @GetMapping("/formPatients")
    public  String formPatients(Model model) {
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }
    @PostMapping(path="/save")
    public  String save(Model model, @Valid Patient patient, BindingResult bindingResult
            ,@RequestParam(name="page",defaultValue ="0") int page,
                        @RequestParam(name="keyword",defaultValue ="") String keyword) {
        if(bindingResult.hasErrors()) return "formPatients";
       patientRepository.save(patient);
        return "redirect:/index?keyword="+keyword+"&page="+page;
    }
    @GetMapping("/editPatient")
    public String editPatient(Model model, Long id, String keyword,int page) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatient";
    }

}
