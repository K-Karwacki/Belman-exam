package dk.easv.belmanexam.dal;

import java.util.Collection;
import java.util.Optional;

public interface BaseRepository<T> {
  Collection<T> getAll();
  Optional<T> getById(long id);
  T add(T entity);
  boolean delete(T entity);
  T update(T newEntity);
}
