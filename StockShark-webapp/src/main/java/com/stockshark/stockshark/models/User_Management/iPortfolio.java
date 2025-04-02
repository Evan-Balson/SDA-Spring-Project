package com.stockshark.stockshark.models.User_Management;

import java.util.List;
import java.util.Map;

public interface iPortfolio {
    Map<String, List<String>> getPortfolio(String porfolioID);
    void updatePortfolio(int userId, Portfolio portfolio);
    void removePortfolio(int userId, Portfolio portfolio);

}
