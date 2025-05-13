package org.learne.platform.learne.application.internal.commandservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learne.platform.learne.domain.model.aggregates.Course;
import org.learne.platform.learne.domain.model.commands.Material.CreateMaterialCommand;
import org.learne.platform.learne.infrastructure.persistence.jpa.CourseRepository;
import org.learne.platform.learne.infrastructure.persistence.jpa.MaterialRepository;
import org.learne.platform.profile.domain.model.aggregates.User;
import org.learne.platform.profile.infrastructure.persistence.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MaterialCommandServiceImplTest {

    @Autowired
    private MaterialCommandServiceImpl materialCommandService;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    private Long courseId;

    @BeforeEach
    void setUp() {
        User teacher = new User();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setUsername("jdoe");
        teacher.setEmail("jdoe@example.com");
        teacher.setPassword("secure123");
        teacher.setType_user(1);
        teacher.setType_plan(1);
        teacher = userRepository.save(teacher);

        Course course = new Course();
        course.setTitle("Curso de prueba");
        course.setDescription("Descripción");
        course.setLevel("Básico");
        course.setDuration("1h");
        course.setPrior_knowledge("Nada");
        course.setPrincipal_image("img.png");
        course.setUrl_video("https://video.test");
        course.setUser(teacher);

        course = courseRepository.save(course);
        courseId = course.getId();
    }

    @Test
    void handle_shouldCreateMaterialSuccessfully() {
        CreateMaterialCommand command = new CreateMaterialCommand(
                courseId, "Material Title", "PDF", "http://example.com"
        );

        Long materialId = materialCommandService.handle(command);

        assertNotNull(materialId);
        var material = materialRepository.findById(materialId);
        assertTrue(material.isPresent());
        assertEquals("Material Title", material.get().getTitle());
    }
}