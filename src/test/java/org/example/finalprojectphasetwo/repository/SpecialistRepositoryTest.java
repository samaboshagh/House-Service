package org.example.finalprojectphasetwo.repository;

import org.example.finalprojectphasetwo.entity.enumeration.SpecialistStatus;
import org.example.finalprojectphasetwo.entity.users.Specialist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class SpecialistRepositoryTest {

    @Autowired
    private SpecialistRepository underTest;

    @Test
    void findSpecialistBySpecialistStatus() {
//  given
        SpecialistStatus status = SpecialistStatus.ACCEPTED;
//        SpecialistStatus statusTest = SpecialistStatus.NEW;
        Specialist specialist = new Specialist(
                "SpecialistFirstname",
                "SpecialistLastname",
                "Specialist@gmail.com",
                "SpecialistUsername",
                "s1234567",
                status
        );
        underTest.save(specialist);
//  when
        boolean expected = underTest.findSpecialistBySpecialistStatus(status).isEmpty();
//  then
        assertThat(expected).isFalse();
    }
}