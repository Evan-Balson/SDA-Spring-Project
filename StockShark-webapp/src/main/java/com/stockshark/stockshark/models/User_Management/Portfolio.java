package com.stockshark.stockshark.models.User_Management;

import java.util.List;
import java.util.Map;

public class Portfolio {

    // create a variable using the component that has the required interface.
    private UserService user;

    public Portfolio(UserService user) {
        this.user = user;

    }

    // create methods that can user the provided service
    // get portfolio items client side
    public Map<String, List<String>> getPortfolio(String portfilioID) {
        return user.getPortfolio(portfilioID);
    }

    // update the portfolio with new items
    public void updatePortfolio(int userId, Portfolio portfolio) {
        user.updatePortfolio(userId, portfolio);
    }

    // remove an entry from the portfolio
    public void removePortfolio(int userId, Portfolio portfolio) {
        user.removePortfolio(userId, portfolio);
    }
}
