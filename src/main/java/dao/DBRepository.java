package dao;

import model.CIK;
import model.Candidate;
import model.User;
import model.Vote;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBRepository implements Repository{
    private Connection connection;

    public DBRepository(Connection connection) {
        this.connection = connection;
    }
    @Override
    public User findUser(int id) {
        return null;
    }

    @Override
    public User findUserByLogin(String login) {
        String query = "select * from votingsystem.users WHERE login = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = new User(0, "", "", "", "", false);
            while (resultSet.next()) {
                int _id = resultSet.getInt("id");
                String _name = resultSet.getString("name");
                String _login = resultSet.getString("login");
                String _password = resultSet.getString("password");
                String _snils = resultSet.getString("snils");
                boolean _isVote = resultSet.getBoolean("isVote");
                user.setId(_id);
                user.setName(_name);
                user.setLogin(_login);
                user.setPassword(_password);
                user.setSnils(_snils);
                user.setVote(_isVote);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUser(int id) {
        try(Statement statement = connection.createStatement();) {
            statement.execute("DELETE FROM votingsystem.users WHERE id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void regUser(User user) {
        String query = "INSERT into votingsystem.users(name, login, password, snils, isVote) VALUES (?,?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getLogin());
            preparedStatement.setString(3,user.getPassword());
            preparedStatement.setString(4,user.getSnils());
            preparedStatement.setBoolean(5,user.isVote());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);}
    }

    @Override
    public CIK findCIK(int id) {
        String query = "select * from votingsystem.cik WHERE id = " + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery(query);
            CIK cik = new CIK(0, "");
            while (resultSet.next()) {
                int _id = resultSet.getInt("id");
                String _name = resultSet.getString("name");
                cik.setId(_id);
                cik.setName(_name);
            }
            return cik;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CIK> getCIKList() {
        String query = "select * from votingsystem.cik";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<CIK> list = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                list.add(new CIK(id, name));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCIK(int id) {
        try(Statement statement = connection.createStatement();) {
            statement.execute("DELETE FROM votingsystem.cik WHERE id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createNewCIK(CIK cik) {
        String query = "INSERT into votingsystem.cik(name) VALUES (?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1,cik.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);}
    }

    @Override
    public Candidate findCandidate(int id) {
        return null;
    }

    @Override
    public List<Candidate> getCandidatsList() {
        String query = "select * from votingsystem.candidats";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Candidate> list = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String party = resultSet.getString("party");
                list.add(new Candidate(id, name, party));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeCandidate(int id) {
        try(Statement statement = connection.createStatement();) {
            statement.execute("DELETE FROM votingsystem.candidats WHERE id=" + id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getUsers() {
        String query = "select * from votingsystem.users";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String snils = resultSet.getString("snils");
                boolean isVote = resultSet.getBoolean("isVote");
                list.add(new User(id, login, password, name, snils, isVote));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createNewCandidate(Candidate candidate) {
        String query = "INSERT into votingsystem.candidats(name, party) VALUES (?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {
            preparedStatement.setString(1,candidate.getName());
            preparedStatement.setString(2,candidate.getParty());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);}
    }

    @Override
    public void toVote(int userId, int candidateId) {
        String query = "INSERT into votingsystem.votes(candidate_id, user_id) VALUES (?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1,candidateId);
            preparedStatement.setInt(2,userId);
            saveUserStatus(userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);}
    }

    @Override
    public void saveUserStatus(int id) {
        String query = "UPDATE votingsystem.users SET isVote= ?  WHERE ID=?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query);) {

            preparedStatement.setBoolean(1,true);
            preparedStatement.setDouble(2,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);}
    }

    @Override
    public List<Vote> getVotesList() {
        String query = "select * from votingsystem.votes";
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            List<Vote> list = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int candidateId = resultSet.getInt("candidate_id");
                int userId = resultSet.getInt("user_id");
                list.add(new Vote(id, candidateId, userId));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
