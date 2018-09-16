package benjamin.thiebaut.fr.warframealertnotifier.BO;

import java.util.ArrayList;
import java.util.List;

public class Alert {
    private String id;
    private String activation;
    private String expiry;
    private List<String> rewards;
    private String credits;
    private String boss;
    private String faction;
    private boolean expired;
    private boolean isNightMare;
    private String eta;
    private String node;
    private String type;
    private String maxLevel;


    public Alert(){
        rewards = new ArrayList<>();
    }

    public Alert(String id, String activation, String expiry, List<String> rewards, String credits, String boss, String faction, boolean expired, boolean isNightMare, String eta, String node, String type, String maxLevel) {
        this();
        this.id = id;
        this.activation = activation;
        this.expiry = expiry;
        this.rewards = rewards;
        this.credits = credits;
        this.boss = boss;
        this.faction = faction;
        this.expired = expired;
        this.isNightMare = isNightMare;
        this.eta = eta;
        this.node = node;
        this.type = type;
        this.maxLevel = maxLevel;
    }

    public Alert(String id, String activation, String expiry, boolean expired, String eta) {
        this();
        this.id = id;
        this.activation = activation;
        this.expiry = expiry;
        this.expired = expired;
        this.eta = eta;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivation() {
        return activation;
    }

    public void setActivation(String activation) {
        this.activation = activation;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public boolean isNightMare() {
        return isNightMare;
    }

    public void setNightMare(boolean nightMare) {
        isNightMare = nightMare;
    }

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public List<String> getRewards() {
        return rewards;
    }

    public void setRewards(List<String> rewards) {
        this.rewards = rewards;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(String maxLevel) {
        this.maxLevel = maxLevel;
    }

    public enum Fields{
        ID("id"),
        ACTIVATION("activation"),
        EXPIRY("expiry"),
        REWARDS("reward"),
        VARIANTS("variants"),
        BOSS("boss"),
        FACTION("faction"),
        EXPIRED("expired"),
        MISSION("mission"),
        ITEMS("items"),
        CREDITS("credits"),
        LEVEL("maxEnemyLevel"),
        NODE("node"),
        TYPE("type"),
        NIGHTMARE("nightmare"),
        ETA("eta");
        private String friendlyName;

        Fields(String friendlyName) {
            this.friendlyName = friendlyName;
        }

        @Override
        public String toString() {
            return this.friendlyName;
        }
    }

}
