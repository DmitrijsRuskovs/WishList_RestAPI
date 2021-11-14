package com.dmitrijs.wishlist.Service;
import java.util.ArrayList;
import java.util.List;

import com.dmitrijs.wishlist.Exceptions.BadResourceException;
import com.dmitrijs.wishlist.Exceptions.ResourceAlreadyExistsException;
import com.dmitrijs.wishlist.Exceptions.ResourceNotFoundException;
import com.dmitrijs.wishlist.Model.Wish;
import com.dmitrijs.wishlist.Repository.WishRepository;
import com.dmitrijs.wishlist.specification.WishSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class WishService {

    @Autowired
    private WishRepository _wishRepository;

    private boolean existsById(Long id) {
        return _wishRepository.existsById(id);
    }

    public Wish findById(Long id) throws ResourceNotFoundException {
        Wish wish = _wishRepository.findById(id).orElse(null);
        if (wish == null) {
            throw new ResourceNotFoundException("Cannot find Wish with id: " + id);
        }
        else return wish;
    }

    public List<Wish> findAll(int pageNumber, int rowPerPage) {
        List<Wish> wishes = new ArrayList<>();
        _wishRepository.findAll(PageRequest.of(pageNumber - 1, rowPerPage)).forEach(wishes::add);
        return wishes;
    }

    public List<Wish> findAllByMatter(String matter, int pageNumber, int rowPerPage) {
        Wish filter = new Wish();
        filter.setMatter(matter);
        Specification<Wish> spec = new WishSpecification(filter);

        List<Wish> wishes = new ArrayList<>();
        _wishRepository.findAll(spec, PageRequest.of(pageNumber - 1, rowPerPage)).forEach(wishes::add);
        return wishes;
    }

    public Wish save(Wish wish) throws BadResourceException, ResourceAlreadyExistsException {
        if (wish.Valid()) {
            if (wish.getId() != null && existsById(wish.getId())) {
                throw new ResourceAlreadyExistsException("Wish with id: " + wish.getId() +
                        " already exists");
            }
            return _wishRepository.save(wish);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save wish");
            exc.addErrorMessage("Wish is null or empty");
            throw exc;
        }
    }

    public void update(Wish wish)
            throws BadResourceException, ResourceNotFoundException {
        if (!StringUtils.isEmpty(wish.getMatter())) {
            if (!existsById(wish.getId())) {
                throw new ResourceNotFoundException("Cannot find Wish with id: " + wish.getId());
            }
            _wishRepository.save(wish);
        }
        else {
            BadResourceException exc = new BadResourceException("Failed to save wish");
            exc.addErrorMessage("Wish is null or empty");
            throw exc;
        }
    }

    public void deleteById(Long id) throws ResourceNotFoundException {
        if (!existsById(id)) {
            throw new ResourceNotFoundException("Cannot find wish with id: " + id);
        }
        else {
            _wishRepository.deleteById(id);
        }
    }

    public Long count() {
        return _wishRepository.count();
    }
}

