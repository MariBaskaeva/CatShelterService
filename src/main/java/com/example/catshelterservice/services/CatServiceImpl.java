package com.example.catshelterservice.services;

import com.example.catshelterservice.dto.CatCreateDTO;
import com.example.catshelterservice.dto.CatDTO;
import com.example.catshelterservice.exceptions.FailedDownloadingException;
import com.example.catshelterservice.exceptions.FailedSavingException;
import com.example.catshelterservice.exceptions.NoSuchCatException;
import com.example.catshelterservice.models.Cat;
import com.example.catshelterservice.models.User;
import com.example.catshelterservice.repositories.CatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
public class CatServiceImpl implements CatService{
    @Value("${upload.path}")
    private String PATH;
    @Autowired
    private CatRepository catRepository;
    @Autowired
    private CatToCatDTOMapper mapper;

    @Override
    public Page<CatDTO> getCards(String search, int page, int limit, User user) {
        Page<Cat> pageCat = catRepository.findAll(PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, search)));

        return pageCat.map(cat -> {
            byte[] image = null;
            try {
                image = getImage(cat.getId());
            } catch (FailedDownloadingException e) {
                e.printStackTrace();
            }
            return mapper.catToDTO(cat, user, image);
        });
    }

    public byte[] getImage(Long id) throws FailedDownloadingException {
        Path path = Paths.get(PATH, id.toString());
        byte[] mas;

        try {
            mas = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new FailedDownloadingException();
        }
        return mas;
    }

    @Override
    public Cat createCat(CatCreateDTO cat) throws FailedSavingException {;
        Cat newCat = catRepository.save(new Cat(cat.getName(), cat.getAge(), cat.getColor(),
                                    cat.getSex(), cat.getVaccinations(), cat.getDescription()));

        try {
            Files.createDirectories(Paths.get(PATH));
            Path path = Paths.get(PATH, newCat.getId().toString());
            Files.write(path, cat.getImage(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new FailedSavingException();
        }
        return catRepository.save(newCat);
    }

    @Override
    public Cat getCat(long id) throws NoSuchCatException {
        Optional<Cat> catOpt = catRepository.getCatById(id);
        if(catOpt.isEmpty())
            throw new NoSuchCatException();

        return catOpt.get();
    }

    @Override
    public void removeCat(long id) {
        catRepository.deleteById(id);
    }

    @Override
    public Cat updateCat(long id, Cat cat) {
        cat.setId(id);
        return catRepository.save(cat);
    }

    public void likeCat(long catId, User user) throws NoSuchCatException {
        Optional<Cat> catOpt = catRepository.getCatById(catId);

        if(catOpt.isEmpty()){
            throw new NoSuchCatException();
        }
        Cat cat = catOpt.get();
        cat.getUsers().add(user);

        catRepository.save(cat);
    }
    public void dislikeCat(long catId, User user) throws NoSuchCatException {
        Optional<Cat> catOpt = catRepository.getCatById(catId);

        if(catOpt.isEmpty()){
            throw new NoSuchCatException();
        }
        Cat cat = catOpt.get();
        cat.getUsers().remove(user);

        catRepository.save(cat);
    }

    public long getLikesForCat(long catId) throws NoSuchCatException {
        Optional<Cat> cat = catRepository.getCatById(catId);
        if(cat.isEmpty())
            throw new NoSuchCatException();

        return cat.get().getUsers().size();
    }
}
