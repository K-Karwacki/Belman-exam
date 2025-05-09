package dk.easv.belmanexam.services.factories;


import dk.easv.belmanexam.repositories.interfaces.BaseRepository;

public interface RepositoryService
{
  <R extends BaseRepository<T>, T> R getRepository(Class<R> repositoryClass);
}
