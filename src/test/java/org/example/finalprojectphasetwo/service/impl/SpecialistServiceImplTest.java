package org.example.finalprojectphasetwo.service.impl;

import org.apache.coyote.BadRequestException;
import org.example.finalprojectphasetwo.dto.createSuggestionDto;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.repository.SubServiceRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpecialistServiceImplTest {

    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private SuggestionService suggestionService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SubServiceRepository subServiceRepository;

    private SubService getSubService() {
        SubService subService = SubService
                .builder()
                .basePrice(12.12)
                .subServiceTitle("title")
                .build();
        subServiceRepository.save(subService);
        return subService;
    }

    private Order orderInit(SubService subService) {
        Order order = Order
                .builder()
                .status(OrderStatus.WAITING_SPECIALIST_SELECTION)
                .subService(subService)
                .build();
        return orderService.save(order);
    }

    private createSuggestionDto createSuggestionDtoInitForRightTime() {
        return createSuggestionDto
                .builder()
                .suggestedPrice(12.12)
                .workDuration(LocalDate.now())
                .suggestedStartDate(LocalDate.now().minusDays(1))
                .build();
    }

    private createSuggestionDto createSuggestionDtoInit() {
        return createSuggestionDto
                .builder()
                .workDuration(LocalDate.now())
                .suggestedStartDate(LocalDate.now().plusDays(1))
                .suggestedPrice(100.0)
                .build();
    }

    @Test
    void testGetSpecialistProfileImageFromDatabaseWithNullSpecialist() throws IOException {
        // when
        String filePath = specialistService.getSpecialistProfileImageFromDatabase(null);
        // then
        assertNull(filePath);
    }

    @Test
    public void testGetSpecialistProfileImageFromDatabase() throws IOException {
        String filePath ="/Users/sama/IdeaProjects/FinalProjectPhaseTwo/src/main/resources/website.jpg";

        Specialist specialist = new Specialist();
        specialist.setFirstName("SpecialistFirstname");
        specialist.setLastName("SpecialistLastname");
        specialist.setEmailAddress("Specialist2@gmail.com");
        specialist.setUsername("Specialist");
        specialist.setPassword("Spe12345");
        specialist.setActive(true);
        specialist.setSpecialistStatus(SpecialistStatus.ACCEPTED);
        specialist.setProfileImage(specialistService.setProfileImageToSpecialist(filePath));
        specialistService.save(specialist);

        assertNotNull(filePath);
        Path path = Path.of("/Users/sama/IdeaProjects/FinalProjectPhaseTwo/src/main/resources/image.jpg");
        assertTrue(Files.exists(path));
        byte[] expectedImage = specialist.getProfileImage();
        byte[] actualImage = Files.readAllBytes(path);

        assertArrayEquals(expectedImage, actualImage);
    }

    @Test
    public void testSetSpecialistProfileImageFromDatabase() throws IOException {

        String filePath ="/Users/sama/IdeaProjects/FinalProjectPhaseTwo/src/main/resources/website.jpg";

        Specialist specialist = new Specialist();
        specialist.setFirstName("SpecialistFirstname");
        specialist.setLastName("SpecialistLastname");
        specialist.setEmailAddress("Specialistt@gmail.com");
        specialist.setUsername("Specialist");
        specialist.setActive(true);
        specialist.setPassword("Spe12345");
        specialist.setSpecialistStatus(SpecialistStatus.ACCEPTED);
        specialist.setProfileImage(specialistService.setProfileImageToSpecialist(filePath));
        specialistService.save(specialist);
        assertNotNull(filePath);
        Path path = Path.of(filePath);
        assertTrue(Files.exists(path));
    }

    @Test
    public void testAddSuggestionToOrderBySpecialistInvalidStartDate() {
        // given
        SubService subService = getSubService();
        Order order = orderInit(subService);
        // when and then
        BadRequestException exception = assertThrows(BadRequestException.class,
                () -> specialistService.addSuggestionToOrderBySpecialist(order, createSuggestionDtoInitForRightTime()));
        assertEquals("NOT RIGHT TIME !", exception.getMessage());
    }

    @Test
    public void testAddSuggestionToOrderBySpecialistBadInvocation() {
        // when and then
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> specialistService.addSuggestionToOrderBySpecialist(null, createSuggestionDtoInit()));
        assertEquals("BAD INVOCATION FOR ORDER !", exception.getMessage());
    }

    @Test
    public void testAddSuggestionToOrderBySpecialist() {
        // given
        Order order = orderInit(getSubService());
        // when and then
        try {
            specialistService.addSuggestionToOrderBySpecialist(order, createSuggestionDtoInit());
            assertNotNull(order);
            assertEquals(OrderStatus.WAITING_SPECIALIST_SELECTION, order.getStatus());
            assertEquals(1, order.getSuggestions().size());
            Suggestion savedSuggestion = suggestionService.findById(order.getSuggestions().get(0).getId()).orElse(null);
            assertNotNull(savedSuggestion);
        } catch (BadRequestException | NullPointerException e) {
            fail("UNEXPECTED EXCEPTION: " + e.getMessage());
        }
    }

    @Test
    public void testAddSuggestionToOrderBySpecialistLowSuggestedPrice() {
        // given
        SubService subService = SubService
                .builder()
                .subServiceTitle("title")
                .basePrice(150.0)
                .build();
        subServiceRepository.save(subService);
        // when and then
        BadRequestException exception = assertThrows(BadRequestException.class, () ->
                specialistService.addSuggestionToOrderBySpecialist(orderInit(subService),createSuggestionDtoInit()));
        assertEquals("SUGGESTED PRICE IS LESS THAN BASE PRICE", exception.getMessage());
    }
}