package eci.ieti.data;

import eci.ieti.data.model.Todo;
import eci.ieti.data.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo,Long> {
    Page<Todo> findByResponsible(User responsible, Pageable pageable);
}