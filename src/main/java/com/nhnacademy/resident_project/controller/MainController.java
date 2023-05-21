package com.nhnacademy.resident_project.controller;

import com.nhnacademy.resident_project.service.ResidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ResidentService residentService;
    @GetMapping({"/", ""})
    public String index(Model model,
                        @PageableDefault(sort = "residentRegistrationNumber", direction = Sort.Direction.ASC) Pageable pageable) {
        model.addAttribute("residentList", residentService.getResidentsBy(pageable).getContent());
        model.addAttribute("pageable", pageable);
        return "index";
    }

    @GetMapping("/resident/certifications/{residentSerialNumber}")
    public String getCertifications(@PathVariable int residentSerialNumber,
                                    Model model,
                                    Pageable pageable) {
        model.addAttribute("certificationList",
                residentService.getCertificationsByResidentNumber(residentSerialNumber, pageable).getContent());
        model.addAttribute("pageable", pageable);
        model.addAttribute("residentSerialNumber", residentSerialNumber);
        return "certificationList";
    }
}
