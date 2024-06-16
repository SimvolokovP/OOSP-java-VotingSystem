package dao;

import model.CIK;
import model.Candidate;
import model.User;
import model.Vote;
import service.UserService;

import java.util.List;

public interface Repository {
    User findUser(int id);
    User findUserByLogin(String login);
    void removeUser(int id);
    void regUser(User user);

    CIK findCIK(int id);
    List<CIK> getCIKList();
    void removeCIK(int id);
    void createNewCIK(CIK cik);

    Candidate findCandidate(int id);
    List<Candidate> getCandidatsList();
    void removeCandidate(int id);
    List <User> getUsers();
    void createNewCandidate(Candidate candidate);
    void toVote(int userId, int candidateId);
    void saveUserStatus(int id);
    List<Vote> getVotesList();
}