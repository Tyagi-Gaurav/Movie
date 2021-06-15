package com.toptal.scr.tz.resource;

import com.toptal.scr.tz.resource.domain.AccountCreateRequestDTO;
import com.toptal.scr.tz.resource.domain.AccountUpdateRequestDTO;
import com.toptal.scr.tz.resource.domain.ImmutableAccountCreateRequestDTO;
import com.toptal.scr.tz.resource.domain.ImmutableAccountUpdateRequestDTO;
import com.toptal.scr.tz.resource.domain.ImmutableUserProfile;
import com.toptal.scr.tz.resource.domain.UserDetailsResponse;
import com.toptal.scr.tz.resource.domain.UserListResponseDTO;
import com.toptal.scr.tz.resource.domain.UserProfile;
import com.toptal.scr.tz.service.UserService;
import com.toptal.scr.tz.service.domain.User;
import com.toptal.scr.tz.util.TestBuilders;
import com.toptal.scr.tz.util.TestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@EnableWebMvc
@SpringBootTest(classes = UserManagementResource.class)
class UserManagementResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(AdditionalAnswers.returnsFirstArg()));
    }

    @Test
    void shouldAllowAdminToCreateUser() throws Exception {
        AccountCreateRequestDTO requestDTO = ImmutableAccountCreateRequestDTO.builder()
                .role("USER")
                .userName(RandomStringUtils.randomAlphabetic(10))
                .password(RandomStringUtils.randomAlphabetic(10))
                .firstName("sdfsdfx")
                .lastName("bsdfsdf")
                .build();
        String content = TestUtils.asJsonString(requestDTO);

        //when
        mockMvc.perform(post("/user/manage")
                .content(content)
                .contentType("application/vnd.user.add.v1+json"))
                .andExpect(status().isNoContent());

        verify(userService).add(any(User.class));
    }

    @Test
    void shouldAllowAdminToReadAllUsers() throws Exception {
        //given
        User user = TestBuilders.aUser();
        given(userService.getAllUsers()).willReturn(Collections.singletonList(user));

        //when
        MvcResult mvcResult = mockMvc.perform(get("/user/manage")
                .contentType("application/vnd.user.read.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService).getAllUsers();
        UserListResponseDTO userListResponseDTO = TestUtils.readFromString(mvcResult.getResponse().getContentAsString(),
                UserListResponseDTO.class);

        List<UserDetailsResponse> userDetailsResponses = userListResponseDTO.userDetails();
        assertThat(userDetailsResponses).isNotEmpty();

        UserDetailsResponse userDetailsResponse = userDetailsResponses.get(0);

        assertThat(userDetailsResponse.userName()).isEqualTo(user.getUsername());
        assertThat(userDetailsResponse.firstName()).isEqualTo(user.firstName());
        assertThat(userDetailsResponse.lastName()).isEqualTo(user.lastName());
        assertThat(userDetailsResponse.id()).isEqualTo(user.id());
    }

    @Test
    void shouldAllowAdminToDeleteUser() throws Exception {
        UUID userIdToDelete = UUID.randomUUID();

        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(UUID.randomUUID())
                .authority("USER")
                .build();

        //when
        mockMvc.perform(delete("/user/manage")
                .requestAttr("userProfile", userProfile)
                .param("userId", userIdToDelete.toString())
                .contentType("application/vnd.user.delete.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService).deleteUser(userIdToDelete);
    }

    @Test
    void shouldNotAllowAdminToDeleteSelf() throws Exception {
        UUID userIdToDelete = UUID.randomUUID();

        UserProfile userProfile = ImmutableUserProfile.builder()
                .id(userIdToDelete)
                .authority("ADMIN")
                .build();

        //when
        mockMvc.perform(delete("/user/manage")
                .requestAttr("userProfile", userProfile)
                .param("userId", userIdToDelete.toString())
                .contentType("application/vnd.user.delete.v1+json"))
                .andExpect(status().isOk())
                .andReturn();

        verifyZeroInteractions(userService);
    }

    @Test
    void shouldAllowAdminToUpdateAUser() throws Exception {
        UUID userIdToUpdate = UUID.randomUUID();
        AccountUpdateRequestDTO requestDTO = ImmutableAccountUpdateRequestDTO.builder()
                .role("user")
                .userName(RandomStringUtils.randomAlphabetic(10))
                .password(RandomStringUtils.randomAlphabetic(10))
                .firstName("x")
                .lastName("b")
                .build();
        String content = TestUtils.asJsonString(requestDTO);

        //when
        mockMvc.perform(put("/user/manage")
                .content(content)
                .param("userId", userIdToUpdate.toString())
                .contentType("application/vnd.user.update.v1+json"))
                .andExpect(status().isOk());

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).update(userArgumentCaptor.capture());

        User user = userArgumentCaptor.getValue();
        assertThat(user.id()).isEqualTo(userIdToUpdate);
    }


}