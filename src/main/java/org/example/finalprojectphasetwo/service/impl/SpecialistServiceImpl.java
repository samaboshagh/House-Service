package org.example.finalprojectphasetwo.service.impl;

import org.example.finalprojectphasetwo.entity.Wallet;
import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.example.finalprojectphasetwo.repository.SpecialistRepository;
import org.example.finalprojectphasetwo.repository.UserRepository;
import org.example.finalprojectphasetwo.service.SpecialistService;
import org.example.finalprojectphasetwo.service.WalletService;
import org.example.finalprojectphasetwo.service.dto.SpecialistSingUpDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SpecialistServiceImpl
        extends UserServiceImpl<Specialist>
        implements SpecialistService {

    private final SpecialistRepository repository;
    private final WalletService WalletService;

    public SpecialistServiceImpl(UserRepository<Specialist> repository, SpecialistRepository repository1, org.example.finalprojectphasetwo.service.WalletService walletService) {
        super(repository);
        this.repository = repository1;
        WalletService = walletService;
    }

    @Override
    public List<Specialist> loadSpecialistWithNewStatus() {
        return repository.loadSpecialistWithNewStatus();
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
        Wallet wallet = WalletService.saveWallet();
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
    public void setAcceptedStatus(Specialist specialist) {
        specialist.setSpecialistStatus(SpecialistStatus.ACCEPTED);
        repository.save(specialist);
    }
}