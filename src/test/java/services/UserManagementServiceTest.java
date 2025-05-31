package services;

import dk.easv.belmanexam.entities.User;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;
import dk.easv.belmanexam.services.factories.RepositoryService;
import dk.easv.belmanexam.services.implementations.UserManagementServiceImpl;
import dk.easv.belmanexam.ui.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserManagementServiceTest {

    @Mock
    private RepositoryService mockRepositoryService;

    @Mock
    private UserRepository mockUserRepository;

    private UserManagementServiceImpl userManagementService;

    @BeforeEach
    void setUp() {
        when(mockRepositoryService.getRepository(UserRepository.class)).thenReturn(mockUserRepository);
        userManagementService = new UserManagementServiceImpl(mockRepositoryService);
    }

    @Test
    public void testCreateUserSuccessfully() {
        // Arrange
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        when(mockUserRepository.add(any())).thenAnswer(invocation -> {
            User user = invocation.getArgument(0);
            user.setId(1);
            return user;
        });

        // Act
        UserModel result = userManagementService.createUser(
                "Kirsten",
                "Nielsen",
                "ADMIN",
                "test@example.com",
                "123456789");

        // Assert
        verify(mockUserRepository).add(userArgumentCaptor.capture());
        User passedtoAdd = userArgumentCaptor.getValue();

        assertEquals("Kirsten", passedtoAdd.getFirstName());
        assertEquals("Nielsen", passedtoAdd.getLastName());
        assertEquals("ADMIN", passedtoAdd.getRole());
        assertEquals("test@example.com", passedtoAdd.getEmail());

        assertEquals("test@example.com", result.getEmail());
        assertEquals(1, result.getID());

    }
}
