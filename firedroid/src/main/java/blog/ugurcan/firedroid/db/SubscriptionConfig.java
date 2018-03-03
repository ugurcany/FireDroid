package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 3.03.2018.
 */
public class SubscriptionConfig {

    private Integer limitToFirst;
    private Integer limitToLast;
    private String orderByChildPath;
    private Boolean orderByKey;

    SubscriptionConfig(SubscriptionConfig.Builder builder) {
        limitToFirst = builder.limitToFirst;
        limitToLast = builder.limitToLast;
        orderByChildPath = builder.orderByChildPath;
        orderByKey = builder.orderByKey;
    }

    public Integer getLimitToFirst() {
        return limitToFirst;
    }

    public Integer getLimitToLast() {
        return limitToLast;
    }

    public String getOrderByChildPath() {
        return orderByChildPath;
    }

    public Boolean getOrderByKey() {
        return orderByKey;
    }


    public static class Builder {

        private Integer limitToFirst;
        private Integer limitToLast;
        private String orderByChildPath;
        private Boolean orderByKey;

        public Builder limitToFirst(int limit) {
            this.limitToFirst = limit;
            return this;
        }

        public Builder limitToLast(int limit) {
            this.limitToLast = limit;
            return this;
        }

        public Builder orderByChild(String path) {
            this.orderByChildPath = path;
            return this;
        }

        public Builder orderByKey() {
            this.orderByKey = true;
            return this;
        }

        public SubscriptionConfig build() {
            return new SubscriptionConfig(this);
        }
    }

}
