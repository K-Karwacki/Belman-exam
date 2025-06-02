package dk.easv.belmanexam.services.factories;

import dk.easv.belmanexam.repositories.implementations.PhotoDocumentationRepositoryImpl;
import dk.easv.belmanexam.repositories.implementations.UserRepositoryImpl;
import dk.easv.belmanexam.repositories.interfaces.BaseRepository;
import dk.easv.belmanexam.repositories.interfaces.PhotoDocumentationRepository;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class RepositoryServiceFactory
{
  private final RepositoryService repositoryService;
  private final Map<Class<? extends BaseRepository<?>>, BaseRepository<?>> repositoryMap;

  public RepositoryServiceFactory(){
    repositoryMap = new HashMap<>();
    repositoryService = new RepositoryServiceImpl(repositoryMap);

    loadRepositories();
  }

  public void loadRepositories(){
    UserRepository userRepository = new UserRepositoryImpl();
    PhotoDocumentationRepository photoDocumentationRepository = new PhotoDocumentationRepositoryImpl(userRepository);

    repositoryMap.putIfAbsent(PhotoDocumentationRepository.class, photoDocumentationRepository);
    repositoryMap.putIfAbsent(UserRepository.class, userRepository);
  }

  public void addRepository(Class<?extends BaseRepository<?>> repositoryInterfaceClass, BaseRepository<?> instance){
    repositoryMap.putIfAbsent(repositoryInterfaceClass, instance);
  }

  public RepositoryService getRepositoryService(){
    return repositoryService;
  }


  private static class RepositoryServiceImpl implements RepositoryService{

    private final Map<Class<? extends BaseRepository<?>>, BaseRepository<?>> repositoryMap;

    public RepositoryServiceImpl(Map<Class<? extends BaseRepository<?>>, BaseRepository<?>> repositoryMap){
      this.repositoryMap = repositoryMap;
    }

    @Override public <R extends BaseRepository<T>, T> R getRepository(Class<R> repositoryClass) {
      return (R) repositoryMap.get(repositoryClass);
    }
  }

}
