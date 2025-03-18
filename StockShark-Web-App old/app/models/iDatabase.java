public interface iDatabase {
    void saveStockData (String stockSymbol, Object data);

    void saveNotification(String message);
}