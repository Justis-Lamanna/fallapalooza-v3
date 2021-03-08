package de.fallapalooza.streamapi.model;

public enum LiveField {
    //Team-related fields
    TEAM_NAME("team name", "/stream/team/display/{displayNum}/name"),
    TEAM_ROUND_ONE_TOTAL("team round_one total", "/stream/team/display/{displayNum}/round/0/total"),
    TEAM_ROUND_TWO_TOTAL("team round_two total", "/stream/team/display/{displayNum}/round/1/total"),
    TEAM_ROUND_THREE_TOTAL("team round_three total", "/stream/team/display/{displayNum}/round/2/total"),
    TEAM_ROUND_FOUR_TOTAL("team round_four total", "/stream/team/display/{displayNum}/round/3/total"),
    TEAM_ROUND_FIVE_TOTAL("team round_five total", "/stream/team/display/{displayNum}/round/4/total"),
    TEAM_CURRENT_ROUND_TOTAL("team round_current total", "/stream/team/display/{displayNum}/round/current/total"),
    TEAM_CURRENT_ROUND_EPISODE_BADGE("team round_current total", "/stream/team/display/{displayNum}/round/current/episode-badge"),
    TEAM_CURRENT_ROUND_ROUND_BADGE("team round_current total", "/stream/team/display/{displayNum}/round/current/round-badge"),
    //Player-one fields
    PLAYER_ONE_NAME("player player_one name no_pronouns", "/stream/team/display/{displayNum}/player/0/name"),
    PLAYER_ONE_PRONOUNS("player player_one name pronouns", "/stream/team/display/{displayNum}/player/0/name?pronouns=true"),
    //Player-two fields
    PLAYER_TWO_NAME("player player_two name no_pronouns", "/stream/team/display/{displayNum}/player/1/name"),
    PLAYER_TWO_PRONOUNS("player player_two name pronouns", "/stream/team/display/{displayNum}/player/1/name?pronouns=true"),
    ;

    private final String classes;
    private final String liveUrl;

    LiveField(String classes, String liveUrl) {
        this.classes = classes;
        this.liveUrl = liveUrl;
    }

    public String getClasses() {
        return classes;
    }

    public String getLiveUrl() {
        return liveUrl;
    }
}
