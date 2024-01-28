package org.example.finalprojectphasetwo.service.impl;

import org.apache.coyote.BadRequestException;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.repository.SpecialistRepository;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.OrderService;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.example.finalprojectphasetwo.service.SuggestionService;
import org.example.finalprojectphasetwo.service.WalletService;
import org.example.finalprojectphasetwo.dto.createSuggestionDto;
import org.example.finalprojectphasetwo.dto.SpecialistSingUpDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class SpecialistServiceImpl
        extends UserServiceImpl<Specialist>
        implements SpecialistService {

    private final SpecialistRepository repository;
    private final WalletService walletService;
    private final OrderService orderService;
    private final SuggestionService suggestionService;

    public SpecialistServiceImpl(UserRepository<Specialist> repository, SpecialistRepository repository1, WalletService walletService, OrderService orderService, SuggestionService suggestionService) {
        super(repository);
        this.repository = repository1;
        this.walletService = walletService;
        this.orderService = orderService;
        this.suggestionService = suggestionService;
    }

    @Transactional
    @Override
    public Specialist save(Specialist specialist) {
        return repository.save(specialist);
    }

    @Override
    public List<Specialist> findSpecialistBySpecialistStatus(SpecialistStatus status) {
        return repository.findSpecialistBySpecialistStatus(status);
    }

    @Transactional
    @Override
    public void specialistSingUp(SpecialistSingUpDto dto) throws IOException {
        Specialist specialist = new Specialist();
        specialist.setFirstName(dto.getFirstName());
        specialist.setLastName(dto.getLastName());
        specialist.setEmailAddress(dto.getEmailAddress());
        specialist.setUsername(dto.getUsername());
        specialist.setPassword(dto.getPassword());
        specialist.setActive(true);
        specialist.setHasPermission(false);
        specialist.setSpecialistStatus(SpecialistStatus.NEW);
        setProfileImageToSpecialist(dto.getPathName());
        specialist.setProfileImage(setProfileImageToSpecialist(dto.getPathName()));
        Wallet wallet = walletService.saveWallet();
        specialist.setWallet(wallet);
        repository.save(specialist);
    }

    public byte[] setProfileImageToSpecialist(String pathName) throws IOException {
        byte[] loadImage;
        Path path = Paths.get(pathName);
        loadImage = Files.readAllBytes(path);
        return loadImage;
    }

    @Override
    public String getSpecialistProfileImageFromDatabase(Specialist specialist) throws IOException {
        if (specialist != null && specialist.getProfileImage() != null) {
            String filePath = "/Users/sama/IdeaProjects/FinalProjectPhaseTwo/src/main/resources/image.jpg";
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                fileOutputStream.write(specialist.getProfileImage());
            }
            return filePath;
        }
        return null;
    }

    @Transactional
    @Override
    public void addSuggestionToOrderBySpecialist(Order order, createSuggestionDto dto) throws BadRequestException {
        addSuggestionToOrderBySpecialistValidation(order, dto);
        Suggestion suggestion = Suggestion.builder()
                .suggestedPrice(dto.getSuggestedPrice())
                .suggestedStartDate(dto.getSuggestedStartDate())
                .workDuration(dto.getWorkDuration())
                .specialist(dto.getSpecialist())
                .build();
        List<Suggestion> suggestions = new ArrayList<>();
        suggestions.add(suggestion);
        if (checkPrice(order, dto)) {
            suggestionService.save(suggestion);
            order.setSuggestions(suggestions);
            order.setStatus(OrderStatus.WAITING_SPECIALIST_SELECTION);
            orderService.save(order);
        } else throw new BadRequestException("SUGGESTED PRICE IS LESS THAN BASE PRICE");
    }

    private static void addSuggestionToOrderBySpecialistValidation(Order order, createSuggestionDto dto) throws BadRequestException {
        if (dto.getSuggestedStartDate().isBefore(LocalDate.now())) throw new BadRequestException("NOT RIGHT TIME !");
        if (order == null) throw new NullPointerException("BAD INVOCATION FOR ORDER !");
    }

    private boolean checkPrice(Order order, createSuggestionDto dto) {
        if (order.getSubService().getBasePrice() == null) throw new NullPointerException("SUB SERVICE IS NULL !");
        return dto.getSuggestedPrice() > order.getSubService().getBasePrice();
    }
}