package dao;

import model.Theme;
import model.User;

import java.util.List;
import java.util.Optional;

public interface ThemeDao {
    List<Theme> getAll() throws Exception;
    Optional<Theme> getById(int id) throws Exception;
    Optional<Theme> getCurrent() throws Exception;
    Optional<Theme> getMostVotedProposal() throws Exception;

    List<Theme> getProposals() throws Exception;
    List<Theme> getAvailableThemes() throws Exception;

    Optional<Theme> getUserProposal(User user) throws Exception;
    Optional<Theme> getUserThemeVote(User user) throws Exception;

    void setUserVote(User user, Integer themeId) throws Exception;
    Integer getNbVotes(int id) throws Exception;

    Theme insert(Theme theme) throws Exception;
    Theme setThemeState(Theme theme, String state) throws Exception;
    Theme setThemeWinner(Theme theme, User winner) throws Exception;
    void refuseCurrentProposals() throws Exception;
    void delete(int id) throws Exception;
}
