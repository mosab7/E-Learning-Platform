package com.elearning.platform.controller;

import com.elearning.platform.dto.TutorDto;
import com.elearning.platform.model.Course;
import com.elearning.platform.model.Tutor;
import com.elearning.platform.repositories.CourseRepository;
import com.elearning.platform.repositories.TutorRepository;
import com.elearning.platform.services.core.impl.TutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tutors")
public class TutorController {

    private TutorService tutorService;
    private TutorRepository tutorRepository;
    private CourseRepository courseRepository;

    @Autowired
    public TutorController(TutorService tutorService, TutorRepository tutorRepository,
                           CourseRepository courseRepository) {
        this.tutorService = tutorService;
        this.tutorRepository = tutorRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String addProfesor(Model model) {
        model.addAttribute("tutor", new TutorDto());
        return "tutors/tutor-add";
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String saveTutor(TutorDto tutor) {
        tutorService.create(tutor);

        return "redirect:/tutors";
    }

    @GetMapping("/edit/{tutorId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getTutorForUpdate(@PathVariable Long tutorId,
                                       Model model) {
        try {
            Tutor tutorActual = tutorRepository.findById(tutorId).get();
            model.addAttribute("tutor", tutorActual);
            return "tutors/tutor-edit";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/update/{tutorId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateTutor(@PathVariable Long tutorId,
                                 Tutor tutor, RedirectAttributes attributes, Model model){

        try {
            Tutor currentTutor = tutorRepository.findById(tutorId).get();
            currentTutor.setTutorName(tutor.getTutorName());
            currentTutor.setTutorSurname(tutor.getTutorSurname());
            currentTutor.setTutorEmail(tutor.getTutorEmail());
            currentTutor.setTutorDescription(tutor.getTutorDescription());
            currentTutor.setImgUrl(tutor.getImgUrl());

            tutorService.update(tutor);
            attributes.addAttribute("tutorId", tutorId);

            return "redirect:/tutors/{tutorId}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/patch/{tutorId}")
    public String patchTutor(@PathVariable Long tutorId, Tutor tutor, RedirectAttributes attributes, Model model) {

        try {
            Tutor current = tutorRepository.findById(tutorId).get();
            current.setTutorDetail(tutor.getTutorDetail());
            tutorService.patch(current);

            attributes.addAttribute("tutorId", tutorId);
            return "redirect:/tutors/{tutorId}";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getTutorsList(Model model) {
        List<Tutor> tutors = tutorService.getAll();
        model.addAttribute("tutors", tutors);
        return "tutors/tutors";
    }

    @GetMapping("/delete/{tutorId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteTutor(@PathVariable Long tutorId, Model model) {
        try {
            Tutor tutorActual = tutorRepository.findById(tutorId).get();
            tutorService.delete(tutorActual);

            return "redirect:/tutors";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }

    @GetMapping("/{tutorId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String getTutorDetail(@PathVariable Long tutorId, Model model) {
        try {
            Tutor tutor = tutorRepository.findById(tutorId).get();
            model.addAttribute("tutor", tutor);
            List<Course> courses = courseRepository.findAllByTutor(tutor);
            model.addAttribute("courses", courses);
            return "tutors/tutor-detail";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e);
            return "error";
        }
    }
}
