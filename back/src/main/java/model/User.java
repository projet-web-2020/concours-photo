package model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    public final Integer id;
    public final String username;
    public final Integer userlevel;
    public final Integer victories;
    public final Integer score;
    public final Integer theme_score;
    public final Integer participations;
    public final Integer theme_participations;
    public final Integer rank;
    public final String photo;
    public final String photoDelete;
    public final Integer theme;

    public final Map<SettingName, UserSetting> settings;

    public User() {
        this(null, new HashMap<>(), null, null, null, null, null, null, null, null, null, null, null);
    }

    public User(String username, Map<SettingName, UserSetting> settings, Integer victories, Integer score, Integer theme_score, Integer userlevel, Integer participations, Integer theme_participations, String photo, String photoDelete, Integer theme, Integer rank, Integer id) {
        this.id = id;
        this.username = username;
        this.settings = settings;
        this.userlevel = userlevel;
        this.victories = victories;
        this.score = score;
        this.theme_score = theme_score;
        this.photo = photo;
        this.rank = rank;
        this.photoDelete = photoDelete;
        this.participations = participations;
        this.theme_participations = theme_participations;
        this.theme = theme;
    }

    public User(String username, Map<SettingName, UserSetting> settings, Integer victories, Integer score, Integer theme_score, Integer userlevel) {
        this(username, settings, userlevel, victories, score, theme_score, null, null, null, null, null, null, null);
    }

    public User(String username) {
        this(username, new HashMap<>(), 0, 0, 0, 0, null, null, null, null, null, null, null);
    }

    public UserPublic getPublicProfile() {
        HashMap<SettingName, UserSetting> publicSettings = new HashMap<>();
        
        this.settings.forEach((setting, userSetting) -> { 
            if (userSetting.isPublic)
                publicSettings.put(setting, userSetting);
        });

        return new UserPublic(this.username, publicSettings, this.victories, this.score, this.theme_score, this.participations, this.theme_participations, this.photo, this.rank, this.id);
    }
}

