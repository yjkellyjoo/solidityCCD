function mintTokens(address _investor) external onlyOwner {
        balances[_investor] +=  promoValue;
        totalSupply += promoValue;
        Transfer(0x0, _investor, promoValue);
        
    }