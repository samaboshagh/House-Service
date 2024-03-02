package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.dto.request.ChangePasswordRequest;
import org.example.finalprojectphasetwo.entity.Comment;
import org.example.finalprojectphasetwo.entity.Order;
import org.example.finalprojectphasetwo.entity.Suggestion;
import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.OrderStatus;
import org.example.finalprojectphasetwo.entity.enumeration.Role;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.exception.InvalidInputException;
import org.example.finalprojectphasetwo.exception.NotFoundException;
import org.example.finalprojectphasetwo.exception.SpecialistQualificationException;
import org.example.finalprojectphasetwo.exception.WrongTimeException;
import org.example.finalprojectphasetwo.repository.ConfirmationTokenRepository;
import org.example.finalprojectphasetwo.repository.SpecialistRepository;
import org.example.finalprojectphasetwo.service.*;
import org.example.finalprojectphasetwo.utility.SemaphoreUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class SpecialistServiceImpl
        extends UserServiceImpl<Specialist, SpecialistRepository>
        implements SpecialistService {


    private final WalletService walletService;
    private final SuggestionService suggestionService;
    private final CommentService commentService;
    private final OrderService orderService;
    private final BCryptPasswordEncoder passwordEncoder;

    public SpecialistServiceImpl(SpecialistRepository userRepository
            , WalletService walletService
            , SuggestionService suggestionService
            , CommentService commentService
            , OrderService orderService
            , BCryptPasswordEncoder passwordEncoder
            , EmailService emailService
            , ConfirmationTokenRepository confirmationTokenRepository) {
        super(userRepository, emailService, confirmationTokenRepository, passwordEncoder);
        this.walletService = walletService;
        this.suggestionService = suggestionService;
        this.commentService = commentService;
        this.orderService = orderService;
        this.passwordEncoder = passwordEncoder;
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
        SemaphoreUtil.acquireNewUserSemaphore();
        Wallet wallet = walletService.saveWallet();
        checkUsernameAndEmailForRegistration(specialist);
        specialist.setSpecialistStatus(SpecialistStatus.NEW);
        specialist.setProfileImage(setProfileImageToSpecialist(path));
        specialist.setStar(0);
        specialist.setPassword(passwordEncoder.encode(specialist.getPassword()));
        specialist.setWallet(wallet);
        specialist.setRole(Role.ROLE_SPECIALIST);
        userRepository.save(specialist);
        sendEmail(specialist.getEmailAddress());
        SemaphoreUtil.releaseNewUserSemaphore();
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest password) {
        Specialist specialist = findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
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
    public List<Order> findAllOrders(String username) {
        Specialist specialist = findByUsername(username);
        return orderService.findOrderWithWaitingStatusBySpecialist(specialist);
    }

    @Transactional
    @Override
    public void addSuggestionToOrderBySpecialist(Suggestion suggestion, Integer orderId) {
        Order order = orderService.findById(orderId);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Specialist specialist = findByUsername(username);
        addSuggestionValidation(order, specialist, suggestion.getSuggestedStartDate());
        if (!checkPrice(order, suggestion))
            throw new InvalidInputException("SUGGESTED PRICE IS LESS THAN BASE PRICE");
        suggestionService.addSuggestion(suggestion, order, specialist);
    }

    @Override
    @Transactional
    public void demotionOfTheSpecialist(Specialist specialist) {
        if (userRepository.findByUsername(specialist.getUsername()).isPresent() && specialist.getStar() < 0) {
            specialist.setSpecialistStatus(SpecialistStatus.WARNING);
            specialist.setEnabled(false);
            userRepository.save(specialist);
        }
    }

    @Override
    @Transactional
    public void reducingScore(Specialist specialist) {
        if (userRepository.findByUsername(specialist.getUsername()).isPresent()) {
            specialist.setStar(specialist.getStar() - 1);
            userRepository.save(specialist);
        }
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

    @Override
    public List<Order> findAllOrdersBySpecialist(String username, OrderStatus status) {
        if (status == null) throw new NotFoundException("STATUS IS REQUIRED");
        Specialist specialist = findByUsername(username);
        return orderService.findAllBySpecialist(specialist, status);
    }

    @Override
    public Double seeCredit(String username) {
        Specialist specialist = findByUsername(username);
        return specialist.getWallet().getCreditAmount();
    }

    private static void addSuggestionValidation(Order order, Specialist specialist, ZonedDateTime suggestedStatDate) {
        if (suggestedStatDate.isBefore(ZonedDateTime.now()))
            throw new WrongTimeException("NOT RIGHT TIME !");
        if (specialist.getSpecialistStatus().equals(SpecialistStatus.NEW) ||
            specialist.getSpecialistStatus().equals(SpecialistStatus.WARNING))
            throw new SpecialistQualificationException("SPECIALIST NOT QUALIFIED");
        if (order == null)
            throw new NotFoundException("ODER ID CANT BE NULL !");
    }

    private boolean checkPrice(Order order, Suggestion suggestion) {
        if (order.getSubService().getBasePrice() == null) throw new NotFoundException("SUB SERVICE IS NULL !");
        return suggestion.getSuggestedPrice() > order.getSubService().getBasePrice();
    }

}