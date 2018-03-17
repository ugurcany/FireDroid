package blog.ugurcan.firedroid.db;

/**
 * Created by ugurcan on 3.03.2018.
 */
public class SubscriptionConfig {

    public enum LimitType {
        FIRST, LAST
    }

    public enum OrderType {
        KEY, VALUE, CHILD,
    }

    private LimitType limitType;
    private Integer limit;

    private OrderType orderType;
    private String orderByChildKey;

    SubscriptionConfig(SubscriptionConfig.Builder builder) {
        limitType = builder.limitType;
        limit = builder.limit;
        orderType = builder.orderType;
        orderByChildKey = builder.orderByChildKey;
    }

    public LimitType getLimitType() {
        return limitType;
    }

    public Integer getLimit() {
        return limit;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public String getOrderByChildKey() {
        return orderByChildKey;
    }


    public static class Builder {

        private LimitType limitType = null;
        private Integer limit;

        private OrderType orderType = null;
        private String orderByChildKey = "";

        public Builder limitTo(LimitType limitType, int limit) {
            this.limitType = limitType;
            this.limit = limit;
            return this;
        }

        public Builder orderBy(OrderType orderType, String... orderByChildKey) {
            this.orderType = orderType;
            if (orderByChildKey.length > 0)
                this.orderByChildKey = orderByChildKey[0];
            return this;
        }

        public SubscriptionConfig build() {
            return new SubscriptionConfig(this);
        }
    }

}
