import java.util.List;

public class GetFullInformationOrderModel {

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<GetOrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<GetOrderModel> orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }

    private boolean success;
       private List<GetOrderModel> orders;
        private int total;
            private int totalToday;
}
