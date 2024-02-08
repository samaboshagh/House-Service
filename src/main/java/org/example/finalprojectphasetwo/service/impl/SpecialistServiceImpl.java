package org.example.finalprojectphasetwo.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.exception.SpecialistQualificationException;
import org.example.finalprojectphasetwo.exception.WrongTimeException;
import org.example.finalprojectphasetwo.repository.SpecialistRepository;
import org.example.finalprojectphasetwo.service.*;
import org.example.finalprojectphasetwo.dto.request.CreateSuggestionDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Transactional(readOnly = true)
@Service
public class SpecialistServiceImpl
        extends UserServiceImpl<Specialist, SpecialistRepository>
        implements SpecialistService {


    private final WalletService walletService;
    private final SuggestionService suggestionService;
    private final CommentService commentService;
    private final OrderService orderService;
    private final Validator validator;

    public SpecialistServiceImpl(SpecialistRepository userRepository, WalletService walletService, SuggestionService suggestionService, CommentService commentService, OrderService orderService, Validator validator) {
        super(userRepository);
        this.walletService = walletService;
        this.suggestionService = suggestionService;
        this.commentService = commentService;
        this.orderService = orderService;
        this.validator = validator;
    }


    @Transactional
    @Override
    public Specialist save(Specialist specialist) {
        return userRepository.save(specialist);
    }

    @Override
    public List<Specialist> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Specialist findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("USER NOT FOUND")
        );
    }

    @Transactional
    @Override
    public void specialistSingUp(Specialist specialist, String path) throws IOException {
        Wallet wallet = walletService.saveWallet();
        checkUsernameAndEmailForRegistration(specialist);
        specialist.setSpecialistStatus(SpecialistStatus.NEW);
        specialist.setProfileImage(setProfileImageToSpecialist(path));
        specialist.setStar(0);
        specialist.setActive(true);
        specialist.setWallet(wallet);
        userRepository.save(specialist);
    }

    @Override
    @Transactional
    public void changePassword(String username, String password) {
        if (username.isBlank() && password.isBlank())
            throw new NotFoundException("USERNAME OR PASSWORD CANNOT BE NULL");
        Specialist specialist = findByUsername(username);
        if (specialist.getSpecialistStatus().equals(SpecialistStatus.WARNING) ||
            specialist.getSpecialistStatus().equals(SpecialistStatus.NEW))
            throw new SpecialistQualificationException("SPECIALIST NOT QUALIFIED");
        changePassword(specialist, password);
    }

    private byte[] setProfileImageToSpecialist(String pathName) throws IOException {
        if (pathName == null) throw new NotFoundException("INVALID PATH : NULL");
        Path path = Paths.get(pathName);
        if (!Files.exists(path)) throw new NotFoundException("FILE NOT FOUND: " + pathName);
        return Files.readAllBytes(path);
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

    @Override
    public List<Order> findAllOrders() {
        return orderService.findAll();
    }

    @Transactional
    @Override
    public void addSuggestionToOrderBySpecialist(CreateSuggestionDto dto) {
        Set<ConstraintViolation<CreateSuggestionDto>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new InvalidInputException("SPECIALIST USER NAME CON NOT BE NULL");
        }
        if (dto.getSuggestedStartDate().isBefore(ZonedDateTime.now())) throw new WrongTimeException("NOT RIGHT TIME !");
        suggestionService.addSuggestion(dto);
    }

    @Override
    @Transactional
    public void demotionOfTheSpecialist(Specialist specialist) {
        if (userRepository.findByUsername(specialist.getUsername()).isPresent() && specialist.getStar() < 0) {
            specialist.setSpecialistStatus(SpecialistStatus.WARNING);
            specialist.setActive(false);
            userRepository.save(specialist);
        }
    }

    @Override
    @Transactional
    public void reducingScore(Specialist specialist) {
        if (userRepository.findByUsername(specialist.getUsername()).isPresent()) {

            int star = specialist.getStar() - 1;
            specialist.setStar(star);
            userRepository.save(specialist);
        }
    }

    private void getAverageScore(Specialist specialist) {
        int totalScore = 0;
        int commentCount = 0;
        for (Comment comment : specialist.getComments()) {
            totalScore += comment.getScore();
            commentCount++;
        }
        Integer averageScore = (commentCount > 0) ? totalScore / commentCount : 0;
        specialist.setStar(averageScore);
        userRepository.save(specialist);
    }

    @Override
    public void specialistGetPayment(Suggestion suggestion) {
        if (suggestion != null && userRepository.findByUsername(suggestion.getSpecialist().getUsername()).isPresent()) {
            Wallet wallet = suggestion.getSpecialist().getWallet();
            wallet.setCreditAmount(wallet.getCreditAmount() + (suggestion.getSuggestedPrice() * 0.7));
            walletService.save(wallet);
        }
    }

    @Override
    public List<Comment> findAllBySpecialist(String specialistUsername) {
        Specialist specialist = findByUsername(specialistUsername);
        return commentService.findAllBySpecialist(specialist);
    }

    @Override
    public List<Specialist> findSpecialistBySpecialistStatus(SpecialistStatus status) {
        return userRepository.findSpecialistBySpecialistStatus(status);
    }
}