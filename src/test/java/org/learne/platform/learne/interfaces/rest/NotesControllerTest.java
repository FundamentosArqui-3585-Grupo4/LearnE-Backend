package org.learne.platform.learne.interfaces.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.*;
import org.learne.platform.learne.domain.model.commands.Note.CreateNotesCommand;
import org.learne.platform.learne.domain.services.notes.NotesCommandService;
import org.learne.platform.learne.domain.services.notes.NotesQueryServices;
import org.learne.platform.learne.infrastructure.persistence.jpa.*;
import org.learne.platform.learne.interfaces.rest.resources.Notes.CreateNotesResource;
import org.learne.platform.learne.interfaces.rest.resources.Notes.NoteResource;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.profile.infrastructure.persistence.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotesControllerTest {

    @Autowired
    private NotesController notesController;

    @Autowired
    private NotesCommandService notesCommandService;

    @Autowired
    private NotesQueryServices notesQueryServices;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private ExamRepository examRepository;

    private User student;
    private Exam exam;

    @BeforeEach
    void setUp() {
        // Crear profesor
        User teacher = new User();
        teacher.setFirstName("Juan");
        teacher.setLastName("Pérez");
        teacher.setUsername("jperez");
        teacher.setEmail("jperez@correo.com");
        teacher.setPassword("123456");
        teacher.setType_user(1);
        teacher.setType_plan(1);
        userRepository.save(teacher);

        // Crear estudiante
        student = new User();
        student.setFirstName("Pedro");
        student.setLastName("López");
        student.setUsername("plopez");
        student.setEmail("plopez@correo.com");
        student.setPassword("123456");
        student.setType_user(2);
        student.setType_plan(1);
        student = userRepository.save(student);

        // Crear curso
        Course course = new Course();
        course.setTitle("Curso Test");
        course.setDescription("Curso básico");
        course.setDuration("2 semanas");
        course.setLevel("Básico");
        course.setPrincipal_image("img.jpg");
        course.setPrior_knowledge("Ninguno");
        course.setUrl_video("url");
        course.setUser(teacher);
        course = courseRepository.save(course);

        // Crear unidad
        Unit unit = new Unit();
        unit.setTitle("Unidad 1");
        unit.setCourses(course);
        unit = unitRepository.save(unit);

        // Crear examen
        exam = new Exam();
        exam.setTitle("Examen 1");
        exam.setCourse(course);
        exam.setUnit(unit);
        exam = examRepository.save(exam);

        // Crear nota real
        Notes note = new Notes();
        note.setStudentId(student);
        note.setExamId(exam);
        note.setNote(18.5f);
        notesRepository.save(note);
    }

    @Test
    void createNotes_shouldReturn201() {
        CreateNotesResource resource = new CreateNotesResource(student.getId(), exam.getId(), 19.0f);
        ResponseEntity<NoteResource> response = notesController.createNotes(resource);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getNoteById_shouldReturn200() {
        Notes note = notesRepository.findAll().get(0);
        ResponseEntity<NoteResource> response = notesController.getNoteById(note.getId());
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
    }

    @Test
    void getAllNotes_shouldReturn200() {
        ResponseEntity<List<NoteResource>> response = notesController.getAllNotes();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }
}