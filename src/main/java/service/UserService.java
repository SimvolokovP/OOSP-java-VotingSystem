package service;

import dao.Repository;
import model.Candidate;
import model.User;

public class UserService {
    private final Repository repository;


    public UserService(Repository repository) {
        this.repository = repository;
    }

    public User authByLogin(String login) {
        return repository.findUserByLogin(login);
    }
    public void createNewUser(User user) {
        repository.regUser(user);
    }

    public void toVote(int userId, int candidateId) {
        repository.toVote(userId, candidateId);
    }

}
