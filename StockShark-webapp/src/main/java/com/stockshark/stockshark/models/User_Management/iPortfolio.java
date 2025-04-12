package com.stockshark.stockshark.models.User_Management;

import com.oracle.wls.shaded.java_cup.runtime.Symbol;

import java.util.List;
import java.util.Map;

public interface iPortfolio {
    Map<String, List<String>> getPortfolio(String porfolioID);
    Boolean updatePortfolio(String email, String symbolA, String SymbolB);
    Boolean removePortfolio(String user, Portfolio portfolio);

}
