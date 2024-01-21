package org.example.finalprojectphasetwo.service;



import org.example.finalprojectphasetwo.entity.services.SubService;
import org.example.finalprojectphasetwo.entity.users.Admin;
import org.example.finalprojectphasetwo.entity.users.Specialist;

import java.util.Set;

public interface AdminService extends UserService<Admin>{

    void init();

    void addByAdmin(Specialist specialist, SubService subService);

    void deleteByAdmin(Set<Specialist> specialists, Specialist specialist, SubService subService);
}
