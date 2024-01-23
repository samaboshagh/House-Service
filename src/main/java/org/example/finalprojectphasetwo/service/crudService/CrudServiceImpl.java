//package org.example.finalprojectphasetwo.service.crudService;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.Optional;
//
//@RequiredArgsConstructor
//public class CrudServiceImpl<T , ID extends Serializable>
//        implements CrudService<T, ID> {
//
//    protected final JpaRepository<T,ID> repository;
//
//    @Override
//    public void save(T entity) {
//        repository.save(entity);
//    }
//
//    @Override
//    public void deleteById(ID id) {
//        repository.deleteById(id);
//    }
//
//    @Override
//    public Optional<T> findById(ID id) {
//        return repository.findById(id);
//    }
//
//    @Override
//    public Collection<T> findAll() {
//        return repository.findAll();
//    }
//
//    @Override
//    public boolean existsById(ID id) {
//        return repository.existsById(id);
//    }
//
//    @Override
//    public long count() {
//        return repository.count();
//    }
//
//}