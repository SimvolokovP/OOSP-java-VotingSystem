package service;

import dao.Repository;
import model.CIK;
import model.Candidate;
import model.User;

import java.util.List;

public class AdminService {
    private final Repository repository;


    public AdminService(Repository repository) {
        this.repository = repository;
    }

    public CIK getCIK(int id) {
        return repository.findCIK(id);
    }

    public void createNewCIK(CIK cik) {
        repository.createNewCIK(cik);
    }

    public List<CIK> getCIKList() {
        return repository.getCIKList();
    }

    public void removeUser(int id) {
        repository.removeUser(id);
    }

    public List<User> getUsersList() {
        return repository.getUsers();
    }

    public List<Candidate> getCandidatsList() {
        return repository.getCandidatsList();
    }

    public void removeCandidate(int id) {
        repository.removeCandidate(id);
    }

    public void removeCIK(int id) {
        repository.removeCIK(id);
    }
}
