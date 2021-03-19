function mintToken(uint256 mintedAmount) onlyOwner public {
        balanceOf[owner] += mintedAmount;
        totalSupply += mintedAmount;
        Transfer(0, this, mintedAmount);
        Transfer(this, owner, mintedAmount);
    }