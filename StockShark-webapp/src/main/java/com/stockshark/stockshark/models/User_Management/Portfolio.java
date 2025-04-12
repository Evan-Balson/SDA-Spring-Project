package com.stockshark.stockshark.models.User_Management;

import com.oracle.wls.shaded.java_cup.runtime.Symbol;
import com.stockshark.stockshark.models.CompoundCoomponents.Data_Handling_Component;
import com.stockshark.stockshark.models.Data_Handling.StockData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {

    // create a variable using the component that has the required interface.
    private UserService user;

    private int portfolioId;
    private String portfolioName;
    private String symbolA;
    private String symbolB;
    private String savedDate;
    private Map<String, StockData> stockData;
    private String symbols;




    public Portfolio(int portfolioID, String portfolioName, String symbols, String savedDate) {
        this.portfolioId = portfolioID;
        this.portfolioName = portfolioName;
        this.symbols = symbols;
        this.savedDate = savedDate;
    }


    public Portfolio(UserService user){
        this.user = user;
    }

    public Portfolio(int PortfolioID, Map<String, StockData> stockData){
        this.portfolioId = PortfolioID;
        this.stockData = new HashMap<String, StockData>();
    }


    public Map<String, List<String>> getPortfolio(String portfilioID) {
        return null;
    }

    public Boolean updatePortfolio(String SymbolA, String SymbolB,String email, Data_Handling_Component dh) {
        return dh.pushPortfolioToDatabase(email, SymbolA,SymbolB);


    }

    public Portfolio getPortfolioById(int portfolioId, Data_Handling_Component dh) {


        return dh.getPortfolioById(portfolioId);
    }

    public List<Portfolio> getPortfolioDetails(String userId, Data_Handling_Component dh) {

        return dh.getPortfolioDetails(userId);
    }

    public Boolean removePortfolio(String user, Portfolio portfolio) {
        return null;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public String getSymbols() {
        return symbols;
    }

    public String getSavedDate() {
        return savedDate;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioID=" + portfolioId +
                ", portfolioName='" + portfolioName + '\'' +
                ", symbols='" + symbols + '\'' +
                ", savedDate='" + savedDate + '\'' +
                '}';
    }

}
