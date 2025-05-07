package dk.easv.belmanexam.bll.factories;


import dk.easv.belmanexam.dal.repositories.BaseRepository;

public interface RepositoryService
{
  <R extends BaseRepository<T>, T> R getRepository(Class<R> repositoryClass);
}
