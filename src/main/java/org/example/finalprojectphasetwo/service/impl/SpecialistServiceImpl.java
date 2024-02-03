package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.exception.WrongTimeException;
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
        Wallet wallet = walletService.saveWallet();
        checkValidName(dto);
        checkUsernameAndEmailForRegistration(dto);
        checkPassword(dto);
        repository.save(
                Specialist
                        .builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .emailAddress(dto.getEmailAddress())
                        .username(dto.getUsername())
                        .password(dto.getPassword())
                        .isActive(true)
                        .hasPermission(false)
                        .specialistStatus(SpecialistStatus.NEW)
                        .specialization(dto.getSpecialization())
                        .profileImage(setProfileImageToSpecialist(dto.getPathName()))
                        .wallet(wallet)
                        .build()
        );
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
    public void addSuggestionToOrderBySpecialist(Order order, createSuggestionDto dto) {
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
        } else throw new InvalidInputException("SUGGESTED PRICE IS LESS THAN BASE PRICE");
    }

    @Override
    @Transactional
    public void demotionOfTheSpecialist(Specialist specialist) {
        if (repository.findByUsername(specialist.getUsername()) != null && specialist.getStar() < 0) {
            specialist.setSpecialistStatus(SpecialistStatus.WARNING);
            specialist.setActive(false);
            repository.save(specialist);
        } else throw new NotFoundException("SPECIALIST NOT FOUND");
    }

    @Override
    @Transactional
    public void reducingScore(Specialist specialist) {
        if (repository.findByUsername(specialist.getUsername()) != null) {
            specialist.setStar(specialist.getStar() - 1);
            repository.save(specialist);
        } else throw new NotFoundException("SPECIALIST NOT FOUND");
    }

    @Override
    public void specialistGetPayment(Suggestion suggestion) {
        if (suggestion != null && repository.findByUsername(suggestion.getSpecialist().getUsername()) != null) {
            Wallet wallet = suggestion.getSpecialist().getWallet();
            wallet.setCreditAmount(suggestion.getSuggestedPrice() * 0.7);
            walletService.save(wallet);
        } else throw new NotFoundException("SPECIALIST NOT FOUND");
    }

    private static void addSuggestionToOrderBySpecialistValidation(Order order, createSuggestionDto dto) {
        if (dto.getSuggestedStartDate().isBefore(LocalDate.now())) throw new WrongTimeException("NOT RIGHT TIME !");
        if (order == null) throw new NotFoundException("BAD INVOCATION FOR ORDER !");
    }

    private boolean checkPrice(Order order, createSuggestionDto dto) {
        if (order.getSubService().getBasePrice() == null) throw new NotFoundException("SUB SERVICE IS NULL !");
        return dto.getSuggestedPrice() > order.getSubService().getBasePrice();
    }
}