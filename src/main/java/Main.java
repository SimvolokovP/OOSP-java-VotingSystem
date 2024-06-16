import dao.DBRepository;
import model.Candidate;
import model.User;
import service.AdminService;
import model.CIK;
import service.CIKService;
import service.UserService;
import utils.DBManager;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static String candidatsToList(List<Candidate> candidates) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < candidates.size(); i++) {
            Candidate candidate = candidates.get(i);
            sb.append(i+1).append(". ").append(candidate.getName()).append(" (").append(candidate.getParty()).append(")\n");
        }
        return sb.toString();
    }

    public static int getVote(int numb, List<Candidate> candidates) {
        if (numb >= 1 && numb <= candidates.size()) {
            return candidates.get(numb - 1).getId();
        } else {
            System.out.println("Некорректный номер кандидата.");
            return -1;
        }
    }

    public static void main(String[] args) {
        DBManager manager = new DBManager();
        AdminService admin = new AdminService(new DBRepository(manager.getConnection()));
        CIKService cikService = new CIKService(new DBRepository(manager.getConnection()));
        UserService userService = new UserService(new DBRepository(manager.getConnection()));
        Scanner scanner = new Scanner(System.in);


        int userType;
        do {
            System.out.println("Выберите тип пользователя:");
            System.out.println("1. Администратор");
            System.out.println("2. Член ЦИК");
            System.out.println("3. Кандидат");
            System.out.println("4. Пользователь");
            System.out.println("0. Выход");

            userType = scanner.nextInt();

            switch (userType) {
                case 1:
                    int adminAction;
                    do {
                        System.out.println("Выберите действие:");
                        System.out.println("1. Добавить ЦИК");
                        System.out.println("2. Список ЦИКов");
                        System.out.println("3. Список пользователей");
                        System.out.println("4. Удалить пользователя по номеру в системе");
                        System.out.println("5. Список кандидотов");
                        System.out.println("6. Удалить кандитата по номеру в системе");
                        System.out.println("7. Удалить цик по номеру в системе");


                        adminAction = scanner.nextInt();
                        switch (adminAction) {
                            case 1:
                                System.out.println("Введите название ЦИК:");
                                String cikName = scanner.next();
                                CIK cik = new CIK(cikName);
                                admin.createNewCIK(cik);
                                break;
                            case 2:
                                System.out.println(admin.getCIKList());
                                break;
                            case 3:
                                System.out.println(admin.getUsersList());
                                break;
                            case 4:
                                System.out.println("Введите номер пользователя:");
                                int userId = scanner.nextInt();
                                admin.removeUser(userId);
                                break;
                            case 5:
                                System.out.println(admin.getCandidatsList());
                                break;
                            case 6:
                                System.out.println("Введите номер кандидата:");
                                int candidateId = scanner.nextInt();
                                admin.removeCandidate(candidateId);
                                break;
                            case 7:
                                System.out.println("Введите номер цика:");
                                int cikId = scanner.nextInt();
                                admin.removeCIK(cikId);
                                break;
                            default:
                                System.out.println("Некорректное действие для админа.");
                        }
                    } while (adminAction != 0);
                    break;
                case 2:
                    int cikAction;
                    do {
                        System.out.println("Выберите действие:");
                        System.out.println("1. Добавить Кандитата");
                        System.out.println("2. Получить pdf рез-тов выборов: ");
                        System.out.println("0. Выход");

                        cikAction = scanner.nextInt();
                        switch (cikAction) {
                            case 1:
                                System.out.println("Введите имя кандидата:");
                                String candidateName = scanner.next();
                                System.out.println("Введите партию кандидата:");
                                String candidateParty = scanner.next();
                                Candidate candidate = new Candidate(candidateName, candidateParty);
                                cikService.createNewCandidate(candidate);
                                break;
                            case 2:
                                System.out.println(cikService.getVotesList());
                                cikService.generatePDF(cikService.getVotesList(), "results2.pdf");
                            case 0:
                                break;
                            default:
                                System.out.println("Некорректное действие для ЦИК.");
                        }
                    } while (cikAction != 0);
                    break;
                case 3:

                    break;
                case 4:
                    int userAction;
                    User loggedInUser = null;
                    boolean isLoggedIn = false;

                    do {
                        if (!isLoggedIn) {
                            System.out.println("Выберите действие:");
                            System.out.println("1. Авторизироваться");
                            System.out.println("2. Зарегистрироваться");
                            System.out.println("0. Выход");
                        } else {
                            System.out.println("Доступные действия для пользователя:");
                            System.out.println("1. Проголосовать");
                            System.out.println("2. Посмотреть список кандидатов");
                            System.out.println("3. Посмотреть информацию о себе");
                            System.out.println("0. Выход");
                        }

                        userAction = scanner.nextInt();
                        switch (userAction) {
                            case 1:
                                if (!isLoggedIn) {
                                    System.out.println("Введите логин: ");
                                    String userLogin = scanner.next();
                                    System.out.println("Введите пароль: ");
                                    String userPassword = scanner.next();
                                    User user = userService.authByLogin(userLogin);

                                    if (Objects.equals(user.getPassword(), userPassword)) {
                                        loggedInUser = user;
                                        isLoggedIn = true;
                                        System.out.println(loggedInUser);
                                    } else {
                                        System.out.println("Invalid login or password");
                                    }

                                } else {
                                    if (loggedInUser.isVote()) {
                                        System.out.println("Вы уже голосовали!");
                                        break;
                                    } else {
                                        System.out.println("Список кандидатов");
                                        System.out.println(candidatsToList(admin.getCandidatsList()));
                                        System.out.println("Введите номер кандидата: ");
                                        int numb = scanner.nextInt();
                                        int vote = getVote(numb, admin.getCandidatsList());
                                        userService.toVote(loggedInUser.getId(), vote);
                                        System.out.println("Вы успешно проголосовали");
                                        loggedInUser = null;
                                        isLoggedIn = false;
                                        userAction = 0;
                                        break;

                                    }
                                }
                                break;
                            case 2:
                                if (!isLoggedIn) {
                                    System.out.println("Введите логин: ");
                                    String _userLogin = scanner.next();
                                    System.out.println("Введите пароль: ");
                                    String _userPassword = scanner.next();
                                    System.out.println("Введите имя: ");
                                    String _userName = scanner.next();
                                    System.out.println("Введите номер снилс: ");
                                    String _userSnils = scanner.next();
                                    User user1 = new User(_userLogin, _userPassword, _userName, _userSnils, false);
                                    userService.createNewUser(user1);
                                    System.out.println("Success!");
                                    break;
                                } else {
                                    System.out.println("Список кандидатов");
                                    System.out.println(candidatsToList(admin.getCandidatsList()));
                                }
                                break;
                            case 3:
                                if (isLoggedIn) {
                                    System.out.println(loggedInUser);
                                } else {
                                    System.out.println("Некорректное действие для пользователя.");
                                }
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Некорректное действие.");
                        }
                    } while (userAction != 0);
                    break;
            }
        } while (userType != 0);

        scanner.close();
    }
}