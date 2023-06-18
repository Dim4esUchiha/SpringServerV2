package org.dim4es.springserver.repository.criteria;

import org.dim4es.springserver.model.Message;
import org.dim4es.springserver.model.Message_;
import org.dim4es.springserver.model.User_;
import org.dim4es.springserver.model.chat.Chat_;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class MessageRepositoryImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Message save(Message message) {
        entityManager.persist(message);
        return message;
    }

    @Override
    public Optional<Message> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Message.class, id));
    }

    @Override
    public List<Message> getByChatIdAndAfterTimestamp(Long chatId, Instant timestamp, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);

        Root<Message> root = criteriaQuery.from(Message.class);

        Predicate predicate = createWherePredicate(root, criteriaBuilder, chatId, timestamp);
        criteriaQuery.select(root).where(predicate).orderBy(criteriaBuilder.asc(root.get(Message_.TIMESTAMP)));

        TypedQuery<Message> query = entityManager.createQuery(criteriaQuery);
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        query.setFirstResult(offset);
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    @Override
    public long countByChatIdAndAfterTimestamp(Long chatId, Instant timestamp) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);

        Root<Message> root = criteriaQuery.from(Message.class);

        Predicate predicate = createWherePredicate(root, criteriaBuilder, chatId, timestamp);
        criteriaQuery.select(criteriaBuilder.count(root)).where(predicate);

        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public long countByChatIdAndUserIdAndAfterTimestamp(Long chatId, Long fromUserId, Instant timestamp) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);

        Root<Message> root = criteriaQuery.from(Message.class);

        Predicate chatAndUserPredicate = cb.and(cb.equal(root.get(Message_.CHAT).get(Chat_.ID), chatId),
                cb.equal(root.get(Message_.FROM_USER).get(User_.ID), fromUserId));
        Predicate queryPredicate;
        if (timestamp != null) {
            queryPredicate = cb.and(chatAndUserPredicate, cb.greaterThan(root.get(Message_.TIMESTAMP), timestamp));
        } else {
            queryPredicate = chatAndUserPredicate;
        }
        criteriaQuery.select(cb.count(root)).where(queryPredicate);
        TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);
        return query.getSingleResult();
    }

    @Override
    public Optional<Message> findLastChatMessage(Long chatId) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);

        Root<Message> root = criteriaQuery.from(Message.class);

        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get(Message_.CHAT).get(Chat_.ID), chatId))
                .orderBy(criteriaBuilder.desc(root.get(Message_.TIMESTAMP)));
        TypedQuery<Message> query = entityManager.createQuery(criteriaQuery);
        query.setMaxResults(1);
        query.setFirstResult(0);

        List<Message> messages = query.getResultList();
        return messages.isEmpty() ? Optional.empty() : Optional.of(messages.get(0));
    }

    private Predicate createWherePredicate(Root<Message> root, CriteriaBuilder criteriaBuilder,
                                           Long chatId, Instant timestamp) {
        Predicate chatIdEqual = criteriaBuilder.equal(root.get(Message_.CHAT).get(Chat_.ID), chatId);
        Predicate queryPredicate;
        if (timestamp != null) {
            queryPredicate = criteriaBuilder.and(chatIdEqual, criteriaBuilder
                    .greaterThan(root.get(Message_.TIMESTAMP), timestamp));
        } else {
            queryPredicate = chatIdEqual;
        }
        return queryPredicate;
    }
}
