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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserManagementServiceTest {

    @Mock
    private RepositoryService repositoryService;
    private UserManagementServiceImpl userManagementService;

    @BeforeEach
    void setUp() {
        userManagementService = new UserManagementServiceImpl(repositoryService);
    }


    @Test
    public void testCreateUserSuccessfully(){


        //Arrange
        UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
        RepositoryService mockRepositoryService = Mockito.mock(RepositoryService.class);

        User dummyUser = new User();
        dummyUser.setId(1);
        dummyUser.setEmail("test@example.com");
        Mockito.when(mockUserRepository.add(Mockito.any())).thenReturn(dummyUser);

        UserManagementServiceImpl service = new UserManagementServiceImpl(mockRepositoryService);

        //Act
        UserModel result = service.createUser(
                "Kirsten",
                "Nielsen",
                "ADMIN",
                "test@example.com",
                "123456789");

        //Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }
}
