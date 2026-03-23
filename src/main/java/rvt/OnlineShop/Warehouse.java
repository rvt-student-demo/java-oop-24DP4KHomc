package rvt.OnlineShop;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class Warehouse {
    private Map<String, Integer> prices;
    private Map<String, Integer> stocks;

    public Warehouse() {
        this.prices = new HashMap<>();
        this.stocks = new HashMap<>();
    }

    public void addProduct(String product, int price, int stock) {
        prices.put(product, price);
        stocks.put(product, stock);
    }

    public int price(String product) {
        return prices.getOrDefault(product, -99);
    }
    
    public int stock(String product) {
        return stocks.getOrDefault(product, 0);
    }

    public boolean take(String product) {
        if (!stocks.containsKey(product) || stocks.get(product) <= 0) {
            return false;
        }

        stocks.put(product, stocks.get(product) - 1);
            return true;
    }

    public Set<String> products() {
        return prices.keySet();
    }
}
