package services;

import dk.easv.belmanexam.auth.UserSession;
import dk.easv.belmanexam.entities.Photo;
import dk.easv.belmanexam.entities.PhotoDocumentation;
import dk.easv.belmanexam.entities.User;
import dk.easv.belmanexam.repositories.interfaces.PhotoDocumentationRepository;
import dk.easv.belmanexam.services.factories.RepositoryService;
import dk.easv.belmanexam.services.implementations.PhotoDocumentationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhotoDocumentationServiceTest {


    @Mock
    private RepositoryService mockRepositoryService;
    @Mock
    private PhotoDocumentationRepository mockPhotoDocumentationRepository;

    @Mock
    private User mockUser;

    @Captor
    ArgumentCaptor<PhotoDocumentation> photoDocumentationArgumentCaptor;

    private PhotoDocumentationServiceImpl photoDocumentationService;
    private PhotoDocumentation sampleDocumentation;

    @BeforeEach
    void setup(){
        when(mockRepositoryService.getRepository(PhotoDocumentationRepository.class)).thenReturn(mockPhotoDocumentationRepository);
        when(mockPhotoDocumentationRepository.getAll()).thenReturn(new ArrayList<>());
        when(mockPhotoDocumentationRepository.getAllLogs()).thenReturn(new ArrayList<>());
        UserSession.INSTANCE.setLoggedUser(mockUser);

        photoDocumentationService = new PhotoDocumentationServiceImpl(mockRepositoryService);

        sampleDocumentation = new PhotoDocumentation();
        sampleDocumentation.setId(1L);
        sampleDocumentation.setOrderNumber("ORDER-001");
        sampleDocumentation.setOperatorEmail("operator@gmail.com");
    }

    @Test
    public void testAddPhotoDocumentationSuccessfully() {
        // Act
        photoDocumentationService.add(sampleDocumentation);

        //Assert
        verify(mockPhotoDocumentationRepository, times(1)).add(photoDocumentationArgumentCaptor.capture());
        assertEquals("ORDER-001", photoDocumentationArgumentCaptor.getValue().getOrderNumber());
    }

    @Test
    public void testUpdatePhotoDocumentationSuccessfully() {
        // Act
        photoDocumentationService.update(sampleDocumentation);

        // Assert
        verify(mockPhotoDocumentationRepository).update(sampleDocumentation);
    }

    @Test
    public void testGetPhotoDocumentationByIdSuccessfully() {
        when(mockPhotoDocumentationRepository.getById(1L)).thenReturn(Optional.of(sampleDocumentation));

        Optional<PhotoDocumentation> result = photoDocumentationService.getById(1L);

        assertTrue(result.isPresent());
        assertEquals("ORDER-001", result.get().getOrderNumber());
    }

    @Test
    public void testGetPhotoDocumentationByOrderNumberSuccessfully() {
        when(mockPhotoDocumentationRepository.getByOrderNumber("ORDER-001")).thenReturn(Optional.of(sampleDocumentation));

        Optional<PhotoDocumentation> result = photoDocumentationService.getByOrderNumber("ORDER-001");

        assertTrue(result.isPresent());
        assertEquals("ORDER-001", result.get().getOrderNumber());
    }

    @Test
    public void testGetAllPhotoDocumentationsSuccessfully() {
        List<PhotoDocumentation> docs = Arrays.asList(sampleDocumentation);
        when(mockPhotoDocumentationRepository.getAll()).thenReturn(docs);

        Collection<PhotoDocumentation> result = photoDocumentationService.getAll();

        assertEquals(1, result.size());
        assertTrue(result.contains(sampleDocumentation));
    }

    @Test
    public void testAddPhoto(){
        byte[] dummyData = new byte[]{1, 2, 3};
        photoDocumentationService.addPhoto(dummyData, 1L, "Front", "Test Info");

        verify(mockPhotoDocumentationRepository).addPhoto(dummyData, 1L, "Front", "Test Info");
    }

    @Test
    public void testGetAllImagesByDocumentationId() {
        List<Photo> photos = Arrays.asList(new Photo());
        when(mockPhotoDocumentationRepository.getAllImagesByDocumentationId(1L)).thenReturn(photos);

        Collection<Photo> result = photoDocumentationService.getAllImagesByDocumentationId(1L);

        assertEquals(1, result.size());
    }

    @Test
    public void testRefresh() {
        List<PhotoDocumentation> newDocs = Arrays.asList(sampleDocumentation);
        when(mockPhotoDocumentationRepository.getAll()).thenReturn(newDocs);

        assertDoesNotThrow(() -> photoDocumentationService.refresh());
    }

    @Test
    public void testGetPhotoDocumentationListModel() {
        assertNotNull(photoDocumentationService.getPhotoDocumentationListModel());
    }

    @Test
    public void testGetLogListModel() {
        assertNotNull(photoDocumentationService.getLogListModel());
    }

}
