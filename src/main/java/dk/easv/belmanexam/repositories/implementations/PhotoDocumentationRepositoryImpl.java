package dk.easv.belmanexam.repositories.implementations;

import dk.easv.belmanexam.model.*;
import dk.easv.belmanexam.repositories.interfaces.PhotoDocumentationRepository;
import dk.easv.belmanexam.repositories.interfaces.UserRepository;
import dk.easv.belmanexam.repositories.utils.DBConnection;
import dk.easv.belmanexam.repositories.utils.QueryBuilder;
import dk.easv.belmanexam.repositories.utils.mappers.LogMapper;
import dk.easv.belmanexam.repositories.utils.mappers.PhotoDocumentationMapper;
import dk.easv.belmanexam.repositories.utils.mappers.PhotoMapper;
import dk.easv.belmanexam.services.utils.ActionType;
import dk.easv.belmanexam.services.utils.Status;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PhotoDocumentationRepositoryImpl implements PhotoDocumentationRepository {
    private final UserRepository userRepository;
    private final LogMapper logMapper;
    private final PhotoMapper photoMapper;
    private final PhotoDocumentationMapper photoDocumentationMapper;

    public PhotoDocumentationRepositoryImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.logMapper = new LogMapper(userRepository);
        this.photoMapper = new PhotoMapper();
        this.photoDocumentationMapper = new PhotoDocumentationMapper(userRepository);
    }

    @Override
    public Collection<PhotoDocumentation> getAll() {
        QueryBuilder<PhotoDocumentation> queryBuilder = new QueryBuilder<>(PhotoDocumentation.class, "photo_doc")
                .withRowMapper(photoDocumentationMapper);

        try (DBConnection dbConnection = new DBConnection()) {
            return queryBuilder.executeSelect(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PhotoDocumentation> getById(long id) {
        QueryBuilder<PhotoDocumentation> queryBuilder = new QueryBuilder<>(PhotoDocumentation.class, "photo_doc")
                .withRowMapper(photoDocumentationMapper)
                .where("ID", id);
        try (DBConnection dbConnection = new DBConnection()) {
            Collection<PhotoDocumentation> pd = queryBuilder.executeSelect(dbConnection.getConnection());
            if(pd.iterator().hasNext()) {
                return Optional.of(pd.iterator().next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public PhotoDocumentation add(PhotoDocumentation entity) {
        QueryBuilder<PhotoDocumentation> queryBuilder = new QueryBuilder<>(PhotoDocumentation.class, "photo_doc")
                .set("date", entity.getDateTime().toString())
                .set("status", Status.PENDING.toString())
                .set("orderNumber", entity.getOrderNumber())
                .set("operatorID", entity.getUser().getID());
//                .set("user_id", entity.getUser().getID());

        try (DBConnection dbConnection = new DBConnection()) {
            queryBuilder.executeInsert(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    @Override
    public boolean delete(PhotoDocumentation entity) {
        return false;
    }

    @Override
    public PhotoDocumentation update(PhotoDocumentation newEntity) {
        QueryBuilder<PhotoDocumentation> queryBuilder = new QueryBuilder<>(PhotoDocumentation.class, "photo_doc")
                .set("date", newEntity.getDateTime().toString())
                .set("status", newEntity.getStatus().toString())
                .where("id", newEntity.getId());
        try (DBConnection dbConnection = new DBConnection()) {
            queryBuilder.executeUpdate(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return newEntity;
    }

    @Override
    public Log addLog(User user, PhotoDocumentation newEntity) {
        System.out.println("Adding log ...");
        Log log = new Log();
        LocalDateTime now = LocalDateTime.now();
        String orderNumber = newEntity.getOrderNumber();
        ActionType actionType;
        update(newEntity);
        switch (newEntity.getStatus()) {
            case PENDING:
                actionType = ActionType.SUBMISSION;
                break;
            case APPROVED:
                actionType = ActionType.APPROVAL;
                break;
            case REJECTED:
                actionType = ActionType.REJECTION;
                break;
            default:
                return null;
        }

        QueryBuilder<Log> queryBuilder = new QueryBuilder<>(Log.class, "Log")
                .set("date", now.toString())
                .set("action_type", actionType.toString())
                .set("order_number", orderNumber)
                .set("user_id", user.getId());
        try (DBConnection dbConnection = new DBConnection()) {
            queryBuilder.executeInsert(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.setDateTime(now);
        log.setOrderNumber(orderNumber);
        log.setActionType(actionType);
        log.setUser(new UserModel(user));
        return log;
    }

    @Override
    public Collection<Log> getAllLogs() {
        QueryBuilder<Log> queryBuilder = new QueryBuilder<>(Log.class, "Log")
                .withRowMapper(logMapper);

        try (DBConnection dbConnection = new DBConnection()) {
            return queryBuilder.executeSelect(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPhoto(byte[] data, long documentation_id, String side, String info) {
        QueryBuilder<Photo> queryBuilder = new QueryBuilder<>(Photo.class, "photo_item")
                .set("documentation_id", documentation_id)
                .set("data", data)
                .set("info", info)
                .set("side", side);
        try (DBConnection dbConnection = new DBConnection()) {
            queryBuilder.executeInsert(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PhotoDocumentation> getByOrderNumber(String orderNumber) {
        QueryBuilder<PhotoDocumentation> queryBuilder = new QueryBuilder<>(PhotoDocumentation.class, "photo_doc")
                .withRowMapper(photoDocumentationMapper)
                .where("orderNumber", orderNumber);
        try (DBConnection dbConnection = new DBConnection()) {
            Collection<PhotoDocumentation> pd = queryBuilder.executeSelect(dbConnection.getConnection());
            if(pd.iterator().hasNext()) {
                return Optional.of(pd.iterator().next());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Collection<Photo> getAllImagesByDocumentationId(long id) {
        QueryBuilder<Photo> queryBuilder = new QueryBuilder<>(Photo.class, "photo_item")
                .withRowMapper(photoMapper)
                .where("documentation_id", id);
        try (DBConnection dbConnection = new DBConnection()) {
            return queryBuilder.executeSelect(dbConnection.getConnection());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}