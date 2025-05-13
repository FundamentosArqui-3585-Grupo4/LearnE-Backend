package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.*;
import org.learne.platform.learne.domain.model.commands.Note.CreateNotesCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.*;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.profile.infrastructure.persistence.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NoteCommandServiceImplTest {

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private NoteCommandServiceImpl noteCommandService;

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UnitRepository unitRepository;

    private Long savedExamId;
    private Long savedStudentId;

    @BeforeEach
    void init() {
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

        // Crear curso
        Course course = new Course();
        course.setTitle("Curso de Prueba");
        course.setDescription("Descripción");
        course.setLevel("Básico");
        course.setDuration("2 semanas");
        course.setPrior_knowledge("Ninguno");
        course.setPrincipal_image("img");
        course.setUrl_video("video");
        course.setUser(teacher);
        courseRepository.save(course);

        // Crear unidad
        Unit unit = new Unit();
        unit.setTitle("Unidad 1");
        unit.setCourses(course);
        unitRepository.save(unit);

        // Crear examen
        Exam exam = new Exam();
        exam.setTitle("Examen");
        exam.setCourse(course);
        exam.setUnit(unit);
        examRepository.save(exam);
        savedExamId = exam.getId();

        // Crear estudiante
        User student = new User();
        student.setFirstName("Pedro");
        student.setLastName("López");
        student.setUsername("plopez");
        student.setEmail("plopez@correo.com");
        student.setPassword("123456");
        student.setType_user(2);
        student.setType_plan(1);
        userRepository.save(student);
        savedStudentId = student.getId();
    }

    @Test
    void handle_shouldCreateNoteSuccessfully() {
        // Arrange
        CreateNotesCommand command = new CreateNotesCommand(savedStudentId, savedExamId, 18.5f);

        // Act
        Long noteId = noteCommandService.handle(command);

        // Assert
        assertNotNull(noteId, "El ID de la nota no debe ser null");
    }
}