package com.myexaminer.service;

import com.myexaminer.enums.RoleEnum;
import com.myexaminer.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleService roleService;

    @Test
    void When_NoRoleInRepository_Expect_EntityNotFoundException(){
        //given
        when(roleRepository.findByName(RoleEnum.ROLE_STUDENT)).thenReturn(Optional.empty());

        //when //then
        assertThrows(EntityNotFoundException.class, () -> {
            roleService.getRoleByName(RoleEnum.ROLE_STUDENT);
        });
    }
}
