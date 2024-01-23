package org.example.finalprojectphasetwo.service.impl;

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
import org.example.finalprojectphasetwo.dto.CreateSuggestionDto;
import org.example.finalprojectphasetwo.dto.SpecialistSingUpDto;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

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

    @Override
    public Specialist save(Specialist specialist) {
        return repository.save(specialist);
    }

    @Override
    public List<Specialist> findSpecialistBySpecialistStatus(SpecialistStatus status) {
        return repository.findSpecialistBySpecialistStatus(status);
    }

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

    private byte[] setProfileImageToSpecialist(String pathName) throws IOException {
        byte[] loadImage;
        Path path = Paths.get(pathName);
        loadImage = Files.readAllBytes(path);
        return loadImage;
    }

    @Override
    public String getSpecialistProfileImageFromDatabase(Specialist specialist) throws IOException {
        if (specialist != null && specialist.getProfileImage() != null) {
            String filePath = "/Users/sama/IdeaProjects/FinalProjectPhaseOne/src/main/resources/image.jpg";
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream.write(specialist.getProfileImage());
            return filePath;
        }
        return null;
    }

    @Override
    public void addSuggestionToOrderBySpecialist(Integer orderID, CreateSuggestionDto dto) {
        if (dto.getSuggestedStartDate().isBefore(LocalDate.now())) throw new IllegalStateException();
        Order order = orderService.findById(orderID).orElse(null);
        Suggestion suggestion = Suggestion.builder()
                .suggestedPrice(dto.getSuggestedPrice())
                .suggestedStartDate(dto.getSuggestedStartDate())
                .workDuration(dto.getWorkDuration())
                .specialist(dto.getSpecialist())
                .build();
        assert order != null;
        if (order.getSubService() != null) {
            if (dto.getSuggestedPrice() < order.getSubService().getBasePrice()) throw new IllegalStateException();
            suggestionService.save(suggestion);
            order.setSuggestion(suggestion);
            order.setStatus(OrderStatus.WAITING_SPECIALIST_SELECTION);
            orderService.save(order);
        } else throw new NullPointerException();
    }
}