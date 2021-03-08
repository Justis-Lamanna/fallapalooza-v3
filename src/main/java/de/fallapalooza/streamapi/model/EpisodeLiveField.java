package de.fallapalooza.streamapi.model;

public class EpisodeLiveField {
    private static final String[] NAMES = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    private final String classes;
    private final String liveUrl;

    public EpisodeLiveField(int player, int round, int episode) {
        String playerAsWord = "player_" + NAMES[player];
        String roundAsWord = "round_" + NAMES[round];
        String episodeAsWord = "episode_" + NAMES[episode];
        this.classes = String.format("%s %s %s", playerAsWord, roundAsWord, episodeAsWord);
        this.liveUrl = String.format("/stream/team/display/{displayNum}/round/%d/player/%d/episode/%d", round, player, episode);
    }

    public EpisodeLiveField(int player, int episode) {
        String playerAsWord = "player_" + NAMES[player];
        String roundAsWord = "round_current";
        String episodeAsWord = "episode_" + NAMES[episode];
        this.classes = String.format("%s %s %s", playerAsWord, roundAsWord, episodeAsWord);
        this.liveUrl = String.format("/stream/team/display/{displayNum}/round/current/player/%d/episode/%d", player, episode);
    }

    public String getClasses() {
        return classes;
    }

    public String getLiveUrl() {
        return liveUrl;
    }

    @Override
    public String toString() {
        return classes;
    }
}
